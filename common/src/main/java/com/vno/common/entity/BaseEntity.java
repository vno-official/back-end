package com.vno.common.entity;

import java.time.LocalDateTime;

public abstract class BaseEntity {
  private LocalDateTime createdAt;
  private LocalDateTime lastModifiedAt;
  private String createdBy;
  private String lastModifiedBy;

  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.lastModifiedAt = LocalDateTime.now();
  }

  public void preUpdate() {
    this.lastModifiedAt = LocalDateTime.now();
  }
}