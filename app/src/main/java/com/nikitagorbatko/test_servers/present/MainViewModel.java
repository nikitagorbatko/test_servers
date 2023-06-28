package com.nikitagorbatko.test_servers.present;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.nikitagorbatko.test_servers.data.UserRepository;
import com.nikitagorbatko.test_servers.database.AppDatabase;
import com.nikitagorbatko.test_servers.database.UserDbo;
import com.nikitagorbatko.test_servers.domain.GetUserUseCase;
import com.nikitagorbatko.test_servers.domain.UpdateBalanceUseCase;

import java.util.List;


public class MainViewModel extends ViewModel {
    private final GetUserUseCase getUserUseCase;
    private final UpdateBalanceUseCase updateBalanceUseCase;

    public MainViewModel(
            GetUserUseCase getUserUseCase,
            UpdateBalanceUseCase updateBalanceUseCase
    ) {
        this.getUserUseCase = getUserUseCase;
        this.updateBalanceUseCase = updateBalanceUseCase;
    }

    public LiveData<UserDbo> getUsers() {
        return getUserUseCase.execute();
    }

    public void updateBalance() {
        updateBalanceUseCase.execute();
    }

    static ViewModelInitializer<MainViewModel> getInitializer(Context context) {
        return new ViewModelInitializer<>(
                MainViewModel.class,
                creationExtras -> {
                    AppDatabase database = AppDatabase.getDatabase(context);
                    UserRepository repository = new UserRepository(database.userDao());
                    GetUserUseCase getUserUseCase = new GetUserUseCase(repository);
                    UpdateBalanceUseCase updateBalanceUseCase = new UpdateBalanceUseCase(repository);

                    return new MainViewModel(getUserUseCase, updateBalanceUseCase);
                }
        );
    }
}

