package com.example.Book_My_Show.Repositories;

import com.example.Book_My_Show.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>
{
    User findByName(String userName);
}
