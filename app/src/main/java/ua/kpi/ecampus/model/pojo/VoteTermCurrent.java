package ua.kpi.ecampus.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by nikita on 19.01.2018.
 */

public class VoteTermCurrent {
  @Getter @Setter @SerializedName("studyPeriod") @Expose private StudyPeriod studyPeriod;
  @Getter @Setter @SerializedName("semester") @Expose private Integer semester;
  @Getter @Setter @SerializedName("voteNumber") @Expose private Integer voteNumber;
  @Getter @Setter @SerializedName("id") @Expose private Integer id;

  @Override public String toString() {
    return "  studyPeriod = "
        + studyPeriod
        + "   semester = "
        + semester
        + "   voteNumber = "
        + voteNumber
        + "   id = "
        + id;
  }
}
