package scut.carson_ho.rxjava_operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function3;

/**
 * Created by Carson_Ho on 17/9/6.
 */

public class CombineUsage extends AppCompatActivity {

    private String TAG = "RxJava";


    /*
     * 步骤1：设置控件变量 & 绑定
     **/
    EditText name,age,job;
    Button list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        job = (EditText) findViewById(R.id.job);
        list = (Button) findViewById(R.id.list);

        /*
         * 步骤2：为每个EditText设置被观察者，用于发送监听事件
         * 说明：
         * 1. 此处采用了RxBinding，需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
         * 2. 传入EditText控件，点击任1个EditText撰写时，都会发送数据事件 = Function3（）的返回值（下面会详细说明）
         * 3. 采用skip(1)原因：跳过 一开始EditText无任何输入时的空值
         **/
        Observable<CharSequence> nameObservable = RxTextView.textChanges(name).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(age).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(job).skip(1);

        /*
         * 步骤3：通过combineLatest（）合并事件 & 联合判断
         **/
        Observable.combineLatest(nameObservable,ageObservable,jobObservable,new Function3<CharSequence, CharSequence, CharSequence,Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence, @NonNull CharSequence charSequence2, @NonNull CharSequence charSequence3) throws Exception {

                /*
                 * 步骤4：规定表单信息输入不能为空
                 **/
                // 1. 姓名信息
                boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) ;
                // 除了设置为空，也可设置长度限制
                // boolean isUserNameValid = !TextUtils.isEmpty(name.getText()) && (name.getText().toString().length() > 2 && name.getText().toString().length() < 9);

                // 2. 年龄信息
                boolean isUserAgeValid = !TextUtils.isEmpty(age.getText());
                // 3. 职业信息
                boolean isUserJobValid = !TextUtils.isEmpty(job.getText()) ;

                /*
                 * 步骤5：返回信息 = 联合判断，即3个信息同时已填写，"提交按钮"才可点击
                 **/
                return isUserNameValid && isUserAgeValid && isUserJobValid;
            }

                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean s) throws Exception {
                /*
                 * 步骤6：返回结果 & 设置按钮可点击样式
                 **/
                Log.e(TAG, "提交按钮是否可点击： "+s);
                list.setEnabled(s);
            }
        });


//        /*
//         * 联合判断
//         * 如，填写表单时，需要表单里所有信息（姓名、年龄、职业等）都被填写后，才允许点击 "提交" 按钮
//         **/
//
//        /*
//         * 设置第1个Observable：表单信息1 = 姓名
//         * 此处仅作模拟：true = 已填写；false = 未填写
//         **/
//        Boolean isNameFinish = true;
//        Observable< Boolean> name = Observable.just(isNameFinish);
//
//        /*
//         * 设置第2个Observable：表单信息2 = 年龄
//         **/
//        Boolean isAgeFinish = true;
//        Observable< Boolean> age = Observable.just(isAgeFinish);
//
//        /*
//         * 设置第3个Observable：表单信息3 = 职业
//         **/
//        Boolean isJobFinish = false;
//        Observable<Boolean> job = Observable.just(isJobFinish);




         /*
         * 合并数据源 & 统一展示
         **/

//        // 用于存放最终展示的数据
//        String result = "数据源来自 = " ;
//
//
//        /*
//         * 设置第1个Observable：通过网络获取数据
//         * 此处仅作网络请求的模拟
//         **/
//        Observable<String> network = Observable.just("网络");
//
//        /*
//         * 设置第2个Observable：通过本地文件获取数据
//         * 此处仅作本地文件请求的模拟
//         **/
//        Observable<String> file = Observable.just("本地文件");
//
//
//        /*
//         * 通过merge（）合并事件 & 同时发送事件
//         **/
//        Observable.merge(network, file)
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String value) {
//                        Log.d(TAG, "数据源有： "+ value  );
//                        result += value + "+";
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    // 接收合并事件后，统一展示
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "获取数据完成");
//                        Log.d(TAG,  result  );
//                    }
//                });





        /*
         * 获取缓存数据
         **/
//
//        // 该2变量用于模拟内存缓存 & 磁盘缓存中的数据
//        String memoryCache = null;
//        String diskCache = "从磁盘缓存中获取数据";
//
//        /*
//         * 设置第1个Observable：检查内存缓存是否有该数据的缓存
//         **/
//        Observable<String> memory = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//
//                // 先判断内存缓存有无数据
//                if (memoryCache != null) {
//                    // 若有该数据，则发送
//                    emitter.onNext(memoryCache);
//                } else {
//                    // 若无该数据，则直接发送结束事件
//                    emitter.onComplete();
//                }
//
//            }
//        });
//
//        /*
//         * 设置第2个Observable：检查磁盘缓存是否有该数据的缓存
//         **/
//        Observable<String> disk = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//
//                // 先判断磁盘缓存有无数据
//                if (diskCache != null) {
//                    // 若有该数据，则发送
//                    emitter.onNext(diskCache);
//                } else {
//                    // 若无该数据，则直接发送结束事件
//                    emitter.onComplete();
//                }
//
//            }
//        });
//
//        /*
//         * 设置第3个Observable：通过网络获取数据
//         **/
//        Observable<String> network = Observable.just("从网络中获取数据");
//        // 此处仅作网络请求的模拟
//
//
//        /*
//         * 通过concat（） 和 firstElement（）操作符实现缓存功能
//         **/
//
//        // 1. 通过concat（）合并memory、disk、network 3个被观察者的事件（即检查内存缓存、磁盘缓存 & 发送网络请求）
//        //    并将它们按顺序串联成队列
//        Observable.concat(memory, disk, network)
//                // 2. 通过firstElement()，从串联队列中取出并发送第1个有效事件（Next事件），即依次判断检查memory、disk、network
//                .firstElement()
//                // 即本例的逻辑为：
//                // a. firstElement()取出第1个事件 = memory，即先判断内存缓存中有无数据缓存；由于memoryCache = null，即内存缓存中无数据，所以发送结束事件（视为无效事件）
//                // b. firstElement()继续取出第2个事件 = disk，即判断磁盘缓存中有无数据缓存：由于diskCache ≠ null，即磁盘缓存中有数据，所以发送Next事件（有效事件）
//                // c. 即firstElement()已发出第1个有效事件（disk事件），所以停止判断。
//
//                // 3. 观察者订阅
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept( String s) throws Exception {
//                        Log.d(TAG,"最终获取的数据来源 =  "+ s);
//                    }
//                });
    }
}

