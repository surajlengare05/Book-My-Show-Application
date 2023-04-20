package com.example.Book_My_Show.Services;

import com.example.Book_My_Show.Converters.TicketConverter;
import com.example.Book_My_Show.EntryDTOs.TicketEntryDto;
import com.example.Book_My_Show.Model.Show;
import com.example.Book_My_Show.Model.ShowSeat;
import com.example.Book_My_Show.Model.Ticket;
import com.example.Book_My_Show.Model.User;
import com.example.Book_My_Show.Repositories.ShowRepository;
import com.example.Book_My_Show.Repositories.ShowSeatRepository;
import com.example.Book_My_Show.Repositories.TicketRepository;
import com.example.Book_My_Show.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class TicketService
{
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ShowRepository showRepository;
    @Autowired
    ShowSeatRepository showSeatRepository;


    @Autowired
    JavaMailSender javaMailSender;


    public String bookTicket(TicketEntryDto ticketEntryDto) throws Exception
    {
        Ticket ticket = TicketConverter.convertEntryDtoToEntity(ticketEntryDto);
        boolean isValidRequest = checkValidityOfRequestedSeats(ticketEntryDto);
        if (isValidRequest==false) {
            throw new Exception("Requested seats are not available");
        }

        int showId = ticketEntryDto.getShowId();
        int userId = ticketEntryDto.getUserId();
        Show show = showRepository.findById(showId).get();
        User user = userRepository.findById(userId).get();

        // calculate total amount
        List<String> requestedSeats = ticketEntryDto.getRequestedSeats();
        List<ShowSeat> showSeatList = show.getListOfShowSeats();

        int total = 0;
        for (ShowSeat showSeat:showSeatList)
        {
            String seatNo = showSeat.getSeatNo();
            if (requestedSeats.contains(seatNo))
            {
              total = total + showSeat.getPrice();
              showSeat.setBooked(true);               // updating attributes of showSeat too here
              showSeat.setBookedAt(new Date());
            }
        }

        ticket.setTotalAmount(total);

        // setting other remaining attributes
        ticket.setMovieName(show.getMovie().getName());
        ticket.setTheaterName(show.getTheater().getName());
        ticket.setShowDate(show.getShowDate());
        ticket.setShowTime(show.getShowTime());

        String str = getAllottedSeatsAsString(requestedSeats);   // to convert list of string to single string
        ticket.setBookedSeats(str);

        ticket.setShow(show);
        ticket.setUser(user);

        // updating attributes of parents also

        show.getListOfBookedTickets().add(ticket);
        user.getBookedTickets().add(ticket);

        // Before saving parents(movie, theater) to their repositories, save child(show) & get it into 1 variable
        // as following-
        //otherwise (if this is not done then), show will get saved twice after saving movie & theater because of cascading
        ticket = ticketRepository.save(ticket);
        showRepository.save(show);
        userRepository.save(user);

        String body = "Movie Ticket Booked \n\n\n Movie name:  "+ticket.getMovieName()+"\n Booking ID:  "+ticket.getTicketId()+"\n Show Date & Time:  "+ticket.getShowDate()+" , "+ticket.getShowTime()+"\n Cinema Hall:  "
                +ticket.getTheaterName()+"\n Location:  "+ticket.getShow().getTheater().getLocation()+" \n Seats:  "+ticket.getBookedSeats();


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("surajlengre.tp@gmail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject("Movie Ticket confirmed");

        javaMailSender.send(mimeMessage);



        return ("Ticket booked successfully");
    }

    public boolean checkValidityOfRequestedSeats(TicketEntryDto ticketEntryDto)
    {
      /* List<String> requestedSeats = ticketEntryDto.getRequestedSeats();

        int showId = ticketEntryDto.getShowId();
        Show show = showRepository.findById(showId).get();
        List<ShowSeat> showSeatList = show.getListOfShowSeats();

        for (ShowSeat showSeat:showSeatList)
        {
            String seatNo = showSeat.getSeatNo();
            if (requestedSeats.contains(seatNo))
            {
                if (showSeat.isBooked()==true)
                {
                    return false;
                }
            }
        }
        return true;  */

        // using custom query
        // getting only requested showSeat and checking its booking status
        List<String> requestedSeats = ticketEntryDto.getRequestedSeats();
        int showId = ticketEntryDto.getShowId();
        for (String seatNo: requestedSeats)
        {
            ShowSeat showSeat = showSeatRepository.findShowSeatBySeatNo(showId, seatNo);
            if (showSeat.isBooked()==true)
            {
                return false;
            }
        }
        return true;

    }

    public String getAllottedSeatsAsString(List<String> requestedSeats)
    {
        String result = "";
        int size = requestedSeats.size();
        for (int i=0; i<size; i++)
        {
            result = result + requestedSeats.get(i);
            if (i < size-1)
            {
                result = result + ", ";
            }
        }
        return result;
    }

    public String cancelTicket(int id) throws Exception
    {
        Ticket ticket = ticketRepository.findById(id).get();
        User user = ticket.getUser();
        Show show = ticket.getShow();

        user.getBookedTickets().remove(ticket);
        show.getListOfBookedTickets().remove(ticket);

        int showId = show.getId();
        String bookedSeats = ticket.getBookedSeats();
        StringTokenizer st = new StringTokenizer(bookedSeats, ", ");   // split at-> , or space
        while (st.hasMoreTokens())
        {
            String seatNo = st.nextToken();
            ShowSeat showSeat = showSeatRepository.findShowSeatBySeatNo(showId, seatNo);

            showSeat.setBooked(false);
            showSeat.setBookedAt(null);
            showSeatRepository.save(showSeat);
        }

                                // mailing
        String body = "Movie Ticket has bee cancelled \n\n\n Movie name:  "+ticket.getMovieName()+"\n Booking ID:  "+ticket.getTicketId()+"\n Show Date & Time:  "+ticket.getShowDate()+" , "+ticket.getShowTime()+"\n Cinema Hall:  "
                +ticket.getTheaterName()+"\n Location:  "+ticket.getShow().getTheater().getLocation()+" \n Seats:  "+ticket.getBookedSeats();
        body = body + "\n\n\nYou will receive refund of the booking amount into registered bank account within 24 hrs.";

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("surajlengre.tp@gmail.com");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject("Movie Ticket cancelled");

        javaMailSender.send(mimeMessage);


        ticketRepository.delete(ticket);

        userRepository.save(user);
        showRepository.save(show);

        return ("Ticket cancellation done");
    }
}
