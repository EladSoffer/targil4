package com.example.friends;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository repository;

    private LiveData<List<User>> users;

    public UserViewModel(Context context) {
        repository = new UserRepository(context);
        users = repository.getAll();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void add(User user) {
        repository.add(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }



}
