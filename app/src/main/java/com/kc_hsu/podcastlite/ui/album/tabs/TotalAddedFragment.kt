package com.kc_hsu.podcastlite.ui.album.tabs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kc_hsu.podcastlite.R

class TotalAddedFragment : Fragment() {

    companion object {
        fun newInstance() = TotalAddedFragment()
    }

    private lateinit var viewModel: TotalAddedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.total_added_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TotalAddedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
