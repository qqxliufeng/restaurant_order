package com.android.ql.restaurant.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ShopBean implements Parcelable {
    private String shop_id;
    private String shop_name;
    private String shop_pic;

    private String shop_phone;
    private String shop_dizhi;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_pic() {
        return shop_pic;
    }

    public void setShop_pic(String shop_pic) {
        this.shop_pic = shop_pic;
    }

    public String getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    public String getShop_dizhi() {
        return shop_dizhi;
    }

    public void setShop_dizhi(String shop_dizhi) {
        this.shop_dizhi = shop_dizhi;
    }

    public ShopBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.shop_id);
        dest.writeString(this.shop_name);
        dest.writeString(this.shop_pic);
        dest.writeString(this.shop_phone);
        dest.writeString(this.shop_dizhi);
    }

    protected ShopBean(Parcel in) {
        this.shop_id = in.readString();
        this.shop_name = in.readString();
        this.shop_pic = in.readString();
        this.shop_phone = in.readString();
        this.shop_dizhi = in.readString();
    }

    public static final Creator<ShopBean> CREATOR = new Creator<ShopBean>() {
        @Override
        public ShopBean createFromParcel(Parcel source) {
            return new ShopBean(source);
        }

        @Override
        public ShopBean[] newArray(int size) {
            return new ShopBean[size];
        }
    };
}
