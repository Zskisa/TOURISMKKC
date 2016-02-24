package com.zskisa.tourismkkc.apimodel;

import java.util.List;

public class ApiPlaces {

    /**
     * places_id : 000003
     * places_name : กลางป่า3
     * places_desc :
     * type_detail_id : 01
     * is_use : 1
     * location_id : 000001
     * user_email :
     * create_date : 2016-01-16 00:22:45
     * admin_email : jimmy29922@gmail.com
     * nstar1 : 0
     * nstar2 : 0
     * nstar3 : 0
     * nstar4 : 0
     * nstar5 : 0
     * avgstar : 0
     * location_address : ซอย ดรุณสำราญ ตำบล ในเมือง อำเภอเมืองขอนแก่น ขอนแก่น 40000 ประเทศไทย
     * location_lat : 16.42866500
     * location_lng : 102.82711730
     * place_id : ChIJIVXx8hiKIjERE6YviW9mzqU
     * type_id : 01
     * type_detail_name : สถานที่ท่องเที่ยวเชิงธรรมชาติ
     * photos : [{"photos_id":"000014","create_date":"2016-01-20 01:02:01","url":"http://localhost/TourismKKC/main/photo/000014"},{"photos_id":"000015","create_date":"2016-02-15 22:44:44","url":"http://localhost/TourismKKC/main/photo/000015"},{"photos_id":"000016","create_date":"2016-02-15 22:45:12","url":"http://localhost/TourismKKC/main/photo/000016"},{"photos_id":"000017","create_date":"2016-02-15 23:01:36","url":"http://localhost/TourismKKC/main/photo/000017"},{"photos_id":"000018","create_date":"2016-02-15 23:02:47","url":"http://localhost/TourismKKC/main/photo/000018"},{"photos_id":"000019","create_date":"2016-02-15 23:05:02","url":"http://localhost/TourismKKC/main/photo/000019"},{"photos_id":"000020","create_date":"2016-02-15 23:21:12","url":"http://localhost/TourismKKC/main/photo/000020"},{"photos_id":"000021","create_date":"2016-02-16 11:42:06","url":"http://localhost/TourismKKC/main/photo/000021"}]
     * review : [{"review_detail":"ทดสอบ","review_datetime":"24 กุมภาพันธ์ 2016 เวลา 14:55","user_fname":"Near","user_lname":"River","user_fb_id":""},{"review_detail":"ทดสอบ","review_datetime":"24 กุมภาพันธ์ 2016 เวลา 14:55","user_fname":"Near","user_lname":"River","user_fb_id":""}]
     */

    private String places_id;
    private String places_name;
    private String places_desc;
    private String type_detail_id;
    private String is_use;
    private String location_id;
    private String user_email;
    private String create_date;
    private String admin_email;
    private String nstar1;
    private String nstar2;
    private String nstar3;
    private String nstar4;
    private String nstar5;
    private String avgstar;
    private String location_address;
    private String location_lat;
    private String location_lng;
    private String place_id;
    private String type_id;
    private String type_detail_name;
    /**
     * photos_id : 000014
     * create_date : 2016-01-20 01:02:01
     * url : http://localhost/TourismKKC/main/photo/000014
     */

    private List<PhotosEntity> photos;
    /**
     * review_detail : ทดสอบ
     * review_datetime : 24 กุมภาพันธ์ 2016 เวลา 14:55
     * user_fname : Near
     * user_lname : River
     * user_fb_id :
     */

    private List<ReviewEntity> review;

    public void setPlaces_id(String places_id) {
        this.places_id = places_id;
    }

    public void setPlaces_name(String places_name) {
        this.places_name = places_name;
    }

    public void setPlaces_desc(String places_desc) {
        this.places_desc = places_desc;
    }

    public void setType_detail_id(String type_detail_id) {
        this.type_detail_id = type_detail_id;
    }

    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public void setNstar1(String nstar1) {
        this.nstar1 = nstar1;
    }

    public void setNstar2(String nstar2) {
        this.nstar2 = nstar2;
    }

    public void setNstar3(String nstar3) {
        this.nstar3 = nstar3;
    }

    public void setNstar4(String nstar4) {
        this.nstar4 = nstar4;
    }

    public void setNstar5(String nstar5) {
        this.nstar5 = nstar5;
    }

    public void setAvgstar(String avgstar) {
        this.avgstar = avgstar;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public void setLocation_lng(String location_lng) {
        this.location_lng = location_lng;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public void setType_detail_name(String type_detail_name) {
        this.type_detail_name = type_detail_name;
    }

    public void setPhotos(List<PhotosEntity> photos) {
        this.photos = photos;
    }

    public void setReview(List<ReviewEntity> review) {
        this.review = review;
    }

    public String getPlaces_id() {
        return places_id;
    }

    public String getPlaces_name() {
        return places_name;
    }

    public String getPlaces_desc() {
        return places_desc;
    }

    public String getType_detail_id() {
        return type_detail_id;
    }

    public String getIs_use() {
        return is_use;
    }

    public String getLocation_id() {
        return location_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public String getNstar1() {
        return nstar1;
    }

    public String getNstar2() {
        return nstar2;
    }

    public String getNstar3() {
        return nstar3;
    }

    public String getNstar4() {
        return nstar4;
    }

    public String getNstar5() {
        return nstar5;
    }

    public String getAvgstar() {
        return avgstar;
    }

    public String getLocation_address() {
        return location_address;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public String getLocation_lng() {
        return location_lng;
    }

    public String getPlace_id() {
        return place_id;
    }

    public String getType_id() {
        return type_id;
    }

    public String getType_detail_name() {
        return type_detail_name;
    }

    public List<PhotosEntity> getPhotos() {
        return photos;
    }

    public List<ReviewEntity> getReview() {
        return review;
    }

    public static class PhotosEntity {
        private String photos_id;
        private String create_date;
        private String url;

        public void setPhotos_id(String photos_id) {
            this.photos_id = photos_id;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPhotos_id() {
            return photos_id;
        }

        public String getCreate_date() {
            return create_date;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class ReviewEntity {
        private String review_detail;
        private String review_datetime;
        private String user_fname;
        private String user_lname;
        private String user_fb_id;

        public void setReview_detail(String review_detail) {
            this.review_detail = review_detail;
        }

        public void setReview_datetime(String review_datetime) {
            this.review_datetime = review_datetime;
        }

        public void setUser_fname(String user_fname) {
            this.user_fname = user_fname;
        }

        public void setUser_lname(String user_lname) {
            this.user_lname = user_lname;
        }

        public void setUser_fb_id(String user_fb_id) {
            this.user_fb_id = user_fb_id;
        }

        public String getReview_detail() {
            return review_detail;
        }

        public String getReview_datetime() {
            return review_datetime;
        }

        public String getUser_fname() {
            return user_fname;
        }

        public String getUser_lname() {
            return user_lname;
        }

        public String getUser_fb_id() {
            return user_fb_id;
        }
    }
}
