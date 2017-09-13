package com.li.food.network.interceptor;

import com.ggxueche.utils.JsonUtil;
import com.ggxueche.utils.log.L;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 日志拦截器
 * Created by lzf on 2017/5/17.
 */

public class LoggingInterceptor implements HttpLoggingInterceptor.Logger {
    private static String TAG = LoggingInterceptor.class.getSimpleName();
    private StringBuilder mMessage = new StringBuilder();

    @Override
    public void log(String message) {
        try {
            // 请求或者响应开始
            if (message.startsWith("--> POST")) {
                mMessage.setLength(0);
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if ((message.startsWith("{") && message.endsWith("}"))
                    || (message.startsWith("[") && message.endsWith("]"))) {
                message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
            }
            mMessage.append(message.concat("\n"));
            // 响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                L.t(TAG).d(mMessage.toString());
            }
        } catch (Exception e) {

        }
    }
}
