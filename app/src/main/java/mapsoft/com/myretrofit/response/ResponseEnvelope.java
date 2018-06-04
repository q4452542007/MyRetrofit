package mapsoft.com.myretrofit.response;

import android.graphics.Picture;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.NamespaceList;
import org.simpleframework.xml.Root;

import java.util.List;

import mapsoft.com.myretrofit.Line;
import mapsoft.com.myretrofit.PictureAd;
import mapsoft.com.myretrofit.PlayInfo;

/**
 * 用户角色返回总信息
 * Created by SmileXie on 16/7/15.
 */
@Root
/*
@NamespaceList({
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
        @Namespace(reference = "http://www.w3.org/2001/XMLSchema", prefix = "xsd"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/encoding/", prefix = "enc"),
        @Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/", prefix = "soapenv")
})*/
public class ResponseEnvelope {

    @ElementList(name = "line", inline = true, required = false)
    public List<Line> lins;

    @ElementList(name = "item", inline = true, required = false)
    public List<PlayInfo> mInfos;

    @ElementList(name = "picture", inline = true, required = false)
    public List<PictureAd> mAds;
}
