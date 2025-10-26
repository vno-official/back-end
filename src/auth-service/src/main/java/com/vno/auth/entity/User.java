package com.vno.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "auth_users_legacy")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;
  private String password;
}
