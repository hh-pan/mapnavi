package com.test.baselib.base

import android.os.Bundle
import com.test.baselib.mvp.IPresenter
import com.test.baselib.mvp.IView
import com.test.business.base.BaseMvpFragment

/**
 *
 * @ProjectName: Test
 * @ClassName: BaseMvpPageFragment
 * @Description: 懒加载fragment
 * @Author: 潘富亚
 * @CreateDate: 2019/12/7 10:48
 * @UpdateUser:
 * @UpdateDate: 2019/12/7 10:48
 * @UpdateRemark:
 * @Version: 1.0.0
 */

abstract class BaseMvpPageFragment<V : IView, P : IPresenter<V>> : BaseMvpFragment<V, P>() {

    private var isViewInitiated: Boolean = false
    private var isVisibleToUser: Boolean = false
    private var isDataInitiated: Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    private fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    private fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData()
            isDataInitiated = true
            return true
        }
        return false
    }

    abstract fun fetchData()
}