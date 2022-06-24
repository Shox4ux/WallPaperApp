package com.example.wappdrop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wappdrop.Adapters.CuratedAdapter;
import com.example.wappdrop.Adapters.CustomRecycler;
import com.example.wappdrop.Models.CuratedApiResponse;
import com.example.wappdrop.Models.Photo;
import com.example.wappdrop.Models.SearchApiResponse;
import com.example.wappdrop.listeners.CuratedResponseListeners;
import com.example.wappdrop.listeners.OnRecyclerClickListener;
import com.example.wappdrop.listeners.SearchResponseListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRecyclerClickListener {
    androidx.appcompat.widget.SearchView searchView;
    CustomRecycler recyclerView;
    CuratedAdapter adapter;
    ProgressDialog dialog;
    RequestManager manager;
    FloatingActionButton fabNext,fabPrev;
    int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        fabPrev = findViewById(R.id.fab_prev);
        fabNext = findViewById(R.id.fab_next);


        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading....");



        manager = new RequestManager(this);
        manager.getCuratedWallpapers(listener,"1");

        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String next_page = String.valueOf(page+1);
                manager.getCuratedWallpapers(listener,next_page);

            }
        });
        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page>1){
                    String prev_page = String.valueOf(page-1);
                    manager.getCuratedWallpapers(listener,prev_page);
                    dialog.show();
                }

            }
        });




    }

    private final CuratedResponseListeners listener = new CuratedResponseListeners() {
        @Override
        public void onFetch(CuratedApiResponse response, String message) {
            dialog.dismiss();
            if (response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "No Image Found", Toast.LENGTH_SHORT).show();
                return;
            }
            page = response.getPage();
            showData(response.getPhotos());

        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

        }

    };

    private void showData(List<Photo> photos) {
        recyclerView = findViewById(R.id.recycler_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setColumnCount(2);
        adapter = new CuratedAdapter(MainActivity.this,photos,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(Photo photo) {
        startActivity( new Intent(MainActivity.this,WallpaperActivity.class)
                .putExtra("photo",photo));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Photos");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                manager.getSearchWallpapers(searchResponseListener,"1",query);
                dialog.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }




    private final SearchResponseListener searchResponseListener = new SearchResponseListener() {
        @Override
        public void onFetch(SearchApiResponse response, String message) {
            if (response.getPhotos().isEmpty()){
                Toast.makeText(MainActivity.this, "No Image Found", Toast.LENGTH_SHORT).show();
                return;
            }

            showData(response.getPhotos());
            dialog.dismiss();

        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };
}
















