package com.hxww.heatmapproject.widget.bean;

/**
 * Created by wulang on 2016/9/22.
 */

public enum DateType {

    TYPE_ALL("yyyy-MM-dd E hh:mm"),
    TYPE_YMDHM("yyyy-MM-dd hh:mm"),
    TYPE_YMDH("yyyy-MM-dd hh"),
    TYPE_YMD("yyyy-MM-dd"),
    TYPE_HM("hh:mm");
    private String format;

    DateType(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

}
