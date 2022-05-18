package Structures.SalesOrders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SalesOrders{

	@SerializedName("salesorder_data")
	private List<SalesorderDataItem> salesorderData;

	@SerializedName("run_date_iso")
	private String runDateIso;

	@SerializedName("run_date_unix")
	private int runDateUnix;

	public List<SalesorderDataItem> getSalesorderData(){
		return salesorderData;
	}

	public String getRunDateIso(){
		return runDateIso;
	}

	public int getRunDateUnix(){
		return runDateUnix;
	}
}