package com.tenis.tournament.model.response;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResultResponse {

  private String tournament;
  private LocalDate date;
  private String gender;
  private List<List<List<PlayerPlaceResponse>>> result;
  private PlayerPlaceResponse winner;

}
