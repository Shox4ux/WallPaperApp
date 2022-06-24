package com.example.wappdrop;

import android.content.Context;
import android.widget.Toast;

import com.example.wappdrop.Models.CuratedApiResponse;
import com.example.wappdrop.Models.SearchApiResponse;
import com.example.wappdrop.listeners.CuratedResponseListeners;
import com.example.wappdrop.listeners.SearchResponseListener;

import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    final static String url = "https://api.pexels.com/v1/";

    public RequestManager(Context context){
        this.context=context;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public void getCuratedWallpapers(CuratedResponseListeners listener,String page){
        CallWallpaperList callWallpaperList = retrofit.create(CallWallpaperList.class);
        Call<CuratedApiResponse> call = callWallpaperList.getWallPapers(page,"20");

        call.enqueue(new Callback<CuratedApiResponse>() {
            @Override
            public void onResponse(Call<CuratedApiResponse> call, Response<CuratedApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                    return;

                }
                listener.onFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<CuratedApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });

    }

    public void getSearchWallpapers(SearchResponseListener listener, String page,String query){
        CallWallpaperListSearch callWallpaperListSearch = retrofit.create(CallWallpaperListSearch.class);
        Call<SearchApiResponse> call = callWallpaperListSearch.SearchWallPapers(query,page,"20");

        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(context, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                    return;
                }
                    listener.onFetch(response.body(),response.message());
            }

            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });

    }



    private interface CallWallpaperList{
        @Headers({
                "Accept: application/json",
                "Authorization: 563492ad6f91700001000001e52aecbfc4cd4ce0adc9898f44d0252c"
        })
        @GET("curated/")
        Call<CuratedApiResponse> getWallPapers(
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }

    private interface CallWallpaperListSearch{
        @Headers({
                "Accept: application/json",
                "Authorization: 563492ad6f91700001000001e52aecbfc4cd4ce0adc9898f44d0252c"
        })
        @GET("search")
        Call<SearchApiResponse> SearchWallPapers(
                @Query("query") String query,
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }

}
