package com.tenis.tournament.route;

import com.tenis.tournament.model.request.PlayerRequest;
import com.tenis.tournament.model.request.TournamentRequest;
import com.tenis.tournament.model.response.ResultResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.tenis.tournament.service.model.TournamentMock.createPlayerRequest;
import static com.tenis.tournament.service.model.TournamentMock.getTournamentRequest;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RouterTest {
  @Autowired
  private WebTestClient client;

  @Test
  void tournamentTest() {
    client.post()
      .uri("api/v2/tournament/result")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .body(Mono.just(getTournamentRequest("male")), TournamentRequest.class)
      .exchange()
      .expectStatus().isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON)
      .expectBody(ResultResponse.class);
  }

  @Test
  void tournamentTestFilter() {
    client.get()
      .uri(uriBuilder ->
        uriBuilder
          .path("api/v2/tournament")
          .queryParam("id", "631e1ba870e4822e404ce8c8")
          .queryParam("date", "10/09/2022")
          .build())
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(ResultResponse.class);
  }

  @Test
  void tournamentTestFilter2() {
    client.post()
      .uri("api/v2/player/save")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .body(Mono.just(createPlayerRequest("Raul", "male")), PlayerRequest.class)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON)
      .expectBody(ResultResponse.class);
  }

  @Test
  void tournamentTestController() {
    client.post()
      .uri("api/v1/tournament/result")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .body(Mono.just(getTournamentRequest("male")), TournamentRequest.class)
      .exchange()
      .expectStatus().isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON)
      .expectBody(ResultResponse.class);
  }

  @Test
  void tournamentTestFilterController() {
    client.get()
      .uri(uriBuilder ->
        uriBuilder
          .path("api/v1/tournament")
          .queryParam("id", "631e1ba870e4822e404ce8c8")
          .queryParam("date", "10/09/2022")
          .build())
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(ResultResponse.class);
  }

  @Test
  void tournamentTestFilterController2() {
    client.post()
      .uri("api/v1/player/save")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .body(Mono.just(createPlayerRequest("Raul", "male")), PlayerRequest.class)
      .exchange()
      .expectStatus().isCreated()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON)
      .expectBody(ResultResponse.class);
  }

  @Test
  void tournamentPlayerIds() {
    client.get()
      .uri("/api/v2/player/gender/{gender}/take/{take}", "male", "8")
      .accept(MediaType.APPLICATION_JSON)
      .exchange()
      .expectStatus().isOk()
      .expectHeader()
      .contentType(MediaType.APPLICATION_JSON)
      .expectBodyList(String.class);
  }
}
