package scut.carson_ho.rxjava_operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by Carson_Ho on 17/9/7.
 */

public class FilterUsage extends AppCompatActivity {

    private String TAG = "RxJava";


        /*
         * 联想搜索优化
         **/


    /*
     * 步骤1：设置控件变量 & 绑定
     **/
    EditText ed;
    TextView tv;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // 控件绑定
        ed = (EditText) findViewById(R.id.ed);
        tv = (TextView) findViewById(R.id.tv);

         /*
         * 说明
         * 1. 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher），需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，输入字符时都会发送数据事件（此处不会马上发送，因为使用了debounce（））
         * 3. 采用skip(1)原因：跳过 第1次请求 = 初始输入框的空字符状态
         **/
        RxTextView.textChanges(ed)
                .debounce(1, TimeUnit.SECONDS).skip(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharSequence>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(CharSequence charSequence) {
                        tv.setText("发送给服务器的字符 = " + charSequence.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应" );

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });




        /*
         * 功能防抖
         **/
        
//        // 注册控件
//        button = (Button)findViewById(R.id.button);
//
//        /*
//         * 1. 此处采用了RxBinding：RxView.clicks(button) = 对控件点击进行监听，需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
//         * 2. 传入Button控件，点击时，都会发送数据事件（但由于使用了throttleFirst（）操作符，所以只会发送该段时间内的第1次点击事件）
//         **/
//        RxView.clicks(button)
//                .throttleFirst(2, TimeUnit.SECONDS)  // 才发送 2s内第1次点击按钮的事件
//                .subscribe(new Observer<Object>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//                    @Override
//                    public void onNext(Object value) {
//                        Log.d(TAG, "发送了网络请求" );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应" + e.toString());
//                        // 获取异常错误信息
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//                });



    }
}
