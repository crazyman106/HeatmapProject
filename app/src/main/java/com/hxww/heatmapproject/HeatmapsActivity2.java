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
import android.graphics.Color;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class HeatmapsActivity2 extends BaseActivity {
    private CompositeSubscription mCompositeSubscription;
    private HeatmapTileProvider mProvider;
    private ProgressDialog progressDialog;
    private List<WeightedLatLng> weightedLatLngList = new ArrayList<>();
    /**
     * Alternative heatmap gradient (blue -> red)
     * Copied from Javascript version
     */
    private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
            Color.argb(0, 0, 255, 255),
            Color.argb(255 / 3 * 2, 0, 255, 255),
            Color.rgb(0, 191, 255),
            Color.rgb(0, 0, 127),
            Color.rgb(255, 0, 0)
    };

    public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = {
            0.0f, 0.10f, 0.20f, 0.60f, 1.0f
    };
    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(ALT_HEATMAP_GRADIENT_COLORS,
            ALT_HEATMAP_GRADIENT_START_POINTS);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_heatmaps;
    }

    @Override
    protected void startDemo() {
        getMap().getUiSettings().setMapToolbarEnabled(false);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        initData();
    }

    protected void placeOnMap(double latitude, double longitude) {
        if (longitude == 0 || latitude == 0) {
            longitude = 143;
            latitude = -25;
        }
        LatLng latLng = new LatLng(latitude, longitude);
        getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
    }

    private void initData() {
        progressDialog.setMessage("加载中...");
        addSubscription(AppClient.getApiService().query(), new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                progressDialog.dismiss();
                Toast.makeText(HeatmapsActivity2.this, "请检查网络连接", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Object o) {
                progressDialog.dismiss();
                LocationModel model = (LocationModel) o;
                if (model.getCode() == 0) {
                    List<LocationModel.DataBean> data = model.getData();
                    WeightedLatLng weightedLatLng;
                    LatLng latLng;
                    if (data != null && data.size() > 0) {
                        for (LocationModel.DataBean bean : data) {
                            int volume = (int) bean.getVolume();
                            double weight;
                            if (volume <= 40) {
                                weight = 0.0f;
                            } else if (volume > 40 && volume <= 45) {
                                weight = 0.10f;
                            } else if (volume > 45 && volume <= 50) {
                                weight = 0.20f;
                            } else if (volume > 55 && volume <= 60) {
                                weight = 0.60f;
                            } else {
                                weight = 1.0f;
                            }
                            double longitude = bean.getLongitude();
                            double latitude = bean.getLatitude();
                            latLng = new LatLng(latitude, longitude);
                            weightedLatLng = new WeightedLatLng(latLng, weight);
                            weightedLatLngList.add(weightedLatLng);
                        }
                        placeOnMap(data.get(data.size() - 1).getLatitude(), data.get(data.size() - 1).getLongitude());
                        mProvider = new HeatmapTileProvider.Builder()
                                .weightedData(weightedLatLngList)
                                .gradient(ALT_HEATMAP_GRADIENT)
                                .build();
                        getMap().addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
                    }
                }
            }
        });
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
