package com.app.phew.base

interface MVPBaseApiView<T>  : MVPBaseView {

    fun onResponseSuccess(data: T )
    fun onResponseError(errorBody: String)
    fun onResponseFailure(t : Throwable)

}
