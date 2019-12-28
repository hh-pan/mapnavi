package com.test.baidunavi

import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import com.test.baidunavi.base.BaseTestActivity
import com.test.baidunavi.mvp.contract.MainContract
import com.test.baidunavi.mvp.presenter.MainPresenter
import com.test.baselib.bean.LoginData
import com.baidu.mapapi.bikenavi.BikeNavigateHelper
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam
import com.baidu.mapapi.bikenavi.params.BikeRouteNodeInfo
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.walknavi.WalkNavigateHelper
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam
import com.baidu.mapapi.walknavi.params.WalkRouteNodeInfo
import com.baidu.navisdk.adapter.*
import com.gyf.barlibrary.ImmersionBar
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.*


class MainActivity : BaseTestActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {

    private val APP_FOLDER_NAME = "BNSDKSimpleDemo"

    private var mStartNode: BNRoutePlanNode? = null
    private var mEndNode: BNRoutePlanNode? = null

    private var walkParam: WalkNaviLaunchParam? = null
    private var bikeParam: BikeNaviLaunchParam? = null

    private var startPt: LatLng? = null
    private var endPt: LatLng? = null

    override fun initImmersionBar() {
        super.initImmersionBar()

        ImmersionBar.with(this).statusBarDarkFont(false).init(); //状态栏默认亮色
    }

    override fun loginSuccess(data: LoginData) {
    }

    override fun showError(handleException: String) {
    }

