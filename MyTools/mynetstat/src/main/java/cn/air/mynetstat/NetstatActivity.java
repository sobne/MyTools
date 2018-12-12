package cn.air.mynetstat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetstatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netstat);

        init();
    }
    private void init() {

        TelephonyManager phone = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        ConnectivityManager connectionManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();

        setEditText(R.id.lianwangname,networkInfo.getTypeName());

        setEditText(R.id.ip,wifi.getConnectionInfo().getIpAddress()+"");
        setEditText(R.id.wifimac, wifi.getConnectionInfo().getMacAddress());
        setEditText(R.id.getssid,wifi.getConnectionInfo().getSSID());
        setEditText(R.id.getbssid,wifi.getConnectionInfo().getBSSID());

        setEditText(R.id.simoperatorname, phone.getSimOperatorName());


        final EditText et_ip=(EditText) this.findViewById(R.id.et_ip);
        final TextView tv_ping=(TextView) this.findViewById(R.id.tv_ping);
        final TextView tv_ping_state=(TextView) this.findViewById(R.id.tv_ping_state);

        tv_ping.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                String ip=et_ip.getText().toString();
                //Log.d("ping",ip);
                if(TextUtils.isEmpty(ip)){
                    tv_ping_state.setText("请输入ip");
                    return;
                }
                tv_ping.setEnabled(false);
                if(isNodeReachable(ip)){
                    tv_ping_state.setText("能连接");
                }else{
                    tv_ping_state.setText("不能连接");
                }
                tv_ping.setEnabled(true);
            }
        });
    }

    private void setEditText(int id, String s) {
        ((TextView) this.findViewById(id)).setText(s);
    }
    public boolean isNodeReachable(String hostname) {
        try {
            return 0==Runtime.getRuntime().exec("ping -c 1 "+hostname).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean isHostReachable(String host, Integer timeOut) {
        try {
            return InetAddress.getByName(host).isReachable(timeOut);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
