package com.bignerdranch.android.courseproject.ui.locations

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
import com.bignerdranch.android.courseproject.R
import com.bignerdranch.android.courseproject.databinding.FragmentLocationsBinding
import com.bignerdranch.android.courseproject.utils.Resource
import com.bignerdranch.android.courseproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationsFragment : Fragment(), LocationsAdapter.LocationsItemListener, SearchView.OnQueryTextListener {

    private var binding: FragmentLocationsBinding by autoCleared()
    private val viewModel: LocationsViewModel by viewModels()
    private lateinit var adapter: LocationsAdapter
    lateinit var searchView: SearchView

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
        getLocations()
    }

    private fun setupRecyclerView() {
        searchView = binding.idSV
        searchView.setOnQueryTextListener(this)
        adapter = LocationsAdapter(this)
        binding.locationsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.locationsRv.adapter = adapter
    }

    private fun getLocations() {
        viewModel.getLocations().observe(viewLifecycleOwner) {
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

    override fun onClickedLocation(locationId: Int) {
        findNavController().navigate(
            R.id.action_navigation_locations_to_locationsDetailFragment,
            bundleOf("id" to locationId)
        )
    }
    private fun showLoading(show: Boolean) {
        binding.swipeRefreshLocations.isRefreshing = show
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrBlank()) {
            getLocations()
        } else {
            adapter.filter.filter(newText)
        }
        return false
    }
}