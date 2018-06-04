package mapsoft.com.myretrofit.request;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * 用户角色返回body
 * Created by SmileXie on 16/7/15.
 */
@Root(name = "soapenv:Body", strict = false)
public class RequestBody {

    @Element(name = "GetPlaybillMain", required = false)
    public RequestModel getVideo;

    @Element(name = "E_station_line_Android", required = false)
    public RequestModel getLine;

    @Element(name = "E_Station_Picture", required = false)
    public RequestModel getAd;
}
