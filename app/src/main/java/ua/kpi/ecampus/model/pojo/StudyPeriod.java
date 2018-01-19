package ua.kpi.ecampus.model.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by nikita on 19.01.2018.
 */

public class StudyPeriod {
  @Getter @Setter @SerializedName("start") @Expose private Integer start;
  @Getter @Setter @SerializedName("end") @Expose private Integer end;

  @Override public String toString() {
    return "  start = " + start + "   end = " + end;
  }
}
