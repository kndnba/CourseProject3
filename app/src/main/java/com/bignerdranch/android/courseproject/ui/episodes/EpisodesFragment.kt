package com.bignerdranch.android.courseproject.ui.episodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.courseproject.R
import com.bignerdranch.android.courseproject.databinding.FragmentEpisodesBinding
import com.bignerdranch.android.courseproject.utils.Resource
import com.bignerdranch.android.courseproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesFragment : Fragment(), EpisodesAdapter.EpisodesItemListener, SearchView.OnQueryTextListener {

    private var binding: FragmentEpisodesBinding by autoCleared()
    private val viewModel: EpisodesViewModel by viewModels()
    private lateinit var adapter: EpisodesAdapter
    lateinit var searchView: SearchView
    var currentPage = 1

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
        getEpisodes()
    }

    private fun setupRecyclerView() {
        searchView = binding.idSV
        searchView.setOnQueryTextListener(this)
        adapter = EpisodesAdapter(this)
        binding.episodesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.episodesRv.adapter = adapter
        binding.episodesRv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisiblePosition == totalItemCount - 10){
                    currentPage++
                    getEpisodes()
                }
            }
        })
    }

    private fun getEpisodes() {
        viewModel.getEpisodes(currentPage).observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBarLocations.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                    showLoading(false)
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    showLoading(true)
            }
        }
    }

    override fun onClickedEpisode(episodeId: Int) {
        findNavController().navigate(
            R.id.action_navigation_episodes_to_episodesDetailFragment,
            bundleOf("id" to episodeId)
        )
    }
    private fun showLoading(show: Boolean) {
        binding.swipeRefreshEpisodes.setOnRefreshListener {
            binding.swipeRefreshEpisodes.isRefreshing = show

        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrBlank()) {
            getEpisodes()
        } else {
            adapter.filter.filter(newText)
        }
        return false
    }
}