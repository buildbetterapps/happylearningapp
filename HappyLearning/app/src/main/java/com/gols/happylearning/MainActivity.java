package com.gols.happylearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.gols.happylearning.adapters.PostAdapter;
import com.gols.happylearning.beans.WPPost;
import com.gols.happylearning.dao.DataResponseListener;
import com.gols.happylearning.dao.WpPostDAO;
import com.gols.happylearning.utils.WpPostParser;

import org.json.JSONArray;

import java.util.List;

public class MainActivity extends AppCompatActivity implements DataResponseListener {

    private final String TAG = MainActivity.class.getSimpleName();

    private static int currentPage = 1;

    private WpPostDAO postDAO;
    private RecyclerView recyclerViewPosts;
    private LinearLayoutManager mLayoutManager;

    private PostAdapter mAdapter;
    private WpPostDAO dao = new WpPostDAO(this,this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postDAO = new WpPostDAO(this,this);
        setupViews();
        loadPosts();

    }

    private void loadPosts() {
        //todo load posts from dataAccess dao
        dao.getWpPosts(currentPage);
    }

    boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private void setupViews() {
        recyclerViewPosts = (RecyclerView) findViewById(R.id.recyclerViewPosts);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerViewPosts.setLayoutManager(mLayoutManager);
        //todo setup adapter
        mAdapter = new PostAdapter(this);
        recyclerViewPosts.setAdapter(mAdapter);

        recyclerViewPosts.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) //check for scroll down
                {
                    loading = true;
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mAdapter.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            Log.v(MainActivity.class.getSimpleName(), "Last Item Wow !");
                            currentPage = currentPage+ 1;
                            dao.getWpPosts(currentPage);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onData(Object data) {
        List<WPPost> posts = WpPostParser.getInstance().parseWpPosts((JSONArray) data);
        mAdapter.addWpPosts(posts);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, "Error loading Posts", Toast.LENGTH_SHORT).show();
    }
}
