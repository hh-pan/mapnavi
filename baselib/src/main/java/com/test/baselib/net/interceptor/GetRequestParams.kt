package com.test.business.network

import okhttp3.Interceptor
import okhttp3.Request

/**
 *
 * @ProjectName: abBusiness
 * @ClassName: GetRequestParams
 * @Description: GET请求参数拦截器
 * @Author: 潘富亚
 * @CreateDate: 2019/11/22 13:39
 * @UpdateUser:
 * @UpdateDate: 2019/11/22 13:39
 * @UpdateRemark:
 * @Version: 1.0.0
 */

class GetRequestParams : IRequestParams {
    override fun getRequest(chain: Interceptor.Chain): Request {
        val oldRequest = chain.request()
        //添加新的参数
        val newBuilder = oldRequest.url.newBuilder().scheme(oldRequest.url.scheme)
            .host(oldRequest.url.host)
            .addQueryParameter("version", "1.0.0")
            .addQueryParameter("os", "2")
            .addQueryParameter("client", "abao")
            .addQueryParameter("uid", "")
            .addQueryParameter("token", "")
        return oldRequest.newBuilder().method(oldRequest.method, oldRequest.body)
            .url(newBuilder.build())
            .build()
    }
}