package com.test.baidunavi

import android.app.Service
import android.os.Vibrator
import androidx.multidex.MultiDexApplication
import com.test.baselib.AppConfig
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer

/**
 *
 * @ProjectName: Test
 * @ClassName: App
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/5 11:33
 * @UpdateUser:
 * @UpdateDate: 2019/12/5 11:33
 * @UpdateRemark:
 * @Version: 1.0.0
 */

class App : MultiDexApplication() {

    lateinit var mVibrator: Vibrator
    lateinit var locationService: LocationService

    override fun onCreate() {
        super.onCreate()

        AppConfig.init(this)

        initBaiduMap()
    }

    /***
     * 初始化定位sdk，建议在Application中创建
     */
    private fun initBaiduMap() {
        locationService = LocationService(this)
        mVibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        SDKInitializer.initialize(this)
        SDKInitializer.setCoordType(CoordType.BD09LL)

    }


}