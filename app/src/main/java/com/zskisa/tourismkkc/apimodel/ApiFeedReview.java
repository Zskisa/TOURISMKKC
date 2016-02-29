package com.zskisa.tourismkkc.apimodel;

import java.util.List;

public class ApiFeedReview {
    /**
     * status : success
     * data : {"action":"places_review","reason":"รายการรีวิว","result":[{"places_id":"000001","review_datetime":"2016-02-29 03:52:11","review_detail":"test","rate_value":"1","user_email":"test@test.com","user_fb_id":"","user_fname":"Test","user_lname":"Test"},{"places_id":"000001","review_datetime":"2016-02-29 03:51:10","review_detail":"test","rate_value":"4","user_email":"test@test.com","user_fb_id":"","user_fname":"Test","user_lname":"Test"},{"places_id":"000001","review_datetime":"2016-02-24 14:55:14","review_detail":"ทดสอบ","rate_value":"0","user_email":"jimmy29922@gmail.com","user_fb_id":"","user_fname":"Near","user_lname":"River"},{"places_id":"000001","review_datetime":"2016-02-24 14:55:12","review_detail":"ทดสอบ","rate_value":"0","user_email":"jimmy29922@gmail.com","user_fb_id":"","user_fname":"Near","user_lname":"River"}]}
     */

    private String status;
    /**
     * action : places_review
     * reason : รายการรีวิว
     * result : [{"places_id":"000001","review_datetime":"2016-02-29 03:52:11","review_detail":"test","rate_value":"1","user_email":"test@test.com","user_fb_id":"","user_fname":"Test","user_lname":"Test"},{"places_id":"000001","review_datetime":"2016-02-29 03:51:10","review_detail":"test","rate_value":"4","user_email":"test@test.com","user_fb_id":"","user_fname":"Test","user_lname":"Test"},{"places_id":"000001","review_datetime":"2016-02-24 14:55:14","review_detail":"ทดสอบ","rate_value":"0","user_email":"jimmy29922@gmail.com","user_fb_id":"","user_fname":"Near","user_lname":"River"},{"places_id":"000001","review_datetime":"2016-02-24 14:55:12","review_detail":"ทดสอบ","rate_value":"0","user_email":"jimmy29922@gmail.com","user_fb_id":"","user_fname":"Near","user_lname":"River"}]
     */

    private DataEntity data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String action;
        private String reason;
        /**
         * places_id : 000001
         * review_datetime : 2016-02-29 03:52:11
         * review_detail : test
         * rate_value : 1
         * user_email : test@test.com
         * user_fb_id :
         * user_fname : Test
         * user_lname : Test
         */

        private List<ResultEntity> result;

        public void setAction(String action) {
            this.action = action;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setResult(List<ResultEntity> result) {
            this.result = result;
        }

        public String getAction() {
            return action;
        }

        public String getReason() {
            return reason;
        }

        public List<ResultEntity> getResult() {
            return result;
        }

        public static class ResultEntity {
            private String places_id;
            private String review_datetime;
            private String review_detail;
            private String rate_value;
            private String user_email;
            private String user_fb_id;
            private String user_fname;
            private String user_lname;

            public void setPlaces_id(String places_id) {
                this.places_id = places_id;
            }

            public void setReview_datetime(String review_datetime) {
                this.review_datetime = review_datetime;
            }

            public void setReview_detail(String review_detail) {
                this.review_detail = review_detail;
            }

            public void setRate_value(String rate_value) {
                this.rate_value = rate_value;
            }

            public void setUser_email(String user_email) {
                this.user_email = user_email;
            }

            public void setUser_fb_id(String user_fb_id) {
                this.user_fb_id = user_fb_id;
            }

            public void setUser_fname(String user_fname) {
                this.user_fname = user_fname;
            }

            public void setUser_lname(String user_lname) {
                this.user_lname = user_lname;
            }

            public String getPlaces_id() {
                return places_id;
            }

            public String getReview_datetime() {
                return review_datetime;
            }

            public String getReview_detail() {
                return review_detail;
            }

            public String getRate_value() {
                return rate_value;
            }

            public String getUser_email() {
                return user_email;
            }

            public String getUser_fb_id() {
                return user_fb_id;
            }

            public String getUser_fname() {
                return user_fname;
            }

            public String getUser_lname() {
                return user_lname;
            }
        }
    }
}
