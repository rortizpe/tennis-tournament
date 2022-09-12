package com.tenis.tournament.controller;

import com.tenis.tournament.model.request.TournamentRequest;
import com.tenis.tournament.model.response.ResultResponse;
import com.tenis.tournament.service.MatchService;
import com.tenis.tournament.util.DateUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.tenis.tournament.builder.RequestBuilder.tournamentRequestBuilder;
import static com.tenis.tournament.util.DateUtil.parseToDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/tournament")
public class MatchController {

  private final MatchService matchService;

  @PostMapping(value = "/result", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResultResponse> getResultTournament(@RequestBody() TournamentRequest tournamentRequest) {
    return matchService.getResultTournament(tournamentRequest);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<ResultResponse> getResultTournament(
    @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size,
    @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = DateUtil.DATE_ONLY_FORMAT) String date,
    @RequestParam(name = "dateUntil", required = false) @DateTimeFormat(pattern = DateUtil.DATE_ONLY_FORMAT)
    String dateUntil,
    @RequestParam(name = "id", required = false) String id,
    @RequestParam(name = "gender", required = false) String gender,
    @RequestParam(name = "nameTournament", required = false) String tournament,
    @RequestParam(name = "nameWinner", required = false) String nameWinner
  ) {
    var request = tournamentRequestBuilder(id, parseToDateTime(date),
      parseToDateTime(dateUntil), gender, tournament, nameWinner);
    return matchService.filterTournament(request, PageRequest.of(page, size));
  }


}
