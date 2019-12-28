package com.test.baidunavi.mvp.contract

import com.test.baselib.bean.HttpResult
import com.test.baselib.bean.LoginData
import com.test.baselib.mvp.IModel
import com.test.baselib.mvp.IPresenter
import com.test.baselib.mvp.IView
import io.reactivex.Observable

/**
 *
 * @ProjectName: Test
 * @ClassName: MainContract
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/6 16:14
 * @UpdateUser:
 * @UpdateDate: 2019/12/6 16:14
 * @UpdateRemark:
 * @Version: 1.0.0
 */

interface MainContract {

    interface View : IView {
        fun loginSuccess(data: LoginData)

    }

    interface Presenter : IPresenter<View> {
        fun login()
    }

    interface Model : IModel {
        fun login(account: String, pwd: String): Observable<HttpResult<LoginData>>

    }
}