package ua.kpi.ecampus.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import butterknife.Bind;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import ua.kpi.ecampus.Config;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.di.UIModule;
import ua.kpi.ecampus.model.pojo.VoteTeacher;
import ua.kpi.ecampus.ui.adapter.VotingAdapter;
import ua.kpi.ecampus.ui.adapter.VotingFragmentPagerAdapter;
import ua.kpi.ecampus.ui.presenter.VotingStudentPresenter;

/**
 * Created by Administrator on 31.05.2016.
 */
public class VotingStudentActivity extends BaseActivity implements VotingStudentPresenter.IView {

  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.viewpager) ViewPager mViewpager;
  @Bind(R.id.sliding_tabs) TabLayout mSlidingTabs;

  @Inject VotingStudentPresenter mPresenter;

  private VotingAdapter mAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_voting_student);
    bindViews();
    mPresenter.setView(this);
    mPresenter.initializeViewComponent();

    VotingFragmentPagerAdapter votingFragmentPagerAdapter =
        new VotingFragmentPagerAdapter(this, getSupportFragmentManager());
    mViewpager.setAdapter(votingFragmentPagerAdapter);
    mSlidingTabs.setupWithViewPager(mViewpager);
  }

  @Override protected List<Object> getModules() {
    List<Object> modules = new ArrayList<>();
    modules.add(new UIModule());
    return modules;
  }

  @Override public void setViewComponent() {
    setToolbar();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK && requestCode == Config.REQUEST_CODE) {
      VoteTeacher teacher = data.getParcelableExtra(Config.KEY_TEACHER);
      mAdapter.updateItem(teacher);
    }
  }

  private void setToolbar() {
    setSupportActionBar(mToolbar);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
    getSupportActionBar().setTitle(R.string.activity_name_voting);
  }
}
