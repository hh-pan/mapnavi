package com.test.baidunavi

import android.content.Intent
import com.test.baselib.base.BaseMvpActivity
import com.test.baselib.mvp.IPresenter
import com.test.baselib.mvp.IView
import kotlinx.android.synthetic.main.activity_second.open
import kotlinx.android.synthetic.main.activity_third.*
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

class ThirdActivity : BaseMvpActivity<IView, IPresenter<IView>>() {

    var num: Int = 0

    override fun initPresenter(): IPresenter<IView>? = null

    override val inflateId: Int = R.layout.activity_third

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(handleException: String) {
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        num = intent?.getIntExtra("num", 1)!!
        value.text = "${num}"
    }

    override fun initView() {
        super.initView()

        num = intent.getIntExtra("num", 1)
        value.text = "${num}"
        open.setOnClickListener {
            num++
            startActivity<ThirdActivity>("num" to num)
        }

        open1.setOnClickListener {
            val intent = Intent(mContext, SecondActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

}