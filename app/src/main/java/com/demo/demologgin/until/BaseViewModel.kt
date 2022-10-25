package com.demo.demologgin.until

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.demo.mariicks19.core.Action
import com.demo.mariicks19.core.Event
import io.reactivex.android.schedulers.AndroidSchedulers

abstract class BaseViewModel<S : MvRxState , A : Action, E : Event>(state: S) :
    BaseMvRxViewModel<S>(state,false) {


    interface Factory<S : MvRxState> {
        fun create(state: S): BaseMvRxViewModel<S>
    }


    protected val _viewEvents = PublishDataSource<E>()
    val viewEvents: DataSource<E> = _viewEvents

    abstract fun hanled(action : A)




}

fun <T : Event> BaseViewModel<*, *, T>.observeViewEvents(observer: (T?) -> Unit) {
    viewEvents
        .observe()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            observer(it)
        }
//                .disposeOnDestroy()
}