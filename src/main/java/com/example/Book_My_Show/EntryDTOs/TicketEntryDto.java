package com.example.Book_My_Show.EntryDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TicketEntryDto
{
     private List<String> requestedSeats = new ArrayList<>();
     private int showId;
     private int userId;
}
