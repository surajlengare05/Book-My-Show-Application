package com.example.Book_My_Show.Converters;

import com.example.Book_My_Show.EntryDTOs.TheaterEntryDto;
import com.example.Book_My_Show.Model.Theater;
import com.example.Book_My_Show.ResponseDTOs.TheaterResponseDto;

public class TheaterConverter
{
    public static Theater convertEntryDtoToEntity(TheaterEntryDto theaterEntryDto)
    {
        Theater theater = Theater.builder()
                .name(theaterEntryDto.getName())
                .location(theaterEntryDto.getLocation())
                .build();

        return theater;
    }

    public static TheaterResponseDto convertEntityToResponseDto(Theater theater)
    {
        TheaterResponseDto theaterResponseDto = TheaterResponseDto.builder()
                .id(theater.getId())
                .theaterName(theater.getName())
                .location(theater.getLocation())
                .build();

        return theaterResponseDto;
    }
}
