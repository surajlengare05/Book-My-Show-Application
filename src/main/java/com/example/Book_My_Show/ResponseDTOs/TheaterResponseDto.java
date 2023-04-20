package com.example.Book_My_Show.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TheaterResponseDto
{
    private int id;
    private String theaterName;
    private String location;
}
