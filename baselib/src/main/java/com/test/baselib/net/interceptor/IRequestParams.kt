package com.test.business.network

import okhttp3.Interceptor
import okhttp3.Request

/**
 *
 * @ProjectName: abBusiness
 * @ClassName: IRequestParams
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/11/22 11:44
 * @UpdateUser:
 * @UpdateDate: 2019/11/22 11:44
 * @UpdateRemark:
 * @Version: 1.0.0
 */

interface IRequestParams {
    fun getRequest(chain: Interceptor.Chain): Request
}