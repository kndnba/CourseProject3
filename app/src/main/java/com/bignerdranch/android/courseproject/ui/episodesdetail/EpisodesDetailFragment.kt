package com.bignerdranch.android.courseproject.ui.episodesdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bignerdranch.android.courseproject.data.entities.Episodes
import com.bignerdranch.android.courseproject.databinding.FragmentEpisodesDetailBinding
import com.bignerdranch.android.courseproject.utils.Resource
import com.bignerdranch.android.courseproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodesDetailFragment : Fragment() {

    private var binding: FragmentEpisodesDetailBinding by autoCleared()
    private val viewModel: EpisodesDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.episode.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindLocation(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.episodeCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.episodeCl.visibility = View.GONE
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindLocation(episodes: Episodes) {
        binding.nameEpisodeDetail.text = episodes.name
        binding.episodeCode.text = episodes.episode
        binding.airDate.text = episodes.air_date
        binding.created.text = episodes.created
    }
}