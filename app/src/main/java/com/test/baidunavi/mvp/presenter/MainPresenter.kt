package com.test.baidunavi.mvp.presenter
import com.test.baselib.mvp.BasePresenter
import com.test.baselib.rx.ss
import com.test.baidunavi.mvp.contract.MainContract
import com.test.baidunavi.mvp.model.MainModel

/**
 *
 * @ProjectName: Test
 * @ClassName: MainPresenter
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/6 16:16
 * @UpdateUser:
 * @UpdateDate: 2019/12/6 16:16
 * @UpdateRemark:
 * @Version: 1.0.0
 */

class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(),
    MainContract.Presenter {

    override fun createModel(): MainContract.Model?  = MainModel()

    override fun login() {
        mModel?.login("1487148","123456")?.ss(mModel, mView, onSuccess = {
            mView?.loginSuccess(it.data)
        })
    }
}