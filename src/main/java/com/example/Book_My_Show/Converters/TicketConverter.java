package com.example.Book_My_Show.Converters;

import com.example.Book_My_Show.EntryDTOs.TicketEntryDto;
import com.example.Book_My_Show.Model.Ticket;
import com.example.Book_My_Show.ResponseDTOs.TicketResponseDto;

public class TicketConverter
{
    public static Ticket convertEntryDtoToEntity(TicketEntryDto ticketEntryDto)
    {
        Ticket ticket = new Ticket();
            // right now we cant set any attribute of ticket. hence simply create object and return it.
        return ticket;
    }

    public static TicketResponseDto convertEntityToResponseDto(Ticket ticket)
    {
        TicketResponseDto ticketResponseDto = TicketResponseDto.builder()
                .id(ticket.getId())
                .ticketId(ticket.getTicketId())
                .totalAmount(ticket.getTotalAmount())
                .movieName(ticket.getMovieName())
                .theaterName(ticket.getTheaterName())
                .showDate(ticket.getShowDate())
                .showTime(ticket.getShowTime())
                .bookedSeats(ticket.getBookedSeats())
                .build();

        return ticketResponseDto;
    }
}
