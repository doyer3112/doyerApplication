package doyer.wifiscaner.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jie.du on 16/10/31.
 */

@Entity
public class WifiInfo {

    @Id(autoincrement = true)
    private long id;
    private String ssid;
    private String bssid;
    private String capabilities;

    public String getBssid() {
        return this.bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    @Generated(hash = 480127554)
    public WifiInfo(long id, String ssid, String bssid, String capabilities) {
        this.id = id;
        this.ssid = ssid;
        this.bssid = bssid;
        this.capabilities = capabilities;
    }

    @Generated(hash = 1003716208)
    public WifiInfo() {
    }

}
