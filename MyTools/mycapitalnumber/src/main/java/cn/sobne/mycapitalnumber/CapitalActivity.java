package cn.sobne.mycapitalnumber;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigDecimal;

import utils4a.AudioUtils;
import utils4j.Number2Zh;

public class CapitalActivity extends AppCompatActivity {
    private EditText input,output;
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btnDot,btnAC,btnX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital);

        input = (EditText) findViewById(R.id.input);
        output = (EditText) findViewById(R.id.output);
        btn0 = (Button) findViewById(R.id.button0);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        btnDot = (Button) findViewById(R.id.buttonDot);
        btnAC = (Button) findViewById(R.id.buttonAC);
        btnX = (Button) findViewById(R.id.buttonX);

        btn0.setOnClickListener(listener);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
        btn7.setOnClickListener(listener);
        btn8.setOnClickListener(listener);
        btn9.setOnClickListener(listener);
        btnDot.setOnClickListener(listener);
        btnAC.setOnClickListener(listener);
        btnX.setOnClickListener(listener);

        AudioUtils.init(getApplicationContext());
        AudioUtils.add(1,R.raw.msg);
        AudioUtils.addSound(2,"beep.wav");
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.button0) {
                num(0);
            } else if (i == R.id.button1) {
                num(1);
            } else if (i == R.id.button2) {
                num(2);
            } else if (i == R.id.button3) {
                num(3);
            } else if (i == R.id.button4) {
                num(4);
            } else if (i == R.id.button5) {
                num(5);
            } else if (i == R.id.button6) {
                num(6);
            } else if (i == R.id.button7) {
                num(7);
            } else if (i == R.id.button8) {
                num(8);
            } else if (i == R.id.button9) {
                num(9);
            } else if (i == R.id.buttonDot) {
                dot();
            } else if (i == R.id.buttonAC) {
                clear();
            } else if (i == R.id.buttonX) {
                backspace();
            } else {
            }

            input.setText(strInput);
            if(!TextUtils.isEmpty(strInput)){
                String strOutput=convertCapital(strInput);
                output.setText(String.valueOf(strOutput));
                if(strOutput.length()>10){
                    output.setTextSize(TypedValue.COMPLEX_UNIT_SP,28);
                }else{
                    output.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimensionPixelSize(R.dimen.sp_38));
                }
            }
        }
    };
    private String strInput = "0";
    private Boolean flagDot = false;
    private void dot() {
        if (!flagDot) {
            strInput = strInput + ".";
            flagDot = true;
        }
    }
    private void backspace(){
        if(strInput.equals("0")) return;
        if(strInput.length()==1) {
            strInput="0";
            return;
        }
        strInput=strInput.substring(0,strInput.length()-1);
        if(strInput.indexOf(".")==-1){
            flagDot=false;
        }
    }
    private void clear() {
        strInput = "0";
        flagDot = false;
    }
    private void num(int i) {
        if(flagDot){
            if(strInput.length()-strInput.indexOf(".")>2 ||strInput.length()>19){
                AudioUtils.play(2);
                return;
            }
        }else if(strInput.length()>16){
            //AudioUtils.play(1);
            vibrateandmedia();
            return;
        }
        strInput =(strInput.equals("0")?"":strInput) + String.valueOf(i);
    }

    private String convertCapital(String str){
        BigDecimal numberOfMoney = new BigDecimal(str);
        return Number2Zh.number2CNMontrayUnit(numberOfMoney);
    }


    private MediaPlayer mMediaPlayer;
    private void releaseMedia(){
        if(mMediaPlayer!=null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }
    private void vibrateandmedia()
    {
        Vibrator mVibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        mVibrator.vibrate(500);
        releaseMedia();
        mMediaPlayer=new MediaPlayer();
        try{
            mMediaPlayer.setDataSource(CapitalActivity.this,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mMediaPlayer.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        releaseMedia();
        super.onDestroy();
    }
}
