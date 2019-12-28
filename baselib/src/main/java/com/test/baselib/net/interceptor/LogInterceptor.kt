package com.test.baselib.net.interceptor

import android.util.Log
import com.test.business.network.GetRequestParams
import com.test.business.network.PostRequestParams
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 *
 * @ProjectName: abBusiness
 * @ClassName: LogInterceptor
 * @Description:网络请求输出
 * @Author: 潘富亚
 * @CreateDate: 2019/11/19 15:21
 * @UpdateUser:
 * @UpdateDate: 2019/11/19 15:21
 * @UpdateRemark:
 * @Version: 1.0.0
 */

class LogInterceptor : Interceptor {

    private val tag = "LogInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {

        val oldRequest = chain.request()
        val method = oldRequest.method
        val t1 = System.nanoTime()
        var newRequest: Request? = null
        when (method) {
            "POST" -> {
                newRequest = PostRequestParams().getRequest(chain)
            }
            "GET" -> {
                newRequest = GetRequestParams().getRequest(chain)
            }
        }

        if ("POST" == method) {
            val sb = StringBuilder()
            if (newRequest?.body is FormBody) {
                val body = newRequest.body as FormBody
                for (i in 0 until body.size) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",")
                }
                sb.delete(sb.length - 1, sb.length)
                Log.d(
                    tag, String.format(
                        "发送请求 %s on %s %n%s %nRequestParams:{%s}",
                        newRequest?.url, chain.connection(), newRequest.headers, sb.toString()
                    )
                )
            }
        } else {
            Log.d(
                tag, String.format(
                    "发送请求 %s on %s%n%s",
                    newRequest?.url, chain.connection(), newRequest?.headers
                )
            )
        }
        val response = newRequest?.let { chain.proceed(it) }
        val t2 = System.nanoTime()//收到响应的时间
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        val responseBody = response?.peekBody(1024 * 1024)
        Log.d(
            tag,
            String.format(
                "接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
                response?.request?.url,
                responseBody?.string(),
                (t2 - t1) / 1e6,
                response?.headers
            )
        )
        return chain.proceed(newRequest!!)
    }

}