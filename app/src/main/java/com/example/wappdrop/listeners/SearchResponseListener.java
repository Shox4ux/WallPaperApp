package com.example.wappdrop.listeners;

import com.example.wappdrop.Models.CuratedApiResponse;
import com.example.wappdrop.Models.SearchApiResponse;

public interface SearchResponseListener {
    void onFetch(SearchApiResponse response, String message);
    void onError(String message);
}
