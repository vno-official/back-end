package com.vno.core.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(nullable = false, unique = true)
    public String email;

    @Column(name = "password_hash")
    public String passwordHash;

    @Column
    public String name;

    @Column(name = "avatar_url")
    public String avatarUrl;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt = Instant.now();

    // Repository methods
    public static User findByEmail(String email) {
        return find("email = ?1", email).firstResult();
    }

    public static User findOrCreate(String email, String name) {
        User user = findByEmail(email);
        if (user == null) {
            user = new User();
            user.email = email;
            user.name = name;
            user.persist();
        }
        return user;
    }
}
