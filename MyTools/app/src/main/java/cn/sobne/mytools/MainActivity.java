package cn.sobne.mytools;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import cn.sobne.mybarcode.BarcodeActivity;
import cn.sobne.mycapitalnumber.CapitalActivity;
import cn.sobne.myconvertor.ConvertorActivity;

public class MainActivity extends AppCompatActivity {

    private Button mConvertor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
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
                switch(position)
                {
                    case 0:
                        startActivity(new Intent(MainActivity.this, ConvertorActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, BarcodeActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, CapitalActivity.class));
                        break;
                }
            }
        });

    }

    private ArrayList<HashMap<String, Object>> getImageItems()
    {
        int[] imageRes = {
                R.drawable.convertor,R.drawable.scan,R.drawable.zero
        };
        String[] name = {
                "进制转换","条码扫描","大写数字"
        };
        int length = imageRes.length;

        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", imageRes[i]);
            map.put("ItemText", name[i]);
            lstImageItem.add(map);
        }
        return lstImageItem;
    }

}
