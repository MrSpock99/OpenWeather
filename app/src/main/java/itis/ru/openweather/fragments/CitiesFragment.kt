package itis.ru.openweather.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import itis.ru.openweather.*
import itis.ru.openweather.database.AppDatabase
import kotlinx.android.synthetic.main.fragment_cities.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_cities.apply {
            layoutManager = LinearLayoutManager(rootView.context)
        }
        val retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val service = retrofit.create(OpenWeatherApiService::class.java)
        val db = context?.let { AppDatabase.getInstance(it) }
        val weatherDataDao = db?.weatherDataDao()
        service.getCityList(lat, lon)
                .subscribeOn(Schedulers.io())
                .map { it.list }
                .map {
                    weatherDataDao?.nukeTable()
                    weatherDataDao?.insert(it)
                    it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy({
                    submitList(it)
                }, onError = {
                    weatherDataDao?.getAll()
                            ?.subscribeOn(Schedulers.io())
                            ?.observeOn(AndroidSchedulers.mainThread())
                            ?.subscribe {
                                submitList(it)
                            }
                })
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

    private fun submitList(cityList: List<City>?) {
        listAdapter = CitiesAdapter()
        listAdapter.submitList(cityList)
        listAdapter.setOnItemClickListener(object : CitiesAdapter.OnCityListClickListener {
            override fun onClick(city: City) {
                WeatherCityFragment.city = city
                listener?.pushFragment(WeatherCityFragment.newInstance())
            }
        })
        rv_cities.adapter = listAdapter
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
