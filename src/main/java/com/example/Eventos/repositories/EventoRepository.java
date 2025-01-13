package com.example.Eventos.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.Eventos.model.entity.Evento;

public interface EventoRepository extends  CrudRepository<Evento, Long> {
}
