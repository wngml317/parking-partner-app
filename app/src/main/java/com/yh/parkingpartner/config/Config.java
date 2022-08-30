package com.yh.parkingpartner.config;

public class Config {
    //구글API
    public static final String GG_BASE_URL="https://maps.googleapis.com";
    public static final String GG_API_KEY="AIzaSyCz-Ns1JcsnKJ_Fguw6YhHtgcezjUkMjAc";

    //파킹파트너API
//    public static final String PP_BASE_URL="aws endpoint";
    public static final String PP_BASE_URL="http://10.0.2.2:5000";

    //SKTmapAPI
    public static final String TM_API_KEY="l7xx0aa51a311ad44a6cbac051cdcd05dcdb";

    //SharedPreferences
    // 저장소명
    public static final String SP_NAME="ParkingPartner";
    // 로그인 회원정보 관련 KEY명
    public static final String SP_KEY_ACCESS_TOKEN="accessToken";
    public static final String SP_KEY_EMAIL="email";
    public static final String SP_KEY_NAME="name";
    public static final String SP_KEY_IMG_PROFILE="img_profile";
    // 주차완료정보 관련 KEY명
    // prk_id-주차ID
    public static final String SP_KEY_PRK_ID="prk_id";
    //prk_center_id-주차장ID
    public static final String SP_KEY_PRK_CENTER_ID="prk_center_id";
    //prk_plce_nm-주차장명
    public static final String SP_KEY_PRK_PLCE_NM="prk_plce_nm";
    //prk_plce_adres-주차장주소
    public static final String SP_KEY_PRK_PLCE_ADRES="prk_plce_adres";
    // start_prk_at-입차시간
    public static final String SP_KEY_START_PRK_AT="start_prk_at";
    // Img_prk-주차사진URL
    public static final String SP_KEY_IMG_PAK="Img_prk";
    // prk_area-주차구역
    public static final String SP_KEY_PRK_AREA="prk_area";
    // parking_chrge_bs_time-기본시간
    public static final String SP_KEY_PARKING_CHRGE_BS_TIME="parking_chrge_bs_time";
    // parking_chrge_bs_chrg-기본요금
    public static final String SP_KEY_PARKING_CHRGE_BS_CHRG="parking_chrge_bs_chrg";
    // parking_chrge_adit_unit_time-추가단위시간
    public static final String SP_KEY_PARKING_CHRGE_ADIT_UNIT_TIME="parking_chrge_adit_unit_time";
    // parking_chrge_adit_unit_chrge-추가단위요금
    public static final String SP_KEY_PARKING_CHRGE_ADIT_UNIT_CHRGE="parking_chrge_adit_unit_chrge";
    // parking_chrge_one_day_chrge-1일요금
    public static final String SP_KEY_PARKING_CHRGE_ONE_DAY_CHRGE="parking_chrge_one_day_chrge";

}
