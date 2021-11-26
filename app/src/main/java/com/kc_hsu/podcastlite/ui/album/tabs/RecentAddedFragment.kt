package com.kc_hsu.podcastlite.ui.album.tabs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kc_hsu.podcastlite.R

class RecentAddedFragment : Fragment() {

    companion object {
        fun newInstance() = RecentAddedFragment()
    }

    private lateinit var viewModel: RecentAddedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recent_added_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecentAddedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
