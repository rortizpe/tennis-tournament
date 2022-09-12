package com.tenis.tournament.util;

import com.tenis.tournament.model.request.FilterTournamentRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FilterUtils {

  public static Query filterAndSearchTournament(FilterTournamentRequest filterTournamentRequest, Pageable page) {

    Query query = Query.query(prepareQuery(filterTournamentRequest));
    query.skip(page.getPageNumber() * (long) page.getPageSize());
    query.limit(page.getPageSize());
    query.with(Sort.by(Sort.Direction.DESC, "id"));
    return query;
  }

  public static Criteria prepareQuery(FilterTournamentRequest filterTournamentRequest) {

    List<Criteria> listAndCriteria = new ArrayList<>();
    Criteria criteria = new Criteria();

    validListPrepareQuery(listAndCriteria, "gender", filterTournamentRequest.getGender());
    validListPrepareQuery(listAndCriteria, "tournament", filterTournamentRequest.getTournament());
    validListPrepareQuery(listAndCriteria, "id", createObjectId(filterTournamentRequest.getId()));
    validListPrepareQuery(listAndCriteria, "winner.name", filterTournamentRequest.getNameWinner());
    validDate(listAndCriteria, "date", filterTournamentRequest.getDate(), filterTournamentRequest.getUntil());
    if (!listAndCriteria.isEmpty()) {
      criteria.andOperator(listAndCriteria.toArray(new Criteria[0]));
    }
    return criteria;
  }

  private static ObjectId createObjectId(String id) {
    if (id != null) {
      return new ObjectId(id);
    }
    return null;
  }

  private static void validListPrepareQuery(List<Criteria> criteriaList, String path, Object obj) {
    if (obj != null) {
      criteriaList.add(new Criteria().and(path).is(obj));
    }
  }

  private static void validDate(List<Criteria> criteriaList, String path, LocalDateTime since, LocalDateTime until) {

    if (since != null) {
      if (until == null) {
        until = since;
      }
      criteriaList.add(new Criteria().and(path)
        .gte(since)
        .lte(until));
    }

  }
}
