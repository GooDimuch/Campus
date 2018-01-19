package ua.kpi.ecampus.rx;

import java.util.List;
import rx.Observable;
import ua.kpi.ecampus.api.service.ServiceCreator;
import ua.kpi.ecampus.api.service.VoteService;
import ua.kpi.ecampus.model.pojo.User;
import ua.kpi.ecampus.model.pojo.VoteTermCurrent;

/**
 * Created by nikita on 19.01.2018.
 */

public class VoteRxLoader {

  public Observable<List<VoteTermCurrent>> getVoteTermCurrent() {
    VoteService service = ServiceCreator.createService(VoteService.class);
    return
        service.getVoteTermCurrent("bearer " + User.getInstance().token);
  }
}
