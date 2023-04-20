package com.example.Book_My_Show.Services;

import com.example.Book_My_Show.Converters.ShowConverter;
import com.example.Book_My_Show.EntryDTOs.ShowEntryDto;
import com.example.Book_My_Show.Enum.SeatType;
import com.example.Book_My_Show.Model.*;
import com.example.Book_My_Show.Repositories.MovieRepository;
import com.example.Book_My_Show.Repositories.ShowRepository;
import com.example.Book_My_Show.Repositories.ShowSeatRepository;
import com.example.Book_My_Show.Repositories.TheaterRepository;
import com.example.Book_My_Show.ResponseDTOs.ShowResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowService
{
    @Autowired
    ShowRepository showRepository;
    @Autowired
    ShowSeatRepository showSeatRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    TheaterRepository theaterRepository;


    public String addShow(ShowEntryDto showEntryDto) throws Exception
    {
        Show show = ShowConverter.convertEntryDtoToEntity(showEntryDto);

        int movieId = showEntryDto.getMovieId();
        int theaterId = showEntryDto.getTheaterId();

        Movie movie = movieRepository.findById(movieId).get();
        Theater theater = theaterRepository.findById(theaterId).get();

        // setting movie , theater attributes of show
        show.setMovie(movie);
        show.setTheater(theater);

        List<ShowSeat> listOfShowSeats = createShowSeats(showEntryDto, show);

        show.setListOfShowSeats(listOfShowSeats);    // setting listOfShowSeats attribute of show


        // we also need to update parents of show i.e add this new show to list of shows in movie , theater

        movie.getShowsList().add(show);     /*List<Show> showsList1 = movie.getShowsList();
                                              showsList1.add(show);
                                              movie.setShowsList(showsList1);*/
        theater.getShowList().add(show);

        // Before saving parents(movie, theater) to their repositories, save child(show) & get it into 1 variable
        // as following-
        //otherwise (if this is not done then), show will get saved twice after saving movie & theater because of cascading
        show = showRepository.save(show);

        movieRepository.save(movie);
        theaterRepository.save(theater);

        // no need to save show into repository because it will automatically be done by cascading
        // i.e by saving its parents (Movie, Theater) to their respective repositories

        return ("Show added successfully");

    }

    public List<ShowSeat> createShowSeats(ShowEntryDto showEntryDto, Show show)
    {
        Theater theater = show.getTheater();
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();
        List<ShowSeat> listOfShowSeats = new ArrayList<>();

        for (TheaterSeat theaterSeat:theaterSeatList)
        {
            ShowSeat showSeat = new ShowSeat();

            showSeat.setSeatNo(theaterSeat.getSeatNo());
            showSeat.setSeatType(theaterSeat.getSeatType());

            if (theaterSeat.getSeatType().equals(SeatType.CLASSIC)) {
                showSeat.setPrice(showEntryDto.getClassicPrice());
            }
            else {
                showSeat.setPrice(showEntryDto.getPremiumPrice());
            }

            showSeat.setBooked(false);
            showSeat.setShow(show);              // setting parent (foreign key)

            listOfShowSeats.add(showSeat);

            // no need to save these ShowSeats in repository because that will automatically done by cascadeing
        }

        return listOfShowSeats;
    }

    public List<ShowResponseDto> getShowsForMovieInTheater(int theaterId, String movieName)
    {
        int movieId = movieRepository.findByName(movieName).getId();
        List<Show> showList = showRepository.findShowsForMovieInTheater(theaterId, movieId);
        List<ShowResponseDto> listOfShows = new ArrayList<>();

        for (Show show: showList)
        {
            ShowResponseDto showResponseDto = ShowConverter.convertEntityToResponseDto(show);
            listOfShows.add(showResponseDto);
        }
        return listOfShows;
    }

    public String getMovieWithMaxShows()
    {
        List<Integer> movieIdsList = showRepository.findListOfAllMovieIds();
        Map<Integer,Integer> map = new HashMap<>();   // key-> movieId,  value-> count

        for (Integer id: movieIdsList)
        {
            if (map.containsKey(id))
            {
                map.put(id, map.get(id)+1);
            }
            else
            {
                map.put(id, 1);
            }
        }

        int maxCount = 0;
        int maxMovie = -1;
        for (Integer key: map.keySet())
        {
            if (map.get(key) > maxCount)
            {
                maxCount = map.get(key);
                maxMovie = key;
            }
        }

        String movieName = movieRepository.findById(maxMovie).get().getName();
        return (movieName + " --> " + maxCount + " shows");
    }

    public List<List<String>> checkSeatsAvailability(int showId)
    {
        Show show = showRepository.findById(showId).get();
        List<ShowSeat> listOfShowSeats = show.getListOfShowSeats();
        List<List<String>> seatsStatus = new ArrayList<>();

        for (ShowSeat showSeat: listOfShowSeats)
        {
            List<String> currentSeat = new ArrayList<>();
            currentSeat.add(showSeat.getSeatNo());
            if (showSeat.isBooked()==true)
            {
                currentSeat.add("Booked");
            }
            else
            {
                currentSeat.add("Available");
            }
            seatsStatus.add(currentSeat);
        }
        return seatsStatus;
    }
}
