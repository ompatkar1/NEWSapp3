package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    RecyclerView recyclerView;
    List<Article>articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.Search_view);
        recyclerView =findViewById(R.id.Recycler_view);


        setupRecyclerView();

        btn1=findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
        btn2=findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
        btn3=findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);
        btn4=findViewById(R.id.btn_4);
        btn4.setOnClickListener(this);
        btn5=findViewById(R.id.btn_5);
        btn5.setOnClickListener(this);
        btn6=findViewById(R.id.btn_6);
        btn6.setOnClickListener(this);
        btn7=findViewById(R.id.btn_7);
        btn7.setOnClickListener(this);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews("General",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        getNews("General",null);
    }


    void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =new NewsRecyclerAdapter(articleList);
        recyclerView.setAdapter(adapter);
    }


    void getNews(String category,String query){

        NewsApiClient newsApiClient = new NewsApiClient("04f9943d02c24618add9573ee1366030");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .q(query)
                        .category(category)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback(){
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        runOnUiThread(()->{
                            Log.d("SearchResults", "Received " + response.getTotalResults() + " results");
                            articleList =response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("GOT FAILURE",throwable.getMessage());
                    }

                }
        );


    }
    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        String category = btn.getText().toString();
        getNews(category, null);

    }
}
