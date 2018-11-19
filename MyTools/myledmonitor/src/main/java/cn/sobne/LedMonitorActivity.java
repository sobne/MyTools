package cn.sobne;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class LedMonitorActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    private String[] mStrings = { "Push up", "Push left", "Cross fade","Hyperspace" };
    private ViewFlipper mFlipper;
    private EditText et_led_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledmonitor);

        initViews();
    }
    private void initViews() {
        mFlipper = ((ViewFlipper) this.findViewById(R.id.flipper));
        mFlipper.startFlipping();
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mStrings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);

        et_led_text=(EditText)findViewById(R.id.et_led_text);
        Button generateQRCodeButton = (Button) this.findViewById(R.id.btn_led_dispaly);
        generateQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(LedMonitorActivity.this, MaqueeTextViewActivity.class));
                Intent intent = new Intent(LedMonitorActivity.this, MaqueeTextViewActivity.class);
                intent.putExtra("maqueetext", et_led_text.getText().toString());
                startActivity(intent);
            }
        });
    }
    /**
     * Spinner的item选择监听事件处理
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id) {
        switch (position) {
            case 0:// 文字从下进入，从上移出，伴随透明度变化
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.push_up_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.push_up_out));
                break;
            case 1:// 文字从右侧向左进入，从右侧移出，伴随透明度变化
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.push_left_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.push_left_out));
                break;
            case 2:// 文字透明度改变，从0-1-0
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        android.R.anim.fade_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        android.R.anim.fade_out));
                break;
            default:// 多维空间动画（复合动画效果）
                mFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.hyperspace_in));
                mFlipper.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.hyperspace_out));
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
        // DO NOTHING
    }
}
