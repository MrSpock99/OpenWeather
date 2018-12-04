package itis.ru.openweather.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import itis.ru.openweather.*
import kotlinx.android.synthetic.main.fragment_cities.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_LAT = "LAT"
private const val ARG_LON = "LON"

class CitiesFragment : Fragment() {
    private var listener: OnFragmentInteraction? = null
    private lateinit var listAdapter: CitiesAdapter
    private var lat: Double? = null
    private var lon: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lat = it.getDouble(ARG_LAT)
            lon = it.getDouble(ARG_LON)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_cities, container, false)
        rootView.rv_cities.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(OpenWeatherApiService::class.java)
        service.getCityList(lat, lon, 50).enqueue(object : Callback<CitiesForecast> {
            override fun onFailure(call: Call<CitiesForecast>?, t: Throwable?) {
                Toast.makeText(context, "failed to load data", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<CitiesForecast>?, response: Response<CitiesForecast>) {
                val cityList = response.body().list
                listAdapter = CitiesAdapter()
                listAdapter.submitList(cityList)
                listAdapter.setOnItemClickListener(object : CitiesAdapter.OnCityListClickListener {
                    override fun onClick(city: City) {
                        WeatherCityFragment.city = city
                        listener?.pushFragment(WeatherCityFragment.newInstance())
                    }
                })
                rootView.rv_cities.adapter = listAdapter
            }
        })
        return rootView
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteraction) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteraction")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        fun newInstance(lat: Double?, lon: Double?) = CitiesFragment().apply {
            arguments = Bundle().apply {
                lat?.let { putDouble(ARG_LAT, it) }
                lon?.let { putDouble(ARG_LON, it) }
            }
        }
    }
}

