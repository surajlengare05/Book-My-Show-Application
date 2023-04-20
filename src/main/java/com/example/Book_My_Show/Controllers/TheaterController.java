package com.example.Book_My_Show.Controllers;

import com.example.Book_My_Show.EntryDTOs.TheaterEntryDto;
import com.example.Book_My_Show.Model.Theater;
import com.example.Book_My_Show.ResponseDTOs.TheaterResponseDto;
import com.example.Book_My_Show.Services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/theater")
public class TheaterController
{
    @Autowired
    TheaterService theaterService;

    @PostMapping("/add")
    public ResponseEntity<String> addTheater (@RequestBody TheaterEntryDto theaterEntryDto)
    {
        try {
            String result =  theaterService.addTheater(theaterEntryDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllTheaters")
    public ResponseEntity<List<TheaterResponseDto>> getAllTheaters()
    {
        List<TheaterResponseDto> allTheaterList = theaterService.getAllTheaters();
        return new ResponseEntity<>(allTheaterList, HttpStatus.FOUND);
    }

    @GetMapping("/getTheatersAtALocation")
    public ResponseEntity<List<TheaterResponseDto>> getTheatersAtALocation(@RequestParam("location") String location)
    {
        List<TheaterResponseDto> theaterList = theaterService.getTheatersAtALocation(location);
        return new ResponseEntity<>(theaterList, HttpStatus.FOUND);
    }

    @GetMapping("/getMoviesInTheater")
    public ResponseEntity<Set<String>> getMoviesInTheater(@RequestParam("theaterId") int theaterId)
    {
        Set<String> listOfMoviesInTheater = theaterService.getMoviesInTheater(theaterId);
        return new ResponseEntity<>(listOfMoviesInTheater, HttpStatus.FOUND);
    }

    @GetMapping("/countOfUniqueLocations")
    public ResponseEntity<Integer> countOfUniqueLocations()
    {
        int count = theaterService.countOfUniqueLocations();
        return new ResponseEntity<>(count, HttpStatus.FOUND);
    }
}
