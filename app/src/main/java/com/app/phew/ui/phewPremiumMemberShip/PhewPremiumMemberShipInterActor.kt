package com.app.phew.ui.phewPremiumMemberShip

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.packages.PackagesResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhewPremiumMemberShipInterActor : PhewPremiumMemberShipContract.InterActor {
    override fun getPackages(output: MVPBaseInteractorOutput<PackagesResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
                .getPackages()
                .enqueue(object : Callback<PackagesResponse> {
                    override fun onResponse(
                            call: Call<PackagesResponse>, response: Response<PackagesResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<PackagesResponse>, t: Throwable) {
                        output.onResponseFailure(t)
                    }
                })
    }

    override fun subscribeToPackage(auth: String, packageId: Int, output: MVPBaseInteractorOutput<BaseResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
                .subscribePackage(auth, packageId,"PUT")
                .enqueue(object : Callback<BaseResponse> {
                    override fun onResponse(
                            call: Call<BaseResponse>, response: Response<BaseResponse>
                    ) {
                        if (response.isSuccessful)
                            output.onResponseSuccess(response)
                        else output.onResponseError(response)
                    }

                    override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                        output.onResponseFailure(t)
                    }
                })
    }


}