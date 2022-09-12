package com.tenis.tournament.model.response;

import com.tenis.tournament.model.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PlayerProbability {
  private int percentage;
  private Player player;
}
