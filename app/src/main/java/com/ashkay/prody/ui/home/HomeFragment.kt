package com.ashkay.prody.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashkay.prody.R
import com.ashkay.prody.adapters.TodoListAdapter
import com.ashkay.prody.models.Todo
import com.ashkay.prody.ui.main.MainViewModel
import com.ashkay.prody.utils.broadcastReceiver.BatteryWatcher
import com.google.android.material.textfield.TextInputEditText

class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var batteryWatcher: BatteryWatcher
    private lateinit var tvBattery: TextView
    private lateinit var etNewTodo: TextInputEditText
    private lateinit var rvTodoList: RecyclerView

    private val todoListAdapter = TodoListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.home_fragment, container, false)
        initViews(rootView)
        initWatchers()
        return rootView
    }

    private fun initViews(rootView: View) {
        tvBattery = rootView.findViewById(R.id.tvBattery)
        rvTodoList = rootView.findViewById(R.id.rvTodoList)
        etNewTodo = rootView.findViewById(R.id.etNewTodo)

        etNewTodo.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                todoListAdapter.addNewTodo(Todo(etNewTodo.text.toString(), false, 0))
                etNewTodo.clearFocus()
                etNewTodo.text?.clear()
                rvTodoList.adapter
                handled = true
            }
            handled
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        rvTodoList.adapter = todoListAdapter
        rvTodoList.layoutManager = LinearLayoutManager(context)
    }

    private fun initWatchers() {
        batteryWatcher = BatteryWatcher(context, tvBattery)
        batteryWatcher.startWatch()
    }

    override fun onDestroy() {
        super.onDestroy()
        batteryWatcher.stopWatch()
    }
}