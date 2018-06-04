package mapsoft.com.myretrofit;

import android.os.Bundle;
import android.util.Log;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import mapsoft.com.myretrofit.download.DownloadUtils;
import mapsoft.com.myretrofit.request.RequestBody;
import mapsoft.com.myretrofit.request.RequestEnvelope;
import mapsoft.com.myretrofit.request.RequestModel;
import mapsoft.com.myretrofit.response.ResponseEnvelope;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
   // private TextView mTextView;
    private List<Line> lines;

    private List<PlayInfo> mPlayInfos;


    private List<PictureAd> ads;

    private DownloadUtils mDownloadUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mTextView = findViewById(R.id.text_view);
        mDownloadUtils = new DownloadUtils(getApplicationContext());
        //getAdinfo();
        //download();
        mDownloadUtils.getVideoInfo();
        //getLineInfo();
        //new GetLineInfo().execute();
        mDownloadUtils.getAdinfo();
    }

   /* *//**
     * 根据路牌的编号获取经过线路的信息
     * 1、当能从服务器获取时，使用最新获取的数据绘制线路，并将数据保存到数据库中；
     * 2、服务器获取不到数据时，从数据库中获取数据绘制线路；
     *//*
    class GetLineInfo extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //loadingView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("verifycode", "FANGYUXI");
            map.put("estationid", 131);
            String responseXML;
            try {
                responseXML = KsoapUtil.ksoap(MainActivity.this,"/webservices/SiteServWebService.asmx" , map,
                        "E_station_line_Android", true);
            } catch (Exception e) {
                Log.i(TAG, "InitActivity-GetLineInfo-doInBackground：" + e.getMessage());
                return "fail";
            }
            return responseXML;
        }

        @Override
        protected void onPostExecute(String strser) {
            super.onPostExecute(strser);
                // 处理服务器获取的数据
                try {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(strser.getBytes("GB2312"));
                    lines = XmlParse.parseLine(inputStream);

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


        }
    }*/

    /*private void login(String userId, String password) {
        Observable<BaseEntity<User>> observable = RetrofitFactory.getInstance().login(userId, password);
        observable.compose(compose(this.<BaseEntity<User>>bindToLifecycle())).subscribe(new BaseObserver<User>(this) {
            @Override
            protected void onHandleSuccess(User user) {
                // 保存用户信息等操作
            }
        });
    }*/

   /* private void translation(){
        Observable<Translation> pObservable = RetrofitFactory.getInstance().getCall();
        pObservable.compose(compose(this.<Translation>bindToLifecycle())).subscribe(new BaseObserver<Translation>(this) {

            @Override
            protected void onHandleSuccess(Translation translation) {
                if (translation != null) {
                    mTextView.setText(translation.getContent());
                }

            }
        });
    }
    private void translation1(){
        Observable<Translation1> pObservable = RetrofitFactory.getInstance().getCall2("I love you");
        pObservable.compose(compose(this.<Translation1>bindToLifecycle())).subscribe(new BaseObserver<Translation1>(this) {

            @Override
            protected void onHandleSuccess(Translation1 translation) {
                if (translation != null) {
                    mTextView.setText(translation.getTranslateResult().get(0).get(0).getTgt());
                }

            }
        });
    }

    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // 步骤5:创建 网络请求接口 的实例
        RetrofitService request = retrofit.create(RetrofitService.class);

        //对 发送请求 进行封装
        Observable<Translation> observable = request.getCall();

        //步骤6:发送网络请求(异步)
       *//* call.enqueue(new Callback<Translation>() {
            //请求成功时候的回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                //请求处理,输出结果
                response.body().show();
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });*//*

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Translation translation) {
                        mTextView.setText(translation.getContent());
                    }
                });
    }*/






}


