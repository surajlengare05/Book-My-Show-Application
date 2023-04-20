package com.example.Book_My_Show.Repositories;

import com.example.Book_My_Show.Model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer>
{
    @Query(value = "select * from shows where theater_id=:theaterId and movie_id=:movieId", nativeQuery = true)
    List<Show> findShowsForMovieInTheater(int theaterId, int movieId);
    // used in getShowsForMovieInTheater (ShowService)

    @Query(value = "select movie_id from shows", nativeQuery = true)
    List<Integer> findListOfAllMovieIds();
    // used in getMovieWithMaxShows()  (showService)
}
