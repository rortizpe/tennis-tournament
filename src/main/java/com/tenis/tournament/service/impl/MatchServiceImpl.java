package com.tenis.tournament.service.impl;

import com.tenis.tournament.builder.MatchBuilder;
import com.tenis.tournament.exception.TournamentException;
import com.tenis.tournament.model.entity.Match;
import com.tenis.tournament.model.entity.Player;
import com.tenis.tournament.model.request.FilterTournamentRequest;
import com.tenis.tournament.model.request.TournamentRequest;
import com.tenis.tournament.model.response.PlayerProbability;
import com.tenis.tournament.model.response.ResultResponse;
import com.tenis.tournament.repository.MatchRepository;
import com.tenis.tournament.service.MatchService;
import com.tenis.tournament.service.PlayerService;
import com.tenis.tournament.util.FilterUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tenis.tournament.enums.GenderEnum.MALE;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {

  private final PlayerService playerService;
  private final MatchRepository matchRepository;
  private final ReactiveMongoTemplate reactiveMongoTemplate;

  @Override
  public Mono<ResultResponse> getResultTournament(TournamentRequest tournamentRequest) {

    return playerService.getListPlayer(tournamentRequest.getPlayers())
      .collectList()
      .doOnNext(players -> validTournament(players, tournamentRequest.getGender()))
      .flatMap(players -> initTournament(players, tournamentRequest))
      .map(MatchBuilder::builderResultResponseFromMatch);

  }

  @Override
  public Flux<ResultResponse> filterTournament(FilterTournamentRequest filterTournamentRequest, Pageable page) {
    Query query = FilterUtils.filterAndSearchTournament(filterTournamentRequest, page);
    return reactiveMongoTemplate.find(query, Match.class)
      .map(MatchBuilder::builderResultResponseFromMatch);
  }

  private Mono<Match> initTournament(List<Player> playerList, TournamentRequest tournamentRequest) {
    Match match = Match.builder()
      .date(LocalDate.now())
      .gender(tournamentRequest.getGender())
      .tournament(tournamentRequest.getTournament())
      .players(new ArrayList<>(new ArrayList<>()))
      .build();


    Collections.shuffle(playerList);

    var iterate = Math.log10(playerList.size()) / Math.log10(2);

    for (var i = 0; i < iterate; i++) {
      System.out.println("**********ITERACION " + i + "*********");

      System.out.println("************ CREANDO GRUPOS***********");
      var a = createGroups(playerList);

      a.forEach(players -> players.forEach(b -> System.out.println(b.getName())));

      match.getPlayers().add(a);

      playerList = getNextMatch(a);
      System.out.println("**********GANADOR POR GRUPO**********");
      playerList.forEach(player -> System.out.println(player.getName()));

      if (i == iterate - 1) {
        System.out.println("************GANADOR DEL TORNEO*************" + playerList.get(0).getName());
        match.setWinner(playerList.get(0));
      }
    }
    System.out.println("WINNER: " + match.getWinner().getName());

    return matchRepository.save(match);
  }

  private static List<List<Player>> createGroups(List<Player> players) {
    return IntStream.range(0, players.size())
      .boxed()
      .collect(Collectors.groupingBy(i -> i % players.size() / 2))
      .values()
      .stream()
      .map(il -> il.stream()
        .map(players::get)
        .collect(Collectors.toList()))
      .collect(Collectors.toList());
  }

  private static List<Player> getNextMatch(List<List<Player>> playerList) {
    return playerList.stream()
      .map(MatchServiceImpl::getMatchWinner)
      .collect(Collectors.toList());
  }

  private static Player getMatchWinner(List<Player> players) {
    var listPlayers = players.stream()
      .map(MatchServiceImpl::generatePlayerProbability)
      .collect(Collectors.toList());
    var playersProbability = new ArrayList<PlayerProbability>();

    listPlayers.forEach(playerProbability -> {
      for (int i = 0; i < playerProbability.getPercentage(); i++) {
        playersProbability.add(playerProbability);
      }
    });

    Collections.shuffle(playersProbability);
    return playersProbability.stream()
      .findAny()
      .orElse(PlayerProbability.builder().build())
      .getPlayer();
  }

  private static PlayerProbability generatePlayerProbability(Player player) {
    List<Integer> calculateList;
    if (player.getGender().equals(MALE.getName())) {
      calculateList = Arrays
        .asList(player.getAbility(), player.getStrength(), player.getDisplacementSpeed());
    } else {
      calculateList = Arrays
        .asList(player.getAbility(), player.getReactionTime());
    }
    var avg = (int) calculateAverage(calculateList);
    var lucky = generateNumber(20, 50);
    var probabilityToWin = (int) calculateAverage(Arrays.asList(avg, lucky));

    return PlayerProbability.builder()
      .percentage(probabilityToWin)
      .player(player)
      .build();
  }

  private static double calculateAverage(List<Integer> stats) {
    return stats.stream()
      .mapToInt(Integer::intValue)
      .summaryStatistics()
      .getAverage();
  }

  public static int generateNumber(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }

  private static void validTournament(List<Player> players, String gender) {
    try {
      validPow(players);
      validGenderPlayer(players, gender);
    } catch (TournamentException e) {
      throw new RuntimeException(e);
    }
  }

  private static void validPow(List<Player> players) throws TournamentException {
    var countPlayer = players.size();
    var isPowTwo = countPlayer != 0 && (countPlayer & -countPlayer) == countPlayer;
    if (!isPowTwo) {
      throw new TournamentException("Los Participantes deben ser potencia de 2");
    }
  }

  private static void validGenderPlayer(List<Player> players, String gender) throws TournamentException {
    var playerSameGender = (int) players.stream()
      .filter(player -> player.getGender().equals(gender))
      .count();
    if (playerSameGender != players.size()) {
      throw new TournamentException("Existen Participantes de diferentes géneros para iniciar este torneo en este"
        + " torneo");


    }
  }

}
