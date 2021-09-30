package com.app.phew.ui.dashboard

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.findly.contries.FindlyCountriesResponse
import com.app.phew.models.findly.findlyCities.FindlyCitiesResponse

class FindlyContract {
    interface Presenter : MVPBasePresenter {
        fun getFindlyCountries()
        fun getFindlyCities(countriesId:Int)
    }

    interface View : MVPBaseApiView<FindlyCountriesResponse> {
        fun getFindlyCitiesSuccess(response: FindlyCitiesResponse)
    }

    interface InterActor {
        fun getFindlyCountries(output: MVPBaseInteractorOutput<FindlyCountriesResponse>)
        fun getFindlyCities(countriesId:Int,output: MVPBaseInteractorOutput<FindlyCitiesResponse>)

    }
}