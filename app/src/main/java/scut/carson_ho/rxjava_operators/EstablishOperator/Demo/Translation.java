package scut.carson_ho.rxjava_operators.EstablishOperator.Demo;

import android.util.Log;

/**
 * Created by Carson_Ho on 17/9/8.
 */

public class Translation {


    private int status;

    private content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.d("RxJava", content.out );
    }
}
