package scut.carson_ho.rxjava_operators.SwitchOperator.Demo;

import android.util.Log;

/**
 * Created by Carson_Ho on 17/9/12.
 */

public class Translation2 {
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

        Log.d("RxJava", "翻译内容 = " + content.out);

    }
}
