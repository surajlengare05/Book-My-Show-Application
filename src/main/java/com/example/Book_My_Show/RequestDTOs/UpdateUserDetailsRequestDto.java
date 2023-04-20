package com.example.Book_My_Show.RequestDTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserDetailsRequestDto
{
    private String currentName;
    private String newName;
    private int newAge;
    private String newEmail;
    private String newMobNo;
    private String newAddress;
}
