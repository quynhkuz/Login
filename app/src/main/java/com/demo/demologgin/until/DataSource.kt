/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.demo.demologgin.until

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

interface DataSource<T> {
    fun observe(): Observable<T>
}

interface MutableDataSource<T> : DataSource<T> {
    fun post(value: T)
}

open class PublishDataSource<T> : MutableDataSource<T> {

    private val publishRelay = PublishRelay.create<T>()

    override fun observe(): Observable<T> {
        return publishRelay.hide().observeOn(AndroidSchedulers.mainThread())
    }

    override fun post(value: T) {
        publishRelay.accept(value!!)
    }
}
