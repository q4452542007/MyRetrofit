package mapsoft.com.myretrofit;

import org.kobjects.base64.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Content-Type", "text/xml;charset=UTF-8")   // 对于SOAP 1.1， 如果是soap1.2 应是Content-Type: application/soap+xml; charset=utf-8
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            Response response = chain.proceed(request);
            if (response.code() == 200 && GZIPUtils.isGzip(response.networkResponse().headers())) {
                //这里是网络拦截器，可以做错误处理
                MediaType mediaType = response.body().contentType();
                //当调用此方法java.lang.IllegalStateException: closed，原因为OkHttp请求回调中response.body().string()只能有效调用一次
                //String content = response.body().string();
                byte[] data = response.body().bytes();
                String oldString = new String(data);
                String soapAction = request.headers().get("SOAPAction").split("/")[3];
                String ss = Tool.subString(oldString, "<" + soapAction + "Result" +">",
                        "</" + soapAction + "Result" +">");
                if (GZIPUtils.isGzip(response.networkResponse().headers())) {
                    //请求头显示有gzip，需要解压
                    byte[] b = Base64.decode(ss);
                    data = BZip2Utils.decompress(b);
                }
                String tmp = new String(data, "UTF-16LE");
                //String tmp = oldString.replace(ss,new String(data,"UTF-16LE"));
                //String test = new String(tmp.getBytes(),3,tmp.getBytes().length,"UTF-8");
                return response.newBuilder()
                        .body(ResponseBody.create(mediaType, tmp.getBytes()))
                        .build();
            }
        return response;
        }

}