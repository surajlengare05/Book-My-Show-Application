package com.example.Book_My_Show.Repositories;

import com.example.Book_My_Show.Model.Movie;
import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>
{

     Movie findByName(String movieName);

     @Query(value = "select distinct theater_id from shows where movie_id=:movieId", nativeQuery = true)
     List<Integer> findTheatersForMovie(int movieId);
     // used in alternate way in getTheatersForMovie()  (MovieService)


     @Query(value = "select sum(total_amount) from ticket where show_id=:showId", nativeQuery = true)
     int findRevenueOfMovie(int showId);
     // used in alternate way in calculateRevenue()  (MovieService)


}
