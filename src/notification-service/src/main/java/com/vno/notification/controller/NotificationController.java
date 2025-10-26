package com.vno.notification.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Instant;

@RestController
@RequestMapping("/")
public class NotificationController {

    @GetMapping("/api/notifications/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    // Simple SSE endpoint at /ws/notifications
    @GetMapping(value = "/ws/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        SseEmitter emitter = new SseEmitter(0L); // no timeout
        try {
            emitter.send(SseEmitter.event().name("init").data("connected:" + Instant.now()));
        } catch (IOException ignored) { }
        return emitter;
    }
}
