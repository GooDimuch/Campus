package ua.kpi.ecampus.ui.presenter;

import javax.inject.Inject;
import ua.kpi.ecampus.model.dao.IDataAccessObject;
import ua.kpi.ecampus.model.dao.VotingDao;
import ua.kpi.ecampus.model.pojo.VoteSet;
import ua.kpi.ecampus.ui.Navigator;

/**
 * Created by Administrator on 01.06.2016.
 */
public class VotingStudentPresenter extends BasePresenter {

    private IView mView;
    private IDataAccessObject<VoteSet> mDataAccess;
    private Navigator mNavigator;

    @Inject public VotingStudentPresenter(Navigator navigator) {
        mDataAccess = new VotingDao();
        mNavigator = navigator;
    }

    public void setView(IView view) {
        mView = view;
    }

    @Override
    public void initializeViewComponent() {
        mView.setViewComponent();
    }

    public interface IView {
        void setViewComponent();
    }
}
