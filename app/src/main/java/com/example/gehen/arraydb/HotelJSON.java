package com.example.gehen.arraydb;

public class HotelJSON {

     int hotelid;
    String hotelname;
    int hotelprice;
    String hotelphone;
    String image_url;
    String hoteladdress;
    double latitude;
    double longitude;
    public HotelJSON(int hotelid, String hotelname, int hotelprice, String hotelphone, String image_url, String hoteladdress, double latitude, double longitude) {
        this.hotelid = hotelid;
        this.hotelname = hotelname;
        this.hotelprice = hotelprice;
        this.hotelphone = hotelphone;
        this.image_url = image_url;
        this.hoteladdress = hoteladdress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getHotelid() {
        return hotelid;
    }

    public String getHotelname() {
        return hotelname;
    }

    public int getHotelprice() {
        return hotelprice;
    }

    public String getHotelphone() {
        return hotelphone;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getHoteladdress() {
        return hoteladdress;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
