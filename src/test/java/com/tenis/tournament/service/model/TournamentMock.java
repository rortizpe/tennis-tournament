package com.tenis.tournament.service.model;

import com.tenis.tournament.model.entity.Match;
import com.tenis.tournament.model.entity.Player;
import com.tenis.tournament.model.request.FilterTournamentRequest;
import com.tenis.tournament.model.request.PlayerRequest;
import com.tenis.tournament.model.request.TournamentRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TournamentMock {
  public static TournamentRequest getTournamentRequest(String gender) {
    return TournamentRequest
      .builder()
      .gender(gender)
      .tournament("tournament Test")
      .players(Arrays.asList("631d1e7935f90066e27d0f4f", "631d1eb135f90066e27d0f50", "631d1f0635f90066e27d0f52", "631d1fdc35f90066e27d0f53"))
      .build();
  }

  public static TournamentRequest getTournamentRequestErrPow(String gender) {
    return TournamentRequest
      .builder()
      .gender(gender)
      .tournament("tournament Test")
      .players(Arrays.asList("1", "2", "3"))
      .build();
  }

  public static List<Player> createListPlayer() {
    return Arrays.asList(
      createPlayer("Jose", "male"),
      createPlayer("Pedro", "male"),
      createPlayer("Pepe", "male"),
      createPlayer("Juan", "male")
    );
  }

  public static List<Player> createListPlayerFemale() {
    return Arrays.asList(
      createPlayer("Jose", "female"),
      createPlayer("Pedro", "female"),
      createPlayer("Pepe", "female"),
      createPlayer("Juan", "female")
    );
  }

  public static List<Player> createListPlayerFemaleErr() {
    return Arrays.asList(
      createPlayer("Jose", "female"),
      createPlayer("Pedro", "female"),
      createPlayer("Pepe", "female")
    );
  }

  public static List<Player> createListPlayerFemaleGenderErr() {
    return Arrays.asList(
      createPlayer("Jose", "female"),
      createPlayer("Pedro", "female"),
      createPlayer("Pepe", "female"),
      createPlayer("Pepe", "male")
    );
  }

  public static Player createPlayer(String name, String gender) {
    return Player.builder()
      .id("id")
      .gender(gender)
      .name(name)
      .ability(generateRandomValue())
      .reactionTime(generateRandomValue())
      .strength(generateRandomValue())
      .displacementSpeed(generateRandomValue())
      .build();
  }

  public static Match createMatch() {
    return Match.builder()
      .id("id")
      .date(LocalDate.now())
      .gender("male")
      .tournament("tournament Test")
      .winner(createPlayer("Jose", "male"))
      .players(List.of(List.of(Collections.singletonList(createPlayer("", "")))))
      .build();
  }

  public static Match createMatch(String gender) {
    return Match.builder()
      .id("id")
      .date(LocalDate.now())
      .gender(gender)
      .tournament("tournament Test")
      .winner(createPlayer("Jose", gender))
      .players(List.of(List.of(Collections.singletonList(createPlayer("", "")))))
      .build();
  }

  private static int generateRandomValue() {
    return ThreadLocalRandom.current().nextInt(0, 11);
  }

  public static FilterTournamentRequest createFilterTournament() {
    return FilterTournamentRequest.builder()
      .id(null)
      .nameWinner("")
      .tournament("tournament Test")
      .date(LocalDateTime.now())
      .gender("male")
      .until(LocalDateTime.now())
      .build();
  }

  public static FilterTournamentRequest createFilterTournamentCaseTwo() {
    return FilterTournamentRequest.builder()
      .id("631d201535f90066e27d0f56")
      .nameWinner("")
      .tournament("tournament Test")
      .date(LocalDateTime.now())
      .gender("male")
      .until(null)
      .build();
  }

  public static FilterTournamentRequest createFilterTournamentCaseThree() {
    return FilterTournamentRequest.builder()
      .build();
  }

  public static List<Match> createListMatch() {
    return Arrays.asList(
      createMatch(),
      createMatch(),
      createMatch(),
      createMatch()
    );
  }

  public static List<Match> createListMatchTwo() {
    return Arrays.asList(
      createMatch(),
      createMatch()
    );
  }

  public static PlayerRequest createPlayerRequest(String name, String gender) {
    return PlayerRequest.builder()
      .gender(gender)
      .name(name)
      .ability(generateRandomValue())
      .reactionTime(generateRandomValue())
      .strength(generateRandomValue())
      .displacementSpeed(generateRandomValue())
      .build();
  }

  public static List<Player> createListPlayer2() {
    return Arrays.asList(
      createPlayer("Raul", "male"),
      createPlayer("Sam", "male")
    );
  }
}
