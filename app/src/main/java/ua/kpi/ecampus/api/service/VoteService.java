package ua.kpi.ecampus.api.service;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;
import ua.kpi.ecampus.model.pojo.VoteTermCurrent;

/**
 * Created by nikita on 19.01.2018.
 */

public interface VoteService {
  @GET("/vote/term/current") Observable<List<VoteTermCurrent>> getVoteTermCurrent(
      @Header("Authorization") String authorization);
}
