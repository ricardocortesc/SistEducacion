package com.example.sistemaedu.bd.JPA;

import com.example.sistemaedu.bd.ORM.EstudianteORM;
import com.example.sistemaedu.bd.ORM.ProfesorORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfesorJPA extends JpaRepository<ProfesorORM, Long> {
    Optional<EstudianteORM> findByEmail(String email);

}
