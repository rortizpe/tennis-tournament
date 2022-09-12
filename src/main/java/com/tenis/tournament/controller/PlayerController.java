package com.tenis.tournament.controller;

import com.tenis.tournament.model.entity.Player;
import com.tenis.tournament.model.request.PlayerRequest;
import com.tenis.tournament.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/player")
public class PlayerController {
  private final PlayerService playerService;

  @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Player> getResultTournament(@RequestBody() PlayerRequest playerRequest) {
    return playerService.savePlayer(playerRequest);
  }
}
