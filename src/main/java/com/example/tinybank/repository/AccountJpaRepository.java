package com.example.tinybank.repository;

import com.example.tinybank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface AccountJpaRepository extends JpaRepository<Account,Integer> {
    List<Account> findAccountsByClient_Id(Integer id);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM account WHERE id=:id",nativeQuery = true)
    void deleteAccountCustom(@Param("id") Integer id);
}
