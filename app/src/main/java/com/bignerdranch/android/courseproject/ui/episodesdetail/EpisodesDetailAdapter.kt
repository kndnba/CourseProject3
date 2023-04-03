package com.bignerdranch.android.courseproject.ui.episodesdetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bignerdranch.android.courseproject.data.entities.Character
import com.bignerdranch.android.courseproject.databinding.ItemCharacterBinding

class EpisodesDetailAdapter(private val listener: CharacterItemListener) : RecyclerView.Adapter<EpisodeDetailViewHolder>() {

    interface CharacterItemListener {
        fun onClickedCharacter(characterId: Int)
    }

    private var items = ArrayList<Character>()

    fun setItems(items: ArrayList<Character>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeDetailViewHolder {
        val binding: ItemCharacterBinding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeDetailViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: EpisodeDetailViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

class EpisodeDetailViewHolder(private val itemBinding: ItemCharacterBinding, private val listener: EpisodesDetailAdapter.CharacterItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var character: Character

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Character) {
        this.character = item
        itemBinding.name.text = item.name
        itemBinding.speciesAndStatus.text = """${item.species} - ${item.status}"""
        Glide.with(itemBinding.root)
            .load(item.image)
            .transform(CircleCrop())
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onClickedCharacter(character.id)
    }
}