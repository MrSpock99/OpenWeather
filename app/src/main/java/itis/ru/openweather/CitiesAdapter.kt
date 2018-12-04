package itis.ru.openweather

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_city.view.*

class CitiesAdapter:
        ListAdapter<City, CitiesAdapter.CityViewHolder>(CityDiffCallback()) {
    private var mListener: OnCityListClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CityViewHolder(inflater.inflate(R.layout.item_city, parent, false))
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            mListener?.onClick(getItem(position))
        }
    }

    fun setOnItemClickListener(mItemClickListener: OnCityListClickListener) {
        this.mListener = mItemClickListener
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(city: City) {
            itemView.tv_city_name.text = city.name
            itemView.tv_item_temperature.text = city.main?.temp.toString()
        }
    }

    class CityDiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }
    }

    interface OnCityListClickListener{
        fun onClick(city: City)
    }
}
