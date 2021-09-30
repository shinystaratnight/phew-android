package com.app.phew.ui.search




import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.searchResponse.SearchResponse
import com.app.phew.network.RetroWeb
import com.app.phew.network.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchInterActor : SearchContract.InterActor {
    override fun search( userName:String,output: MVPBaseInteractorOutput<SearchResponse>) {
        output.onServiceRunning()
        RetroWeb
            .client
            .create(ServiceApi::class.java)
            .search(userName)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful){
                        output.onResponseSuccess(response)
                    }
                    else {
                        output.onResponseError(response)
                    }
                }
                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    output.onResponseFailure(t)
                }


            })
    }


}