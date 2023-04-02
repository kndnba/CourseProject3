package com.bignerdranch.android.courseproject.ui.locationsdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bignerdranch.android.courseproject.data.entities.Locations
import com.bignerdranch.android.courseproject.databinding.FragmentLocationsDetailBinding
import com.bignerdranch.android.courseproject.utils.Resource
import com.bignerdranch.android.courseproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsDetailFragment : Fragment() {

    private var binding: FragmentLocationsDetailBinding by autoCleared()
    private val viewModel: LocationsDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.location.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindLocation(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.locationCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.locationCl.visibility = View.GONE
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun bindLocation(locations: Locations) {
        binding.nameLocationDetail.text = locations.name
        binding.type.text = locations.type
        binding.dimension.text = locations.dimension
        binding.created.text = locations.created
    }
}