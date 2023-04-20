package com.example.Book_My_Show.EntryDTOs;

import com.example.Book_My_Show.Enum.Genre;
import com.example.Book_My_Show.Enum.Language;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieEntryDto
{
    private String name;
    private int duration;
    private Double rating;
    private Language language;
    private Genre genre;
}
