package com.tenis.tournament.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document("player")
@AllArgsConstructor
public class Player {
  @Id
  private String id;
  private String name;
  private int ability;
  private int strength;
  private int displacementSpeed;
  private String gender;
  private int reactionTime;

}
