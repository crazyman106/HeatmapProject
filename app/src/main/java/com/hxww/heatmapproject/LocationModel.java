package com.hxww.heatmapproject;

import java.util.List;

/**
 * @author 作者：杨荣华
 * @date 创建时间：2017/11/20 18:06
 * @description 描述：
 */

public class LocationModel {

    /**
     * Code : 0
     * Data : [{"Id":14825,"Longitude":"116.657797","Latitude":"40.159617","Volume":41,"CreateTime":"2017-12-01 14:02"},{"Id":14826,"Longitude":"116.657797","Latitude":"40.159617","Volume":31,"CreateTime":"2017-12-01 14:02"},{"Id":14827,"Longitude":"116.657797","Latitude":"40.159617","Volume":31,"CreateTime":"2017-12-01 14:02"},{"Id":14828,"Longitude":"116.657797","Latitude":"40.159617","Volume":25,"CreateTime":"2017-12-01 14:02"}]
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
         * Id : 14825
         * Longitude : 116.657797
         * Latitude : 40.159617
         * Volume : 41.0
         * CreateTime : 2017-12-01 14:02
         */

        private int Id;
        private double Longitude;
        private double Latitude;
        private int Volume;
        private String CreateTime;
        private String UpLoadTime;
        private int count;
        private int originalVolume;
        private String Code;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public int getOriginalVolume() {
            return originalVolume;
        }

        public void setOriginalVolume(int originalVolume) {
            this.originalVolume = originalVolume;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getUpLoadTime() {
            return UpLoadTime;
        }

        public void setUpLoadTime(String upLoadTime) {
            UpLoadTime = upLoadTime;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double Longitude) {
            this.Longitude = Longitude;
        }

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double Latitude) {
            this.Latitude = Latitude;
        }

        public int getVolume() {
            return Volume;
        }

        public void setVolume(int Volume) {
            this.Volume = Volume;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
