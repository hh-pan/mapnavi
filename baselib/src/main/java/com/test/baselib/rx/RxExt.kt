package com.test.baselib.rx

import com.test.baselib.bean.BaseBean
import com.test.baselib.mvp.IModel
import com.test.baselib.mvp.IView
import com.test.baselib.net.HttpStatus
import com.test.baselib.utils.NetWorkUtil
import com.cxz.kotlin.baselibs.http.exception.ExceptionHandle
import com.cxz.kotlin.baselibs.http.function.RetryWithDelay
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author 潘富亚
 * @date 20119/11/20
 */

fun <T : BaseBean> Observable<T>.ss(
    model: IModel?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit,
    onError: ((T) -> Unit)? = null
) {
    this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe(object : Observer<T> {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onSubscribe(d: Disposable) {
                if (isShowLoading) view?.showLoading()
                model?.addDisposable(d)
                if (!NetWorkUtil.isConnected()) {
                    view?.showError("当前网络不可用，请检查网络设置")
                    d.dispose()
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                view?.hideLoading()
                when (t.code) {
                    HttpStatus.SUCCESS -> onSuccess.invoke(t)
                    HttpStatus.TOKEN_INVALID -> {
                        // Token 过期，重新登录
                    }
                    else -> {
                        if (onError != null) {
                            onError.invoke(t)
                        } else {
                            if (t.message.isNotEmpty())
                                view?.showError(t.message)
                        }
                    }
                }
            }

            override fun onError(t: Throwable) {
                view?.hideLoading()
                view?.showError(ExceptionHandle.handleException(t))
            }
        })
}

fun <T : BaseBean> Observable<T>.sss(
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit,
    onError: ((T) -> Unit)? = null
): Disposable {
    if (isShowLoading) view?.showLoading()
    return this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe({
            if (isShowLoading) view?.showLoading()
            when(it.code) {
                HttpStatus.SUCCESS -> onSuccess.invoke(it)
                HttpStatus.TOKEN_INVALID -> {
                    // Token 过期，重新登录
                }
                else -> {
                    if (onError != null) {
                        onError.invoke(it)
                    } else {
                        if (it.message.isNotEmpty())
                            view?.showError(it.message)
                    }
                }
            }
        }, {
            view?.hideLoading()
            view?.showError(ExceptionHandle.handleException(it))
        })
}
