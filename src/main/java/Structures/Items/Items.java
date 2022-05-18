package Structures.Items;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Items{

	@SerializedName("run_date_iso")
	private String runDateIso;

	@SerializedName("run_date_unix")
	private int runDateUnix;

	@SerializedName("item_data")
	private List<ItemDataItem> itemData;

	public String getRunDateIso(){
		return runDateIso;
	}

	public int getRunDateUnix(){
		return runDateUnix;
	}

	public List<ItemDataItem> getItemData(){
		return itemData;
	}
}