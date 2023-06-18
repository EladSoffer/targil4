package com.example.friends;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MessageViewModel extends ViewModel {
    private MesRepository repository;

    private LiveData<List<Message>> messages;

    public MessageViewModel(Context context){
        repository = new MesRepository(context);
        messages = repository.getAll();
    }

    public LiveData<List<Message>> getMessages(){
        return messages;
    }

    public void add(String mes){

    }
}
