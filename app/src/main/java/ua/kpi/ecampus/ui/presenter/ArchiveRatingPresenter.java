package ua.kpi.ecampus.ui.presenter;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;
import ua.kpi.ecampus.model.dao.IDataAccessObject;
import ua.kpi.ecampus.model.dao.VotingDao;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.VoteSet;
import ua.kpi.ecampus.model.pojo.VoteTerm;

/**
 * Created by Dimuch on 14.01.2018.
 */

public class ArchiveRatingPresenter extends BasePresenter {

  private IView mView;
  private IDataAccessObject<VoteSet> mDataAccess;

  @Inject public ArchiveRatingPresenter() {
    mDataAccess = new VotingDao();
  }

  public void loadVoting() {
    // load
    makeStubData();
    setResult();
    Timber.e("loadVoting");
  }

  private void makeStubData() {
    VoteSet vs = new VoteSet();

    List<VoteTerm> terms = new ArrayList<>();
    terms.add(new VoteTerm(1, "2015-2016", "2015-09-01", "2016-09-01"));
    //terms.add(new VoteTerm(2, "2014-2015", "2014-09-01", "2015-09-01"));
    //terms.add(new VoteTerm(3, "2016-2017", "2016-09-01", "2017-09-01"));
    //terms.add(new VoteTerm(4, "2017-2018", "2017-09-01", "2018-09-01"));

    vs.setTerms(terms);

    mDataAccess.setData(new ArrayList<VoteSet>() {{
      add(vs);
    }});
  }

  public void setResult() {
    List<VoteTerm> terms = getVoting().getTerms();

    List<Item> termNames = new ArrayList<>();
    for (VoteTerm t : terms) {
      Item i = new Item(t.getVoteId(), t.getVoteName());
      termNames.add(i);
    }
    mView.setTermsSpinner(termNames);
  }

  public VoteSet getVoting() {
    return mDataAccess.getData().iterator().next();
  }

  public void setView(IView view) {
    mView = view;
  }

  @Override public void initializeViewComponent() {
    Timber.wtf("initializeViewComponent");
    mView.setViewComponent();
  }

  public interface IView {
    void setViewComponent();

    void setTermsSpinner(List<Item> terms);
  }
}
