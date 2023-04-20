package com.example.Book_My_Show.Controllers;

import com.example.Book_My_Show.EntryDTOs.ShowEntryDto;
import com.example.Book_My_Show.ResponseDTOs.ShowResponseDto;
import com.example.Book_My_Show.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/show")
public class ShowController
{
    @Autowired
    ShowService showService;

    @PostMapping("/add")
    public ResponseEntity<String> addShow(@RequestBody ShowEntryDto showEntryDto)
    {
        try {
            String result = showService.addShow(showEntryDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (Exception e) {
            String response = "Show could not be added";
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getShowsForMovieInTheater")
    public ResponseEntity<List<ShowResponseDto>> getShowsForMovieInTheater(@RequestParam("theaterId") int theaterId, @RequestParam("movieName") String movieName)
    {
        List<ShowResponseDto> listOfShows = showService.getShowsForMovieInTheater(theaterId, movieName);
        return new ResponseEntity<>(listOfShows, HttpStatus.FOUND);
    }

    @GetMapping("/getMovieWithMaxShows")
    public ResponseEntity<String> getMovieWithMaxShows()
    {
        String result = showService.getMovieWithMaxShows();
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @GetMapping("/checkSeatsAvailability")
    public List<List<String>> checkSeatsAvailability(@RequestParam("showId") int showId)
    {
        List<List<String>> seatsStatus = showService.checkSeatsAvailability(showId);
        return seatsStatus;
    }

}
