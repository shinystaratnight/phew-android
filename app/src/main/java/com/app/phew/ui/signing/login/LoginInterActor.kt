package com.app.phew.ui.signing.login

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.auth.LoginResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginInterActor : LoginContract.InterActor {
    override fun login(
            identifier: String, password: String, deviceType: String?, deviceToken: String?,
            output: MVPBaseInteractorOutput<LoginResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
                .login(identifier, password, deviceType, deviceToken)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                            call: Call<LoginResponse>, response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        output.onResponseFailure(t)
                    }
                })
    }

    override fun socialLogin(fullName: String, providerType: String, providerId: String, deviceType: String?, deviceToken: String?, output: MVPBaseInteractorOutput<LoginResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
                .socialLogin(fullName, providerType, providerId, deviceType, deviceToken)
                .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                            call: Call<LoginResponse>, response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        output.onResponseFailure(t)
                    }
                })
    }
}