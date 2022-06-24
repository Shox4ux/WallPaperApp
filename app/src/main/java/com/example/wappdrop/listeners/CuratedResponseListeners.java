package com.example.wappdrop.listeners;

import com.example.wappdrop.Models.CuratedApiResponse;

public interface CuratedResponseListeners {
    void onFetch(CuratedApiResponse response,String message);
    void onError(String message);
}
