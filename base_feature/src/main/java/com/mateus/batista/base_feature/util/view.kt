package com.mateus.batista.base_feature.util

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun Fragment.showDialog(dialog: DialogFragment, bundle: Bundle? = null){
    dialog.arguments = bundle
    dialog.setTargetFragment(this, 0)
    dialog.show(this.fragmentManager!!, dialog::class.java.simpleName)
}

fun <D> LiveData<FlowState<D>>.observeEvent(
    lifecycleOwner: LifecycleOwner,
    loading: (() -> Unit)? = null,
    success: ((D) -> Unit)? = null,
    error: ((Throwable) -> Unit)? = null
) {

    observe(lifecycleOwner, Observer { state ->
        when (state.status) {
            FlowState.Status.LOADING -> {
                loading?.invoke()
            }
            FlowState.Status.SUCCESS -> state.data?.let {
                success?.invoke(state.data)
            }
            FlowState.Status.ERROR -> state.error?.let {
                error?.invoke(state.error)
            }
        }
    })
}