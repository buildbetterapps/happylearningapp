package com.gols.happylearning.utils;

import android.text.Html;

import com.gols.happylearning.beans.WPPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by USER on 2/16/2017.
 */

public class WpPostParser {

    public final String JSON_TITLE ="title";
    public final String JSON_RENDERED ="rendered";
    public final String JSON_CONTENT ="content";
    public final String JSON_LINK ="link";

    private static WpPostParser instance;
    private WpPostParser(){
    }

    public synchronized static WpPostParser getInstance(){

        if(instance == null){
            instance = new WpPostParser();
        }
        return instance;
    }
    public List<WPPost> parseWpPosts(JSONArray data){

        List<WPPost> posts = new ArrayList<>();
        try{
            for(int i=0; i<data.length() ;i++){
                JSONObject post = data.getJSONObject(i);
                posts.add(parseWpPost(post));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return posts;
    }
    public WPPost parseWpPost(JSONObject data){
        WPPost wpPost = new WPPost();

        try {
            wpPost.setTitle(Html.fromHtml(data.getJSONObject(JSON_TITLE).getString(JSON_RENDERED)).toString());
            wpPost.setLink(data.get(JSON_LINK).toString());
            wpPost.setImageURL(getImageUrl(data.getJSONObject(JSON_CONTENT).get(JSON_RENDERED).toString()));
            wpPost.setContent(data.getJSONObject(JSON_CONTENT).get(JSON_RENDERED).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wpPost;
    }

    private String getImageUrl(String s) {
        Pattern p = Pattern.compile("src=\"(.*?)\"");
        Matcher m = p.matcher(s);
        try {
            if (m.find()) {

                String imgUrl = m.group();
                return imgUrl.substring(5,imgUrl.length()-2);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
