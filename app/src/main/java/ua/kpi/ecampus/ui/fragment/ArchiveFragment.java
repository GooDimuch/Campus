package ua.kpi.ecampus.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import java.util.List;
import javax.inject.Inject;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.VoteTeacher;
import ua.kpi.ecampus.ui.activity.ArchiveRatingActivity;
import ua.kpi.ecampus.ui.adapter.ItemSpinnerAdapter;
import ua.kpi.ecampus.ui.adapter.NothingSelectedAdapter;
import ua.kpi.ecampus.ui.adapter.RatingAdapter;
import ua.kpi.ecampus.ui.presenter.ArchivePresenter;
import ua.kpi.ecampus.ui.view.OnItemClickListener;
import ua.kpi.ecampus.util.ItemClickSupport;

public class ArchiveFragment extends BaseFragment implements ArchivePresenter.IView {

  @Bind(R.id.recyclerview_archive_teachers) RecyclerView mRecyclerView;
  @Bind(R.id.spinner_archive_terms) Spinner mSpinnerTerms;
  @Bind(R.id.tv_title_archive_teachers) TextView mTitleTeachers;

  @Inject ArchivePresenter mPresenter;
  //private ArchivePresenter mPresenter;

  private RatingAdapter mAdapter;
  private int iPeriodSpinnerPosition;
  private String sPeriodSpinner;

  public ArchiveFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mPresenter.setView(this);

    mPresenter.loadVoting();
  }

  //@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
  //    Bundle savedInstanceState) {
  //  View view = inflater.inflate(R.layout.fragment_current, container, false);
  //  ButterKnife.bind(this, view);
  //  //mPresenter = new ArchivePresenter();
  //  mPresenter.setView(this);
  //
  //  mPresenter.loadVoting();
  //  return view;
  //}

  @Override protected int getFragmentLayout() {
    return R.layout.fragment_archive;
  }

  @Override public void setTermsSpinner(List<Item> list) {
    ArrayAdapter<Item> adapter =
        new ItemSpinnerAdapter(getContext(), R.layout.spinner_item, R.layout.spinner_dropdown_item,
            list);
    mSpinnerTerms.setAdapter(
        new NothingSelectedAdapter(adapter, R.layout.spinner_nothing_selected_terms, getContext()));
    mSpinnerTerms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) parent.getItemAtPosition(position);
        if (item != null) {
          if (mAdapter == null) {
            mTitleTeachers.setVisibility(View.VISIBLE);
            setRecyclerView();
            mPresenter.setSpecificAdapter();
          }
          mAdapter.filterByTerm(item.getId());
          iPeriodSpinnerPosition = position;
        }
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {
        // N/A
      }
    });
  }

  @Override public void setVoteInProgressAdapter(List<VoteTeacher> teachers) {
    setVotingAdapter(teachers);
  }

  @Override public void setVoteEndedAdapter(List<VoteTeacher> teachers) {

  }

  private void setRecyclerView() {
    mRecyclerView.setVisibility(View.VISIBLE);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setSaveEnabled(true);

    ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener((recyclerView, position, v) -> {
      Intent intent = new Intent(getContext(), ArchiveRatingActivity.class);
      intent.putExtra("teacher_name", mAdapter.getTeacherName(position));
      intent.putExtra("teacher_rating", mAdapter.getTeacherRating(position));
      intent.putExtra("string_period", sPeriodSpinner);
      intent.putExtra("int_period", iPeriodSpinnerPosition);
      startActivity(intent);
    });
  }

  private void setVotingAdapter(List<VoteTeacher> teachers) {
    mAdapter = new RatingAdapter();
    mAdapter.setAllItems(teachers);
    mAdapter.setHasStableIds(true);
    mRecyclerView.setAdapter(mAdapter);
  }
}
