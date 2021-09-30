package com.app.phew.ui.signing.register

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.cities.CitiesResponse
import com.app.phew.models.countries.CountriesResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import com.app.phew.utils.ProgressRequestBody
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RegisterInterActor : RegisterContract.InterActor, ProgressRequestBody.UploadCallbacks {
    override fun register(
        fullname: String, mobile: String, email: String, password: String, confirmPassword: String,
        images: ArrayList<String>?, countryId: Int?, cityId: Int?, deviceType: String?,
        deviceToken: String?, output: MVPBaseInteractorOutput<BaseResponse>
    ) {
        output.onServiceRunning()
        if (images.isNullOrEmpty())
            RetroWeb.client.create(ServiceApi::class.java).registerWithoutImages(
                fullname, mobile, email, password, countryId, cityId, deviceType, deviceToken
            ).enqueue(object : Callback<BaseResponse> {
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
        else RetroWeb.client.create(ServiceApi::class.java).registerWithImages(
            fullname, mobile, email, password,
            convertAllToMultiPart(images), countryId, cityId, deviceType, deviceToken
        ).enqueue(object : Callback<BaseResponse> {
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

    override fun getCountries(output: MVPBaseInteractorOutput<CountriesResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).getCountries()
            .enqueue(object : Callback<CountriesResponse> {
                override fun onResponse(
                    call: Call<CountriesResponse>, response: Response<CountriesResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<CountriesResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

    override fun getCities(countryId: Int, output: MVPBaseInteractorOutput<CitiesResponse>) {
        output.onServiceRunning()
        RetroWeb.client.create(ServiceApi::class.java).getCities(countryId)
            .enqueue(object : Callback<CitiesResponse> {
                override fun onResponse(
                    call: Call<CitiesResponse>, response: Response<CitiesResponse>
                ) {
                    if (response.isSuccessful)
                        output.onResponseSuccess(response)
                    else output.onResponseError(response)
                }

                override fun onFailure(call: Call<CitiesResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }
            })
    }

    private fun convertAllToMultiPart(images: ArrayList<String>): ArrayList<MultipartBody.Part> {
        val parts = ArrayList<MultipartBody.Part>()
        for (image in images) {
            val myImage = File(image)
            val imageBody = ProgressRequestBody(myImage, this)
            parts.add(MultipartBody.Part.createFormData("avatar[]", myImage.name, imageBody))
        }
        return parts
    }

    override fun onProgressUpdate(percentage: Int) {}
    override fun onError() {}
    override fun onFinish() {}
}