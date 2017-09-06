package com.li.food.network.interfaces;

import retrofit2.Call;

public interface ErrorHandler {
    void onFailure(Call call, int code, String msg);
}
