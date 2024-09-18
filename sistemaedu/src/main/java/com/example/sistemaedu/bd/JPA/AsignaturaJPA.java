package com.example.sistemaedu.bd.JPA;

import com.example.sistemaedu.bd.ORM.AsignaturaORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaJPA extends JpaRepository<AsignaturaORM, Long> {
}
