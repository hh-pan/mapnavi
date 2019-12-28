package com.test.baselib.bean

/**
 *
 * @ProjectName: Test
 * @ClassName: LoginData
 * @Description:
 * @Author: 潘富亚
 * @CreateDate: 2019/12/7 9:21
 * @UpdateUser:
 * @UpdateDate: 2019/12/7 9:21
 * @UpdateRemark:
 * @Version: 1.0.0
 */

// 登录数据
data class LoginData(
    val access_token: String,
    val developer: Boolean,
    val expires_in: Int,
    val ispaypassword: Boolean,
    val modified: String,
    val re_expires_in: Int,
    val refresh_token: String,
    val schoolId: Int,
    val schoolName: String,
    val token: String,
    val uid: String,
    val zhName: String
)