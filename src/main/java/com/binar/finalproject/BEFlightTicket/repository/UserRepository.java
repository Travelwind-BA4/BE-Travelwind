package com.binar.finalproject.BEFlightTicket.repository;

import com.binar.finalproject.BEFlightTicket.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    @Query("SELECT u FROM Users u WHERE LOWER(u.fullName) LIKE LOWER(:fullName)")
    Users findByName(@Param("fullName") String fullName);

    @Query("SELECT u FROM Users u WHERE LOWER(u.email) = LOWER(:email)")
    Users findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Users u WHERE (u.telephone) = (:telephone)")
    Users findPhoneNumber(@Param("telephone") String telephone);
}