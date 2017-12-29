package com.hxww.heatmapproject.widget.listener;


import com.hxww.heatmapproject.widget.bean.DateBean;
import com.hxww.heatmapproject.widget.bean.DateType;

/**
 * Created by codbking on 2016/9/22.
 */

public interface WheelPickerListener {
     void onSelect(DateType type, DateBean bean);
}
