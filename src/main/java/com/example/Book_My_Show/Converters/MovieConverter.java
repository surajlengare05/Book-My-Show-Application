package com.example.Book_My_Show.Converters;

import com.example.Book_My_Show.EntryDTOs.MovieEntryDto;
import com.example.Book_My_Show.Model.Movie;
import com.example.Book_My_Show.ResponseDTOs.MovieResponseDto;

public class MovieConverter
{
    // make all methods Static , so that we can call them by className (without creating an object of class)

    public static Movie convertEntryDtoToEntity(MovieEntryDto movieEntryDto)
    {
        Movie movie = Movie.builder()
                .name(movieEntryDto.getName())
                .duration(movieEntryDto.getDuration())
                .rating(movieEntryDto.getRating())
                .language(movieEntryDto.getLanguage())
                .genre(movieEntryDto.getGenre())
                .build();

        return movie;
    }

    public static MovieResponseDto convertEntityToResponseDto(Movie movie)
    {
        MovieResponseDto movieResponseDto = MovieResponseDto.builder()
                .id(movie.getId())
                .name(movie.getName())
                .duration(movie.getDuration())
                .rating(movie.getRating())
                .language(movie.getLanguage())
                .genre(movie.getGenre())
                .build();

        return movieResponseDto;
    }
}
