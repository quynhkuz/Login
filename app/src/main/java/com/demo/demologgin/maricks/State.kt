package com.demo.demologgin.maricks

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.demo.demologgin.model.Responsive
import com.demo.oceantechtranning.model.TokenResponse

data class State(
    var asyncLogin: Async<TokenResponse> = Uninitialized,

    var asyncSucess: Async<Responsive> = Uninitialized
) : MvRxState {
}