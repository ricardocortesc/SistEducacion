package com.example.sistemaedu.bd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorJPA extends JpaRepository<ProfesorORM, Long> {

}
