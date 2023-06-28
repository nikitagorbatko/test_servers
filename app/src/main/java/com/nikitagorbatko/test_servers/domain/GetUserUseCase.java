package com.nikitagorbatko.test_servers.domain;

import androidx.lifecycle.LiveData;

import com.nikitagorbatko.test_servers.data.UserRepository;
import com.nikitagorbatko.test_servers.database.UserDbo;


public class GetUserUseCase {
    private final UserRepository repository;

    public GetUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public LiveData<UserDbo> execute() {
        return repository.getUser();
    }
}
