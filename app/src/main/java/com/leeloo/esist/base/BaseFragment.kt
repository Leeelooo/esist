package com.leeloo.esist.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<VS : BaseViewState, I : BaseIntent<A>, A : BaseAction> : Fragment() {
    protected abstract val viewModel: BaseViewModel<VS, I, A>
    protected abstract val layoutResource: Int
    protected abstract val intentsFlow: Flow<I>

    protected abstract fun initViews()
    protected abstract fun render(viewState: VS)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        layoutResource,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.processIntents(intentsFlow)
        lifecycleScope.launch {
            viewModel.viewStatesFlow().collect { render(it) }
        }
    }

}