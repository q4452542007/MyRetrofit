package mapsoft.com.myretrofit;


import io.reactivex.Observable;
import mapsoft.com.myretrofit.request.RequestBody;
import mapsoft.com.myretrofit.request.RequestEnvelope;

import mapsoft.com.myretrofit.response.ResponseEnvelope;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 接口请求
 * Created by SmileXie on 16/7/15.
 */
public interface WeatherInterfaceApi {

    @Headers({"Content-Type: text/xml;charset=UTF-8", "SOAPAction: http://www.56gps.cn/E_station_line_Android"
            , "Connection: Close"})//请求的Action，类似于方法名
    @POST("/webservices/SiteServWebService.asmx")
    Observable<ResponseEnvelope> E_station_line_Android(@Body RequestEnvelope requestEnvelope);

    @Headers({"Content-Type: text/xml;charset=UTF-8", "SOAPAction: http://www.56gps.cn/E_Station_Picture"
            , "Connection: Close"})//请求的Action，类似于方法名
    @POST("/webservices/SiteServWebService.asmx")
    Observable<ResponseEnvelope> getAdInfo(@Body RequestEnvelope requestEnvelope);

    @Headers({"Content-Type: text/xml;charset=UTF-8", "SOAPAction: http://www.56gps.cn/GetPlaybillMain"
            , "Connection: Close"})//请求的Action，类似于方法名
    @POST("/webservices/SiteServWebService.asmx")
    Observable<ResponseEnvelope> getVideoInfo(@Body RequestEnvelope requestEnvelope);


    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String   fileUrl);


}
