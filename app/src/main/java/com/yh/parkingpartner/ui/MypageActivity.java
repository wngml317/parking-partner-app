package com.yh.parkingpartner.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.yh.parkingpartner.R;
import com.yh.parkingpartner.adapter.MypageAdapter;
import com.yh.parkingpartner.api.ApiMypageActivity;
import com.yh.parkingpartner.api.NetworkClient;
import com.yh.parkingpartner.config.Config;
import com.yh.parkingpartner.model.Review;
import com.yh.parkingpartner.model.ReviewListRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MypageActivity extends AppCompatActivity {

    String accessToken;

    RecyclerView recyclerView;
    ProgressBar progressBar;
    MypageAdapter adapter;
    ArrayList<Review> reviewList = new ArrayList<>();

    TextView txtEmail;
    TextView txtName;
    TextView txtResult;
    ImageView imgProfile;

    Button btnTotal;
    Button btnWrite;
    Button btnUnwritten;

    // 페이징 처리 멤버 변수
    int count = 0;
    String order = "total";
    int offset = 0;
    int limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        txtResult = findViewById(R.id.txtResult);
        txtEmail = findViewById(R.id.txtEmail);
        txtName = findViewById(R.id.txtName);
        imgProfile = findViewById(R.id.imgProfile);

        //SharedPreferences 를 이용해서, 앱 내의 저장소에 영구저장된 데이터를 읽어오는 방법
        SharedPreferences sp = getApplication().getSharedPreferences(Config.SP_NAME, MODE_PRIVATE);
        accessToken = sp.getString(Config.SP_KEY_ACCESS_TOKEN, "");
        txtEmail.setText(sp.getString(Config.SP_KEY_EMAIL, ""));
        txtName.setText(sp.getString(Config.SP_KEY_NAME, ""));
        GlideUrl url = new GlideUrl(sp.getString(Config.SP_KEY_IMG_PROFILE, ""), new LazyHeaders.Builder().addHeader("User_Agent", "Android").build());
        Glide.with(MypageActivity.this).load(url).placeholder(R.drawable.ic_baseline_person_24).into(imgProfile);

        btnTotal = (Button) findViewById(R.id.btnTotal);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnUnwritten = (Button) findViewById(R.id.btnUnwritten);

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTotal.setEnabled(false);
                btnWrite.setEnabled(true);
                btnUnwritten.setEnabled(true);

                order = "total";

                getNetworkData();
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTotal.setEnabled(true);
                btnWrite.setEnabled(false);
                btnUnwritten.setEnabled(true);

                order = "write";

                getNetworkData();
            }
        });

        btnUnwritten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnTotal.setEnabled(true);
                btnWrite.setEnabled(true);
                btnUnwritten.setEnabled(false);

                order = "unwritten";

                getNetworkData();
            }
        });

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MypageActivity.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                if(  lastPosition+1  == totalCount  ){

                    if(count == limit){
                        // 네트워크 통해서, 남아있는 데이터를 추가로 가져오기
                        addNetworkData();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetworkData();
    }

    // 데이터를 처음 가져올때만 실행하는 함수
    // 데이터의 초기화도 필요하다.
    private void getNetworkData() {
        reviewList.clear();
        count = 0;
        offset = 0;
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MypageActivity.this, Config.PP_BASE_URL);
        ApiMypageActivity api = retrofit.create(ApiMypageActivity.class);
        Call<ReviewListRes> call = api.getReviewList("Bearer " + accessToken, order, offset, limit);
        call.enqueue(new Callback<ReviewListRes>() {
            @Override
            public void onResponse(Call<ReviewListRes> call, Response<ReviewListRes> response) {
                if (response.isSuccessful()) {
                    ReviewListRes data = response.body();

                    count = data.getCount();
                    reviewList.addAll(data.getItems());
                    offset = offset + count;

                    progressBar.setVisibility(View.INVISIBLE);
                    adapter = new MypageAdapter(MypageActivity.this, reviewList);
                    recyclerView.setAdapter(adapter);

                    txtResult.setText("");
                    if (count == 0) {
                        if (order.equals("write")) {
                            txtResult.setText("리뷰를 작성한 주차장 이력이 없습니다.");
                        } else if (order.equals("unwritten")) {
                            txtResult.setText("이용한 모든 주차장 리뷰를 작성하였습니다.");
                        } else {
                            txtResult.setText("주차장 사용 이력이 없습니다.");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewListRes> call, Throwable t) {

            }
        });
    }

    // 처음이 아니라 더 가져오는 경우
    private void addNetworkData() {
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MypageActivity.this, Config.PP_BASE_URL);
        ApiMypageActivity api = retrofit.create(ApiMypageActivity.class);
        Call<ReviewListRes> call = api.getReviewList("Bearer " + accessToken, order, offset, limit);
        call.enqueue(new Callback<ReviewListRes>() {
            @Override
            public void onResponse(Call<ReviewListRes> call, Response<ReviewListRes> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);

                    ReviewListRes data = response.body();

                    txtResult.setText("");

                    count = data.getCount();
                    reviewList.addAll(data.getItems());
                    offset = offset + count;
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ReviewListRes> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}