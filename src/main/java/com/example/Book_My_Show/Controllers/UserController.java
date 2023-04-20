package com.example.Book_My_Show.Controllers;

import com.example.Book_My_Show.EntryDTOs.UserEntryDto;
import com.example.Book_My_Show.Model.User;
import com.example.Book_My_Show.RequestDTOs.UpdateUserDetailsRequestDto;
import com.example.Book_My_Show.ResponseDTOs.TicketResponseDto;
import com.example.Book_My_Show.ResponseDTOs.UserResponseDto;
import com.example.Book_My_Show.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserEntryDto userEntryDto)
    {
      try {
         String result =  userService.addUser(userEntryDto);
         return new ResponseEntity<>(result, HttpStatus.CREATED);

      } catch (Exception e) {
          String response = "User could not be added";
          return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      }
    }

    @GetMapping("/getUserByName")
    public ResponseEntity<UserResponseDto> getUserByName(@RequestParam("name") String userName)
    {
        UserResponseDto userResponseDto = userService.getUserByName(userName);
        return new ResponseEntity<>(userResponseDto, HttpStatus.FOUND);
    }


    @PutMapping("/updateUserDetails")
    public ResponseEntity<String> updateUserDetails(@RequestBody UpdateUserDetailsRequestDto updateUserDetailsRequestDto)
    {
        String result = userService.updateUserDetails(updateUserDetailsRequestDto);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getTicketsBookedByUser")
    public ResponseEntity<List<TicketResponseDto>> getTicketsBookedByUser(@RequestParam("userId") int userId)
    {
        List<TicketResponseDto> listOfTickets = userService.getTicketsBookedByUser(userId);
        return new ResponseEntity<>(listOfTickets, HttpStatus.FOUND);
    }
}

