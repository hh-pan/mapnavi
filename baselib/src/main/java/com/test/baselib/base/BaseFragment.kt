package com.test.business.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

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

abstract class BaseFragment : Fragment() {

    abstract val inflateId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(inflateId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    abstract fun initData()

    abstract fun initView()

}