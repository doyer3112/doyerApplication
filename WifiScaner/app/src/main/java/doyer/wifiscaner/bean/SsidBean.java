package doyer.wifiscaner.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jie.du on 16/11/1.
 */

@Entity
public class SsidBean {
    @Id(autoincrement = true)
    private Long id;
    private String ssid;

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    @Generated(hash = 1707341531)
    public SsidBean(Long id, String ssid) {
        this.id = id;
        this.ssid = ssid;
    }

    @Generated(hash = 274775066)
    public SsidBean() {
    }
}
