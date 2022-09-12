package com.tenis.tournament.repository;

import com.tenis.tournament.model.entity.Match;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends ReactiveMongoRepository<Match, String> {
}
