package com.tenis.tournament.repository;

import com.tenis.tournament.model.entity.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PlayerRepository extends ReactiveMongoRepository<Player, String> {
  Flux<Player> findAllByGender(String gender);
}
