package cn.sobne.mytools.adtx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.qq.e.ads.nativ.NativeAD;
import com.qq.e.ads.nativ.NativeADDataRef;
import com.qq.e.comm.constants.AdPatternType;
import com.qq.e.comm.util.AdError;
import com.qq.e.comm.util.GDTLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sobne.LedMonitorActivity;
import cn.sobne.mybarcode.BarcodeActivity;
import cn.sobne.mycapitalnumber.CapitalActivity;
import cn.sobne.myconvertor.ConvertorActivity;
import cn.sobne.mytools.MainActivity;
import cn.sobne.mytools.R;
import cn.sobne.mytools.unity.MainUtil;
import cn.sobne.mytools.unity.SyncEventListener;

public class NativeMainActivity extends MainActivity implements NativeAD.NativeAdListener {
    private static final String TAG = "NativeMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adtx_native_activity_main);
        initAd();
    }



    //adtx
    private void initAd(){
        $ = new AQuery(this);
        loadAD();
    }
    private NativeADDataRef adItem;
    private NativeAD nativeAD;
    protected AQuery $;
    @Override
    public void onADLoaded(List<NativeADDataRef> arg0) {
        if (arg0.size() > 0) {
            adItem = arg0.get(0);
            //showAD();
            Log.i("AD_DEMO", "原生广告加载成功");
        } else {
            Log.i("AD_DEMO", "NOADReturn");
        }
    }

    @Override
    public void onADStatusChanged(NativeADDataRef arg0) {
        $.id(R.id.btn_download).text(getADButtonText());
    }

    @Override
    public void onADError(NativeADDataRef adData, AdError error) {
        Log.i(
                "AD_DEMO",
                String.format("onADError, error code: %d, error msg: %s", error.getErrorCode(),
                        error.getErrorMsg()));
    }

    @Override
    public void onNoAD(AdError error) {
        Log.i(
                "AD_DEMO",
                String.format("onNoAD, error code: %d, error msg: %s", error.getErrorCode(),
                        error.getErrorMsg()));
    }


    public void loadAD() {
        if (this.nativeAD == null) {
            this.nativeAD = new NativeAD(this, Constants.APPID, Constants.NativePosID, this);
        }
        ArrayList<String> categories = new ArrayList<String>();
        nativeAD.setCategories(categories);
        int count = 1; // 一次拉取的广告条数：范围1-10
        nativeAD.loadAD(count);
    }

    /**
     * 展示原生广告时，一定要先调用onExposured接口曝光广告，否则将无法调用onClicked点击接口
     */
    public void showAD() {
        if (adItem.getAdPatternType() == AdPatternType.NATIVE_3IMAGE) {
            GDTLogger.d("show three img ad.");
            findViewById(R.id.native_3img_ad_container).setVisibility(View.VISIBLE);
            findViewById(R.id.native_ad_container).setVisibility(View.INVISIBLE);
            $.id(R.id.img_1).image(adItem.getImgList().get(0), false, true);
            $.id(R.id.img_2).image(adItem.getImgList().get(1), false, true);
            $.id(R.id.img_3).image(adItem.getImgList().get(2), false, true);
            $.id(R.id.native_3img_title).text(adItem.getTitle());
            $.id(R.id.native_3img_desc).text(adItem.getDesc());
        } else if (adItem.getAdPatternType() == AdPatternType.NATIVE_2IMAGE_2TEXT) {
            GDTLogger.d("show two img ad.");
            findViewById(R.id.native_3img_ad_container).setVisibility(View.INVISIBLE);
            findViewById(R.id.native_ad_container).setVisibility(View.VISIBLE);
            $.id(R.id.img_logo).image(adItem.getIconUrl(), false, true);
            $.id(R.id.img_poster).image(adItem.getImgUrl(), false, true);
            $.id(R.id.text_name).text(adItem.getTitle());
            $.id(R.id.text_desc).text(adItem.getDesc());
        } else if (adItem.getAdPatternType() == AdPatternType.NATIVE_1IMAGE_2TEXT) {
            GDTLogger.d("show one img ad.");
            findViewById(R.id.native_3img_ad_container).setVisibility(View.INVISIBLE);
            findViewById(R.id.native_ad_container).setVisibility(View.VISIBLE);
            $.id(R.id.img_logo).image( adItem.getImgUrl(), false, true);
            $.id(R.id.img_poster).clear();
            $.id(R.id.text_name).text(adItem.getTitle());
            $.id(R.id.text_desc).text(adItem.getDesc());
        }
        $.id(R.id.btn_download).text(getADButtonText());
        adItem.onExposured(this.findViewById(R.id.adtx_nativeADContainer)); // 需要先调用曝光接口
        $.id(R.id.btn_download).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adItem.onClicked(view); // 点击接口
            }
        });
    }

    /**
     * App类广告安装、下载状态的更新（普链广告没有此状态，其值为-1） 返回的AppStatus含义如下： 0：未下载 1：已安装 2：已安装旧版本 4：下载中（可获取下载进度“0-100”）
     * 8：下载完成 16：下载失败
     */
    private String getADButtonText() {
        if (adItem == null) {
            return "……";
        }
        if (!adItem.isAPP()) {
            return "查看详情";
        }
        switch (adItem.getAPPStatus()) {
            case 0:
                return "点击下载";
            case 1:
                return "点击启动";
            case 2:
                return "点击更新";
            case 4:
                return adItem.getProgress() > 0 ? "下载中" + adItem.getProgress()+ "%" : "下载中"; // 特别注意：当进度小于0时，不要使用进度来渲染界面
            case 8:
                return "下载完成";
            case 16:
                return "下载失败,点击重试";
            default:
                return "查看详情";
        }
    }
}
