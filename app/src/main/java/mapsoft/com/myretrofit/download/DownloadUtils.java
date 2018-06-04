package mapsoft.com.myretrofit.download;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mapsoft.com.myretrofit.FileHelper;
import mapsoft.com.myretrofit.ImageActivity;
import mapsoft.com.myretrofit.Line;
import mapsoft.com.myretrofit.PictureAd;
import mapsoft.com.myretrofit.PlayInfo;
import mapsoft.com.myretrofit.RetrofitGenerator;
import mapsoft.com.myretrofit.SPDownloadUtil;
import mapsoft.com.myretrofit.WeatherInterfaceApi;
import mapsoft.com.myretrofit.request.RequestBody;
import mapsoft.com.myretrofit.request.RequestEnvelope;
import mapsoft.com.myretrofit.request.RequestModel;
import mapsoft.com.myretrofit.response.ResponseEnvelope;
import okhttp3.ResponseBody;

/**
 * @author djl
 * @function
 */

public class DownloadUtils {

    private static final String TAG = "DownloadUtils";
    private FileHelper pFileHelper;

    private Context mContext;


    private List<Line> lines;

    private List<PlayInfo> mPlayInfos;


    private List<PictureAd> ads;

    public DownloadUtils(Context context) {
        mContext = context;
        pFileHelper = new FileHelper(mContext);
    }