    override fun showLoading() {
        Toast.makeText(this, "showLoading", Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        Toast.makeText(this, "hideLoading", Toast.LENGTH_SHORT).show()
    }

    override fun initPresenter(): MainContract.Presenter = MainPresenter()

    override val inflateId: Int = R.layout.activity_main

    override fun initView() {
        initRoutePlanNode()
        if (initDirs()) {
            AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted {
                    initNavi()
                }
                .onDenied {
                    Toast.makeText(mContext, "权限拒绝", Toast.LENGTH_SHORT).show()
                }
                .start()
        }

        //定位
        click.setOnClickListener {
            BaiduLocHelpter.getLocation(
                this, object : BaiduLocHelpter.OnLocationResult {
                    override fun onLocationResult(
                        city: String?,
                        countryCode: String
                    ) {
                        Toast.makeText(mContext, city, Toast.LENGTH_SHORT).show()
                    }
                })
        }
        //驾车导航
        car.setOnClickListener {
            if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited) {
                routePlanToNavi(mStartNode!!, mEndNode!!, null)
            }
        }
        //步行导航
        walk.setOnClickListener {
            if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited) {
                WalkGuidInit()
            }
        }
        //骑行导航
        ride.setOnClickListener {
            if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited) {
                rideGuideInit()
            }
        }
    }

    private fun rideGuideInit() {
        try {
            BikeNavigateHelper.getInstance().initNaviEngine(this, object : IBEngineInitListener {
                override fun engineInitSuccess() {
                    Log.d("sdfsdfsdfsd", "rideGuideInit engineInitSuccess")
                    routePlanWithBikeParam()
                }

                override fun engineInitFail() {
                    Log.d("sdfsdfsdfsd", "rideGuideInit engineInitFail")
                    BikeNavigateHelper.getInstance().unInitNaviEngine()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 发起骑行导航算路
     */
    private fun routePlanWithBikeParam() {
        BikeNavigateHelper.getInstance()
            .routePlanWithRouteNode(bikeParam, object : IBRoutePlanListener {
                override fun onRoutePlanStart() {
                }

                override fun onRoutePlanSuccess() {
                    val intent = Intent()
                    intent.setClass(this@MainActivity, BNaviGuideActivity::class.java!!)
                    startActivity(intent)
                }

                override fun onRoutePlanFail(error: BikeRoutePlanError) {
                }

            })
    }

    /**
     * 初始化步行导航
     */
    private fun WalkGuidInit() {
        try {
            WalkNavigateHelper.getInstance().initNaviEngine(this, object : IWEngineInitListener {
                override fun engineInitSuccess() {
                    Log.d("sdfsdfsdfsd", "WalkNavi engineInitSuccess")
                    routePlanWithWalkParam()
                }

                override fun engineInitFail() {
                    Log.d("sdfsdfsdfsd", "WalkNavi engineInitFail")
                    WalkNavigateHelper.getInstance().unInitNaviEngine()
                }
            })
        } catch (e: Exception) {
            Log.d("sdfsdfsdfsd", "startBikeNavi Exception")
            e.printStackTrace()
        }

    }

    private val endPoint: LatLng = LatLng(39.908749, 116.397491)
    private val starPoint: LatLng? = LatLng(40.050969, 116.300821)
    private val gcjEndPoint: LatLng? = null
    private val gcjStartPoint: LatLng? = null

    private fun routePlanWithWalkParam() {
        //构造WalkNaviLaunchParam
        val start = WalkRouteNodeInfo()
        start.location = starPoint
        val end = WalkRouteNodeInfo()
        end.location = endPoint
        val mParam = WalkNaviLaunchParam().startNodeInfo(start).endNodeInfo(end)
        //发起算路
        WalkNavigateHelper.getInstance()
            .routePlanWithRouteNode(mParam, object : IWRoutePlanListener {
                override fun onRoutePlanStart() {
                    //开始算路的回调

                }

                override fun onRoutePlanSuccess() {
                    //算路成功
                    //跳转至诱导页面
                    val intent = Intent(mContext, WNaviGuideActivity::class.java)
                    mContext.startActivity(intent)
                }

                override fun onRoutePlanFail(walkRoutePlanError: WalkRoutePlanError) {
                    //算路失败的回调
                    Toast.makeText(mContext, "导航失败,请重试!", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun initNavi() {
        if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited) {
            return
        }
        BaiduNaviManagerFactory.getBaiduNaviManager().init(this,
            mSDCardPath, APP_FOLDER_NAME, object : IBaiduNaviManager.INaviInitListener {

                override fun onAuthResult(status: Int, msg: String) {
                    val result: String
                    if (0 == status) {
                        result = "key校验成功!"
                    } else {
                        result = "key校验失败, $msg"
                    }
                    Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()
                }

                override fun initStart() {
                    Toast.makeText(
                        this@MainActivity.applicationContext,
                        "百度导航引擎初始化开始", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun initSuccess() {
                    Toast.makeText(
                        this@MainActivity.applicationContext,
                        "百度导航引擎初始化成功", Toast.LENGTH_SHORT
                    ).show()
                    // 使用内置TTS
//                    BaiduNaviManagerFactory.getTTSManager()
//                        .initTTS(object : IBNTTSManager.IBNOuterTTSPlayerCallback() {
//
//
//                            override fun getTTSState(): Int {
//                                return 0
//                            }
//
//                            override fun playTTSText(
//                                s: String,
//                                s1: String,
//                                i: Int,
//                                s2: String
//                            ): Int {
//                                //调用语音识别SDK的语音回调进行播报
//                                if (!TextUtils.isEmpty(s)) {
//                                    Log.e("xsdfsdfsdfs","s: ${s}")
//                                }
//                                return i
//                            }
//
//                            override fun stopTTS() {
//                            }
//                        })
//                    BaiduNaviManagerFactory.getBaiduNaviManager().enableOutLog(true)

                    // 使用内置TTS
                    BaiduNaviManagerFactory.getTTSManager().initTTS(
                        applicationContext,
                        getSdcardDir(), APP_FOLDER_NAME, "18123214"
                    )

                }

                override fun initFailed(errCode: Int) {
                    Toast.makeText(
                        this@MainActivity.applicationContext,
                        "百度导航引擎初始化失败 $errCode", Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private var mSDCardPath: String? = null

    private fun initDirs(): Boolean {
        mSDCardPath = getSdcardDir()
        if (mSDCardPath == null) {
            return false
        }
        val f = File(mSDCardPath, APP_FOLDER_NAME)
        if (!f.exists()) {
            try {
                f.mkdir()
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }
        return true
    }

    private fun getSdcardDir(): String? {
        return if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED,
                ignoreCase = true
            )
        ) {
            Environment.getExternalStorageDirectory().toString()
        } else null
    }

    private fun routePlanToNavi(sNode: BNRoutePlanNode, eNode: BNRoutePlanNode, bundle: Bundle?) {
        val list = ArrayList<BNRoutePlanNode>()
        list.add(sNode)
        list.add(eNode)

        BaiduNaviManagerFactory.getRoutePlanManager().routeplanToNavi(
            list,
            IBNRoutePlanManager.RoutePlanPreference.ROUTE_PLAN_PREFERENCE_DEFAULT,
            bundle,
            object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    when (msg.what) {
                        IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_START -> Toast.makeText(
                            this@MainActivity.getApplicationContext(),
                            "算路开始", Toast.LENGTH_SHORT
                        ).show()
                        IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS -> {
                            Toast.makeText(
                                this@MainActivity.getApplicationContext(),
                                "算路成功", Toast.LENGTH_SHORT
                            ).show()
                            // 躲避限行消息
                            val infoBundle = msg.obj as Bundle
                            if (infoBundle != null) {
                                val info = infoBundle.getString(
                                    BNaviCommonParams.BNRouteInfoKey.TRAFFIC_LIMIT_INFO
                                )
                                Log.d("OnSdkDemo", "info = ${info}")
                            }
                        }
                        IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED -> Toast.makeText(
                            this@MainActivity.getApplicationContext(),
                            "算路失败", Toast.LENGTH_SHORT
                        ).show()
                        IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI -> {
                            Toast.makeText(
                                this@MainActivity.getApplicationContext(),
                                "算路成功准备进入导航", Toast.LENGTH_SHORT
                            ).show()

                            var intent: Intent? = null
                            if (bundle == null) {
                                intent = Intent(this@MainActivity, DemoExtGpsActivity::class.java)
                            } else {
                                intent = Intent(this@MainActivity, DemoGuideActivity::class.java)
                            }
                            startActivity(intent)
                        }
                        else -> {
                        }
                    }// nothing
                }
            })
    }

    private fun initRoutePlanNode() {

        startPt = LatLng(40.057038, 116.307899)
        endPt = LatLng(40.035916, 116.340722)

        val bikeStartNode = BikeRouteNodeInfo()
        bikeStartNode.location = startPt
        val bikeEndNode = BikeRouteNodeInfo()
        bikeEndNode.location = endPt
        bikeParam = BikeNaviLaunchParam().startNodeInfo(bikeStartNode).endNodeInfo(bikeEndNode)

        val walkStartNode = WalkRouteNodeInfo()
        walkStartNode.location = startPt
        val walkEndNode = WalkRouteNodeInfo()
        walkEndNode.location = endPt
        walkParam = WalkNaviLaunchParam().startNodeInfo(walkStartNode).endNodeInfo(walkEndNode)

        mStartNode = BNRoutePlanNode.Builder()
            .latitude(40.050969)
            .longitude(116.300821)
            .name("百度大厦")
            .description("百度大厦")
            .coordinateType(BNRoutePlanNode.CoordinateType.WGS84)
            .build()
        mEndNode = BNRoutePlanNode.Builder()
            .latitude(39.908749)
            .longitude(116.397491)
            .name("北京天安门")
            .description("北京天安门")
            .coordinateType(BNRoutePlanNode.CoordinateType.WGS84)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()


    }
}
