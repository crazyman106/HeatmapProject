/*
 * Copyright 2014 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hxww.heatmapproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hxww.heatmapproject.widget.DatePickDialog;
import com.hxww.heatmapproject.widget.OnSureLisener;
import com.hxww.heatmapproject.widget.bean.DateType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HeatmapsActivity3 extends FragmentActivity implements OnMapReadyCallback {
    private static final int[] greenColor = {R.drawable.green1, R.drawable.green2, R.drawable.green3, R.drawable.green4, R.drawable.green5, R.drawable.green6, R.drawable.green7, R.drawable.green8, R.drawable.green9, R.drawable.green10, R.drawable.green11, R.drawable.green12, R.drawable.green13, R.drawable.green14, R.drawable.green15, R.drawable.green16, R.drawable.green17, R.drawable.green18, R.drawable.green19, R.drawable.green20, R.drawable.green21, R.drawable.green22, R.drawable.green23, R.drawable.green24, R.drawable.green25, R.drawable.green26, R.drawable.green27, R.drawable.green28, R.drawable.green29, R.drawable.green30};
    private static final int[] redColor = {R.drawable.red1, R.drawable.red2, R.drawable.red3, R.drawable.red4, R.drawable.red5, R.drawable.red6, R.drawable.red7, R.drawable.red8, R.drawable.red9, R.drawable.red10, R.drawable.red11, R.drawable.red12, R.drawable.red13, R.drawable.red14, R.drawable.red15, R.drawable.red16, R.drawable.red17, R.drawable.red18, R.drawable.red19, R.drawable.red20, R.drawable.red21, R.drawable.red22, R.drawable.red23, R.drawable.red24, R.drawable.red25, R.drawable.red26, R.drawable.red27, R.drawable.red28, R.drawable.red29, R.drawable.red30};
    private CompositeSubscription mCompositeSubscription;
    private ProgressDialog progressDialog;
    private GoogleMap googleMap;
    private SeekBar seekBar;
    private LinearLayout ll_start_time, ll_end_time;
    private TextView tv_start_time, tv_end_time, tv_time;
    private ImageView iv_plus, iv_minus, iv_view;
    private RecyclerView rv_time, rv_location;
    private String startTime;
    private String endTime;
    private Date startDate;
    private int differenceTime;
    private int myProgress;
    private List<LocationModel.DataBean> list;
    private List<LocationModel.DataBean> tempList;
    private DateAdapter dateAdapter;
    private List<LocationModel.DataBean> otherList = new ArrayList<>();
    private DotAdapter dotAdapter;
    private LocationManager locationManager;
    private Location location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heatmaps);
        initView();
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        initData();
    }


    private void initView() {
        ll_start_time = findViewById(R.id.ll_start_time);
        ll_end_time = findViewById(R.id.ll_end_time);
        tv_start_time = findViewById(R.id.tv_start_time);
        tv_end_time = findViewById(R.id.tv_end_time);
        tv_time = findViewById(R.id.tv_time);
        iv_plus = findViewById(R.id.iv_plus);
        iv_minus = findViewById(R.id.iv_minus);
        seekBar = findViewById(R.id.seekBar);
        iv_view = findViewById(R.id.iv_view);
        rv_time = findViewById(R.id.rv_time);
        rv_location = findViewById(R.id.rv_location);

        rv_time.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_location.setLayoutManager(linearLayoutManager);

        dateAdapter = new DateAdapter(this);
        rv_time.setAdapter(dateAdapter);

        dotAdapter = new DotAdapter(this);
        rv_location.setAdapter(dotAdapter);
        rv_location.addOnItemTouchListener(new OnRecyclerItemClickListener(rv_location) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                LocationModel.DataBean bean = otherList.get(position);
                LatLng latLng = new LatLng(bean.getLatitude(), bean.getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            }
        });

        tv_time.setText(DateUtils.getCurrentDate());

        iv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateList();
            }
        });

        ll_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv_time.getVisibility() == View.VISIBLE) {
                    rv_time.setVisibility(View.GONE);
                }
                selectTime(1);
            }
        });
        ll_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv_time.getVisibility() == View.VISIBLE) {
                    rv_time.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(startTime)) {
                    selectTime(2);
                } else {
                    ToastUtils.show("Please select start time");
                }
            }
        });
        iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv_time.getVisibility() == View.VISIBLE) {
                    rv_time.setVisibility(View.GONE);
                }
                if (myProgress < differenceTime) {
                    seekBar.setProgress(myProgress + 1);
                }
            }
        });
        iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv_time.getVisibility() == View.VISIBLE) {
                    rv_time.setVisibility(View.GONE);
                }
                if (myProgress > 0) {
                    seekBar.setProgress(myProgress - 1);
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (rv_time.getVisibility() == View.VISIBLE) {
                    rv_time.setVisibility(View.GONE);
                }
                myProgress = progress;
                if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
                    if (progress > 0) {
                        String startTempTime = DateUtils.addSecond(startTime + ":00", progress - 1);
                        String endTempTime = DateUtils.addSecond(startTime + ":00", progress);
                        tv_time.setText(startTempTime + "~" + endTempTime);
                        filter(startTempTime, endTempTime);
                    } else {
                        tv_time.setText(startTime);
                    }
                } else {
                    ToastUtils.show("Please select a time period");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void filter(String startTime, String endTime) {
        if (list != null && list.size() > 0) {
            tempList = new ArrayList<>();
            for (LocationModel.DataBean bean : list) {
                String upLoadTime = bean.getUpLoadTime();
                if (!TextUtils.isEmpty(upLoadTime)) {
                    if (DateUtils.compare2(upLoadTime, startTime) >= 0 && DateUtils.compare2(upLoadTime, endTime) <= 0) {
                        bean.setCount(1);
                        bean.setVolume(bean.getOriginalVolume());
                        tempList.add(bean);
                    }
                }
            }
            if (tempList != null && tempList.size() > 0) {
                for (int i = 0; i < tempList.size() - 1; i++) {
                    for (int j = tempList.size() - 1; j > i; j--) {
                        if (tempList.get(j).getLatitude() == tempList.get(i).getLatitude() && tempList.get(j).getLongitude() == tempList.get(i).getLongitude()) {
                            tempList.get(i).setVolume(tempList.get(i).getVolume() + tempList.get(j).getOriginalVolume());
                            int count = tempList.get(i).getCount();
                            tempList.get(i).setCount(count++);
                            tempList.remove(j);
                        }
                    }
                }
                rv_location.setVisibility(View.VISIBLE);
                otherList.clear();
                otherList = tempList;
                dotAdapter.setData(otherList);

                googleMap.clear();
                for (int i = 0; i < tempList.size(); i++) {
                    LocationModel.DataBean bean = tempList.get(i);
                    int volume = bean.getVolume() / bean.getCount();
                    int resId = 0;
                    if (volume >= 10 && volume <= 50) {
                        resId = R.mipmap.green1;
                    } else if (volume > 50 && volume <= 60) {
                        resId = R.mipmap.green2;
                    } else if (volume > 60 && volume <= 70) {
                         resId = R.mipmap.green3;
                    } else if (volume > 70 && volume <= 80) {
                        resId = R.mipmap.red3;
                    } else if (volume > 80 && volume <= 90) {
                        resId = R.mipmap.red2;
                    } else if (volume > 90) {
                        resId = R.mipmap.red1;
                    }
                    LatLng latLng = new LatLng(bean.getLatitude(), bean.getLongitude());
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(resId))
                            .title(volume + ""));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            } else {
                googleMap.clear();
                otherList.clear();
                rv_location.setVisibility(View.GONE);
                ToastUtils.show("No data from this time period");
                setLocation();
            }
        } else {
            ToastUtils.show("No data");
        }
    }

    private void selectTime(final int type) {
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("");
        //设置类型
        dialog.setType(DateType.TYPE_YMDHM);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);
        //设置点击确定按钮回调
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
                switch (type) {
                    case 1:
                        startDate = date;
                        startTime = format;
                        tv_start_time.setText(startTime);
                        break;
                    case 2:
                        Date tempDate = date;
                        String tempTime = format;
                        int compare = DateUtils.compare(tempTime, startTime);
                        if (compare > 0) {
                            int datePoor = DateUtils.getDatePoor(startDate, tempDate);
                            if (datePoor <= 30) {
                                differenceTime = datePoor * 2;
                                seekBar.setMax(differenceTime);
                                seekBar.setProgress(0);
                                endTime = format;
                                tv_time.setText(startTime);
                                tv_end_time.setText(endTime);
                            } else {
                                ToastUtils.show("Can choose only 30 minutes");
                            }
                        } else {
                            ToastUtils.show("End time must be greater than start time");
                        }
                        break;
                    default:
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        initLocation();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (rv_time.getVisibility() == View.VISIBLE) {
                    rv_time.setVisibility(View.GONE);
                }
            }
        });
        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                if (rv_time.getVisibility() == View.VISIBLE) {
                    rv_time.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initData() {
        progressDialog.setMessage("loading...");
        addSubscription(AppClient.getApiService().query(), new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                ToastUtils.show("network error");
            }

            @Override
            public void onNext(Object o) {
                progressDialog.dismiss();
/*                String json = "{\"Code\":0,\"Data\":[{\"UpLoadTime\":\"2017-12-07 10:40:01\",\"Id\":15124,\"Longitude\":\"-0.5909657478\",\"Latitude\":\"51.2411380018\",\"Volume\":40,\"CreateTime\":\"\"},{\"UpLoadTime\":\"2017-12-07 10:40:11\",\"Id\":15125,\"Longitude\":\"-0.5948925018\",\"Latitude\":\"51.2428977500\",\"Volume\":55,\"CreateTime\":\"\"},{\"UpLoadTime\":\"2017-12-07 10:40:11\",\"Id\":15126,\"Longitude\":\"-0.5915880203\",\"Latitude\":\"51.2421186332\",\"Volume\":65,\"CreateTime\":\"\"},{\"UpLoadTime\":\"2017-12-07 10:40:15\",\"Id\":15127,\"Longitude\":\"-0.5884766579\",\"Latitude\":\"51.2450201047\",\"Volume\":75,\"CreateTime\":\"\"},{\"UpLoadTime\":\"2017-12-07 10:40:25\",\"Id\":15128,\"Longitude\":\"-0.5849790573\",\"Latitude\":\"51.2450872663\",\"Volume\":85,\"CreateTime\":\"\"},{\"UpLoadTime\":\"2017-12-07 10:40:15\",\"Id\":15128,\"Longitude\":\"-0.5862450600\",\"Latitude\":\"51.2421186332\",\"Volume\":95,\"CreateTime\":\"\"}],\"Message\":\"成功\"}";
                LocationModel locationModel = new Gson().fromJson(json, LocationModel.class);*/
                LocationModel model = (LocationModel) o;
                list = model.getData();
                for (LocationModel.DataBean bean : list) {
                    bean.setCount(1);
                    bean.setOriginalVolume(bean.getVolume());
                }
            }
        });
    }

    private void getDateList() {
        addSubscription(AppClient.getApiService().queryTime(), new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show("network error");
            }

            @Override
            public void onNext(Object o) {
                DateModel model = (DateModel) o;
                if (model.getCode() == 0) {
                    List<DateModel.DataBean> dataList = model.getData();
                    if (dataList != null && dataList.size() > 0) {
                        rv_time.setVisibility(View.VISIBLE);
                        dateAdapter.setData(dataList);
                    }
                } else {
                    ToastUtils.show("No data");
                }
            }
        });
    }

    private void initLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            String provider = LocationManager.GPS_PROVIDER;
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            location = locationManager.getLastKnownLocation(provider);
            if (location == null) {
                if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                    String provider2 = LocationManager.NETWORK_PROVIDER;
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    location = locationManager.getLastKnownLocation(provider2);
                }
            }
            setLocation();
        } else {
            ToastUtils.show("Open the GPS can get accurate location");
        }
    }

    private void setLocation() {
        LatLng latLng;
        if (location != null) {
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            latLng = new LatLng(51.2421839000, -0.5905421000);
        }
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        onUnsubscribe();
    }

}
