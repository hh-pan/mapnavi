package com.test.baidunavi

import android.content.Context
import android.os.Handler
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation

/**
 *
 * @ProjectName: abDeliver
 * @ClassName: BaiduLocHelpter
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/20 13:47
 * @UpdateUser:
 * @UpdateDate: 2019/12/20 13:47
 * @UpdateRemark:
 * @Version: 1.0.0
 */

object BaiduLocHelpter {

    private lateinit var locationService: LocationService
    private lateinit var listener: OnLocationResult

    fun getLocation(context: Context, listener: OnLocationResult) {
        this.listener = listener
        locationService = (context.applicationContext as App).locationService
        locationService.registerListener(mListener)
        locationService.start()
    }

    private val mListener = object : BDAbstractLocationListener() {
        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        override fun onReceiveLocation(location: BDLocation?) {
            if (null != location && location.locType != BDLocation.TypeServerError) {
                location.apply {
                    listener.onLocationResult(city,countryCode)
                }
            }
            stopLocation()
        }

        override fun onConnectHotSpotMessage(s: String?, i: Int) {
            super.onConnectHotSpotMessage(s, i)
        }

        /**
         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
         * @param locType 当前定位类型
         * @param diagnosticType 诊断类型（1~9）
         * @param diagnosticMessage 具体的诊断信息释义
         */
        override fun onLocDiagnosticMessage(
            locType: Int,
            diagnosticType: Int,
            diagnosticMessage: String?
        ) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage)

            stopLocation()

            val tag = 2
            val sb = StringBuffer(256)
            sb.append("诊断结果: ")
            if (locType == BDLocation.TypeNetWorkLocation) {
                if (diagnosticType == 1) {
                    sb.append("网络定位成功，没有开启GPS，建议打开GPS会更好")
                    sb.append("\n" + diagnosticMessage!!)
                } else if (diagnosticType == 2) {
                    sb.append("网络定位成功，没有开启Wi-Fi，建议打开Wi-Fi会更好")
                    sb.append("\n" + diagnosticMessage!!)
                }
            } else if (locType == BDLocation.TypeOffLineLocationFail) {
                if (diagnosticType == 3) {
                    sb.append("定位失败，请您检查您的网络状态")
                    sb.append("\n" + diagnosticMessage!!)
                }
            } else if (locType == BDLocation.TypeCriteriaException) {
                if (diagnosticType == 4) {
                    sb.append("定位失败，无法获取任何有效定位依据")
                    sb.append("\n" + diagnosticMessage!!)
                } else if (diagnosticType == 5) {
                    sb.append("定位失败，无法获取有效定位依据，请检查运营商网络或者Wi-Fi网络是否正常开启，尝试重新请求定位")
                    sb.append(diagnosticMessage)
                } else if (diagnosticType == 6) {
                    sb.append("定位失败，无法获取有效定位依据，请尝试插入一张sim卡或打开Wi-Fi重试")
                    sb.append("\n" + diagnosticMessage!!)
                } else if (diagnosticType == 7) {
                    sb.append("定位失败，飞行模式下无法获取有效定位依据，请关闭飞行模式重试")
                    sb.append("\n" + diagnosticMessage!!)
                } else if (diagnosticType == 9) {
                    sb.append("定位失败，无法获取任何有效定位依据")
                    sb.append("\n" + diagnosticMessage!!)
                }
            } else if (locType == BDLocation.TypeServerError) {
                if (diagnosticType == 8) {
                    sb.append("定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限")
                    sb.append("\n" + diagnosticMessage!!)
                }
            }
        }
    }

    fun stopLocation() {
        Handler().postDelayed({
            locationService.unregisterListener(mListener) //注销掉监听
            locationService.stop() //停止定位服务
        }, 1000)
    }

    interface OnLocationResult {
        fun onLocationResult(city: String?, countryCode: String)
    }
}