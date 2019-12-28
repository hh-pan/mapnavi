package com.test.baidunavi.net

import com.test.baselib.net.RetrofitFactory

/**
 *
 * @ProjectName: Test
 * @ClassName: AbaoRetrofit
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/6 17:39
 * @UpdateUser:
 * @UpdateDate: 2019/12/6 17:39
 * @UpdateRemark:
 * @Version: 1.0.0
 */

object AbaoRetrofit : RetrofitFactory<ApiService>() {
    override fun baseUrl(): String = "https://www.mofing.com/"

    override fun getService(): Class<ApiService> = ApiService::class.java
}