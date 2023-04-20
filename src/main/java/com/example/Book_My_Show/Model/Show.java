package com.example.Book_My_Show.Model;

import com.example.Book_My_Show.Enum.ShowType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "shows")
public class Show
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
                                    // LocalDate-> gives only date,  LocalTime-> gives only time
    private LocalDate showDate;      // LolcalDate & Localtime are customizable. That means we can customize
    private LocalTime showTime;       // them in whichever format we want

    @Enumerated(value = EnumType.STRING)
    private ShowType showType;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

                      // Unidirectional  parent-> Movie , child-> Show
    @ManyToOne
    @JoinColumn
    private Movie movie;

                      // Unidirectional  parent-> Theater , child-> Show
    @ManyToOne
    @JoinColumn
    private Theater theater;


                    // Bidirectional parent-> Show , child-> Ticket
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private List<Ticket> listOfBookedTickets = new ArrayList<>();

                    // Bidirectional parent-> Show , child-> ShowSeat
    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private List<ShowSeat> listOfShowSeats = new ArrayList<>();

}
