package com.bignerdranch.android.courseproject.ui.episodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.courseproject.R
import com.bignerdranch.android.courseproject.databinding.FragmentEpisodesBinding
import com.bignerdranch.android.courseproject.utils.Resource
import com.bignerdranch.android.courseproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesFragment : Fragment(), EpisodesAdapter.EpisodesItemListener {

    private var binding: FragmentEpisodesBinding by autoCleared()
    private val viewModel: EpisodesViewModel by viewModels()
    private lateinit var adapter: EpisodesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = EpisodesAdapter(this)
        binding.episodesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.episodesRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.episodes.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBarLocations.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                    showLoading(false)
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBarLocations.visibility = View.VISIBLE
            }
        }
    }

    override fun onClickedEpisode(episodeId: Int) {
        findNavController().navigate(
            R.id.action_navigation_episodes_to_episodesDetailFragment,
            bundleOf("id" to episodeId)
        )
    }
    fun showLoading(show: Boolean) {
        binding.swipeRefreshEpisodes.isRefreshing = show
    }
}