package com.example.Book_My_Show.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "ticket")
public class Ticket
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String ticketId = UUID.randomUUID().toString();      // Randome no. generation
    private int totalAmount;       // Total price to be paid for no. of seats booked
    private String movieName;
    private String theaterName;
    private LocalDate showDate;
    private LocalTime showTime;
    private String bookedSeats;   // since SQL cant store list hence concatinating all seats in one string and saving it

                   // Unidirectional  parent-> User , child-> Ticket
    @ManyToOne
    @JoinColumn
    private User user;

                    // Unidirectional  parent-> Show , child-> Ticket
    @ManyToOne
    @JoinColumn
    private Show show;
}
