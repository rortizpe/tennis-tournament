package com.tenis.tournament.builder;

import com.tenis.tournament.model.request.FilterTournamentRequest;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestBuilder {

  public static FilterTournamentRequest tournamentRequestBuilder(String id, LocalDateTime date, LocalDateTime dateUntil,
    String gender, String tournament, String nameWinner) {
    return FilterTournamentRequest.builder()
      .id(id)
      .gender(gender)
      .nameWinner(nameWinner)
      .date(date)
      .tournament(tournament)
      .until(dateUntil)
      .build();
  }
}
