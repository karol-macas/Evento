package com.example.Eventos.services;

import java.util.List;
import java.util.Optional;

import com.example.Eventos.model.entity.Evento;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.Eventos.repositories.EventoRepository;
import org.springframework.stereotype.Service;

@Service
public class EventoServiceImplement implements EventoService {
   @Autowired
    private EventoRepository eventoRepository;

    @Override
    public List<Evento> findAll() {
        return (List<Evento>) eventoRepository.findAll();
    }

    @Override
    public Optional<Evento> findById(Long id) {
        return eventoRepository.findById(id);
    }

    @Override
    public Evento save(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    public void deleteById(Long id) {
        eventoRepository.deleteById(id);
    }


}

