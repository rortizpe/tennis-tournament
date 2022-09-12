package com.tenis.tournament.service;

import com.tenis.tournament.model.request.FilterTournamentRequest;
import com.tenis.tournament.model.request.TournamentRequest;
import com.tenis.tournament.model.response.ResultResponse;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MatchService {

  Mono<ResultResponse> getResultTournament(TournamentRequest tournamentRequest);

  Flux<ResultResponse> filterTournament(FilterTournamentRequest tournamentRequest, Pageable pageable);
}
