package mapsoft.com.myretrofit;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * Created by Even_Kwok on 2016/12/9.
 * 站点对象
 */
@Root(name = "station", strict = false)
public class Station {

    @Attribute(name = "index")
    private int index;      //站点序号
    @Attribute(name = "name")
    private String name;    //站点名称
    @Attribute(name = "id")
    private int id;  //站点ID
    @Attribute(name = "subway")
    private String subway;//地铁号
    @Attribute(name = "latitude")
    private float latitude; //纬度
    @Attribute(name = "longitude")
    private float longitude;//经度

    public int getSequence() {
        return index;
    }

    public void setSequence(int sequence) {
        this.index = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStationId() {
        return id;
    }

    public void setStationId(int stationId) {
        this.id = stationId;
    }

    public String getSubway() {
        return subway;
    }

    public void setSubway(String subway) {
        this.subway = subway;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
