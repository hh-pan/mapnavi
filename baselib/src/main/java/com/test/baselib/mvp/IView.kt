package com.test.baselib.mvp

/**
 *
 * @ProjectName: abBusiness
 * @ClassName: IView
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/11/19 17:41
 * @UpdateUser:
 * @UpdateDate: 2019/11/19 17:41
 * @UpdateRemark:
 * @Version: 1.0.0
 */

interface IView {
    fun showLoading()
    fun hideLoading()
    fun showError(handleException: String)
}
