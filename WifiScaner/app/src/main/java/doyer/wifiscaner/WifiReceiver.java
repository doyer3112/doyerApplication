package doyer.wifiscaner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jie.du on 16/12/5.
 */

public class WifiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {//在此监听wifi有无
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);

            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    Log.d("jie.du", "onReceive: WIFI_STATE_DISABLED");
                    Toast.makeText(context, "WIFI_STATE_DISABLED",
                            Toast.LENGTH_LONG).show();
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    Log.d("jie.du", "onReceive: WIFI_STATE_DISABLING");
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    Toast.makeText(context, "WIFI_STATE_ENABLED",
                            Toast.LENGTH_LONG).show();
                    Log.d("jie.du", "onReceive: WIFI_STATE_ENABLED");
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    Log.d("jie.du", "onReceive: WIFI_STATE_ENABLING");
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    Log.d("jie.du", "onReceive: WIFI_STATE_UNKNOWN");
                    break;
            }
        }
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state == NetworkInfo.State.CONNECTED;// 当然，这边可以更精确的确定状态
                if (isConnected) {
                    Log.d("jie.du", "onReceive: "+ networkInfo.getSubtypeName());
                } else {

                }
            }
        }
    }
}
