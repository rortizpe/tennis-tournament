package com.tenis.tournament.config;


import com.tenis.tournament.handler.PlayerHandler;
import com.tenis.tournament.handler.TournamentHandler;
import com.tenis.tournament.model.entity.Player;
import com.tenis.tournament.model.request.TournamentRequest;
import com.tenis.tournament.model.response.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@AllArgsConstructor
public class RouterConfig {
  private final TournamentHandler tournamentHandler;

  @Bean
  @RouterOperations(
    {
      @RouterOperation(path = "api/v2/tournament",
        produces = {
          MediaType.APPLICATION_JSON_VALUE
        },
        method = RequestMethod.GET,
        beanClass = TournamentHandler.class,
        beanMethod = "getFilterTournament",

        operation = @Operation(
          operationId = "getFilterTournament",
          responses = {@ApiResponse(
            responseCode = "200",
            description = "operation successful",
            content = {
              @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = ResultResponse.class))
              )
            })
          },
          parameters = {
            @Parameter(in = ParameterIn.QUERY, name = "id"),
            @Parameter(in = ParameterIn.QUERY, name = "date"),
            @Parameter(in = ParameterIn.QUERY, name = "dateUntil"),
            @Parameter(in = ParameterIn.QUERY, name = "gender"),
            @Parameter(in = ParameterIn.QUERY, name = "nameTournament"),
            @Parameter(in = ParameterIn.QUERY, name = "nameWinner"),
            @Parameter(in = ParameterIn.QUERY, name = "page"),
            @Parameter(in = ParameterIn.QUERY, name = "size")
          }
        )
      ),
      @RouterOperation(
        path = "api/v2/tournament/result",
        produces = {
          MediaType.APPLICATION_JSON_VALUE
        },
        method = RequestMethod.POST,
        beanClass = TournamentHandler.class,
        beanMethod = "getResultTournament",
        operation = @Operation(operationId = "getResultTournament",
          method = "POST",
          responses = {
            @ApiResponse(
              responseCode = "200",
              description = "operation successful",
              content = @Content(schema = @Schema(implementation = ResultResponse.class))
            )
          },
          requestBody = @RequestBody(
            content = @Content(schema = @Schema(implementation = TournamentRequest.class))
          )
        )
      )
    }
  )
  public RouterFunction<ServerResponse> tournamentRoutes() {
    return RouterFunctions.route()
      .GET("api/v2/tournament", tournamentHandler::getFilterTournament)
      .POST("api/v2/tournament/result", tournamentHandler::getResultTournament)
      .build();
  }

  @Bean
  @RouterOperations(
    {
      @RouterOperation(
        path = "api/v2/player/save",
        produces = {
          MediaType.APPLICATION_JSON_VALUE
        },
        method = RequestMethod.POST,
        beanClass = PlayerHandler.class,
        beanMethod = "savePlayer",
        operation = @Operation(operationId = "savePlayer",
          responses = {
            @ApiResponse(
              responseCode = "201",
              description = "Created",
              content = @Content(schema = @Schema(
                implementation = Player.class
              ))
            )
          },
          requestBody = @RequestBody(
            content = @Content(schema = @Schema(implementation = String.class))
          )
        )
      ),
      @RouterOperation(
        path = "api/v2/player/{gender}/{take}",
        produces = {
          MediaType.APPLICATION_JSON_VALUE
        },
        method = RequestMethod.POST,
        beanClass = PlayerHandler.class,
        beanMethod = "getIdsPlayers",
        operation = @Operation(operationId = "getIdsPlayers",
          responses = {
            @ApiResponse(
              responseCode = "200",
              description = "operation successful",
              content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array = @ArraySchema(schema = @Schema(implementation = String.class))
              )
            )
          },
          parameters = {
            @Parameter(in = ParameterIn.PATH, name = "gender"),
            @Parameter(in = ParameterIn.PATH, name = "take")
          }
        )
      )
    }
  )
  public RouterFunction<ServerResponse> playerRoutes(PlayerHandler playerHandler) {
    return route(POST("api/v2/player/save"), playerHandler::savePlayer)
      .andRoute(GET("/api/v2/player/gender/{gender}/take/{take}"), playerHandler::getIdsPlayers);
  }

}
