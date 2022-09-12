package com.tenis.tournament.handler;

import com.tenis.tournament.model.request.FilterTournamentRequest;
import com.tenis.tournament.model.request.TournamentRequest;
import com.tenis.tournament.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.tenis.tournament.builder.RequestBuilder.tournamentRequestBuilder;
import static com.tenis.tournament.util.DateUtil.parseToDateTime;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@AllArgsConstructor
public class TournamentHandler {

  private MatchService matchService;

  @Bean
  public WebProperties.Resources resources() {
    return new WebProperties.Resources();
  }

  public Mono<ServerResponse> getResultTournament(ServerRequest serverRequest) {

    var idPlayerList = serverRequest.bodyToMono(TournamentRequest.class);
    return
      idPlayerList.flatMap(strings -> matchService.getResultTournament(strings))
        .flatMap(resultResponse -> ServerResponse.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(fromValue(resultResponse)))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> getFilterTournament(ServerRequest serverRequest) {

    var id = serverRequest.queryParam("id")
      .orElse(null);
    var date = serverRequest.queryParam("date")
      .orElse(null);
    var dateUntil = serverRequest.queryParam("dateUntil")
      .orElse(null);
    var gender = serverRequest.queryParam("gender")
      .orElse(null);
    var tournament = serverRequest.queryParam("nameTournament")
      .orElse(null);
    var nameWinner = serverRequest.queryParam("nameWinner")
      .orElse(null);
    var page = serverRequest.queryParam("page")
      .map(Integer::valueOf)
      .orElse(0);
    var size = serverRequest.queryParam("size")
      .map(Integer::valueOf)
      .orElse(20);
    var request = tournamentRequestBuilder(id, parseToDateTime(date),
      parseToDateTime(dateUntil), gender, tournament, nameWinner);

    return ServerResponse.ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(matchService.filterTournament(request, PageRequest.of(page, size)), FilterTournamentRequest.class)
      .switchIfEmpty(ServerResponse.notFound().build());
  }


}
