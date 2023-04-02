package com.bignerdranch.android.courseproject.ui.episodes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.courseproject.data.entities.Episodes
import com.bignerdranch.android.courseproject.databinding.ItemEpisodesBinding

class EpisodesAdapter(private val listener: EpisodesItemListener) : RecyclerView.Adapter<EpisodesViewHolder>() {

    interface EpisodesItemListener {
        fun onClickedEpisode(episodeId: Int)
    }

    private val items = ArrayList<Episodes>()

    fun setItems(items: ArrayList<Episodes>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val binding: ItemEpisodesBinding = ItemEpisodesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodesViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) = holder.bind(items[position])
}

class EpisodesViewHolder(private val itemBinding: ItemEpisodesBinding, private val listener: EpisodesAdapter.EpisodesItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var episode: Episodes

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Episodes) {
        this.episode = item
        itemBinding.nameEpisode.text = item.name
        itemBinding.airDateEpisode.text = item.air_date
        itemBinding.codeEpisode.text = item.episode
    }

    override fun onClick(v: View?) {
        listener.onClickedEpisode(episode.id)
    }
}