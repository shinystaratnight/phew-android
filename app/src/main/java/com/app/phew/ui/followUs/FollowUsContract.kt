package com.app.phew.ui.followUs

import com.app.phew.base.MVPBaseApiView
import com.app.phew.base.MVPBaseInteractorOutput
import com.app.phew.base.MVPBasePresenter
import com.app.phew.models.followUs.FollowUsResponse


class FollowUsContract {
    interface Presenter : MVPBasePresenter {

        fun followUs( )

    }

    interface View : MVPBaseApiView<FollowUsResponse> {
    }

    interface InterActor {
        fun followUs(
            output: MVPBaseInteractorOutput<FollowUsResponse>
        )
    }
}