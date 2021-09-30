package com.app.phew.ui.dashboard

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.findly.contries.FindlyCountriesResponse
import com.app.phew.models.findly.findlyCities.FindlyCitiesResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindlyInterActor : FindlyContract.InterActor {
    override fun getFindlyCountries(output: MVPBaseInteractorOutput<FindlyCountriesResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .findlyCountries()
            .enqueue(object : Callback<FindlyCountriesResponse> {
                override fun onResponse(
                    call: Call<FindlyCountriesResponse>, response: Response<FindlyCountriesResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<FindlyCountriesResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

    override fun getFindlyCities(
        countriesId: Int,
        output: MVPBaseInteractorOutput<FindlyCitiesResponse>
    ) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java)
            .findlyCities(countriesId)
            .enqueue(object : Callback<FindlyCitiesResponse> {
                override fun onResponse(
                    call: Call<FindlyCitiesResponse>, response: Response<FindlyCitiesResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<FindlyCitiesResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

}