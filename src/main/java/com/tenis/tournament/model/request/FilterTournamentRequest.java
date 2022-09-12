package com.tenis.tournament.model.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class FilterTournamentRequest {
  private String id;
  private LocalDateTime date;
  private LocalDateTime until;
  private String gender;
  private String tournament;
  private String nameWinner;

}
