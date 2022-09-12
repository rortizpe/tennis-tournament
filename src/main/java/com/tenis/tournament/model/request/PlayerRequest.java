package com.tenis.tournament.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PlayerRequest {

  private String name;
  private int ability;
  private int strength;
  private int displacementSpeed;
  private String gender;
  private int reactionTime;
}
