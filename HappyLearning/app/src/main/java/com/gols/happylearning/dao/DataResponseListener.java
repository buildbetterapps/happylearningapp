package com.gols.happylearning.dao;

import java.util.Objects;

/**
 * Created by USER on 1/8/2017.
 */

public interface DataResponseListener {
    void onData(Object data);
    void onError(String error);
}
