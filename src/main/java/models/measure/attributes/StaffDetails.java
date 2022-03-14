package models.measure.attributes;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StaffDetails {
	@JacksonXmlProperty(localName = "staff-lines")
	public int staffLines;
	@JacksonXmlProperty(localName = "staff-tuning")
	@JacksonXmlElementWrapper(useWrapping = false)
	public List<StaffTuning> staffTuning;

	public StaffDetails() {

	}

	public StaffDetails(int staffLines, List<StaffTuning> staffTuning) {
		this.staffLines = staffLines;
		this.staffTuning = staffTuning;
	}

	public int getStaffLines() {
		return staffLines;
	}

	public List<StaffTuning> getStaffTuning() {
		return staffTuning;
	}

	public void setStaffLines(int staffLines) {
		this.staffLines = staffLines;
	}

	public void setStaffTuning(List<StaffTuning> staffTuning) {
		this.staffTuning = staffTuning;
	}
}
