package com.test.baselib

import android.app.Application

/**
 * @author 潘富亚
 * @date 2019/12/7
 * @desc 用来初始化项目所需要的配置
 */
object AppConfig {

    var debug = false

    private var application: Application? = null

    /**
     * Init, it must be call before used .
     */
    fun init(application: Application) {
        this.application = application
    }

    fun getApplication(): Application {
        if (application == null) {
            throw RuntimeException("Please init in Application#onCreate first.")
        }
        return application!!
    }

    fun openDebug() {
        debug = true
    }

}