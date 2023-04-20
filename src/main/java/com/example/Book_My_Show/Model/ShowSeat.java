package com.example.Book_My_Show.Model;

import com.example.Book_My_Show.Enum.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "show_seat")
public class ShowSeat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isBooked;
    private int price;
    private String seatNo;

    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;

    private Date bookedAt;

                        // unidirectional  parent-> Show , child-> ShowSeat
    @ManyToOne
    @JoinColumn
    private Show show;
}
