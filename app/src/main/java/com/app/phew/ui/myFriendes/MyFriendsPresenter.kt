package com.app.phew.ui.myFriendes

import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.models.BaseResponse
import com.app.phew.models.friends.FriendsResponse
import com.app.phew.models.home.PostResponse
import com.app.phew.models.movies.MoviesResponse
import com.app.phew.models.movies.MoviesSearchResponse
import retrofit2.Response

class MyFriendsPresenter(var view: MyFriendsContract.View?) : MyFriendsContract.Presenter {
    private var mInterActor: MyFriendsContract.InterActor? = MyFriendsInterActor()



    override fun getFriends(auth: String,userId:Int) {
        mInterActor!!.getFriends(auth,userId, object : MVPBaseInteractorOutput<FriendsResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<FriendsResponse>) {
                view?.apply {
                    hideProgress()
                    onResponseSuccess(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<FriendsResponse>) {
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

    override fun removeFriend(auth: String, userId: Int) {
        mInterActor!!.removeFriend(auth,userId, object : MVPBaseInteractorOutput<BaseResponse> {
            override fun onServiceRunning() {
                view?.showProgress()
            }

            override fun onResponseSuccess(response: Response<BaseResponse>) {
                view?.apply {
                    hideProgress()
                    onRemoveFriends(response.body()!!)
                }
            }

            override fun onResponseError(response: Response<BaseResponse>) {
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