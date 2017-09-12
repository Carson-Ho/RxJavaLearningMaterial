package scut.carson_ho.rxjava_operators.CombineOperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import scut.carson_ho.rxjava_operators.R;

/**
 * Created by Carson_Ho on 17/9/12.
 * 合并数据源 & 统一展示
 */

public class UsageDemo2 extends AppCompatActivity {

    private String TAG = "RxJava";
    // 用于存放最终展示的数据
    private String result = "数据源来自 = " ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combine);

        /*
         * 设置第1个Observable：通过网络获取数据
         * 此处仅作网络请求的模拟
         **/
        Observable<String> network = Observable.just("网络");

        /*
         * 设置第2个Observable：通过本地文件获取数据
         * 此处仅作本地文件请求的模拟
         **/
        Observable<String> file = Observable.just("本地文件");

        /*
         * 通过merge（）合并事件 & 同时发送事件
         **/
        Observable.merge(network, file)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.d(TAG, "数据源有： "+ value  );
                        result += value + "+";
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    // 接收合并事件后，统一展示
                    @Override
                    public void onComplete() {
                        Log.d(TAG, "获取数据完成");
                        Log.d(TAG,  result  );
                    }
                });
    }
}