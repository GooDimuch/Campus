package ua.kpi.ecampus.api.service;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;
import ua.kpi.ecampus.model.pojo.VoteTermCurrent;

/**
 * Created by nikita on 19.01.2018.
 */

public interface VoteService {
  @GET("/vote/term/current") Observable<List<VoteTermCurrent>> getVoteTermCurrent(
      @Header("Authorization") String authorization);

  // TODO: 19.01.2018 сервер не возвращает никаких сущьностей , поэтому Void,нужно написать сущность
  @GET("t") Observable<Void> getVoteTermIdLecturer(
      @Path("voteTermId") String voteTermId, @Header("Authorization") String authorization);
}
