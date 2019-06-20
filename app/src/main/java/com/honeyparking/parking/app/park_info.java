package com.honeyparking.parking.app;

public class park_info {
    public String park_name_s;
    public String park_lo_s;
    public String park_lo_y;
    public String park_can;
    public int park_dist;

    public String getPark_name_s() {
        return park_name_s;
    }

    public String getPark_lo_s() {
        return park_lo_s;
    }

    public String getPark_can() {
        return park_can;
    }

    public String getPark_lo_y(){
        return park_lo_y;
    }

    public int getPark_dist(){
        return park_dist;
    }

    public void setPark_name_s(String member_id) {
        this.park_name_s = member_id;
    }

    public void setPark_lo_s(String member_name) {
        this.park_lo_s = member_name;
    }

    public void setPark_lo_y(String park_y){
        this.park_lo_y=park_y;
    }

    public void setPark_dist(int dist){
        this.park_dist=dist;
    }

    public void setPark_can(String member_address) {
        this.park_can = member_address;
    }
}
