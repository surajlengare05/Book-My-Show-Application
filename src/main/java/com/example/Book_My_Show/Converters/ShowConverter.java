package com.example.Book_My_Show.Converters;

import com.example.Book_My_Show.EntryDTOs.ShowEntryDto;
import com.example.Book_My_Show.Model.Show;
import com.example.Book_My_Show.ResponseDTOs.ShowResponseDto;

public class ShowConverter
{
    public static Show convertEntryDtoToEntity(ShowEntryDto showEntryDto)
    {
        Show show = Show.builder()
                .showDate(showEntryDto.getShowDate())
                .showTime(showEntryDto.getShowTime())
                .showType(showEntryDto.getShowType())
                .build();

        return show;
    }

    public static ShowResponseDto convertEntityToResponseDto(Show show)
    {
        ShowResponseDto showResponseDto = ShowResponseDto.builder()
                .id(show.getId())
                .movieName(show.getMovie().getName())
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .showType(show.getShowType())
                .build();

        return showResponseDto;
    }
}
