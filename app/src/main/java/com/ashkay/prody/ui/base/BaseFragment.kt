package com.ashkay.prody.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import timber.log.Timber

/**
 * Create Fragments by Extending this class.
 *
 * Also @see [BaseViewModel] for [STATE], [EFFECT] and [EVENT] explanation.
 * @param ViewModel Respective ViewModel class for this activity which extends [BaseViewModel]
 */
abstract class BaseFragment<STATE, EFFECT, EVENT, ViewModel : BaseViewModel<STATE, EFFECT, EVENT>>(
    @LayoutRes contentLayoutId: Int
) :
    Fragment(contentLayoutId) {

    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer<STATE> {
        Timber.d("observed viewState : $it")
        renderViewState(it)
    }

    private val viewEffectObserver = Observer<EFFECT> {
        Timber.d("observed viewEffect : $it")
        renderViewEffect(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewStates().observe(viewLifecycleOwner, viewStateObserver)
        viewModel.viewEffects().observe(viewLifecycleOwner, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}