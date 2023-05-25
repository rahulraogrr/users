package com.reactive.users.controllers;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactive.users.documents.User;
import com.reactive.users.documents.UserStatus;
import com.reactive.users.services.UserService;

import reactor.core.publisher.Mono;

@WebFluxTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserService userService;

    @Test
    void testGetUserById() {
        User user = new User("1", "john.doe", "password", "John", "M", "Doe", LocalDate.now(), "john.doe@example.com", UserStatus.ACTIVE);

        when(userService.getUserById("1")).thenReturn(Mono.just(user));

        webTestClient.get().uri("/users/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class);
    }

}
