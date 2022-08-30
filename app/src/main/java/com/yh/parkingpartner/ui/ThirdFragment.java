package com.yh.parkingpartner.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.yh.parkingpartner.R;
import com.yh.parkingpartner.adapter.MypageAdapter;
import com.yh.parkingpartner.api.ApiSecondFragment;
import com.yh.parkingpartner.api.ApiThirdFragment;
import com.yh.parkingpartner.api.NetworkClient;
import com.yh.parkingpartner.config.Config;
import com.yh.parkingpartner.model.Data;
import com.yh.parkingpartner.model.DataListRes;
import com.yh.parkingpartner.model.Review;
import com.yh.parkingpartner.model.ReviewListRes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {


    MainActivity mainActivity;
    ProgressDialog progressDialog;
    boolean blnCreatedView;
    ArrayList<Data> dataListRes = new ArrayList<>();
    Data data=new Data();
    ImageView Lct_prk_img;
    TextView Lct_prk_plce_nm;
    TextView Lct_prk_plce_nm2;
    TextView Lct_prk_plce_adres;
    TextView Lct_prk_plce_adres2;
    TextView Lct_start_prk_at;
    TextView Lct_start_prk_at2;
    TextView Lct_prk_area;
    TextView Lct_prk_area2;
    String accessToken;
    String img_prk;
    private ProgressDialog dialog;
    int count = 0;
    String order = "total";
    int offset = 0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            mainActivity = (MainActivity) getActivity();
            mainActivity.getSupportActionBar().setTitle("주차 위치");
            ReadSharedPreferences();



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_third, container, false);


        Lct_prk_img = rootView.findViewById(R.id.Lct_prk_img);
        Lct_prk_area = rootView.findViewById(R.id.Lct_prk_area);
        Lct_prk_area2 = rootView.findViewById(R.id.Lct_prk_area2);
        Lct_start_prk_at = rootView.findViewById(R.id.Lct_start_prk_at);
        Lct_start_prk_at2 = rootView.findViewById(R.id.Lct_start_prk_at2);
        Lct_prk_plce_adres = rootView.findViewById(R.id.Lct_prk_plce_adres);
        Lct_prk_plce_adres2 = rootView.findViewById(R.id.Lct_prk_plce_adres2);
        Lct_prk_plce_nm = rootView.findViewById(R.id.Lct_prk_plce_nm);
        Lct_prk_plce_nm2 = rootView.findViewById(R.id.Lct_prk_plce_nm2);


        Lct_prk_area2.setText(data.getPrk_area());
        Lct_prk_plce_adres2.setText(data.getPrk_plce_adres());
        Lct_prk_plce_nm2.setText(data.getPrk_plce_nm());
        Lct_start_prk_at2.setText(data.getStart_prk_at());
        Lct_prk_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(data.getPrk_id()!=0) {
            //클라이드 라이브러리 사용
            Lct_prk_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideUrl url=new GlideUrl(data.getImg_prk(), new LazyHeaders.Builder().addHeader("User-Agent", "Android").build());
            Glide.with(getActivity()).load(url).into(Lct_prk_img);}










        // Inflate the layout for this fragment
        return rootView;
        }

        void ReadSharedPreferences(){
        //SharedPref소erences 를 이용해서, 앱 내의 저장에 영구저장된 데이터를 읽어오는 방법
        SharedPreferences sp = getActivity().getSharedPreferences(Config.SP_NAME, getActivity().MODE_PRIVATE);
        accessToken = sp.getString(Config.SP_KEY_ACCESS_TOKEN, "");
        Log.i("로그", "accessToken : " + accessToken);

    }

    @Override
    public void onResume() {
        super.onResume();
        getNetworkData();
    }


    private void getNetworkData() {
        dataListRes.clear();
        count = 1;

        Retrofit retrofit = NetworkClient.getRetrofitClient(getContext(), Config.PP_BASE_URL);
        ApiThirdFragment api = retrofit.create(ApiThirdFragment.class);
        Call<DataListRes> call = api.getDataList("Bearer " + accessToken, data.getPrk_id());
        call.enqueue(new Callback<DataListRes>() {
            @Override
            public void onResponse(Call<DataListRes> call, Response<DataListRes> response) {
                if (response.isSuccessful()) {


                    DataListRes data = response.body();
                    count = data.getCount();
                    dataListRes.addAll(data.getItems());

                }
            }

            @Override
            public void onFailure(Call<DataListRes> call, Throwable t) {

            }
        });
    }

}