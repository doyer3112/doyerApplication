package doyer.wifiscaner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import doyer.wifiscaner.bean.SsidBean;
import doyer.wifiscaner.gen.DaoSession;
import doyer.wifiscaner.gen.SsidBeanDao;

/**
 * Created by jie.du on 16/11/1.
 */
public class WifiDataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<SsidBean> scanResults;
    private SsidBeanDao ssidBeanDao;
    private RecyclerAdapter recyclerAdapter;
    private List<String> scanStrings = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_wifi_save);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        initDao();
        scanResults = queryHistory();
        if (scanResults != null)
            for (SsidBean ssid : scanResults
                    ) {
                scanStrings.add(ssid.getSsid());
            }
        if (scanStrings.size() > 0) {
            recyclerAdapter = new RecyclerAdapter((ArrayList) scanStrings);
            recyclerView.setAdapter(recyclerAdapter);
        }

    }

    private void initDao() {
        DaoSession daoSession = BaseApplication.getDaoSession();
        ssidBeanDao = daoSession.getSsidBeanDao();
    }

    private ArrayList<SsidBean> queryHistory() {
        Query query = ssidBeanDao.queryBuilder().build();
        List<SsidBean> scanResultList = query.list();
        if (scanResultList.size() > 0)
            return (ArrayList<SsidBean>) scanResultList;
        else return null;

    }
}
