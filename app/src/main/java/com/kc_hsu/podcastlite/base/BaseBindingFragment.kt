package com.kc_hsu.podcastlite.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseBindingFragment<B : ViewDataBinding, VM : ViewModel>(@LayoutRes private val layoutResourceId: Int) :
    Fragment() {

    protected var binding by AutoClearedValue<B>()
    protected abstract val viewModel: VM

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<B>(inflater, layoutResourceId, container, false).also {
            binding = it
        }.root

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        // TODO Uncomment it when using data binding
        // binding.setVariable(BR.viewmodel, viewModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
