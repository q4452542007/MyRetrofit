package mapsoft.com.myretrofit;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * @author djl
 * @function
 */
@Root(name = "picture")
public class PictureAd {
    @Attribute(required = false)
    private int seq;
    @Attribute(required = false)
    private String url;
    @Attribute(required = false)
    private String startdate;
    @Attribute(required = false)
    private String enddate;
    @Attribute(required = false)
    private String starttime;
    @Attribute(required = false)
    private String endtime;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
