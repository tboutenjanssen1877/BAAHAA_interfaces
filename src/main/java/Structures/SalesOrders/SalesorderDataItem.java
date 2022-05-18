package Structures.SalesOrders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SalesorderDataItem{

	@SerializedName("DOC_NUM")
	private String dOCNUM;

	@SerializedName("Lines")
	private List<LinesItem> lines;

	public String getDOCNUM(){
		return dOCNUM;
	}

	public List<LinesItem> getLines(){
		return lines;
	}
}