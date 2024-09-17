package com.example.sistemaedu.bd.JPA;

import com.example.sistemaedu.bd.ORM.EstudianteORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteJPA extends JpaRepository<EstudianteORM, Long> {

}
