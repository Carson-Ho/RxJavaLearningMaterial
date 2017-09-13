package scut.carson_ho.rxjava_operators.FunctionOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import scut.carson_ho.rxjava_operators.R;
import scut.carson_ho.rxjava_operators.EstablishOperator.Demo.GetRequest_Interface;
import scut.carson_ho.rxjava_operators.EstablishOperator.Demo.Translation;

/**
 * Created by Carson_Ho on 17/9/9.
 * Demo for 有条件轮询
 */

public class RxJavafixRetrofit extends AppCompatActivity {

    private static final String TAG = "Rxjava";

    // 设置变量 = 模拟轮询服务器次数
    private int i = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                        // 步骤1：创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                                .build();

                        // 步骤2：创建 网络请求接口 的实例
                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

                        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
                        Observable<Translation> observable = request.getCall();

                        // 步骤4：发送网络请求 & 通过repeatWhen（）进行轮询
                        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                            @Override
                            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
                            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                                // 此处有2种情况：
                                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                                    @Override
                                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                                        if (i > 3) {
                                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                                            return Observable.error(new Throwable("轮询结束"));
                                        }
                                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                                        return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                                    }
                                });

                            }
                        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                    }

                                    @Override
                                    public void onNext(Translation result) {
                                        // e.接收服务器返回的数据
                                        result.show() ;
                                        i++;
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        // 获取轮询结束信息
                                        Log.d(TAG,  e.toString());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    }
    }

