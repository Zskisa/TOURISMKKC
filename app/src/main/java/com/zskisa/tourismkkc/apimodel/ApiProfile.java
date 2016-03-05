package com.zskisa.tourismkkc.apimodel;

import java.util.List;

public class ApiProfile {

    /**
     * status : success
     * data : {"action":"edit_profile","reason":"เข้าระบบสำเร็จ","result":[{"user_email":"test@test.com","type_detail_id":"01"},{"user_email":"test@test.com","type_detail_id":"04"},{"user_email":"test@test.com","type_detail_id":"05"}]}
     */

    private String status;
    /**
     * action : edit_profile
     * reason : เข้าระบบสำเร็จ
     * result : [{"user_email":"test@test.com","type_detail_id":"01"},{"user_email":"test@test.com","type_detail_id":"04"},{"user_email":"test@test.com","type_detail_id":"05"}]
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
         * user_email : test@test.com
         * type_detail_id : 01
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
            private String user_email;
            private String type_detail_id;

            public void setUser_email(String user_email) {
                this.user_email = user_email;
            }

            public void setType_detail_id(String type_detail_id) {
                this.type_detail_id = type_detail_id;
            }

            public String getUser_email() {
                return user_email;
            }

            public String getType_detail_id() {
                return type_detail_id;
            }
        }
    }
}
