package com.vno.core.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "organizations")
public class Organization extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String slug;

    @Column(name = "plan")
    public String plan = "free";

    @Column(name = "stripe_customer_id")
    public String stripeCustomerId;

    @Column(name = "trial_ends_at")
    public Instant trialEndsAt;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt = Instant.now();

    @Column(name = "deleted_at")
    public Instant deletedAt;

    // Repository methods
    public static Organization findBySlug(String slug) {
        return find("slug = ?1 and deletedAt is null", slug).firstResult();
    }

    public static boolean slugExists(String slug) {
        return count("slug = ?1 and deletedAt is null", slug) > 0;
    }
}
