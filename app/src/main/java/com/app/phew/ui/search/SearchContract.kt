package com.app.phew.ui.search

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.searchResponse.SearchResponse


class SearchContract {
    interface Presenter : MVPBasePresenter {

        fun search(
                userName: String
        )

    }

    interface View : MVPBaseApiView<SearchResponse> {
    }

    interface InterActor {

        fun search(
                userName: String,
                output: MVPBaseInteractorOutput<SearchResponse>
        )

    }
}