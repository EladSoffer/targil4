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

    private ContactAPI contactAPI;

    public UserRepository(Context context) {
        AppDB db = Room.databaseBuilder(context, AppDB.class, "Contacts")
                .allowMainThreadQueries().build();

        dao = db.userDao();
        userListData = new UserListData();
        contactAPI = new ContactAPI(userListData,dao,context);
    }

//    public void add(String user) {
//        contactAPI.insert(user);
//        userListData.postValue(dao.allUsers());
//    }

    public void delete(User user) {
        contactAPI.delete(user);
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
                contactAPI.get();
            }).start();
        }
    }

    public LiveData<List<User>> getAll() {
        return userListData;
    }
}

