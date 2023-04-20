package com.example.Book_My_Show.ResponseDTOs;

import com.example.Book_My_Show.Enum.Genre;
import com.example.Book_My_Show.Enum.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MovieResponseDto
{
    private int id;
    private String name;
    private int duration;
    private Double rating;
    private Language language;
    private Genre genre;
}
