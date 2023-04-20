package com.example.Book_My_Show.Repositories;

import com.example.Book_My_Show.Model.Theater;
import com.example.Book_My_Show.ResponseDTOs.TheaterResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer>
{
    List<Theater> findByLocation(String location);

    @Query(value = "select count(distinct location) from theater", nativeQuery = true)
    int findCountOfUniqueLocations ();
    // used in countOfUniqueLocations() (TheaterService)
}
