package com.tenis.tournament.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlayerPlaceResponse {

  private String id;
  private String name;

}
