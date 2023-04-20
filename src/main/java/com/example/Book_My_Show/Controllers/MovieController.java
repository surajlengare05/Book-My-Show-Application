package com.example.Book_My_Show.Controllers;

import com.example.Book_My_Show.EntryDTOs.MovieEntryDto;
import com.example.Book_My_Show.Model.Movie;
import com.example.Book_My_Show.Model.Theater;
import com.example.Book_My_Show.ResponseDTOs.MovieResponseDto;
import com.example.Book_My_Show.ResponseDTOs.TheaterResponseDto;
import com.example.Book_My_Show.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movie")
public class MovieController
{
    @Autowired
    MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<String> addMovie( @RequestBody MovieEntryDto movieEntryDto)
    {
        try {
            String result = movieService.addMovie(movieEntryDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (Exception e) {
            String response = "Movie could not be added";
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getMovieByName")
    public ResponseEntity<MovieResponseDto> getMovieByName(@RequestParam("name") String movieName)
    {
        MovieResponseDto movieResponseDto = movieService.getMovieByName(movieName);
        return new ResponseEntity<>(movieResponseDto, HttpStatus.FOUND);
    }

    @GetMapping("/getAllMoviesNames")
    public ResponseEntity<List<String>> getAllMoviesNames()
    {
        List<String> movieNamesList = movieService.getAllMoviesNames();
        return  new ResponseEntity<>(movieNamesList, HttpStatus.FOUND);
    }

    @GetMapping("/getTheatersForMovie")
    public ResponseEntity<Set<TheaterResponseDto>> getTheatersForMovie(@RequestParam("movieName") String movieName)
    {
        Set<TheaterResponseDto> theaterList = movieService.getTheatersForMovie(movieName);
        return new ResponseEntity<>(theaterList, HttpStatus.FOUND);
    }

    @GetMapping("/calculateRevenue")
    public ResponseEntity<Integer> calculateRevenue(@RequestParam("movieName") String movieName)
    {
        int revenue = movieService.calculateRevenue(movieName);
        return new ResponseEntity<>(revenue, HttpStatus.FOUND);
    }

    @GetMapping("/rateFlopOrHit")
    public ResponseEntity<String> rateFlopOrHit(String movieName)
    {
        String result = movieService.rateFlopOrHit(movieName);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }


}
