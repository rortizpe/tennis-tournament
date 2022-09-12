package com.tenis.tournament.service;

import com.tenis.tournament.model.entity.Player;
import com.tenis.tournament.model.request.PlayerRequest;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerService {

  Mono<Player> savePlayer(PlayerRequest playerRequest);

  Flux<Player> getListPlayer(List<String> playersId);

  Flux<String> getPlayerIds(String gender, int take);
}
