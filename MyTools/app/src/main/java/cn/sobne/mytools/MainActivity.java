package cn.sobne.mytools;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cn.sobne.myconvertor.ConvertorActivity;

public class MainActivity extends AppCompatActivity {

    private Button mConvertor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }
    private void init()
    {
        mConvertor =(Button)findViewById(R.id.btnConvertor);

        mConvertor.setOnClickListener(clickListener);
    }
    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnConvertor:{
                    startActivity(new Intent(MainActivity.this, ConvertorActivity.class));
                }
            }
        }
    };
}
