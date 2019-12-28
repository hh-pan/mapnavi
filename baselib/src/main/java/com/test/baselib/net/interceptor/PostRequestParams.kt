package com.test.business.network

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request

/**
 *
 * @ProjectName: abBusiness
 * @ClassName: PostRequestParams
 * @Description: POST请求参数拦截器
 * @Author: 潘富亚
 * @CreateDate: 2019/11/22 13:39
 * @UpdateUser:
 * @UpdateDate: 2019/11/22 13:39
 * @UpdateRemark:
 * @Version: 1.0.0
 */

class PostRequestParams : IRequestParams {
    override fun getRequest(chain: Interceptor.Chain): Request {
        val oldRequest = chain.request()
        val newRequest: Request? = null
        if (oldRequest.body is FormBody) {
            var formBody = oldRequest.body as FormBody
            val bodyBuilder = FormBody.Builder()
            for (i in 0 until formBody.size) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i))
            }
            formBody = bodyBuilder
                .addEncoded("version", "1.0.0")
                .addEncoded("os", "2")
                .addEncoded("client", "abao")
                .addEncoded("uid", "")
                .addEncoded("token","")
                .build()
            return oldRequest.newBuilder().post(formBody).build()
        }
        return newRequest!!
    }
}