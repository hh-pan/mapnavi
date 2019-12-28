package com.test.baidunavi

import com.test.baselib.base.BaseMvpActivity
import com.test.baselib.mvp.IPresenter
import com.test.baselib.mvp.IView
import kotlinx.android.synthetic.main.activity_second.*
import org.jetbrains.anko.startActivity

/**
 *
 * @ProjectName: Test
 * @ClassName: SecondActivity
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/3 14:32
 * @UpdateUser:
 * @UpdateDate: 2019/12/3 14:32
 * @UpdateRemark:
 * @Version: 1.0.0
 */

class SecondActivity : BaseMvpActivity<IView, IPresenter<IView>>() {

    override fun initPresenter(): IPresenter<IView>? = null

    override val inflateId: Int = R.layout.activity_second

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(handleException: String) {
    }

    override fun initView() {
        super.initView()

        open.setOnClickListener { startActivity<MainActivity>() }
    }

}