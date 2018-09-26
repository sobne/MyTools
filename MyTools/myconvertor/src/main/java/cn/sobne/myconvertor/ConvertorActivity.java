package cn.sobne.myconvertor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class ConvertorActivity extends AppCompatActivity {

    private EditText et_bit,et_oct,et_des,et_hex;
    private TextWatcher tw_bit,tw_oct,tw_des,tw_hex;
    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertor);

        init();
    }
    private void init()
    {
        et_bit=(EditText)findViewById(R.id.et_bit);
        et_oct=(EditText)findViewById(R.id.et_oct);
        et_des=(EditText)findViewById(R.id.et_des);
        et_hex=(EditText)findViewById(R.id.et_hex);



        tw_bit =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    int i =Integer.parseInt(s.toString(),2);
                    flag=2;
                    et_des.setText(String.valueOf(i));
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
        tw_oct=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    int i =Integer.parseInt(s.toString(),8);
                    flag=8;
                    et_des.setText(String.valueOf(i));
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };
        tw_des=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                et_bit.removeTextChangedListener(tw_bit);
                et_oct.removeTextChangedListener(tw_oct);
                et_hex.removeTextChangedListener(tw_hex);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int i=Integer.parseInt(s.toString());
                    if(flag !=2) et_bit.setText(Integer.toBinaryString(i));
                    if(flag !=8) et_oct.setText(Integer.toOctalString(i));
                    if(flag !=16) et_hex.setText(Integer.toHexString(i));
                    flag=10;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                et_bit.addTextChangedListener(tw_bit);
                et_oct.addTextChangedListener(tw_oct);
                et_hex.addTextChangedListener(tw_hex);
            }
        };
        tw_hex=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    int i =Integer.parseInt(s.toString(),16);
                    flag=16;
                    et_des.setText(String.valueOf(i));
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        et_bit.addTextChangedListener(tw_bit);
        et_oct.addTextChangedListener(tw_oct);
        et_des.addTextChangedListener(tw_des);
        et_hex.addTextChangedListener(tw_hex);
    }
}