    private void downloadVideo(final LinkedList<PlayInfo> playInfos) {
        long range = 0;
        final PlayInfo pInfo = playInfos.pollFirst();
        final String downloadUrl = pInfo.getPdpath() + pInfo.getPdfilename();
        final String mDownloadFileName = pInfo.getPdfilename();
        final File file = new File(pFileHelper.SDCardPath() + "Movies/" + mDownloadFileName);
        if (file.exists()) {
            range = SPDownloadUtil.getInstance().get(downloadUrl, 0);
            if (range == file.length()) {
                if (playInfos.size() == 0) {
                    return;
                }else {
                    downloadVideo(playInfos);
                }

                return;
            }
        }
        Observable<ResponseBody> pObservable = RetrofitGenerator.weatherInterfaceApi.downloadFile(downloadUrl);
        final long finalRange = range;
        pObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<ResponseBody, Object>() {

                    @Override
                    public Object apply(ResponseBody responseBody) throws Exception {
                        write(finalRange,responseBody,file,downloadUrl);
                        return responseBody;
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        downloadVideo(playInfos);
                    }

                    @Override
                    public void onComplete() {
                        if (playInfos.size() == 0) {
                            Toast.makeText(mContext.getApplicationContext(),"视频下载完成", Toast.LENGTH_SHORT).show();
                        }else {
                            downloadVideo(playInfos);
                        }
                    }
                });
    }

    public void write(long finalRange, ResponseBody responseBody, File file, String downloadUrl){
        RandomAccessFile randomAccessFile = null;
        InputStream inputStream = null;
        long total = finalRange;
        long responseLength = 0;
        try {
            byte[] buf = new byte[2048];
            int len = 0;
            responseLength = responseBody.contentLength();
            inputStream = responseBody.byteStream();
            randomAccessFile = new RandomAccessFile(file, "rwd");
            if (finalRange == 0) {
                randomAccessFile.setLength(responseLength);
            }
            randomAccessFile.seek(finalRange);
            while ((len = inputStream.read(buf)) != -1) {
                randomAccessFile.write(buf, 0, len);
                total += len;

            }

        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                SPDownloadUtil.getInstance().save(downloadUrl, total);
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void getAdinfo() {
        final RequestEnvelope requestEnvelop = new RequestEnvelope();
        RequestBody requestBody = new RequestBody();
        RequestModel requestModel = new RequestModel();
        requestModel.verifycode = "FANGYUXI";
        requestModel.estationid = "17";
        requestModel.company = "http://www.56gps.cn/";
        requestBody.getAd = requestModel;
        requestEnvelop.body = requestBody;
        WeatherInterfaceApi pInterfaceApi = RetrofitGenerator.getWeatherInterfaceApi();

        Observable<ResponseEnvelope> pObservable = pInterfaceApi.getAdInfo(requestEnvelop);

        pObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnNext(new Consumer<ResponseEnvelope>() {
                    @Override
                    public void accept(ResponseEnvelope responseEnvelope) throws Exception {

                    }
                })
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<ResponseEnvelope>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseEnvelope responseEnvelope) {
                        ads = responseEnvelope.mAds;

                        PictureAd[] pAds = new PictureAd[6];
                        for (int i = 0; i < pAds.length; i++) {
                            pAds[i] = new PictureAd();
                        }
                        pAds[0].setUrl("http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg");
                        pAds[1].setUrl("http://p2.gexing.com/G1/M00/14/03/rBACE1aTbCCSTsY5AAEIjDXspWQ96.jpeg");
                        pAds[2].setUrl("http://img.zcool.cn/community/0125fd5770dfa50000018c1b486f15.jpg@1280w_1l_2o_100sh.jpg");
                        pAds[3].setUrl("http://img.zcool.cn/community/010f87596f13e6a8012193a363df45.jpg@1280w_1l_2o_100sh.jpg");
                        pAds[4].setUrl("http://img.zcool.cn/community/01635d571ed29832f875a3994c7836.png@900w_1l_2o_100sh.jpg");
                        pAds[5].setUrl("http://pic.pptbz.com/201209/2014120951215081.jpg");
                        Collections.addAll(ads, pAds);
                        LinkedList<PictureAd> tmp = new LinkedList<>();
                        tmp.addAll(ads);
                        download(tmp);

                    }


                });
    }
    public void getVideoInfo() {
        final RequestEnvelope requestEnvelop = new RequestEnvelope();
        RequestBody requestBody = new RequestBody();
        RequestModel requestModel = new RequestModel();
        requestModel.verifycode = "FANGYUXI";
        requestModel.estationid = "131";
        requestModel.company = "http://www.56gps.cn/";
        requestBody.getVideo = requestModel;
        requestEnvelop.body = requestBody;

        Observable<ResponseEnvelope> pObservable = RetrofitGenerator.getWeatherInterfaceApi().getVideoInfo(requestEnvelop);

        pObservable.subscribeOn(Schedulers.io())

                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<ResponseEnvelope>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseEnvelope responseEnvelope) {
                        mPlayInfos = responseEnvelope.mInfos;
                        LinkedList<PlayInfo> tmp = new LinkedList<>();
                        tmp.addAll(mPlayInfos);
                        downloadVideo(tmp);
                    }

                });
    }



    private void download(final LinkedList<PictureAd> ad) {
        final String url = ad.pollFirst().getUrl();
        Observable<ResponseBody> pObservable = RetrofitGenerator.weatherInterfaceApi.downloadFile(url);
        pObservable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<ResponseBody, Object>() {

                    @Override
                    public Object apply(ResponseBody responseBody) throws Exception {
                        for (String s : url.split("/")){
                            if (s.endsWith(".png") || s.endsWith(".jpg") || s.endsWith(".jepg")){
                                writeResponseBodyToDisk(s,responseBody);
                            }
                        }
                        return responseBody;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        download(ad);
                    }

                    @Override
                    public void onComplete() {
                        if (ad.size() == 0) {
                            Intent pIntent = new Intent(mContext,ImageActivity.class);
                            pIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(pIntent);
                        }else {
                            download(ad);
                        }

                    }
                });

       /* File file = new File(getFilesDir(),"test.jpg");
        RequestUtil.downLoad(new FileDownLoadSubscriber(file) {
            @Override
            public void onSuccess(File file) {
                Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProgress(long current, long total) {
                mTextView.setText(current+"/"+total);
            }
        },"http://60.191.133.133:18006/File/turan_LOGO.mp4");*/



    }

    /**
     * 保存下载的图片流写入SD卡文件
     * @param imageName  xxx.jpg
     * @param body  image stream
     */
    public void writeResponseBodyToDisk(String imageName, ResponseBody body) {
        if(body==null){
            return;
        }

        try {
            InputStream is = body.byteStream();
            File fileDr = new File(pFileHelper.SDCardPath() + "Pictures");
            if (!fileDr.exists()) {
                fileDr.mkdir();
            }
            File file = new File(pFileHelper.SDCardPath() + "Pictures", imageName);
            if (file.exists()) {
                file.delete();
                file = new File(pFileHelper.SDCardPath() + "Pictures", imageName );
            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
