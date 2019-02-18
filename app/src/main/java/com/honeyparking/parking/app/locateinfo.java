package com.honeyparking.parking.app;

import com.skt.Tmap.TMapPoint;

public class locateinfo {
    public int drawableId;
    public String price;
    public TMapPoint point;

    public locateinfo(int drawableId, String price, TMapPoint tMapPoint){
        this.drawableId = drawableId;
        this.price = price;
        this.point=tMapPoint;
    }
}
