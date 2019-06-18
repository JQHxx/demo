package wenran.com.baselibrary.server;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wenran.com.baselibrary.bean.BaseApi;
import wenran.com.baselibrary.server.retrofiti.AddCookiesInterceptor;
import wenran.com.baselibrary.server.retrofiti.ReceivedCookiesInterceptor;

/**@author crowhine */
public class RetrofitManager {
    /**
     * 保存一个retrofit的实例，通过吸（baseUrl来获取）
     */
    private HashMap<String, Retrofit> mRetrofitHashMap = new HashMap<>();
    /**默认的超时时间*/
    private static final int DEFAULT_MILLISECONDS = 3000;


    /**
     * 内部类单列设计模式
     */
    private RetrofitManager() {
    }

    private static class RetrofitManagerInstance {
        private final static RetrofitManager RETROFIT_MANAGER = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitManagerInstance.RETROFIT_MANAGER;
    }

    /**
     * 获取retrofit的实例
     *
     * @return Retrofit
     */
    private Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit;

        if (mRetrofitHashMap.containsKey(baseUrl)) {
            retrofit = mRetrofitHashMap.get(baseUrl);
        } else {
            retrofit = createRetrofit(baseUrl);
        }

        return retrofit;
    }

    /**
     * 创建retrofit
     *
     * @return Retrofit
     */
    private Retrofit createRetrofit(String baseUrl) {

        OkHttpClient httpClient = new OkHttpClient().newBuilder()
                .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor())
                .retryOnConnectionFailure(true)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(httpClient)
                .build();
    }


    /**
     *根据各模块业务接口 获取不同的retrofit service接口对象
     */
    public <T> T getRetrofitService(Class<T> cls) {

        return createRetrofit(BaseApi.getBaseUrl()).create(cls);
    }


}
