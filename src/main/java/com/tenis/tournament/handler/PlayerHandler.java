package com.tenis.tournament.handler;

import com.tenis.tournament.model.request.FilterTournamentRequest;
import com.tenis.tournament.model.request.PlayerRequest;
import com.tenis.tournament.service.PlayerService;
import java.net.URI;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@AllArgsConstructor
public class PlayerHandler {

  private final PlayerService playerService;

  public Mono<ServerResponse> savePlayer(ServerRequest serverRequest) {

    var playerRequest = serverRequest.bodyToMono(PlayerRequest.class);
    return playerRequest.flatMap(playerService::savePlayer)
      .flatMap(player -> ServerResponse.created(URI.create("api/v2/player/save"))
        .contentType(MediaType.APPLICATION_JSON)
        .body(fromValue(player)))
      .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> getIdsPlayers(ServerRequest serverRequest) {

    var gender = serverRequest.pathVariable("gender");
    var take = Integer.parseInt(serverRequest.pathVariable("take"));
    return ServerResponse.ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(playerService.getPlayerIds(gender, take), FilterTournamentRequest.class)
      .switchIfEmpty(ServerResponse.notFound().build());
  }
}
