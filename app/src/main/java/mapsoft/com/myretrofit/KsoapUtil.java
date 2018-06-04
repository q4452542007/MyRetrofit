package mapsoft.com.myretrofit;

import android.content.Context;
import android.content.SharedPreferences;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Even_Kwok on 2015/12/25.
 */
public class KsoapUtil {

    public static String NAMESPACE = "http://www.56gps.cn/";
    private static String NOT_NETWORK = "没有网络";
    private static String NOT_SERVICE = "没有配置服务器";

    /**
     * WebService请求
     * @param context
     * @param serviceURL
     * @param paramsMap
     * @param method
     * @param isZip
     * @return
     * @throws IOException
     * @throws XmlPullParserException
     */
    public static String ksoap(Context context, String serviceURL, Map<String,Object> paramsMap,
                               String method, boolean isZip)
            throws IOException, XmlPullParserException {

        if(!Tool.isNetworkConnected(context)){
            return NOT_NETWORK;
        }


        String ip = "60.191.133.133";
        String port = "18006";

        /*if("".equals(ip) || "".equals(port)){
            return NOT_SERVICE;
        }*/
        // 实例化SoapObject对象
        SoapObject request = new SoapObject(NAMESPACE, method);
        // 设置调用方法参数
        if (paramsMap != null && paramsMap.size() > 0) {
            for (String str : paramsMap.keySet())
            request.addProperty(str, paramsMap.get(str));
        }
        // 设置SOAP请求信息(参数部分为SOAP协议版本号，与要调用的webService中版本号一致):
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet = true;

        String errorMsg;
        HttpTransportSE ht = new HttpTransportSE("http://"+ip+":"+port+serviceURL);
        String s = null;
        try {
            ht.call(NAMESPACE + method, envelope);
            if (envelope.getResponse() != null) {
                Object sp = envelope.getResponse();
                // 解压
                if (isZip) {
                    byte[] b = Base64.decode(sp.toString());
                    b = BZip2Utils.decompress(b);
                   // b = WebServiceUtil.unGZip(b);
                    s = new String(b, "UTF-16LE");
                } else {
                    s = sp.toString();
                }
            }
        } catch (IOException e) {
            errorMsg = method + " IOException:" + e.getMessage();
            throw new IOException(errorMsg);
        } catch (XmlPullParserException e) {
            errorMsg = method + " XmlPullParserException:" + e.getMessage();
            throw new XmlPullParserException(errorMsg);
        }
        return s;
    }


}
