package com.example.Book_My_Show.EntryDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TheaterEntryDto
{
    private String name;
    private String location;

    private int classicSeatsCount;
    private int premiumSeatsCount;
}
