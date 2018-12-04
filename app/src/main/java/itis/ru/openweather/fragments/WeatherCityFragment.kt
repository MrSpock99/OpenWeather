package itis.ru.openweather.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import itis.ru.openweather.City
import itis.ru.openweather.R
import kotlinx.android.synthetic.main.fragment_weather_city.view.*


class WeatherCityFragment : Fragment(){

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_weather_city, container, false)
        rootView.tv_cityName.text = city?.name
        rootView.tv_humidity.text = city?.main?.humidity.toString()
        rootView.tv_press.text = city?.main?.pressure.toString()
        rootView.tv_temperature.text = city?.main?.temp.toString()
        rootView.tv_wind.text = city?.wind?.deg.toString()
        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        var city: City? = null
        fun newInstance() = WeatherCityFragment()
    }
}
