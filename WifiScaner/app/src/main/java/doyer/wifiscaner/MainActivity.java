package doyer.wifiscaner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import doyer.wifiscaner.bean.SsidBean;
import doyer.wifiscaner.gen.DaoSession;
import doyer.wifiscaner.gen.SsidBeanDao;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_WIFI = 001;
    private String TAG = "doyer";
    private ArrayList<ScanResult> scanResults;
    private List<String> scanStrings = new ArrayList<>();
    private WifiManager wifiManager;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recycler;
    private SsidBeanDao ssidBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recycler = (RecyclerView) findViewById(R.id.recycler_wifi);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recycler.setLayoutManager(layoutManager);

        setSupportActionBar(toolbar);
        initDao();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Start Scan Wifi", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startWifiScan();
                            }
                        }).show();
            }
        });
    }

    private void initDao() {
        DaoSession daoSession = BaseApplication.getDaoSession();
        ssidBeanDao = daoSession.getSsidBeanDao();
    }

    private void startWifiScan() {
        if (wifiManager == null)
            wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
            wifiManager.startScan();
        }
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED
                    ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE}, PERMISSION_WIFI);
                return;
            } else {
                getScanResults();
            }
        } else getScanResults();


    }

    private void getScanResults() {
        if (scanResults != null && scanResults.size() > 0)
            scanResults.clear();
        scanResults = (ArrayList<ScanResult>) wifiManager.getScanResults();
        if (scanResults.size() > 0) {
            for (ScanResult scanresult : scanResults
                    ) {
                String ssid = scanresult.SSID;
                scanStrings.add(ssid);
                Log.d(TAG, "startWifiScan: " + ssid);
            }
            HashSet<String> hashSet = new HashSet<>(scanStrings);
            scanStrings.clear();
            scanStrings.addAll(hashSet);
        }
        recyclerAdapter = new RecyclerAdapter((ArrayList) scanStrings);
        recycler.setAdapter(recyclerAdapter);
        saveWifiSsid(scanResults);
    }

    private void saveWifiSsid(final List<ScanResult> scanResults) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ScanResult scan : scanResults
                        ) {
                    SsidBean ssid;
                    try {
                        // Try to find the Example-Object if it is already existing.
                        ssid = ssidBeanDao.queryBuilder().where(SsidBeanDao.Properties.Ssid.eq(scan.SSID)).unique();
                    } catch (Exception ex) {
                        ssid = null;
                    }
                    if (ssid == null) {
                        ssid = new SsidBean(null, scan.SSID);
                        ssidBeanDao.insertOrReplace(ssid);
                    }
// update oldObj with the data from JSON
                }
            }
        }).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, WifiDataActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_WIFI) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取所有可用的位置提供器
                getScanResults();
            } else {
                Toast.makeText(MainActivity.this, permissions[0] + "获取位置被拒", Toast.LENGTH_LONG).show();
            }
        }

    }


}
