package com.example.friends;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private UserDao dao;
    private UserListData userListData;

    public UserRepository(Context context) {
        AppDB db = Room.databaseBuilder(context, AppDB.class, "Contacts")
                .allowMainThreadQueries().build();

        dao = db.userDao();
        userListData = new UserListData();
    }

    public void add(User user) {
        dao.insert(user);
        userListData.postValue(dao.allUsers());
    }

    public void delete(User user) {
        dao.delete(user);
        userListData.postValue(dao.allUsers());
    }



    class UserListData extends MutableLiveData<List<User>> {
        public UserListData() {
            super();
            setValue(new ArrayList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                userListData.postValue(dao.allUsers());
            }).start();
        }
    }

    public LiveData<List<User>> getAll() {
        return userListData;
    }
}

