package com.example.Book_My_Show.Converters;

import com.example.Book_My_Show.EntryDTOs.UserEntryDto;
import com.example.Book_My_Show.Model.User;
import com.example.Book_My_Show.ResponseDTOs.UserResponseDto;

public class UserConverter
{
    public static User convertEntryDtoToEntity(UserEntryDto userEntryDto)
    {
         /* Here we need to convert Dto obj to Entity obj and save it.
            Old method : create an obj of Entity and set its attributes.

       userEntity.setAge(userEntryDto.getAge())
       userEntity.setAdress(userEntryDto.getAdress())  */

        User user = User.builder()
                .name(userEntryDto.getName())
                .age(userEntryDto.getAge())
                .email(userEntryDto.getEmail())
                .mobNo(userEntryDto.getMobNo())
                .address(userEntryDto.getAddress())
                .build();

        // set all attributes in one go using Builder.
        // write @Builder @Allargconstructor annotations above a class whose object we
        // want to create using the .builder().build()

        return user;
    }

    public static UserResponseDto convertEntityToResponseDto(User user)
    {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .mobNo(user.getMobNo())
                .address(user.getAddress())
                .build();
        return userResponseDto;
    }
}
