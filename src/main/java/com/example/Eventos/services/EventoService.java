package com.example.Eventos.services;

import com.example.Eventos.model.entity.Evento;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EventoService {
    List <Evento> findAll();
    Optional<Evento> findById(Long id);
    Evento save(Evento evento);
    void deleteById(Long id);
}
