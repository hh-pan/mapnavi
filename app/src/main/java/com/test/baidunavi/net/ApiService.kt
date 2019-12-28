package com.test.baidunavi.net

import com.test.baselib.bean.HttpResult
import com.test.baselib.bean.LoginData
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *
 * @ProjectName: Test
 * @ClassName: ApiService
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/6 17:18
 * @UpdateUser:
 * @UpdateDate: 2019/12/6 17:18
 * @UpdateRemark:
 * @Version: 1.0.0
 */

interface ApiService {
    //登录
    @FormUrlEncoded
    @POST("account/authenticate.json")
    fun login(
        @Field("account") account: String,
        @Field("password") password: String
    ): Observable<HttpResult<LoginData>>

}