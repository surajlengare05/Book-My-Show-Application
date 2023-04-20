package com.example.Book_My_Show.ResponseDTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponseDto
{
    private int id;
    private String name;
    private int age;
    private String email;
    private String mobNo;
    private String address;
}
