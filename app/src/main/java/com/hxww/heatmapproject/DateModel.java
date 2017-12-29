package com.hxww.heatmapproject;

import java.util.List;

/**
 * @author 作者：杨荣华
 * @date 创建时间：2017/12/5 14:03
 * @description 描述：
 */

public class DateModel {

    /**
     * Code : 0
     * Data : [{"Id":1,"Code":"B4:0B:44:D0:34:371512453703919","BeginTime":"2017-12-05 14:01:30","EndTime":"2017-12-05 14:01:43","Remark1":"","Remark2":"","Remark3":"","Remark4":"","Remark5":""},{"Id":2,"Code":"B4:0B:44:D0:34:371512454035442","BeginTime":"2017-12-05 14:06:33","EndTime":"2017-12-05 14:07:15","Remark1":"","Remark2":"","Remark3":"","Remark4":"","Remark5":""}]
     * Message : 成功
     */

    private int Code;
    private String Message;
    private List<DataBean> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * Id : 1
         * Code : B4:0B:44:D0:34:371512453703919
         * BeginTime : 2017-12-05 14:01:30
         * EndTime : 2017-12-05 14:01:43
         * Remark1 :
         * Remark2 :
         * Remark3 :
         * Remark4 :
         * Remark5 :
         */

        private int Id;
        private String Code;
        private String BeginTime;
        private String EndTime;
        private String Remark1;
        private String Remark2;
        private String Remark3;
        private String Remark4;
        private String Remark5;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getBeginTime() {
            return BeginTime;
        }

        public void setBeginTime(String BeginTime) {
            this.BeginTime = BeginTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String EndTime) {
            this.EndTime = EndTime;
        }

        public String getRemark1() {
            return Remark1;
        }

        public void setRemark1(String Remark1) {
            this.Remark1 = Remark1;
        }

        public String getRemark2() {
            return Remark2;
        }

        public void setRemark2(String Remark2) {
            this.Remark2 = Remark2;
        }

        public String getRemark3() {
            return Remark3;
        }

        public void setRemark3(String Remark3) {
            this.Remark3 = Remark3;
        }

        public String getRemark4() {
            return Remark4;
        }

        public void setRemark4(String Remark4) {
            this.Remark4 = Remark4;
        }

        public String getRemark5() {
            return Remark5;
        }

        public void setRemark5(String Remark5) {
            this.Remark5 = Remark5;
        }
    }
}
