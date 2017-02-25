package com.gols.happylearning.dao;

import android.content.Context;

import com.android.volley.Request;
import com.gols.happylearning.network.NetworkManager;
import com.gols.happylearning.network.NetworkResponseListener;
import com.gols.happylearning.utils.AppUtil;

/**
 * Created by USER on 1/7/2017.
 */

public class WpPostDAO implements NetworkResponseListener {

    private Context context;

    private DataResponseListener dataResponseListener;

    private static final int REQUEST_GET_POSTS = 101;
    public WpPostDAO(Context context,DataResponseListener dataResponseListener){
        this.context = context;
        this.dataResponseListener = dataResponseListener;
    }
    public void getWpPosts(int pageNumber){

        //todo get data from disk

        //todo get posts from network
        getWpPostsFromNetwork(pageNumber);
    }


    private void getWpPostsFromNetwork(int pageNumber){
        NetworkManager.getInstance(context).makeNetworkRequestForJSONArray(
                REQUEST_GET_POSTS,
                Request.Method.GET,
                AppUtil.BLOG_POST_BASE_URL+AppUtil.POST_PARAMETER_PAGE+pageNumber,
                null,
                null,
                this
        );
    }

    @Override
    public void onDataReceived(int requestCode, Object data) {
        if(requestCode == REQUEST_GET_POSTS){
            dataResponseListener.onData(data);
        }
    }

    @Override
    public void onDataFailed(int requestCode, Object error) {

        dataResponseListener.onError(error.toString());
    }
}
