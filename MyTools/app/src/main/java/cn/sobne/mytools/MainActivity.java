package cn.sobne.mytools;

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

import java.util.ArrayList;
import java.util.HashMap;

import cn.air.mynetstat.NetstatActivity;
import cn.sobne.LedMonitorActivity;
import cn.sobne.mybarcode.BarcodeActivity;
import cn.sobne.mycapitalnumber.CapitalActivity;
import cn.sobne.myconvertor.ConvertorActivity;
import cn.sobne.mytaxticket.TaxTicketActivity;
import cn.sobne.mytools.adtx.ContentADActivity;
import cn.sobne.mytools.unity.AppUnity;
import cn.sobne.mytools.unity.MainUtil;
import cn.sobne.mytools.unity.SyncEventListener;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    private Button mConvertor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initEvents();//test
    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
//                case R.id.btnConvertor:{
//                    startActivity(new Intent(MainActivity.this, ConvertorActivity.class));
//                }
            }
        }
    };
    private void initEvents(){
        MainUtil mainUtil=new MainUtil(this);
        mainUtil.addSyncEventListener(new SyncEventListener() {
            @Override
            public void OnSynced(boolean synced) {
                Log.d(TAG, synced+"==OnSynced==callServer");
                if(synced){
                    Toast.makeText(getApplicationContext(), "finish ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mainUtil.callServer();
    }
    private void init()
    {
//        mConvertor =(Button)findViewById(R.id.btnConvertor);
//        mConvertor.setOnClickListener(clickListener);

        GridView gridview = (GridView) findViewById(R.id.gvmain);
        ArrayList<HashMap<String, Object>> lstImageItem=getImageItems();
        SimpleAdapter saImageItems = new SimpleAdapter(this,
                lstImageItem,
                R.layout.item,
                new String[]{"ItemImage", "ItemText"},
                new int[]{R.id.img_convertor, R.id.txt_convertor});
        gridview.setAdapter(saImageItems);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(items[position])
                {
                    case 0:
                        startActivity(new Intent(MainActivity.this, CapitalActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, LedMonitorActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, BarcodeActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, ConvertorActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this, NetstatActivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(MainActivity.this, TaxTicketActivity.class));
                        break;
                    case 6:
                        if(AppUnity.AdId==1){
                            startActivity(new Intent(MainActivity.this, ContentADActivity.class));
                        }
                        break;
                }
            }
        });

    }
    //aaaddd
    private int[] items={0,1,2,3,4,5};
    private ArrayList<HashMap<String, Object>> getImageItems()
    {
        int[] imageRes = {
                R.drawable.zero,R.drawable.maquee,R.drawable.scan,
                R.drawable.convertor,R.drawable.netstat,R.drawable.invoice,
                R.drawable.contentad
        };
        String[] name = {
                "大写数字","跑马灯","条码扫描",
                "进制转换","设备状态","发票抬头",
                "发现好玩"
        };

        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i:items) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", imageRes[i]);
            map.put("ItemText", name[i]);
            lstImageItem.add(map);
        }
        return lstImageItem;
    }
}
