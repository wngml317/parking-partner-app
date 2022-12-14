package com.yh.parkingpartner.ui;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.yh.parkingpartner.R;
import com.yh.parkingpartner.api.ApiFourthFragment;
import com.yh.parkingpartner.api.NetworkClient;
import com.yh.parkingpartner.config.Config;
import com.yh.parkingpartner.model.Data;
import com.yh.parkingpartner.model.DataListRes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FourthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FourthFragment extends Fragment {


    MainActivity mainActivity;
    Fragment fourthFragment;
    ProgressDialog progressDialog;

    ArrayList<Data> dataList = new ArrayList<>();
    Data data=new Data();
    Fragment secondFragment;
    Fragment firstFragment;
    TextView prkNm;
    TextView prkStart;
    TextView prkLct;
    TextView prkLct2;
    TextView prkTime;
    TextView prkTime2;
    TextView prkPay;
    TextView prkPay2;
    Button btnCheckPay;
    Button btnEndParking;
    int prkId;
    int pushprkId;
    int count = 0;
    DataListRes dataListRes;
    String[] time;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FourthFragment() {
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
        }

        mainActivity = (MainActivity) getActivity();
        mainActivity.getSupportActionBar().setTitle("?????? ??????");
        ReadSharedPreferences();
        // ????????? prkId(SP_KEY_PRK_ID) ??? ?????? ??????

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_fourth,container,false);


        firstFragment = new FirstFragment();
        fourthFragment = new FourthFragment();
        secondFragment = new SecondFragment();
        prkNm = (TextView) rootView.findViewById(R.id.prkNm);
        prkStart = (TextView) rootView.findViewById(R.id.prkStart);
        prkLct = (TextView) rootView.findViewById(R.id.prkLct);
        prkLct2 = (TextView) rootView.findViewById(R.id.prkLct2);
        prkTime = (TextView) rootView.findViewById(R.id.prkTime);
        prkTime2 = (TextView) rootView.findViewById(R.id.prkTime2);
        prkPay = (TextView) rootView.findViewById(R.id.prkPay);
        prkPay2 = (TextView) rootView.findViewById(R.id.prkPay2);
        btnCheckPay = (Button) rootView.findViewById(R.id.btnCheckPay);
        btnEndParking = (Button) rootView.findViewById(R.id.btnEndParking);
        // Inflate the layout for this fragment

        

