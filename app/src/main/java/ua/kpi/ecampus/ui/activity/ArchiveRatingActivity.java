package ua.kpi.ecampus.ui.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;
import ua.kpi.ecampus.R;
import ua.kpi.ecampus.di.UIModule;
import ua.kpi.ecampus.model.pojo.Item;
import ua.kpi.ecampus.ui.adapter.ItemSpinnerAdapter;
import ua.kpi.ecampus.ui.adapter.NothingSelectedAdapter;
import ua.kpi.ecampus.ui.presenter.ArchiveRatingPresenter;

public class ArchiveRatingActivity extends BaseActivity implements ArchiveRatingPresenter.IView {

  @Bind(R.id.clTeacherRating) ConstraintLayout clTeacherRating;
  @Bind(R.id.spinnerRatingTerms) Spinner spinnerRatingTerms;
  @Bind(R.id.toolbar) Toolbar mToolbar;
  @Bind(R.id.tvTeacherName) TextView tvTeacherName;
  @Bind(R.id.tvPeriod) TextView tvPeriod;
  @Bind(R.id.tvTeacherRating) TextView tvTeacherRating;
  @Bind(R.id.tvRating1) TextView tvRating1;
  @Bind(R.id.tvRating2) TextView tvRating2;
  @Bind(R.id.tvRating3) TextView tvRating3;
  @Bind(R.id.tvRating4) TextView tvRating4;
  @Bind(R.id.tvRating5) TextView tvRating5;
  @Bind(R.id.tvRating6) TextView tvRating6;
  @Bind(R.id.tvTableRating1) TextView tvTableRating1;
  @Bind(R.id.tvTableRating2) TextView tvTableRating2;
  @Bind(R.id.tvTableRating3) TextView tvTableRating3;
  @Bind(R.id.tvTableRating4) TextView tvTableRating4;
  @Bind(R.id.tvTableRating5) TextView tvTableRating5;
  @Bind(R.id.tvTableRating6) TextView tvTableRating6;
  @Bind(R.id.tvTableRating7) TextView tvTableRating7;

  @Inject ArchiveRatingPresenter mPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_archive_rating);
    bindViews();
    mPresenter.setView(this);
    mPresenter.initializeViewComponent();
    mPresenter.loadVoting();
  }

  private void fillActivity() {
    tvTeacherName.setText(getIntent().getStringExtra("teacher_name"));
    tvPeriod.setText(getIntent().getStringExtra("string_period"));
    tvTeacherRating.setText(getIntent().getStringExtra("teacher_rating"));
    tvRating1.setText("3.97");
    tvRating2.setText("4.21");
    tvRating3.setText("3.97");
    tvRating4.setText("4.21");
    tvRating5.setText("3.97");
    tvRating6.setText("4.21");
    tvTableRating1.setText("3.97");
    tvTableRating2.setText("4.6");
    tvTableRating3.setText("3.88");
    tvTableRating4.setText("3");
    tvTableRating5.setText("4");
    tvTableRating6.setText("5");
    tvTableRating7.setText("35");
  }

  @Override protected List<Object> getModules() {
    List<Object> modules = new ArrayList<>();
    modules.add(new UIModule());
    return modules;
  }

  @Override public void setViewComponent() {
    setToolbar();
  }

  @Override public void setTermsSpinner(List<Item> list) {

    ArrayAdapter<Item> adapter =
        new ItemSpinnerAdapter(this, R.layout.spinner_item, R.layout.spinner_dropdown_item, list);
    spinnerRatingTerms.setAdapter(
        new NothingSelectedAdapter(adapter, R.layout.spinner_nothing_selected_terms, this));
    spinnerRatingTerms.setSelection(getIntent().getIntExtra("int_period", 0));
    fillActivity();
    spinnerRatingTerms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) parent.getItemAtPosition(position);
        if (item != null) {
          clTeacherRating.setVisibility(ConstraintLayout.VISIBLE);
          fillActivity();
        }
      }

      @Override public void onNothingSelected(AdapterView<?> parent) {
        // N/A
      }
    });
  }



  private void setToolbar() {
    setSupportActionBar(mToolbar);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
    mToolbar.setNavigationOnClickListener(v -> {
      Timber.e("click");
      finish();
    });
    getSupportActionBar().setTitle(R.string.activity_name_voting);
  }
}
