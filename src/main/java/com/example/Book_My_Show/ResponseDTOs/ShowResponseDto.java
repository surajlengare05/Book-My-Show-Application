package com.example.Book_My_Show.ResponseDTOs;

import com.example.Book_My_Show.Enum.ShowType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ShowResponseDto
{
    private int id;
    private String movieName;
    private LocalDate showDate;
    private LocalTime showTime;
    private ShowType showType;
}
