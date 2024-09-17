package com.example.sistemaedu.bd.JPA;

import com.example.sistemaedu.bd.ORM.ProfesorORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorJPA extends JpaRepository<ProfesorORM, Long> {

}
