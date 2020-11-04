package com.ashkay.prody.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import timber.log.Timber

/**
 * Create Activities by Extending this class.
 *
 * Also @see [BaseViewModel] for [STATE], [EFFECT] and [EVENT] explanation.
 * @param ViewModel Respective ViewModel class for this activity which extends [BaseViewModel]
 */
abstract class BaseActivity<STATE, EFFECT, EVENT, ViewModel : BaseViewModel<STATE, EFFECT, EVENT>> :
    AppCompatActivity() {

    abstract val viewModel: ViewModel

    private val viewStateObserver = Observer<STATE> {
        Timber.d("observed viewState : $it")
        renderViewState(it)
    }

    private val viewEffectObserver = Observer<EFFECT> {
        Timber.d("observed viewEffect : $it")
        renderViewEffect(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Registering observers
        viewModel.viewStates().observe(this, viewStateObserver)
        viewModel.viewEffects().observe(this, viewEffectObserver)
    }

    abstract fun renderViewState(viewState: STATE)

    abstract fun renderViewEffect(viewEffect: EFFECT)
}