//        Retrofit retrofit = NetworkClient.getRetrofitClient(getContext(),Config.PP_BASE_URL);
//        ApiFourthFragment api = retrofit.create(ApiFourthFragment.class);
//        Call<DataListRes> call = api.getPay(prkId);
//        call.enqueue(new Callback<DataListRes>() {
//            @Override
//            public void onResponse(Call<DataListRes> call, Response<DataListRes> response) {
//                Log.i("??????", "?????? : "+response.isSuccessful());
//                if(response.isSuccessful()){
//                    getNetworkData();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DataListRes> call, Throwable t) {
//                t.printStackTrace();
//
//            }
//        });

        btnCheckPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                btnCheckPay.setEnabled(true);
                btnEndParking.setEnabled(true);
                getNetworkData();
            }
        });


        btnEndParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                btnCheckPay.setEnabled(false);
                btnEndParking.setEnabled(false);
                getNetworkData2();
            }
        });




        return rootView;
        }


        void ReadSharedPreferences(){
        //SharedPref???erences ??? ????????????, ??? ?????? ????????? ??????????????? ???????????? ???????????? ??????
        SharedPreferences sp = getActivity().getSharedPreferences(Config.SP_NAME, getActivity().MODE_PRIVATE);
        prkId = sp.getInt(Config.SP_KEY_PRK_ID,0);
        pushprkId = sp.getInt(Config.SP_KEY_PUSH_PRK_ID, 0);
        Log.i("??????", "prk_id : " + prkId);
        Log.i("??????", "prk_id : " + pushprkId);


    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getActivity().getSharedPreferences(Config.SP_NAME, getActivity().MODE_PRIVATE);
        prkId = sp.getInt(Config.SP_KEY_PRK_ID,0);

        getNetworkData();

    }


    // ???????????? ?????? ??????????????? ???????????? ??????
    // ???????????? ???????????? ????????????.
    private void getNetworkData() {
        dataList.clear();
        count = 1;

        if (prkId == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("?????? ?????? ?????? ??????");
            alert.setMessage("?????? ?????? ???, ??????????????????.");
            alert.setPositiveButton("Ok", null);
            alert.show();
            return;
        }

        showProgress("????????? ???????????? ????????????.");

        Retrofit retrofit = NetworkClient.getRetrofitClient(getContext(),Config.PP_BASE_URL);
        ApiFourthFragment api = retrofit.create(ApiFourthFragment.class);
        Call<DataListRes> call = api.getPay(prkId);
        call.enqueue(new Callback<DataListRes>() {
            @Override
            public void onResponse(Call<DataListRes> call, Response<DataListRes> response) {
                if (response.isSuccessful()) {
                    dismissProgress();
                    dataList = response.body().getItems();
                    prkNm.setText(dataList.get(0).getPrk_plce_nm());
                    prkStart.setText(dataList.get(0).getStart_prk_at().replace("T"," ").substring(0, 16));
                    prkLct2.setText(dataList.get(0).getPrk_area());
                    prkPay2.setText(String.valueOf(dataList.get(0).getEnd_pay()));
                    time = dataList.get(0).getUse_prk_at().split(":");
                    if (time[0].equals("0") ) {
                        prkTime2.setText(""+time[1] + "???");
                    } else {
                        if (dataList.get(0).getUse_prk_at().contains("day")) {
                            prkTime2.setText(""+time[0] + "?????? " + time[1] + "???");
                        } else {
                            prkTime2.setText(""+time[0] + "?????? " + time[1] + "???");
                        }
                    }

                } else {
                    try{
                        JSONObject errorBody= new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(),
//                                "????????????\n"+
//                                        "?????? : "+response.code()+"\n" +
                                        "?????? : "+errorBody.getString("error")
                                , Toast.LENGTH_LONG).show();
                        Log.i("??????", "???????????? : "+response.code()+", "+errorBody.getString("error"));
                    }catch (IOException | JSONException e){
                        Toast.makeText(getActivity(),
//                                "????????????\n"+
//                                        "?????? : "+response.code()+"\n" +
                                        "?????? : "+e.getMessage()
                                , Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<DataListRes> call, Throwable t) {

            }
        });
    }

    public void getNetworkData2(){
        Retrofit retrofit = NetworkClient.getRetrofitClient(getContext(),Config.PP_BASE_URL);
        ApiFourthFragment api = retrofit.create(ApiFourthFragment.class);
        Call<DataListRes> call = api.endParking(prkId,data);
        call.enqueue(new Callback<DataListRes>() {
            @Override
            public void onResponse(Call<DataListRes> call, Response<DataListRes> response) {
                if (response.isSuccessful()){
                    DataListRes dataListRes=response.body();
                    if (dataListRes.getResult().equals("success")){
                        android.app.AlertDialog.Builder alert=new android.app.AlertDialog.Builder(getContext());
                        if(data.getPrk_id()==0){
                            alert.setTitle("????????? ?????????????????????.");
                        }
                        alert.setMessage("????????? ?????????????????????????");
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getActivity(), ReviewAddActivity.class);
                                intent.putExtra("prkId",prkId);
                                intent.putExtra("prkNm",dataList.get(0).getPrk_plce_nm());
                                intent.putExtra("prkEnd",dataListRes.getItems().get(0).getEnd_prk());
                                Log.i("??????", "?????? : " + dataListRes.getItems().get(0).getEnd_prk());
                                SharedPreferences sp = getActivity().getSharedPreferences(Config.SP_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("prk_id", 0);
                                editor.putInt("push_prk_id",0);
                                Log.i("??????", "?????? : " + editor.putInt("prk_id", 0));
                                Log.i("??????", "?????? : " + editor.putInt("push_prk_id",0));
                                editor.apply();

                                prkId = sp.getInt(Config.SP_KEY_PRK_ID, 0);
                                pushprkId = sp.getInt(Config.SP_KEY_PUSH_PRK_ID, 0);
                                startActivity(intent);
                            }
                        });
                        alert.setNegativeButton("No",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                SharedPreferences sp = getActivity().getSharedPreferences(Config.SP_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("prk_id", 0);
                                editor.putInt("push_prk_id",0);
                                Log.i("??????", "?????? : " + editor.putInt("prk_id", 0));
                                Log.i("??????", "?????? : " + editor.putInt("push_prk_id",0));
                                editor.apply();

                                prkId = sp.getInt(Config.SP_KEY_PRK_ID, 0);
                                pushprkId = sp.getInt(Config.SP_KEY_PUSH_PRK_ID, 0);
                                Log.i("??????", "?????? : ?????? " );
                                mainActivity.changeFragment(R.id.firstFragment, firstFragment);
                            }
                        });
                        //????????? ?????????????????? ????????? ????????????, ????????? ???????????? ??????..
                        alert.setCancelable(false);
                        //??????????????? ????????? ?????????
                        alert.show();
                    }

                } else {
                    try{
                        JSONObject errorBody= new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(),
//                                "????????????\n"+
//                                        "?????? : "+response.code()+"\n" +
                                        "?????? : "+errorBody.getString("error")
                                , Toast.LENGTH_LONG).show();
                        Log.i("??????", "???????????? : "+response.code()+", "+errorBody.getString("error"));
                    }catch (IOException | JSONException e){
                        Toast.makeText(getActivity(),
//                                "????????????\n"+
//                                        "?????? : "+response.code()+"\n" +
                                        "?????? : "+e.getMessage()
                                , Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<DataListRes> call, Throwable t) {
                Log.i("??????", "??????: ??????");
                t.printStackTrace();


            }
        });

    }

    //?????????????????????????????? ??????
    void showProgress(String msg){
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }
    //?????????????????????????????? ?????????
    void dismissProgress(){
        progressDialog.dismiss();
    }

}