package com.test.baidunavi.mvp.model

import com.test.baselib.bean.HttpResult
import com.test.baselib.bean.LoginData
import com.test.baselib.mvp.BaseModel
import com.test.baidunavi.mvp.contract.MainContract
import com.test.baidunavi.net.AbaoRetrofit
import io.reactivex.Observable

/**
 *
 * @ProjectName: Test
 * @ClassName: MainModel
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/6 17:45
 * @UpdateUser:
 * @UpdateDate: 2019/12/6 17:45
 * @UpdateRemark:
 * @Version: 1.0.0
 */

class MainModel : BaseModel(), MainContract.Model {
    override fun login(account: String, pwd: String): Observable<HttpResult<LoginData>> {
        return AbaoRetrofit.service.login(account, pwd)
    }

}