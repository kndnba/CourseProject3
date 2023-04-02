package com.bignerdranch.android.courseproject.ui.locations

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
import com.bignerdranch.android.courseproject.databinding.FragmentLocationsBinding
import com.bignerdranch.android.courseproject.utils.Resource
import com.bignerdranch.android.courseproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : Fragment(), LocationsAdapter.LocationsItemListener {

    private var binding: FragmentLocationsBinding by autoCleared()
    private val viewModel: LocationsViewModel by viewModels()
    private lateinit var adapter: LocationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = LocationsAdapter(this)
        binding.locationsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.locationsRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.locations.observe(viewLifecycleOwner) {
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

    override fun onClickedLocation(locationId: Int) {
        findNavController().navigate(
            R.id.action_navigation_locations_to_locationsDetailFragment,
            bundleOf("id" to locationId)
        )
    }
    fun showLoading(show: Boolean) {
        binding.swipeRefreshLocations.isRefreshing = show
    }
}