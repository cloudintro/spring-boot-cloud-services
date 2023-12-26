package com.cloudcode.springcloud.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id
  private long id;
  private String name;
  private double price;
  private LocalDateTime createOn;
  private LocalDateTime updatedOn;

}
