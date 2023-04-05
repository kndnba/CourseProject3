package com.bignerdranch.android.courseproject.ui.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.courseproject.R
import com.bignerdranch.android.courseproject.databinding.FragmentCharacterBinding
import com.bignerdranch.android.courseproject.utils.Resource
import com.bignerdranch.android.courseproject.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment(), CharacterAdapter.CharacterItemListener, SearchView.OnQueryTextListener {

    private var binding: FragmentCharacterBinding by autoCleared()
    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var adapter: CharacterAdapter
    lateinit var searchView: SearchView
    var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getCharacters()
    }

    private fun setupRecyclerView() {
        searchView = binding.idSV
        searchView.setOnQueryTextListener(this)
        adapter = CharacterAdapter(this)
        binding.charactersRv.layoutManager = LinearLayoutManager(requireContext())
        binding.charactersRv.adapter = adapter
        binding.charactersRv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisiblePosition == totalItemCount - 10){
                    currentPage++
                    getCharacters()
                }
            }
        })
    }

    private fun getCharacters(){
        viewModel.getCharacters(currentPage).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                    showLoading(false)
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    showLoading(true)
                }
            }
        })
    }

    override fun onClickedCharacter(characterId: Int) {
        findNavController().navigate(
            R.id.action_charactersFragment_to_characterDetailFragment,
            bundleOf("id" to characterId)
        )
    }
    private fun showLoading(show: Boolean) {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = show

        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrBlank()) {
            getCharacters()
        } else {
            adapter.filter.filter(newText)
        }
        return false
    }
}