package com.app.phew.ui.followUs


import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.followUs.FollowUsResponse
import retrofit2.Response

class FollowUsPresenter(var view: FollowUsContract.View?) : FollowUsContract.Presenter {

    private var mInterActor: FollowUsContract.InterActor? = FollowUsInterActor()
    override fun followUs() {
        mInterActor!!.followUs(
            object : MVPBaseInteractorOutput<FollowUsResponse> {
                override fun onServiceRunning() {
                    view?.showProgress()
                }

                override fun onResponseSuccess(response: Response<FollowUsResponse>) {
                    view?.apply {
                        hideProgress()
                        onResponseSuccess(response.body()!!)
                    }
                }
                override fun onResponseError(response: Response<FollowUsResponse>) {
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