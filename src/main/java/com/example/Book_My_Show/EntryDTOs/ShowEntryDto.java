package com.example.Book_My_Show.EntryDTOs;

import com.example.Book_My_Show.Enum.ShowType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class ShowEntryDto
{
    private LocalDate showDate;
    private LocalTime showTime;
    private ShowType showType;
    private int movieId;
    private int theaterId;
    private int classicPrice;
    private int premiumPrice;
}
