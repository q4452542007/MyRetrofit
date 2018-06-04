package mapsoft.com.myretrofit;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * 线路对象
 */
@Root(name = "line", strict = false)
public class Line {

    @ElementList(name = "line", inline = true, required = false)
    private List<Station> stations; //站点集合
    @Attribute(required = false)
    private String sSta_Summers;    //夏令首站首末班时间
    @Attribute(required = false)
    private String eSta_Summers;      //夏令末站首末班时间
    @Attribute(required = false)
    private String sSta_Winters;    //冬令首站首末班时间
    @Attribute(required = false)
    private String eSta_Winters;      //冬令末站首末班时间
    @Attribute(required = false)
    private String id;             //线路ID
    @Attribute(required = false)
    private String name;        //线路名
    @Attribute(required = false)
    private int staCount;      //站点
    @Attribute(required = false)
    private float price;            //票价
    @Attribute(required = false)
    private String comment;         //备注
    @Attribute(required = false)
    private int curstaid;           //当前站点ID
    @Element(required = false)
    private VehicleInfo vehicleInfo;//线路数车辆位置信息
    @Attribute(required = false)
    private String isUP;
    @Attribute(required = false)
    private int index;      //站点序号
   /* @Attribute(name = "name")
    private String name;    //站点名称*/
    /*@Attribute(name = "id")
    private int stationId;  //站点ID*/
    @Attribute(required = false)
    private String subway;//地铁号
    @Attribute(required = false)
    private float latitude; //纬度
    @Attribute(required = false)
    private float longitude;//经度

   /* @Attribute(name = "index")
    private int index;
    @Attribute(name = "latitude")
    private String latitude;
    @Attribute(name = "longitude")
    private float longitude;//经度
*/
    // 警报演示字段
    private int warnning;           //0:取消警报；1：火警；2：水警；

    public String getsSta_Summers() {
        return sSta_Summers;
    }

    public void setsSta_Summers(String sSta_Summers) {
        this.sSta_Summers = sSta_Summers;
    }

    public String geteSta_Summers() {
        return eSta_Summers;
    }

    public void seteSta_Summers(String eSta_Summers) {
        this.eSta_Summers = eSta_Summers;
    }

    public String getsSta_Winters() {
        return sSta_Winters;
    }

    public void setsSta_Winters(String sSta_Winters) {
        this.sSta_Winters = sSta_Winters;
    }

    public String geteSta_Winters() {
        return eSta_Winters;
    }

    public void seteSta_Winters(String eSta_Winters) {
        this.eSta_Winters = eSta_Winters;
    }

    public String getLineId() {
        return id;
    }

    public void setLineId(String lineId) {
        this.id = lineId;
    }

    public String getLineName() {
        return name;
    }

    public void setLineName(String lineName) {
        this.name = lineName;
    }

    public int getStationCounts() {
        return staCount;
    }

    public void setStationCounts(int stationCounts) {
        this.staCount = stationCounts;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

  /*  public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }*/

    public int getCurstaid() {
        return curstaid;
    }

    public void setCurstaid(int curstaid) {
        this.curstaid = curstaid;
    }

    public VehicleInfo getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(VehicleInfo vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

    public int getWarnning() {
        return warnning;
    }

    public void setWarnning(int warnning) {
        this.warnning = warnning;
    }
}
