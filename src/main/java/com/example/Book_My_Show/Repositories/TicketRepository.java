package com.example.Book_My_Show.Repositories;

import com.example.Book_My_Show.Model.ShowSeat;
import com.example.Book_My_Show.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
}
