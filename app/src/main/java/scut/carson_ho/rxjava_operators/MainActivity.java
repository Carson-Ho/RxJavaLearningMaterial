package scut.carson_ho.rxjava_operators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {

    private String TAG = "RxJava";
    Integer i = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                // 隔段事件发送时间
                e.onNext(1);
                Thread.sleep(500);

                e.onNext(2);

                Thread.sleep(1500);

                e.onNext(3);
                Thread.sleep(1500);

                e.onNext(4);
                Thread.sleep(500);
                e.onNext(5);
                Thread.sleep(500);
                e.onNext(6);
                Thread.sleep(1500);

                e.onComplete();
            }
        }).throttleWithTimeout(1, TimeUnit.SECONDS)//每1秒中采用数据
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.d(TAG, "接收到了事件"+ value  );
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






//        // 使用2：获取位置索引大于发送事件序列时，设置默认参数
//        Observable.just(1, 2, 3, 4, 5)
//                .elementAt(6,10)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept( Integer integer) throws Exception {
//                        Log.d(TAG,"获取到的事件元素是： "+ integer);
//                    }
//                });
//
//
//        // 获取的位置索引 ＞ 发送事件序列长度时，抛出异常
//        Observable.just(1, 2, 3, 4, 5)
//                .elementAtOrError(6)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept( Integer integer) throws Exception {
//                        Log.d(TAG,"获取到的事件元素是： "+ integer);
//                    }
//                });



//        // 参数说明：
//        // 参数1 = 第1次延迟时间；
//        // 参数2 = 间隔时间数字；
//        // 参数3 = 时间单位；
//        Observable.interval(3,1,TimeUnit.SECONDS)
//                // 该例子发送的事件序列特点：延迟3s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "开始采用subscribe连接");
//                    }
//                    // 默认最先调用复写的 onSubscribe（）
//
//                    @Override
//                    public void onNext(Long value) {
//                        Log.d(TAG, "接收到了事件"+ value  );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//
//                });



//        // 参数说明：
//        // 参数1 = 事件序列起始点；
//        // 参数2 = 事件数量；
//        // 注：若设置为负数，则会抛出异常
//        Observable.range(3,10)
//                // 该例子发送的事件序列特点：从3开始发送，每次发送事件递增1，一共发送10个事件
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "开始采用subscribe连接");
//                    }
//                    // 默认最先调用复写的 onSubscribe（）
//
//                    @Override
//                    public void onNext(Integer value) {
//                        Log.d(TAG, "接收到了事件"+ value  );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//
//                });


//
//        Observable.sequenceEqual(
//                Observable.just(4,5,6),
//                Observable.just(4,5,6)
//        )
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept( Boolean aBoolean) throws Exception {
//                        Log.d(TAG,"2个Observable是否相同："+ aBoolean);
//                        // 输出返回结果
//                    }
//                });


//        // （原始）第1个Observable：每隔1s发送1个数据 = 从0开始，每次递增1
//        Observable.interval(1, TimeUnit.SECONDS)
//                // 第2个Observable：延迟5s后开始发送1个Long型数据
//                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "开始采用subscribe连接");
//                    }
//
//                    @Override
//                    public void onNext(Long value) {
//                        Log.d(TAG, "接收到了事件"+ value  );
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "对Error事件作出响应");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "对Complete事件作出响应");
//                    }
//
//                });






//
//        // 1. 每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
//        Observable.interval(1, TimeUnit.SECONDS)
//                // 2. 通过takeWhile传入一个判断条件
//                .takeWhile(new Predicate<Long>(){
//                    @Override
//                    public boolean test( Long integer) throws Exception {
//                        return (integer<3);
//                        // 当发送的数据满足<3时，才发送Observable的数据
//                    }
//                }).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//            }
//
//            @Override
//            public void onNext(Long value) {
//                Log.d(TAG,"发送了事件 "+ value);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        });






//        Observable.just(1,2,3,4,5,6)
//                .map(new Function<Integer,String>() {
//                    @Override
//                    public String apply(Integer value) throws Exception {
//                        File file = new File(value);
//                        file.createNewFile();
//                        return "aa";
//                    }
//                });



    }




//        Observable.just(1,2,3,4,5,6)
//                .all(new Func2<Integer, Boolean>() {
//                    @Override
//                    public Boolean apply(Integer integer, @NonNull Object o2) throws Exception {
//                        return integer<=10;
//                        // 该函数用于判断Observable发送的10个数据是否都满足integer<=10
//                    }
//
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer<=10;
//                        // 该函数用于判断Observable发送的10个数据是否都满足integer<=10
//                    }
//                }).subscribe(new Consumer<Boolean>() {
//            @Override
//            public void accept(Boolean aBoolean) throws Exception {
//                Log.d(TAG,"result is "+ aBoolean);
//                // 输出返回结果
//            }
//
//        });

//        Observable.create(new Observable.OnSubscribe<Integer>() {
//            @Override
//            public void call(Subscriber<? super Integer> subscriber) {
//                  // 不发送任何有效事件
//                  // subscriber.onNext(1);
//                  // subscriber.onNext(2);
//                  // subscriber.onNext(3);
//
//                subscriber.onCompleted();
//                // 仅发送Complete事件
//            }
//        }).defaultIfEmpty(10) // 若仅发送了Complete事件，默认发送 值 = 10
//                .subscribe(new Subscriber<Integer>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.v(TAG, "onCompleted");
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.v(TAG, "onError:"+e.getMessage());
//                    }
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.v(TAG, "onNext:"+integer);
//                    }
//                });

//
//// 1. 通过creat（）创建被观察者对象
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            // 2. 在复写的subscribe（）里定义需要发送的事件
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onNext(4);
//
//                emitter.onComplete();
//            }  // 至此，一个被观察者对象（Observable）就创建完毕
//        }).subscribe(new Observer<Integer>() {
//            // 以下步骤仅为展示一个完整demo，可以忽略
//            // 3. 通过通过订阅（subscribe）连接观察者和被观察者
//            // 4. 创建观察者 & 定义响应事件的行为
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "开始采用subscribe连接");
//            }
//            // 默认最先调用复写的 onSubscribe（）
//
//            @Override
//            public void onNext(Integer value) {
//                Log.d(TAG, "接收到了事件"+ value  );
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "对Error事件作出响应");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "对Complete事件作出响应");
//            }
//
//        });
}




