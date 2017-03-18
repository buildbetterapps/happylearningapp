
package com.gols.happylearning.network;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by gaurav_polekar on 4/25/2016.
 */
public class NetworkManager {
    private static final String TAG = NetworkManager.class.getName();
    private static NetworkManager mInstance = null;
    //for Volley API
    public RequestQueue requestQueue;

    private ImageLoader imageLoader;

    private NetworkManager(Context context)
    {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    /*    Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
*/

    /*    imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);
                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
  */  }

    private RequestQueue getRequestQueue(){
        return requestQueue;
    }
    public static synchronized NetworkManager getInstance(Context context)
    {
        if (null == mInstance)
            mInstance = new NetworkManager(context);
        return mInstance;
    }



  /*
  Network request for JSON Object
  */
    public void makeNetworkRequestForJSON(final int requestCode, int method, String url, JSONObject param, final Map<String, String> headers, final NetworkResponseListener listener){
        JsonObjectRequest request = new JsonObjectRequest(
                method, url, param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onDataReceived(requestCode,response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onDataFailed(requestCode,error);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(headers != null)
                    return headers;
                else
                    return super.getHeaders();
            }
        };
        request.setTag(requestCode);
        getRequestQueue().add(request);
    }

/*
Network request for String
*/

    /*
    Network request for JSON Object
    */
    public void makeNetworkRequestForString(final int requestCode, int method, String url, final Map<String, String> headers, final NetworkResponseListener listener){
        StringRequest request = new StringRequest(
                method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onDataReceived(requestCode, response);
            }
        },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       listener.onDataFailed(requestCode,error);
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(headers != null)
                    return headers;
                else
                    return super.getHeaders();
            }
        };
        request.setTag(requestCode);
        getRequestQueue().add(request);
    }


    /*
        Network request for JSON Array
    */
    public void makeNetworkRequestForJSONArray(final int requestCode, int method, String url, JSONArray param, final Map<String, String> headers, final NetworkResponseListener listener){
        Log.d(TAG ,"loading url "+url);
        JsonArrayRequest request = new CustomJsonArrayRequest(
                method, url, param,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse() called with: " + "response = [" + response + "]");
                        listener.onDataReceived(requestCode, response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse() called with: " + "error = [" + error + "]");
                listener.onDataFailed(requestCode,error);
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(headers != null)
                    return headers;
                else
                    return super.getHeaders();
            }
        };
        request.setTag(requestCode);
        getRequestQueue().add(request);
    }

/*
calcel request for specific request code
*/

	public void cancelRequest(int requestCode){
		getRequestQueue().cancelAll(requestCode);
	}
}
