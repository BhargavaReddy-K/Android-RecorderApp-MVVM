package com.company.recorder.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.company.recorder.model.dto.MusicModel;
import com.company.recorder.model.repository.MusicRepository;

import java.util.List;


public class MusicViewModel extends ViewModel {

    private MutableLiveData<List<MusicModel>> mutableLiveDataList;

    public void initialize() {
        MusicRepository musicRepository = MusicRepository.getInstance();
        mutableLiveDataList = musicRepository.getData();
    }

    public LiveData<List<MusicModel>> getMusicRepository() {
        return mutableLiveDataList;
    }

}
