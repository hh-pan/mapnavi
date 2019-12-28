package com.test.baselib.base

import com.test.baselib.mvp.IPresenter
import com.test.baselib.mvp.IView
import com.test.business.base.BaseActivity

/**
 *
 * @ProjectName: Test
 * @ClassName: BaseMvpActivity
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/6 15:25
 * @UpdateUser:
 * @UpdateDate: 2019/12/6 15:25
 * @UpdateRemark:
 * @Version: 1.0.0
 */

abstract class BaseMvpActivity<V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    var mPresenter: P? = null

    abstract fun initPresenter(): P?

    override fun init() {
        mPresenter = initPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        mPresenter?.detachView()
        this.mPresenter = null
        super.onDestroy()
    }
}