package com.tenis.tournament.service.impl;

import com.tenis.tournament.builder.PlayerBuilder;
import com.tenis.tournament.model.entity.Player;
import com.tenis.tournament.model.request.PlayerRequest;
import com.tenis.tournament.repository.PlayerRepository;
import com.tenis.tournament.service.PlayerService;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

  private final PlayerRepository playerRepository;

  @Override
  public Mono<Player> savePlayer(PlayerRequest playerRequest) {
    return Mono.just(playerRequest)
      .map(PlayerBuilder::builderPlayerRequestToEntity)
      .flatMap(playerRepository::save);
  }

  @Override
  public Flux<Player> getListPlayer(List<String> playersId) {
    return playerRepository.findAllById(playersId);
  }

  @Override
  public Flux<String> getPlayerIds(String gender, int take) {
    return playerRepository.findAllByGender(gender)
      .collectList()
      .doOnNext(Collections::shuffle)
      .flatMapMany(Flux::fromIterable)
      .take(take)
      .map(Player::getId);
  }


}
