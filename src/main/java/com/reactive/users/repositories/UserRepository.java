package com.reactive.users.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reactive.users.documents.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    
}
