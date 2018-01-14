package ua.kpi.ecampus.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import java.util.List;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.model.pojo.VoteTeacher;
import ua.kpi.ecampus.ui.adapter.ItemSpinnerAdapter;
import ua.kpi.ecampus.ui.adapter.NothingSelectedAdapter;
import ua.kpi.ecampus.ui.adapter.VotingAdapter;
import ua.kpi.ecampus.ui.presenter.CurrentPresenter;
import ua.kpi.ecampus.ui.view.OnItemClickListener;

public class CurrentFragment extends Fragment implements CurrentPresenter.IView {

  @Bind(R.id.recyclerview_teachers) RecyclerView mRecyclerView;
  @Bind(R.id.spinner_terms) Spinner mSpinnerTerms;
  @Bind(R.id.tv_title_teachers) TextView mTitleTeachers;

  //@Inject CurrentPresenter mPresenter;
  private CurrentPresenter mPresenter;

  private VotingAdapter mAdapter;

  public CurrentFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_current, container, false);
    ButterKnife.bind(this, view);
    mPresenter = new CurrentPresenter();
    mPresenter.setView(this);

    mPresenter.loadVoting();
    return view;
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

  private OnItemClickListener onItemClickListener =
      (view, position, item) -> mPresenter.onItemClick(item);

  private void setRecyclerView() {
    mRecyclerView.setVisibility(View.VISIBLE);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    mRecyclerView.addItemDecoration(
        new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setSaveEnabled(true);
  }

  private void setVotingAdapter(List<VoteTeacher> teachers) {
    mAdapter = new VotingAdapter();
    mAdapter.setAllItems(teachers);
    mAdapter.setHasStableIds(true);
    mAdapter.setOnItemClickListener(onItemClickListener);
    mRecyclerView.setAdapter(mAdapter);
  }
}
