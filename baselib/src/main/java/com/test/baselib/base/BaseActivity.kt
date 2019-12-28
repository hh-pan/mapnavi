package com.test.business.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.baselib.utils.CommonUtil

/**
 *
 * @ProjectName: abBusiness
 * @ClassName: BaseActivity
 * @Description: Activity基类
 * @Author: 潘富亚
 * @CreateDate: 2019/11/19 14:42
 * @UpdateUser:
 * @UpdateDate: 2019/11/19 14:42
 * @UpdateRemark:
 * @Version: 1.0.0
 */

abstract class BaseActivity : AppCompatActivity() {

    abstract val inflateId: Int

    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(inflateId)
        mContext = this

        init()
        initView()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        CommonUtil.fixInputMethodManagerLeak(this)
    }

    open fun init(){}

    open fun initData(){}

    open fun initView(){}
}