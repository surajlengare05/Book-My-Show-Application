package com.example.Book_My_Show.Services;

import com.example.Book_My_Show.Converters.TicketConverter;
import com.example.Book_My_Show.Converters.UserConverter;
import com.example.Book_My_Show.EntryDTOs.UserEntryDto;
import com.example.Book_My_Show.Model.Ticket;
import com.example.Book_My_Show.Model.User;
import com.example.Book_My_Show.Repositories.UserRepository;
import com.example.Book_My_Show.RequestDTOs.UpdateUserDetailsRequestDto;
import com.example.Book_My_Show.ResponseDTOs.TicketResponseDto;
import com.example.Book_My_Show.ResponseDTOs.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService
{
    @Autowired
    UserRepository userRepository;

    public String addUser(UserEntryDto userEntryDto) throws Exception
    {
        User user = UserConverter.convertEntryDtoToEntity(userEntryDto);

        userRepository.save(user);
        return ("User added successfully");
    }

    public UserResponseDto getUserByName(String userName)
    {
        User user = userRepository.findByName(userName);

        UserResponseDto userResponseDto = UserConverter.convertEntityToResponseDto(user);
        return userResponseDto;
    }

   public String updateUserDetails(UpdateUserDetailsRequestDto updateUserDetailsRequestDto)
   {
       String currentName = updateUserDetailsRequestDto.getCurrentName();
       String newName = updateUserDetailsRequestDto.getNewName();
       int newAge = updateUserDetailsRequestDto.getNewAge();
       String newEmail = updateUserDetailsRequestDto.getNewEmail();
       String newMobNo = updateUserDetailsRequestDto.getNewMobNo();
       String newAddress = updateUserDetailsRequestDto.getNewAddress();

       User user = userRepository.findByName(currentName);

       if ( newName != null) {
           user.setName(newName);
       }
       if (newAge>0 && newAge<=200) {
         user.setAge(newAge);
       }
       if ( newEmail != null) {
           user.setEmail(newEmail);
       }
       if ( newMobNo != null) {
           user.setMobNo(newMobNo);
       }
       if ( newAddress != null) {
           user.setAddress(newAddress);
       }

       userRepository.save(user);
       return ("User details updated successfully");
   }

   public List<TicketResponseDto> getTicketsBookedByUser(int userId)
   {
       List<Ticket> ticketList = userRepository.findById(userId).get().getBookedTickets();
       List<TicketResponseDto> listOfTickets = new ArrayList<>();

       for (Ticket ticket: ticketList)
       {
           TicketResponseDto ticketResponseDto = TicketConverter.convertEntityToResponseDto(ticket);
           listOfTickets.add(ticketResponseDto);
       }
       return listOfTickets;
   }
}
