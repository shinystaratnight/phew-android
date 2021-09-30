package com.app.phew.base

import retrofit2.Response

interface MVPBaseInteractorOutput<T> {
    fun onServiceRunning()
    fun onResponseSuccess(response : Response<T>)
    fun onResponseError(response: Response<T>)
    fun onResponseFailure(t:Throwable)

}