package com.tenis.tournament.model.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TournamentRequest {
  private String tournament;
  private String gender;
  private List<String> players;

}
