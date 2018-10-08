package cn.sobne.mybarcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.encode.CodeCreator;


public class BarcodeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SCAN=0;
    private TextView resultTextView;
    private EditText qrStrEditText;
    private ImageView qrImgImageView,barcodeImageView;
    private CheckBox mCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
        qrStrEditText = (EditText) this.findViewById(R.id.et_qr_string);
        qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);
        mCheckBox = (CheckBox) findViewById(R.id.logo);
        barcodeImageView=(ImageView)findViewById(R.id.iv_barcode_image);

        Button scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);
        scanBarCodeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BarcodeActivity.this, CaptureActivity.class);
//                ZxingConfig config = new ZxingConfig();
//                config.setPlayBeep(true);//是否播放扫描声音 默认为true
//                config.setShake(true);//是否震动  默认为true
//                config.setDecodeBarCode(false);//是否扫描条形码 默认为true
//                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为淡蓝色
//                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
//                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
//                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });

        Button generateQRCodeButton = (Button) this.findViewById(R.id.btn_add_qrcode);
        generateQRCodeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String contentString = qrStrEditText.getText().toString().trim();
                if (TextUtils.isEmpty(contentString)) {
                    Toast.makeText(BarcodeActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Bitmap logo =mCheckBox.isChecked() ? BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) : null;
                    Bitmap bitmap = CodeCreator.createQRCode(contentString, 400, 400, logo);
                    qrImgImageView.setImageBitmap(bitmap);
                    Bitmap barcode = QRCodeUtil.creatBarcode( getApplicationContext(),contentString, 1000, 400,false);
                    barcodeImageView.setImageBitmap(barcode);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                resultTextView.setText(content);
            }
        }
    }



}
