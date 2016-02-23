package com.zskisa.tourismkkc.apimodel;

import java.util.List;

public class ApiFeed {

    /**
     * status : success
     * data : {"action":"rating_places","reason":"เข้าระบบสำเร็จ","result":[{"places_id":"000001","places_name":"กลางป่า","places_desc":"กลางป่า","avgstar":"0","type_detail_name":"สถานที่ท่องเที่ยวเชิงธรรมชาติ","location_address":"ซอย ดรุณสำราญ ตำบล ในเมือง อำเภอเมืองขอนแก่น ขอนแก่น 40000 ประเทศไทย","location_lat":"16.42866500","location_lng":"102.82711730","photo_link":""},{"places_id":"000003","places_name":"กลางป่า3","places_desc":"","avgstar":"0","type_detail_name":"สถานที่ท่องเที่ยวเชิงธรรมชาติ","location_address":"ซอย ดรุณสำราญ ตำบล ในเมือง อำเภอเมืองขอนแก่น ขอนแก่น 40000 ประเทศไทย","location_lat":"16.42866500","location_lng":"102.82711730","photo_link":"http://localhost/TourismKKC/api/photo000014"}]}
     */
    private String status;

    /**
     * action : rating_places
     * reason : เข้าระบบสำเร็จ
     * result : [{"places_id":"000001","places_name":"กลางป่า","places_desc":"กลางป่า","avgstar":"0","type_detail_name":"สถานที่ท่องเที่ยวเชิงธรรมชาติ","location_address":"ซอย ดรุณสำราญ ตำบล ในเมือง อำเภอเมืองขอนแก่น ขอนแก่น 40000 ประเทศไทย","location_lat":"16.42866500","location_lng":"102.82711730","photo_link":""},{"places_id":"000003","places_name":"กลางป่า3","places_desc":"","avgstar":"0","type_detail_name":"สถานที่ท่องเที่ยวเชิงธรรมชาติ","location_address":"ซอย ดรุณสำราญ ตำบล ในเมือง อำเภอเมืองขอนแก่น ขอนแก่น 40000 ประเทศไทย","location_lat":"16.42866500","location_lng":"102.82711730","photo_link":"http://localhost/TourismKKC/api/photo000014"}]
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
         * places_name : กลางป่า
         * places_desc : กลางป่า
         * avgstar : 0
         * type_detail_name : สถานที่ท่องเที่ยวเชิงธรรมชาติ
         * location_address : ซอย ดรุณสำราญ ตำบล ในเมือง อำเภอเมืองขอนแก่น ขอนแก่น 40000 ประเทศไทย
         * location_lat : 16.42866500
         * location_lng : 102.82711730
         * photo_link :
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
            private String places_name;
            private String places_desc;
            private String avgstar;
            private String type_detail_name;
            private String location_address;
            private String location_lat;
            private String location_lng;
            private String photo_link;

            public void setPlaces_id(String places_id) {
                this.places_id = places_id;
            }

            public void setPlaces_name(String places_name) {
                this.places_name = places_name;
            }

            public void setPlaces_desc(String places_desc) {
                this.places_desc = places_desc;
            }

            public void setAvgstar(String avgstar) {
                this.avgstar = avgstar;
            }

            public void setType_detail_name(String type_detail_name) {
                this.type_detail_name = type_detail_name;
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

            public void setPhoto_link(String photo_link) {
                this.photo_link = photo_link;
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

            public String getAvgstar() {
                return avgstar;
            }

            public String getType_detail_name() {
                return type_detail_name;
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

            public String getPhoto_link() {
                return photo_link;
            }
        }
    }
}
