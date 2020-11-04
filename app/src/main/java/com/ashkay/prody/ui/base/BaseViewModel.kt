package com.ashkay.prody.ui.base

import android.app.Application
import androidx.annotation.CallSuper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber

/**
 * Create ViewModels by Extending this class.
 *
 * @param STATE ViewState should represent the current state of the view at any given time.
 * So this class should have all the variable content on which our view is dependent.
 * Every time there is any user input/action we will expose modified
 * copy (to maintain the previous state which is not being modified) of this class.
 * We can create this model using Kotlin's data class.
 *
 * @param EFFECT ViewEffect is useful for actions that are fire-and-forget and we do not
 * want to maintain its state. We can create this class using Kotlin's sealed class.
 *
 * @param EVENT Represents all actions/events a user can perform on the view.
 * This is used to pass user input/action to the ViewModel.
 * We can create this event set using Kotlin's sealed class.
 *
 * @property process(viewEvent: EVENT) Process ViewEvents (viewEvent) passed by Activity/Fragment/View
 *                                     and update ViewState and ViewEffect accordingly.
 */
open class BaseViewModel<STATE, EFFECT, EVENT>(application: Application) :
    AndroidViewModel(application), ViewModelContract<EVENT> {

    private val _viewStates: MutableLiveData<STATE> = MutableLiveData()
    fun viewStates(): LiveData<STATE> = _viewStates

    private var _viewState: STATE? = null
    protected var viewState: STATE
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            Timber.d("setting viewState : $value")
            _viewState = value
            _viewStates.value = value
        }


    private val _viewEffects: SingleLiveEvent<EFFECT> = SingleLiveEvent()
    fun viewEffects(): SingleLiveEvent<EFFECT> = _viewEffects

    private var _viewEffect: EFFECT? = null
    protected var viewEffect: EFFECT
        get() = _viewEffect
            ?: throw UninitializedPropertyAccessException("\"viewEffect\" was queried before being initialized")
        set(value) {
            Timber.d("setting viewEffect : $value")
            _viewEffect = value
            _viewEffects.value = value
        }

    @CallSuper
    override fun process(viewEvent: EVENT) {
        if (!viewStates().hasObservers()) {
            throw NoObserverAttachedException("No observer attached. In case of custom View \"startObserving()\" function needs to be called manually.")
        }
        Timber.d("processing viewEvent: $viewEvent")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
    }
}