package com.test.baidunavi.base

import android.os.Bundle
import com.test.baselib.base.BaseMvpActivity
import com.test.baselib.mvp.IPresenter
import com.test.baselib.mvp.IView
import com.test.baidunavi.R
import com.gyf.barlibrary.ImmersionBar

/**
 *
 * @ProjectName: Test
 * @ClassName: BaseTestActivity
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/12 9:53
 * @UpdateUser:
 * @UpdateDate: 2019/12/12 9:53
 * @UpdateRemark:
 * @Version: 1.0.0
 */

abstract class BaseTestActivity<V : IView, P : IPresenter<V>> : BaseMvpActivity<V, P>() {

    var mImmersionBar: ImmersionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    open fun initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.apply {
            navigationBarColor(R.color.white)//修改导航栏颜色
            init()
        }
    }
}