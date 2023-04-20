package com.example.Book_My_Show.Model;

import com.example.Book_My_Show.Enum.SeatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "theater_seat")
public class TheaterSeat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String seatNo;

    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;

                            // Unidirectional  parent-> Theater , child-> TheaterSeat
    @ManyToOne
    @JoinColumn
    private Theater theater;
}
