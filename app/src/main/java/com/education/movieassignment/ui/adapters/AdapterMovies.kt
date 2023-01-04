package com.example.movieassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.education.movieassignment.databinding.ItemMovieBinding
import com.education.movieassignment.glide.Glide
import com.education.movieassignment.models.MovieItem
import com.education.movieassignment.models.MovieResponseModel


class AdapterMovies(var list:ArrayList<MovieItem>,val listener:IClickListener): RecyclerView.Adapter<AdapterMovies.MoviesViewHolder>() {


   interface  IClickListener{
       fun onItemClicked(pos:Int)
   }
    class MoviesViewHolder(val binding:ItemMovieBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(movieItem: MovieItem){
            binding.movieName.text=movieItem.name
            binding.year.text=""+movieItem.year
            Glide.glideFetch(movieItem.thumbnail,binding.image)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
           holder.bind(list.get(position))
           holder.itemView.setOnLongClickListener {
               listener.onItemClicked(position)
               true
           }

    }


    fun updateList(list:ArrayList<MovieItem>)
    {
        this.list=list;
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}