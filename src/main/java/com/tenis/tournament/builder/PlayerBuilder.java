package com.tenis.tournament.builder;

import com.tenis.tournament.model.entity.Player;
import com.tenis.tournament.model.request.PlayerRequest;
import com.tenis.tournament.model.response.PlayerPlaceResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerBuilder {

  public static Player builderPlayerRequestToEntity(PlayerRequest playerRequest) {
    return Player.builder()
      .name(playerRequest.getName())
      .ability(playerRequest.getAbility())
      .strength(playerRequest.getStrength())
      .displacementSpeed(playerRequest.getDisplacementSpeed())
      .gender(playerRequest.getGender())
      .reactionTime(playerRequest.getReactionTime())
      .build();

  }

  public static List<List<List<PlayerPlaceResponse>>> getPlayerList(List<List<List<Player>>> lists) {
    return lists.stream()
      .map(l1 -> l1.stream()
        .map(l2 -> l2.stream()
          .map(PlayerBuilder::builderPlayerResponse)
          .collect(Collectors.toList()))
        .collect(Collectors.toList()))
      .collect(Collectors.toList());
  }

  public static PlayerPlaceResponse builderPlayerResponse(Player player) {
    return PlayerPlaceResponse.builder()
      .id(player.getId())
      .name(player.getName())
      .build();
  }
}
