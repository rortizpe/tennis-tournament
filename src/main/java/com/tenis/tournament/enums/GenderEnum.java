package com.tenis.tournament.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {
  MALE("male"),
  FEMALE("female");

  private final String name;

}
