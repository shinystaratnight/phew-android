package com.app.phew.ui.search



import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.searchResponse.SearchResponse
import retrofit2.Response

class SearchPresenter(var view: SearchContract.View?) : SearchContract.Presenter {

    private var mInterActor: SearchContract.InterActor? = SearchInterActor()
    override fun search(userName:String) {
        mInterActor!!.search(userName,
            object : MVPBaseInteractorOutput<SearchResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<SearchResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)

                    }
                }

                override fun onResponseError(response: Response<SearchResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseError(response.errorBody()!!.string())
                    }
                }

                override fun onResponseFailure(t: Throwable) {
                    view?.apply {
                        hideProgress()
                        onResponseFailure(t)
                    }
                }
            })
    }


    override fun onDestroy() {
        view = null
        mInterActor = null
    }

}