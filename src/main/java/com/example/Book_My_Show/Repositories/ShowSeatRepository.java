package com.example.Book_My_Show.Repositories;

import com.example.Book_My_Show.Model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Integer>
{
    @Query(value = "select * from show_seat where show_id=:showId and seat_no=:seatNo", nativeQuery = true)
    ShowSeat findShowSeatBySeatNo(int showId, String seatNo);
    // used in cancelTicket()   (TicketService)
    // used in alternate way in checkValidityOfRequestedSeats()  (TicketService)
}
