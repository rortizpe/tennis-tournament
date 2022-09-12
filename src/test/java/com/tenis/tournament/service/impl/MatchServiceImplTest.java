package com.tenis.tournament.service.impl;

import com.tenis.tournament.model.entity.Match;
import com.tenis.tournament.model.entity.Player;
import com.tenis.tournament.model.response.ResultResponse;
import com.tenis.tournament.repository.MatchRepository;
import com.tenis.tournament.service.PlayerService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.tenis.tournament.service.model.TournamentMock.createFilterTournament;
import static com.tenis.tournament.service.model.TournamentMock.createFilterTournamentCaseThree;
import static com.tenis.tournament.service.model.TournamentMock.createFilterTournamentCaseTwo;
import static com.tenis.tournament.service.model.TournamentMock.createListMatch;
import static com.tenis.tournament.service.model.TournamentMock.createListMatchTwo;
import static com.tenis.tournament.service.model.TournamentMock.createListPlayer;
import static com.tenis.tournament.service.model.TournamentMock.createListPlayerFemale;
import static com.tenis.tournament.service.model.TournamentMock.createListPlayerFemaleErr;
import static com.tenis.tournament.service.model.TournamentMock.createListPlayerFemaleGenderErr;
import static com.tenis.tournament.service.model.TournamentMock.createMatch;
import static com.tenis.tournament.service.model.TournamentMock.getTournamentRequest;
import static com.tenis.tournament.service.model.TournamentMock.getTournamentRequestErrPow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {


  @InjectMocks
  private MatchServiceImpl matchService;

  @Mock
  private PlayerService playerService;

  @Mock
  private MatchRepository matchRepository;

  @Mock
  private ReactiveMongoTemplate reactiveMongoTemplate;

  @Test
  void getResultTournament() {
    var tournamentRequest = getTournamentRequest("male");

    List<Player> playerList = createListPlayer();

    when(playerService.getListPlayer(anyList())).thenReturn(Flux.fromIterable(playerList));
    when(matchRepository.save(any(Match.class))).thenReturn(Mono.just(createMatch("male")));

    Mono<ResultResponse> resultResponseMono = matchService.getResultTournament(tournamentRequest);
    StepVerifier.create(resultResponseMono)
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("male", resultResponse.getGender());
      })
      .verifyComplete();
  }

  @Test
  void getResultTournamentFemale() {
    var tournamentRequest = getTournamentRequest("female");

    List<Player> playerList = createListPlayerFemale();

    when(playerService.getListPlayer(anyList())).thenReturn(Flux.fromIterable(playerList));
    when(matchRepository.save(any(Match.class))).thenReturn(Mono.just(createMatch("female")));

    Mono<ResultResponse> resultResponseMono = matchService.getResultTournament(tournamentRequest);
    StepVerifier.create(resultResponseMono)
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("female", resultResponse.getGender());
      })
      .verifyComplete();
  }

  @Test
  void getResultTournamentErrorPow() {
    var tournamentRequest = getTournamentRequestErrPow("female");

    List<Player> playerList = createListPlayerFemaleErr();

    when(playerService.getListPlayer(anyList())).thenReturn(Flux.fromIterable(playerList));

    Mono<ResultResponse> resultResponseMono = matchService.getResultTournament(tournamentRequest);
    StepVerifier.create(resultResponseMono)
      .expectError()
      .verify();
  }

  @Test
  void getResultTournamentErrorGender() {
    var tournamentRequest = getTournamentRequestErrPow("female");

    List<Player> playerList = createListPlayerFemaleGenderErr();

    when(playerService.getListPlayer(anyList())).thenReturn(Flux.fromIterable(playerList));

    Mono<ResultResponse> resultResponseMono = matchService.getResultTournament(tournamentRequest);
    StepVerifier.create(resultResponseMono)
      .expectError()
      .verify();
  }

  @Test
  void getResultTournamentEmptyList() {
    var tournamentRequest = getTournamentRequestErrPow("female");

    List<Player> playerList = Collections.emptyList();

    when(playerService.getListPlayer(anyList())).thenReturn(Flux.fromIterable(playerList));

    Mono<ResultResponse> resultResponseMono = matchService.getResultTournament(tournamentRequest);
    StepVerifier.create(resultResponseMono)
      .expectError()
      .verify();
  }

  @Test
  void filterTournament() {
    var tournamentRequest = createFilterTournament();
    var page = PageRequest.of(0, 10);
    when(reactiveMongoTemplate.find(any(Query.class), any())).thenReturn(Flux.fromIterable(createListMatch()));

    Flux<ResultResponse> resultResponseMono = matchService.filterTournament(tournamentRequest, page);

    StepVerifier.create(resultResponseMono)
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("male", resultResponse.getGender());
      })
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("male", resultResponse.getGender());
      })
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("male", resultResponse.getGender());
      })
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("male", resultResponse.getGender());
      })
      .expectComplete()
      .verify();

  }

  @Test
  void filterTournamentIf() {
    var tournamentRequest = createFilterTournamentCaseTwo();
    var page = PageRequest.of(0, 10);
    when(reactiveMongoTemplate.find(any(Query.class), any())).thenReturn(Flux.fromIterable(createListMatchTwo()));

    Flux<ResultResponse> resultResponseMono = matchService.filterTournament(tournamentRequest, page);

    StepVerifier.create(resultResponseMono)
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("male", resultResponse.getGender());
      })
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("male", resultResponse.getGender());
      })
      .expectComplete()
      .verify();

  }

  @Test
  void filterTournamentIf2() {
    var tournamentRequest = createFilterTournamentCaseThree();
    var page = PageRequest.of(0, 10);
    when(reactiveMongoTemplate.find(any(Query.class), any())).thenReturn(Flux.fromIterable(createListMatchTwo()));

    Flux<ResultResponse> resultResponseMono = matchService.filterTournament(tournamentRequest, page);

    StepVerifier.create(resultResponseMono)
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("male", resultResponse.getGender());
      })
      .assertNext(resultResponse -> {
        assertEquals("tournament Test", resultResponse.getTournament());
        assertEquals("male", resultResponse.getGender());
      })
      .expectComplete()
      .verify();

  }


  @Test
  void generateNumber() {
  }
}