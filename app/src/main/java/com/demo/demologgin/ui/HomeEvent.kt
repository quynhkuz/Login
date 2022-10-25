package com.demo.demologgin.ui

import com.demo.mariicks19.core.Event

sealed class HomeEvent:Event {
    data class Send(val ten:String) : HomeEvent()
}