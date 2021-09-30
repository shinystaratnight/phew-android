package com.app.phew.ui.phewPremiumMemberShip

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.BaseResponse
import com.app.phew.models.packages.PackagesResponse

class PhewPremiumMemberShipContract {
    interface Presenter : MVPBasePresenter {
        fun getPackages()

        fun subscribeToPackage(
                auth: String,packageId: Int )
    }

    interface View : MVPBaseApiView<PackagesResponse> {
        fun  subscribeToSuccess(response: BaseResponse)
    }

    interface InterActor {
        fun getPackages(
            output: MVPBaseInteractorOutput<PackagesResponse>
        )

        fun subscribeToPackage(
                auth: String,packageId: Int,
            output: MVPBaseInteractorOutput<BaseResponse>
        )

    }
}