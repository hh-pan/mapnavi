package com.test.business.base

import com.test.baselib.mvp.IPresenter
import com.test.baselib.mvp.IView

/**
 *
 * @ProjectName: abBusiness
 * @ClassName: BaseFragment
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/11/19 15:39
 * @UpdateUser:
 * @UpdateDate: 2019/11/19 15:39
 * @UpdateRemark:
 * @Version: 1.0.0
 */

abstract class BaseMvpFragment<V : IView, P : IPresenter<V>> : BaseFragment() {

    protected var mPresenter: P? = null

    abstract fun initPresenter(): P?

    override fun initView() {
        mPresenter = initPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        mPresenter?.detachView()
        this.mPresenter = null
        super.onDestroyView()
    }
}