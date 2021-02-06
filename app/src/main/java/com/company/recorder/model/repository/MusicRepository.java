package com.company.recorder.model.repository;

import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.support.annotation.NonNull;


import com.company.recorder.model.core.Constants;
import com.company.recorder.model.dto.MusicModel;

import com.company.recorder.model.retrofit.response.ResponseModelArray;
import com.company.recorder.model.retrofit.server.RestAPICall;
import com.company.recorder.model.retrofit.server.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicRepository {
    private static MusicRepository musicRepository;
    private final RestAPICall restApi;


    public static MusicRepository getInstance() {
        if (musicRepository == null) {
            musicRepository = new MusicRepository();
        }
        return musicRepository;
    }

    private MusicRepository() {
        restApi = RetrofitClient.getClient(Constants.BASE_URL).create(RestAPICall.class);
    }

    public MutableLiveData<List<MusicModel>> getData() {


        final MutableLiveData<List<MusicModel>> mutableLiveData = new MutableLiveData<>();
        restApi.getDetailsApi().enqueue(new Callback<ResponseModelArray<MusicModel>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModelArray<MusicModel>> call, @NonNull Response<ResponseModelArray<MusicModel>> response) {
                try {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        List<MusicModel> musicModelList = response.body().getData();
                        if (musicModelList.size() > 0) {
                            for (MusicModel musicModel : musicModelList) {
                                String url = Constants.MUSIC_URL + musicModel.getSource();
                                Uri uri = Uri.parse(url);
                                musicModel.setUri(uri);
                            }
                            mutableLiveData.setValue(musicModelList);

                        } else {
                            mutableLiveData.setValue(null);
                        }
                    } else {

                        mutableLiveData.setValue(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModelArray<MusicModel>> call, @NonNull Throwable t) {
                try {

                    mutableLiveData.setValue(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return mutableLiveData;
    }


}
