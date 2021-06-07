package com.example.tinybank.repository;

import com.example.tinybank.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface ClientJpaRepository extends JpaRepository<Client,Integer> {
    Client findClientByName(String name);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM client WHERE id=:id",nativeQuery = true)
    void deleteClientCascade(@Param("id") Integer id);
    Client findClientByUsername(String username);
}
