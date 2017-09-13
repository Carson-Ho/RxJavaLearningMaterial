package scut.carson_ho.rxjava_operators.EstablishOperator.Demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import scut.carson_ho.rxjava_operators.R;

/**
 * Created by Carson_Ho on 17/9/8.
 * 实战系列：无条件轮询
 */

public class RxJavafixRxjava extends AppCompatActivity {

    private static final String TAG = "Rxjava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * 步骤1：采用interval（）延迟发送
         **/
        Observable.interval(2,1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                 /*
                  * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                  * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                  **/
                 .doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long integer) throws Exception {
                Log.d(TAG, "第 " + integer + " 次轮询" );

                 /*
                  * 步骤3：通过Retrofit发送网络请求
                  **/
                  // a. 创建Retrofit对象
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                        .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                        .build();

                  // b. 创建 网络请求接口 的实例
                GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

                  // c. 采用Observable<...>形式 对 网络请求 进行封装
                  Observable<Translation> observable = request.getCall();
                  // d. 通过线程切换发送网络请求
                  observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                        .subscribe(new Observer<Translation>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Translation result) {
                                // e.接收服务器返回的数据
                                result.show() ;
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "请求失败");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });


    }
}
