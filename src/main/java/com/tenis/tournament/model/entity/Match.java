package com.tenis.tournament.model.entity;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document("match")
@AllArgsConstructor
public class Match {
  @Id
  private String id;
  private Player winner;
  private List<List<List<Player>>> players;
  private String gender;
  private String tournament;
  private LocalDate date;

}
