package com.yh.parkingpartner.model;

import java.io.Serializable;

public class Data implements Serializable {

    String prk_center_id;               //"15204-29391-00083-00-1",
    String prk_plce_nm;                 // "신동아홈타운",
    String prk_plce_adres;              // "인천광역시 서구 심곡동  325-25",
    double prk_plce_entrc_la;           // 126.6721880577,
    double prk_plce_entrc_lo;           // 37.5459130584,
    int prk_cmprt_co;                       // 주차장의 총 주차 구획 수 46,
    int pkfc_Available_ParkingLots_total;       // 주차가능 구획 수 0,
    int parking_chrge_bs_time;          // 기본시간 30,
    int parking_chrge_bs_chrg;          // 기본요금 0,
    int parking_chrge_adit_unit_time;       // 추가단위시간 10,
    int parking_chrge_adit_unit_chrge;      // 추가단위요금 0,

    int parking_chrge_one_day_chrge;      // 1일요금 0,
    float rating;                                           // 별점평균 0.0,

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getParking_chrge_one_day_chrge() {
        return parking_chrge_one_day_chrge;
    }

    public void setParking_chrge_one_day_chrge(int parking_chrge_one_day_chrge) {
        this.parking_chrge_one_day_chrge = parking_chrge_one_day_chrge;
    }

    public String getPrk_center_id() {
        return prk_center_id;
    }

    public void setPrk_center_id(String prk_center_id) {
        this.prk_center_id = prk_center_id;
    }

    public String getPrk_plce_nm() {
        return prk_plce_nm;
    }

    public void setPrk_plce_nm(String prk_plce_nm) {
        this.prk_plce_nm = prk_plce_nm;
    }

    public String getPrk_plce_adres() {
        return prk_plce_adres;
    }

    public void setPrk_plce_adres(String prk_plce_adres) {
        this.prk_plce_adres = prk_plce_adres;
    }

    public double getPrk_plce_entrc_la() {
        return prk_plce_entrc_la;
    }

    public void setPrk_plce_entrc_la(double prk_plce_entrc_la) {
        this.prk_plce_entrc_la = prk_plce_entrc_la;
    }

    public double getPrk_plce_entrc_lo() {
        return prk_plce_entrc_lo;
    }

    public void setPrk_plce_entrc_lo(double prk_plce_entrc_lo) {
        this.prk_plce_entrc_lo = prk_plce_entrc_lo;
    }

    public int getPrk_cmprt_co() {
        return prk_cmprt_co;
    }

    public void setPrk_cmprt_co(int prk_cmprt_co) {
        this.prk_cmprt_co = prk_cmprt_co;
    }

    public int getPkfc_Available_ParkingLots_total() {
        return pkfc_Available_ParkingLots_total;
    }

    public void setPkfc_Available_ParkingLots_total(int pkfc_Available_ParkingLots_total) {
        this.pkfc_Available_ParkingLots_total = pkfc_Available_ParkingLots_total;
    }

    public int getParking_chrge_bs_time() {
        return parking_chrge_bs_time;
    }

    public void setParking_chrge_bs_time(int parking_chrge_bs_time) {
        this.parking_chrge_bs_time = parking_chrge_bs_time;
    }

    public int getParking_chrge_bs_chrg() {
        return parking_chrge_bs_chrg;
    }

    public void setParking_chrge_bs_chrg(int parking_chrge_bs_chrg) {
        this.parking_chrge_bs_chrg = parking_chrge_bs_chrg;
    }

    public int getParking_chrge_adit_unit_time() {
        return parking_chrge_adit_unit_time;
    }

    public void setParking_chrge_adit_unit_time(int parking_chrge_adit_unit_time) {
        this.parking_chrge_adit_unit_time = parking_chrge_adit_unit_time;
    }

    public int getParking_chrge_adit_unit_chrge() {
        return parking_chrge_adit_unit_chrge;
    }

    public void setParking_chrge_adit_unit_chrge(int parking_chrge_adit_unit_chrge) {
        this.parking_chrge_adit_unit_chrge = parking_chrge_adit_unit_chrge;
    }
}
