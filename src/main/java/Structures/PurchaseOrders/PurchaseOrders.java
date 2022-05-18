package Structures.PurchaseOrders;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PurchaseOrders{

	@SerializedName("purchaseorder_data")
	private List<PurchaseorderDataItem> purchaseorderData;

	@SerializedName("run_date_iso")
	private String runDateIso;

	@SerializedName("run_date_unix")
	private int runDateUnix;

	public List<PurchaseorderDataItem> getPurchaseorderData(){
		return purchaseorderData;
	}

	public String getRunDateIso(){
		return runDateIso;
	}

	public int getRunDateUnix(){
		return runDateUnix;
	}
}