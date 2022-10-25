package com.demo.demologgin.maricks

import com.airbnb.mvrx.*
import com.demo.demologgin.repository.AuthRepository
import com.demo.demologgin.ui.HomeAction
import com.demo.mariicks19.core.Action
import com.demo.mariicks19.core.Event
import com.demo.demologgin.until.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class ViewModel @AssistedInject constructor(
    @Assisted state: State,
    val repository: AuthRepository
) :
    BaseViewModel<State, Action, Event>(state) {


    override fun hanled(action: Action) {
        when (action) {

            is HomeAction.LOGIN -> handelLogin(action.account,action.password)
            is HomeAction.LOGINSUCESS -> handelsucess(action.code,action.name)
        }
    }

    private fun handelsucess(code: String, name: String) {
        setState {
            copy(asyncSucess= Loading())
        }
        repository.loginsucess(code,name).execute {
            copy(asyncSucess = it)
        }

    }

    private fun handelLogin(account: String, password: String) {
        setState {
            copy(asyncLogin= Loading())
        }
        repository.login(account,password).execute {
            copy(asyncLogin = it)
        }
    }



    @AssistedFactory
    interface Factory {
        fun create(initialState: State): ViewModel
    }

    companion object : MvRxViewModelFactory<ViewModel, State> {
        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: State): ViewModel {
            val factory = when (viewModelContext) {
                is FragmentViewModelContext -> viewModelContext.fragment as? Factory
                is ActivityViewModelContext -> viewModelContext.activity as? Factory
            }
            return factory?.create(state) ?: error("You should let your activity/fragment implements Factory interface")
        }
    }
}