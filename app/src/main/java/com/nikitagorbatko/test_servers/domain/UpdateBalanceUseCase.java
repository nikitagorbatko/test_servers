package com.nikitagorbatko.test_servers.domain;

import com.nikitagorbatko.test_servers.data.UserRepository;

public class UpdateBalanceUseCase {
    private UserRepository repository;

    public UpdateBalanceUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute() {
        repository.updateBalance();
    }
}
