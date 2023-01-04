package com.education.movieassignment.models




data class MovieResponseModel (
    var status_code : Int,
    var message : String,
    var data:Data) {

}


data class MovieItem (
    var id : Int,
    var name : String,
    var year : Int,
    var main_star : String,
    var thumbnail : String,
    var favorited_by_users : Int,
    var director : String,
    var genres: List<String>,
    var description : String,
    var genre: ArrayList<String>

)

data class  Data(
    var movies:ArrayList<MovieItem>
)