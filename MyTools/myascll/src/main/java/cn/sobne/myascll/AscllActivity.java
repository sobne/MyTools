package cn.sobne.myascll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class AscllActivity extends AppCompatActivity {

    private EditText et_char,et_des,et_hex;
    private TextWatcher tw_char,tw_des,tw_hex;
    private int flag=0;
    private String splitBank=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ascll);

        init();
    }

    private void init()
    {
        et_char=(EditText)findViewById(R.id.et_char);
        et_hex=(EditText)findViewById(R.id.et_hex);
        et_des=(EditText)findViewById(R.id.et_des);

        tw_char=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    flag=8;
                    et_des.setText(string2Ascii(s.toString(),splitBank));
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
                et_char.removeTextChangedListener(tw_char);
                et_hex.removeTextChangedListener(tw_hex);
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if(s.toString().endsWith(" ")) return;
                    if(flag !=8) et_char.setText(ascii2String(s.toString(),splitBank));
                    if(flag !=16) et_hex.setText(ascii2StringHex(s.toString(),splitBank));
                    flag=10;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                et_char.addTextChangedListener(tw_char);
                et_hex.addTextChangedListener(tw_hex);
            }
        };
        tw_hex=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    if(s.toString().endsWith(" ")) return;
                    flag=16;
                    et_des.setText(stringHex2Ascll(s.toString(),splitBank));
                }catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        };

        et_char.addTextChangedListener(tw_char);
        et_des.addTextChangedListener(tw_des);
        et_hex.addTextChangedListener(tw_hex);
    }
    public String string2Ascii(String value,String split)
    {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(i != chars.length - 1) sbu.append((int)chars[i]).append(split);
            else sbu.append((int)chars[i]);
        }
        return sbu.toString();
    }
    public String ascii2String(String value,String split)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(split);
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
    public String ascii2StringHex(String value,String split)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(split);
        for (int i = 0; i < chars.length; i++) {
            if(i==0) sbu.append(Integer.toHexString(Integer.parseInt(chars[i])));
            else sbu.append(split+Integer.toHexString(Integer.parseInt(chars[i])));
        }
        return sbu.toString();
    }
    public String stringHex2Ascll(String value,String split){
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(split);
        for (int i = 0; i < chars.length; i++) {
            if(i==0) sbu.append(String.valueOf(Integer.parseInt(chars[i],16)));
            else sbu.append(split+String.valueOf(Integer.parseInt(chars[i],16)));
        }
        return sbu.toString();
    }
}
