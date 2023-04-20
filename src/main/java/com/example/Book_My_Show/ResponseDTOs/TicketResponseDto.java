package com.example.Book_My_Show.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TicketResponseDto
{
    private int id;
    private String ticketId;
    private int totalAmount;
    private String movieName;
    private String theaterName;
    private LocalDate showDate;
    private LocalTime showTime;
    private String bookedSeats;
}
