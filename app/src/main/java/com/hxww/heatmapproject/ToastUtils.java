package com.hxww.heatmapproject;

import android.widget.Toast;

/**
 * @author 作者：杨荣华
 * @date 创建时间：2017/7/24 18:00
 * @description 描述：
 */

public class ToastUtils {
    private static Toast toast;
    public static void show(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
