package com.example.Book_My_Show.Controllers;

import com.example.Book_My_Show.EntryDTOs.TicketEntryDto;
import com.example.Book_My_Show.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class TicketController
{
    @Autowired
    TicketService ticketService;

    @PostMapping("/book")
    public ResponseEntity<String> bookTicket(@RequestBody TicketEntryDto ticketEntryDto)
    {
        try {
            String result = ticketService.bookTicket(ticketEntryDto);
            return new ResponseEntity<>(result, HttpStatus.CREATED);

        } catch (Exception e) {
            String response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/cancelTicket")
    public ResponseEntity<String> cancelTicket(@RequestParam("id") Integer id)
    {
        try{
            String result = ticketService.cancelTicket(id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            String response = "Can't cancel a ticket";
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

}
