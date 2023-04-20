package com.example.Book_My_Show.Services;

import com.example.Book_My_Show.Converters.TheaterConverter;
import com.example.Book_My_Show.EntryDTOs.TheaterEntryDto;
import com.example.Book_My_Show.Enum.SeatType;
import com.example.Book_My_Show.Model.Movie;
import com.example.Book_My_Show.Model.Show;
import com.example.Book_My_Show.Model.Theater;
import com.example.Book_My_Show.Model.TheaterSeat;
import com.example.Book_My_Show.Repositories.TheaterRepository;
import com.example.Book_My_Show.Repositories.TheaterSeatRepository;
import com.example.Book_My_Show.ResponseDTOs.TheaterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TheaterService
{
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    TheaterSeatRepository theaterSeatRepository;

    public String addTheater(TheaterEntryDto theaterEntryDto) throws Exception
    {
        if (theaterEntryDto.getName()==null || theaterEntryDto.getLocation()==null)
        {
            throw new Exception("Name and Location must be valid");
        }

        Theater theater = TheaterConverter.convertEntryDtoToEntity(theaterEntryDto);

        List<TheaterSeat> theaterSeatList = createTheaterSeats(theaterEntryDto, theater);

        theater.setTheaterSeatList(theaterSeatList);

        theaterRepository.save(theater);    // this saves all theaterSeats (of this particular theater)
                                            // in TheaterSeatRepository also.  p->Theater,  c->TheaterSeat
        return ("Theater added successfully");
    }


    private List<TheaterSeat> createTheaterSeats(TheaterEntryDto theaterEntryDto, Theater theater)
    {
        int noClassicSeats = theaterEntryDto.getClassicSeatsCount();
        int noPremiumSeats = theaterEntryDto.getPremiumSeatsCount();

        List<TheaterSeat> theaterSeatList = new ArrayList<>();

        for (int count=1; count<=noClassicSeats; count++)       // creating classic seats
        {
            // we need to make new TheaterSeat
            TheaterSeat theaterSeat = TheaterSeat.builder()
                    .seatType(SeatType.CLASSIC)
                    .seatNo(count+"C")
                    .theater(theater)                        // setting parent (foreign) attribute
                    .build();
            theaterSeatList.add(theaterSeat);
        }

        for (int count=1; count<=noPremiumSeats; count++)       // creating premium seats
        {
            TheaterSeat theaterSeat = TheaterSeat.builder()
                    .seatType(SeatType.PREMIUM)
                    .seatNo(count+"P")
                    .theater(theater)
                    .build();
            theaterSeatList.add(theaterSeat);
        }

        // save all theaterSeats into theaterSeatRepository also
        // instead of saving each seat individually, we can save all at a time (by saving list)
        //theaterSeatRepository.saveAll(theaterSeatList);
        // but because of cascading, no need to save theaterSeats. They will get saved automatically
        // by saving parent (Theater) above 'addTheater' function

        return theaterSeatList;
    }

    public List<TheaterResponseDto> getAllTheaters()
    {
        List<Theater> theaterList = theaterRepository.findAll();
        List<TheaterResponseDto> allTheaterList = new ArrayList<>();

        for (Theater theater: theaterList)
        {
           TheaterResponseDto theaterResponseDto = TheaterConverter.convertEntityToResponseDto(theater);
            allTheaterList.add(theaterResponseDto);
        }
        return allTheaterList;
    }

    public  List<TheaterResponseDto> getTheatersAtALocation(String location)
    {
        List<Theater> theaterList = theaterRepository.findByLocation(location);
        List<TheaterResponseDto> listOfTheatersAtALocation = new ArrayList<>();

        for (Theater theater: theaterList)
        {
            TheaterResponseDto theaterResponseDto = TheaterConverter.convertEntityToResponseDto(theater);
            listOfTheatersAtALocation.add(theaterResponseDto);
        }
        return listOfTheatersAtALocation;
    }

    public Set<String> getMoviesInTheater(int theaterId)
    {
        Theater theater = theaterRepository.findById(theaterId).get();
        List<Show> showList = theater.getShowList();
        Set<String> movieNames = new HashSet<>();

        for (Show show: showList)
        {
            Movie movie = show.getMovie();
            movieNames.add(movie.getName());
        }
        return movieNames;
    }

    public int countOfUniqueLocations()
    {
        int count = theaterRepository.findCountOfUniqueLocations();
        return count;
    }


}
