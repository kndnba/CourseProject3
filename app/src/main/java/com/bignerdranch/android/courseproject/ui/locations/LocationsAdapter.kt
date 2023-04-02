package com.bignerdranch.android.courseproject.ui.locations

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.courseproject.data.entities.Locations
import com.bignerdranch.android.courseproject.databinding.ItemLocationsBinding

class LocationsAdapter(private val listener: LocationsItemListener) : RecyclerView.Adapter<LocationsViewHolder>() {

    interface LocationsItemListener {
        fun onClickedLocation(locationId: Int)
    }

    private val items = ArrayList<Locations>()

    fun setItems(items: ArrayList<Locations>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val binding: ItemLocationsBinding = ItemLocationsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationsViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) = holder.bind(items[position])
}

class LocationsViewHolder(private val itemBinding: ItemLocationsBinding, private val listener: LocationsAdapter.LocationsItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var location: Locations

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: Locations) {
        this.location = item
        itemBinding.nameLocation.text = item.name
        itemBinding.type.text = item.type
        itemBinding.dimension.text = item.dimension
    }

    override fun onClick(v: View?) {
        listener.onClickedLocation(location.id)
    }
}