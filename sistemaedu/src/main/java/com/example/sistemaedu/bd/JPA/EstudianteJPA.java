package com.example.sistemaedu.bd.JPA;

import com.example.sistemaedu.bd.ORM.EstudianteORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteJPA extends JpaRepository<EstudianteORM, Long> {

    Optional<EstudianteORM> findByEmail(String email);

}
