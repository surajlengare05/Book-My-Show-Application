package com.example.Book_My_Show.Services;


import com.example.Book_My_Show.Converters.MovieConverter;
import com.example.Book_My_Show.Converters.TheaterConverter;
import com.example.Book_My_Show.EntryDTOs.MovieEntryDto;

import com.example.Book_My_Show.Model.*;
import com.example.Book_My_Show.Repositories.*;
import com.example.Book_My_Show.ResponseDTOs.MovieResponseDto;
import com.example.Book_My_Show.ResponseDTOs.TheaterResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MovieService
{
    @Autowired
    MovieRepository movieRepository;


    public String addMovie(MovieEntryDto movieEntryDto) throws Exception
    {
        Movie movie = MovieConverter.convertEntryDtoToEntity(movieEntryDto);

        movieRepository.save(movie);
        return ("Movie added successfully");
    }

    public MovieResponseDto getMovieByName(String movieName)
    {
        Movie movie = movieRepository.findByName(movieName);

       MovieResponseDto movieResponseDto = MovieConverter.convertEntityToResponseDto(movie);
       return  movieResponseDto;
    }

    public List<String> getAllMoviesNames()
    {
        List<String> movieNamesList = new ArrayList<>();
        List<Movie> movieList = movieRepository.findAll();

        for (Movie movie: movieList) {
            movieNamesList.add(movie.getName());
        }

        return movieNamesList;
    }

    public Set<TheaterResponseDto> getTheatersForMovie(String movieName)
    {
        Movie movie = movieRepository.findByName(movieName);
        List<Show> showList = movie.getShowsList();
        Set<TheaterResponseDto> theaterList = new HashSet<>();

        for (Show show: showList)
        {
            Theater theater = show.getTheater();
            TheaterResponseDto theaterResponseDto = TheaterConverter.convertEntityToResponseDto(theater);
            theaterList.add(theaterResponseDto);
        }
        return theaterList;

                    // using Custom Query-

       /* int movieId = movieRepository.findByName(movieName).getId();
        List<Integer> theaterIds = movieRepository.findTheatersForMovie(movieId);
        List<TheaterResponseDto> theaterList = new ArrayList<>();

        for (Integer id: theaterIds)
        {
            Theater theater = theaterRepository.findById(id).get();
            TheaterResponseDto theaterResponseDto = TheaterConverter.convertEntityToResponseDto(theater);
            theaterList.add(theaterResponseDto);
        }
        return theaterList;*/
    }

    public int calculateRevenue(String movieName)
    {
        Movie movie = movieRepository.findByName(movieName);
        List<Show> showList = movie.getShowsList();

        int revenue = 0;
        for (Show show : showList)
        {
            List<Ticket> listOfBookedTickets = show.getListOfBookedTickets();
            for (Ticket ticket: listOfBookedTickets)
            {
                revenue = revenue + ticket.getTotalAmount();
            }
        }
        return revenue;

                    // using custom query
       /* Movie movie = movieRepository.findByName(movieName);
        List<Show> showList = movie.getShowsList();

        int revenue = 0;
        for (Show show: showList)
        {
            int showId = show.getId();
            revenue = movieRepository.findRevenueOfMovie(showId);
        }
        return revenue; */
    }
public String rateFlopOrHit(String movieName)
    {
        int revenue = calculateRevenue(movieName);
        Movie movie = movieRepository.findByName(movieName);
        List<Show> showList = movie.getShowsList();

        int expectedRevenue = 0;       // total amount if all shows of this movie are assumed to be houseful
        for (Show show: showList)
        {
            List<ShowSeat> listOfShowSeat = show.getListOfShowSeats();
            int expectedShowCollection = 0;       // total amount if all seats of current show are assumed to be booked
            for (ShowSeat showSeat: listOfShowSeat)
            {
                expectedShowCollection = expectedShowCollection + showSeat.getPrice();
            }
            expectedRevenue = expectedRevenue + expectedShowCollection;
        }

        if (revenue < (expectedRevenue/2))      // if actual revenue generated is less than 50% of expected
        {
            return ("This movie got FLOP");
        }
        return ("This movie got HIT");
    }

}
