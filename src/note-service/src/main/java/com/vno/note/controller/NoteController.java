package com.vno.note.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/")
public class NoteController {

    private final Map<Long, Note> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    @GetMapping("/api/notes/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @GetMapping("/api/notes")
    public List<Note> list() {
        return new ArrayList<>(store.values());
    }

    @PostMapping("/api/notes")
    public ResponseEntity<Note> create(@Validated @RequestBody Note req) {
        long id = seq.getAndIncrement();
        Note note = new Note(id, req.getTitle(), req.getContent(), req.getTags());
        store.put(id, note);
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }

    @GetMapping("/api/notes/{id}")
    public ResponseEntity<Note> get(@PathVariable Long id) {
        Note n = store.get(id);
        return n == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(n);
    }

    @PutMapping("/api/notes/{id}")
    public ResponseEntity<Note> update(@PathVariable Long id, @Validated @RequestBody Note req) {
        if (!store.containsKey(id)) return ResponseEntity.notFound().build();
        Note note = new Note(id, req.getTitle(), req.getContent(), req.getTags());
        store.put(id, note);
        return ResponseEntity.ok(note);
    }

    @DeleteMapping("/api/notes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        store.remove(id);
        return ResponseEntity.noContent().build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Note {
        private Long id;
        private String title;
        private String content;
        private List<String> tags;
    }
}
