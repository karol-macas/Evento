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

    // Crear un nuevo evento con validaciones
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Evento evento, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validarErrores(result));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.save(evento));
    }

    // Listar todos los eventos
    @GetMapping
    public List<Evento> listar() {
        return eventoService.findAll();
    }

    // Buscar un evento por ID con validaciones
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<Evento> eventoExiste = eventoService.findById(id);
        if (eventoExiste.isPresent()) {
            return ResponseEntity.ok().body(eventoExiste.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Evento no encontrado"));
    }

    // Editar un evento con validaciones
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @Valid @RequestBody Evento evento, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(validarErrores(result));
        }
        Optional<Evento> eventoExistente = eventoService.findById(id);
        if (eventoExistente.isPresent()) {
            Evento eventoActual = eventoExistente.get();
            eventoActual.setNombre(evento.getNombre());
            eventoActual.setDescripcion(evento.getDescripcion());
            eventoActual.setDuracionHoras(evento.getDuracionHoras());
            eventoActual.setFechaInicio(evento.getFechaInicio());
            return ResponseEntity.ok().body(eventoService.save(eventoActual));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Evento no encontrado"));
    }

    // Eliminar un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (eventoService.findById(id).isPresent()) {
            eventoService.deleteById(id);
            return ResponseEntity.ok().body(Map.of("message", "Evento eliminado con éxito"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Evento no encontrado"));
    }

    // Método para extraer errores de validación
    private Map<String, String> validarErrores(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), err.getDefaultMessage());
        });
        return errores;
    }
}
