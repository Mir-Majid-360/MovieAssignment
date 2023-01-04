package com.education.movieassignment.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.dzmitry_lakisau.month_year_picker_dialog.MonthYearPickerDialog
import com.education.movieassignment.R
import com.education.movieassignment.databinding.ActivityMainBinding
import com.education.movieassignment.models.MovieItem
import com.education.movieassignment.viewmodel.MainViewModel
import com.example.movieassignment.AdapterMovies
import java.security.AccessController.getContext
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
     lateinit var  mainViewModel:MainViewModel
     var movieList=ArrayList<MovieItem>()
    lateinit  var movieAdapter:AdapterMovies
    lateinit var  yearPickerDialog: MonthYearPickerDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initAdapter()
        getMovies()
        setClicklisteners()

    }

    private fun setClicklisteners() {
       binding.toolbar.calenderPicker.setOnClickListener{

            if(!binding.toolbar.calenderPicker.text.isEmpty())
            {
                binding.toolbar.calenderPicker.text=""
                binding.toolbar.calenderPicker.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_calender, 0);
                movieAdapter.updateList(movieList)
                return@setOnClickListener
            }


           yearPicker()
       }


    }

    private fun initAdapter() {
             movieAdapter = AdapterMovies(movieList,object:AdapterMovies.IClickListener{
                 override fun onItemClicked(pos: Int) {
                      showDeleteAlert(pos)
                 }


             })

        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=movieAdapter
    }


    fun showDeleteAlert(pos:Int)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(""+movieList.get(pos).name)
        builder.setMessage("Do you want to delete this movie")


        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            movieList.removeAt(pos)
            movieAdapter.notifyDataSetChanged()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->

        }


        builder.show()
    }


    fun yearPicker()
    {
       yearPickerDialog=  MonthYearPickerDialog.Builder(
            this,
            R.style.Style_MonthYearPickerDialog_Orange
        ).setMode(MonthYearPickerDialog.Mode.YEAR_ONLY)
            .setOnYearSelectedListener { year ->
                yearPickerDialog.dismiss()
                filterList(year)
            }
            .build()

        yearPickerDialog.show()
    }

  fun filterList(year: Int) {

        val list=ArrayList<MovieItem>()

        for(movie in movieList)
        {
            if(movie.year.toInt()==year)
            {
                list.add(movie)

            }
        }
        binding.toolbar.calenderPicker.text=""+year
        binding.toolbar.calenderPicker.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_close_24, 0);
       movieAdapter.updateList(list)
       movieAdapter.notifyDataSetChanged()

  }


    private fun initViewModel() {

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    fun getMovies()
    {
        mainViewModel.getMovies().observe(this){response->
            if(response!=null)
            {
                if(response.status_code==200)
                {
                     movieList.clear()
                     movieList.addAll(response.data.movies)
                     movieList.sortByDescending { it.year }
                     movieAdapter.notifyDataSetChanged()


                }

            }
        }
    }


}