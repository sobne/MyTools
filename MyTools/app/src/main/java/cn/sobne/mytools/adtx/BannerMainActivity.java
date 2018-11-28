package cn.sobne.mytools.adtx;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.comm.util.AdError;

import cn.sobne.mytools.MainActivity;
import cn.sobne.mytools.R;

public class BannerMainActivity extends MainActivity{
    private static final String TAG = "BannerMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showBannerAd();
        showInterstitialAD();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //adtx
    private void showBannerAd(){
        ViewGroup bannerContainer = (ViewGroup) this.findViewById(R.id.adtx_bannerContainer);
        BannerView bv = new BannerView(this, ADSize.BANNER, Constants.APPID,Constants.BannerPosID);
        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
        bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {
            @Override
            public void onNoAD(AdError error) {
                Log.e("AD_DEMO", "err:"+error.toString());
            }
            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        bannerContainer.addView(bv);
        bv.loadAD();
    }
    //ad InterstitialAD
    private void showInterstitialAD() {
        final InterstitialAD iad = new InterstitialAD(this, Constants.APPID, Constants.InterteristalPosID);
        iad.setADListener(new AbstractInterstitialADListener() {
            @Override
            public void onNoAD(AdError error) {
                Log.e("InterstitialAD", "err:"+error.toString());
            }
            @Override
            public void onADReceive() {
                Log.i("InterstitialAD", "onADReceive");
                iad.show();
            }
        });
        iad.loadAD();
    }
}
