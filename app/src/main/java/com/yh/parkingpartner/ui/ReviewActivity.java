package com.yh.parkingpartner.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yh.parkingpartner.R;
import com.yh.parkingpartner.api.ApiMypageActivity;
import com.yh.parkingpartner.api.ApiReviewActivity;
import com.yh.parkingpartner.api.NetworkClient;
import com.yh.parkingpartner.config.Config;
import com.yh.parkingpartner.model.PostRes;
import com.yh.parkingpartner.model.Review;
import com.yh.parkingpartner.model.UserRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewActivity extends AppCompatActivity {

    String accessToken;
    int reviewId;
    int prkId;
    float rating;
    String content = null;

    Review review;

    TextView txtPrkNm;
    TextView txtEnd;
    RatingBar ratingBar;
    EditText txtContent;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // 액션바 타이틀 설정
        getSupportActionBar().setTitle("리뷰 저장");
        // 액션바 뒤로가기 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        review = (Review) getIntent().getSerializableExtra("review");

        SharedPreferences sp = getApplication().getSharedPreferences(Config.SP_NAME, MODE_PRIVATE);
        accessToken = sp.getString(Config.SP_KEY_ACCESS_TOKEN, "");

        reviewId = review.getId();
        prkId = review.getPrk_id();

        txtPrkNm = findViewById(R.id.txtPrkNm);
        txtEnd = findViewById(R.id.txtEnd);
        ratingBar = findViewById(R.id.ratingBar);
        txtContent = findViewById(R.id.txtContent);
        btnSave = findViewById(R.id.btnSave);

        txtPrkNm.setText(review.getPrk_plce_nm());
        txtEnd.setText(review.getEnd_prk().replace("T", " ").substring(0, 16));
        ratingBar.setRating(review.getRating());
        txtContent.setText(review.getContent());

        // 작성한 리뷰가 없을 경우 (별점이 0이고, 리뷰 id가 없는 경우) -> 작성 / 작성한 리뷰가 있을 경우 -> 수정
        if (review.getRating() == 0 && review.getId() == 0) {
            btnSave.setText("리뷰 작성");

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Retrofit retrofit = NetworkClient.getRetrofitClient(ReviewActivity.this, Config.PP_BASE_URL);
                    ApiReviewActivity api = retrofit.create(ApiReviewActivity.class);

                    rating = ratingBar.getRating();

                    if (rating == 0) {
                        Toast.makeText(ReviewActivity.this, "별점은 최소 1점 이상 입력해주세요.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (!txtContent.getText().toString().isEmpty()) {
                        content = txtContent.getText().toString();
                    }

                    Review reviewAdd = new Review(prkId, rating, content);
                    reviewAdd.setPrk_id(prkId);
                    reviewAdd.setRating(rating);
                    reviewAdd.setContent(content);

                    Call<PostRes> call = api.review("Bearer " + accessToken, reviewAdd);
                    call.enqueue(new Callback<PostRes>() {
                        @Override
                        public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(ReviewActivity.this, MypageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostRes> call, Throwable t) {

                        }
                    });

                }
            });
        } else {
            btnSave.setText("리뷰 수정");
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Retrofit retrofit = NetworkClient.getRetrofitClient(ReviewActivity.this, Config.PP_BASE_URL);
                    ApiReviewActivity api = retrofit.create(ApiReviewActivity.class);

                    rating = ratingBar.getRating();

                    if (rating == 0) {
                        Toast.makeText(ReviewActivity.this, "별점은 최소 1점 이상 입력해주세요.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (!txtContent.getText().toString().isEmpty()) {
                        content = txtContent.getText().toString();
                    }

                    Review reviewUpdate = new Review(rating,content);
                    reviewUpdate.setRating(rating);
                    reviewUpdate.setContent(content);
                    Log.i("content:::" , "ReviewActivity Content ==============="+content);

                    Call<PostRes> call = api.updateReview("Bearer " + accessToken, reviewId, reviewUpdate);
                    call.enqueue(new Callback<PostRes>() {
                        @Override
                        public void onResponse(Call<PostRes> call, Response<PostRes> response) {
                            if(response.isSuccessful()) {
                                Intent intent = new Intent(ReviewActivity.this, MypageActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<PostRes> call, Throwable t) {

                        }
                    });
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == android.R.id.home) {
            Intent intent = new Intent(ReviewActivity.this, MypageActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}