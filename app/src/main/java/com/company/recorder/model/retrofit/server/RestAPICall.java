package com.company.recorder.model.retrofit.server;



import com.company.recorder.model.dto.MusicModel;
import com.company.recorder.model.retrofit.response.ResponseModelArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestAPICall {

    @GET("automotive-media/music.json")
    Call<ResponseModelArray<MusicModel>> getDetailsApi();

}
