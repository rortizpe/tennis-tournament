package com.tenis.tournament.builder;

import com.tenis.tournament.model.entity.Match;
import com.tenis.tournament.model.response.ResultResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.tenis.tournament.builder.PlayerBuilder.builderPlayerResponse;
import static com.tenis.tournament.builder.PlayerBuilder.getPlayerList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchBuilder {
  public static ResultResponse builderResultResponseFromMatch(Match match) {
    return ResultResponse.builder()
      .date(match.getDate())
      .gender(match.getGender())
      .tournament(match.getTournament())
      .winner(builderPlayerResponse(match.getWinner()))
      .result(getPlayerList(match.getPlayers()))
      .build();
  }

}
