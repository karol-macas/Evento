package com.example.Eventos.controllers;


import com.example.Eventos.model.entity.Evento;
import com.example.Eventos.services.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/eventos")

public class EventoController {
    @Autowired
    private EventoService eventoService;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Evento evento, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, Object> errores = new HashMap<>();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(), err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.save(evento));
    }

    @GetMapping
    public List<Evento> listar() {
        return eventoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id, @RequestBody Evento evento) {
        Optional<Evento> eventoExiste = eventoService.findById(id);
        if (eventoExiste.isPresent()) {
            return ResponseEntity.ok().body(eventoExiste.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Evento evento) {
        Optional<Evento> eventoExistente = eventoService.findById(id);
        if (eventoExistente.isPresent()) {
            Evento eventoAtual = eventoExistente.get();
            eventoAtual.setNombre(evento.getNombre());
            eventoAtual.setDescripcion(evento.getDescripcion());
            eventoAtual.setDuracionHoras(evento.getDuracionHoras());
            return ResponseEntity.ok().body(eventoService.save(eventoAtual));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (eventoService.findById(id).isPresent()) {
            eventoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


}
