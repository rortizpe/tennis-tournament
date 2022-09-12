package com.tenis.tournament.service.impl;

import com.tenis.tournament.model.entity.Player;
import com.tenis.tournament.repository.PlayerRepository;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.tenis.tournament.service.model.TournamentMock.createListPlayer2;
import static com.tenis.tournament.service.model.TournamentMock.createPlayer;
import static com.tenis.tournament.service.model.TournamentMock.createPlayerRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

  @Mock
  private PlayerRepository playerRepository;
  @InjectMocks
  private PlayerServiceImpl playerService;

  @Test
  void savePlayer() {
    var player = createPlayer("Raul", "male");
    when(playerRepository.save(any(Player.class))).thenReturn(Mono.just(player));

    Mono<Player> playerMono = playerService.savePlayer(createPlayerRequest("Raul", "male"));
    StepVerifier.create(playerMono).assertNext(resultResponse -> {
        assertEquals("Raul", resultResponse.getName());
        assertEquals("male", resultResponse.getGender());
      })
      .verifyComplete();
  }

  @Test
  void getListPlayer() {
    var player = createPlayer("Raul", "male");

    when(playerRepository.findAllById(anyList()))
      .thenReturn(Flux.fromIterable(createListPlayer2()));

    Flux<Player> playerMono = playerService.getListPlayer(Arrays.asList("1", "2"));
    StepVerifier.create(playerMono)
      .assertNext(resultResponse -> {
        assertEquals("Raul", resultResponse.getName());
        assertEquals("male", resultResponse.getGender());
      })
      .assertNext(resultResponse -> {
        assertEquals("Sam", resultResponse.getName());
        assertEquals("male", resultResponse.getGender());
      })
      .verifyComplete();
  }

  @Test
  void getPlayerIds() {
    var player = createPlayer("Raul", "male");
    when(playerRepository.findAllByGender(anyString()))
      .thenReturn(Flux.fromIterable(createListPlayer2()));

    Flux<String> playerMono = playerService.getPlayerIds("male", 2);
    StepVerifier.create(playerMono)
      .assertNext(resultResponse -> {
        assertEquals("id", resultResponse);
      })
      .assertNext(resultResponse -> {
        assertEquals("id", resultResponse);
      })
      .verifyComplete();
  }
}