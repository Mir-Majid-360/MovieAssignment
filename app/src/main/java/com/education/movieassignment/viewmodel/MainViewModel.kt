package com.education.movieassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.education.movieassignment.api.APIService
import com.education.movieassignment.api.RetrofitBuilder
import com.education.movieassignment.models.MovieResponseModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {



     val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
          throwable.printStackTrace()
     }

     fun getMovies():MutableLiveData<MovieResponseModel>{
          var responseLiveData = MutableLiveData<MovieResponseModel>()
          val api=RetrofitBuilder.getInstance().create(APIService::class.java)

          viewModelScope.launch(Dispatchers.IO+ coroutineExceptionHandler) {

               val response= api.getMovies()
               if (response.isSuccessful && response.code() == 200) {
                    responseLiveData.postValue(response.body())

               } else {
                    responseLiveData.postValue(null)
               }


          }

          return  responseLiveData
     }









}