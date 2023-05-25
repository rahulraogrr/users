package com.reactive.users.services.impl;

import org.springframework.stereotype.Service;

import com.reactive.users.documents.User;
import com.reactive.users.repositories.UserRepository;
import com.reactive.users.services.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> updateUser(String id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setMiddleName(user.getMiddleName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setDateOfBirth(user.getDateOfBirth());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setStatus(user.getStatus());
                    return userRepository.save(existingUser);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + id)));
    }

    @Override
    public Mono<Void> deleteUser(String id) {
        return userRepository.findById(id)
                .flatMap(userRepository::delete)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + id)));
    }

}
