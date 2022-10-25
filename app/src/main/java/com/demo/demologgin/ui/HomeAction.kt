package com.demo.demologgin.ui

import com.demo.mariicks19.core.Action

sealed class HomeAction :Action {
    object demo: HomeAction()
    data class LOGIN(val account :String,val password :String) :HomeAction()
    data class LOGINSUCESS(val code :String,val name :String) :HomeAction()
}