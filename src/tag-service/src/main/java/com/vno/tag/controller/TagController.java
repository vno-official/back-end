package com.vno.tag.controller;

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
public class TagController {

    private final Map<Long, Tag> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    @GetMapping("/api/tags/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @GetMapping("/api/tags")
    public List<Tag> list() {
        return new ArrayList<>(store.values());
    }

    @PostMapping("/api/tags")
    public ResponseEntity<Tag> create(@Validated @RequestBody Tag req) {
        long id = seq.getAndIncrement();
        Tag tag = new Tag(id, req.getName());
        store.put(id, tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(tag);
    }

    @GetMapping("/api/tags/{id}")
    public ResponseEntity<Tag> get(@PathVariable Long id) {
        Tag t = store.get(id);
        return t == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(t);
    }

    @PutMapping("/api/tags/{id}")
    public ResponseEntity<Tag> update(@PathVariable Long id, @Validated @RequestBody Tag req) {
        if (!store.containsKey(id)) return ResponseEntity.notFound().build();
        Tag tag = new Tag(id, req.getName());
        store.put(id, tag);
        return ResponseEntity.ok(tag);
    }

    @DeleteMapping("/api/tags/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        store.remove(id);
        return ResponseEntity.noContent().build();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Tag {
        private Long id;
        private String name;
    }
}
