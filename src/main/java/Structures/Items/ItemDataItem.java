package Structures.Items;

import com.google.gson.annotations.SerializedName;

public class ItemDataItem{

	@SerializedName("COMMODITY_DESC")
	private String cOMMODITYDESC;

	@SerializedName("UPC")
	private String uPC;

	@SerializedName("MFG_COUNTRY")
	private String mFGCOUNTRY;

	@SerializedName("SHELF_LIFE_DAYS")
	private String sHELFLIFEDAYS;

	@SerializedName("NS_INTERNAL_ID")
	private String nSINTERNALID;

	@SerializedName("BV_3PL_ITEM")
	private boolean bV3PLITEM;

	@SerializedName("ITEM_LENGTH_CM")
	private String iTEMLENGTHCM;

	@SerializedName("ITEM_EXPIRES")
	private String iTEMEXPIRES;

	@SerializedName("ITEM_WIDTH_CM")
	private String iTEMWIDTHCM;

	@SerializedName("MFG")
	private String mFG;

	@SerializedName("ITEM_HEIGHT_CM")
	private String iTEMHEIGHTCM;

	@SerializedName("STOCK_LEVEL")
	private String sTOCKLEVEL;

	@SerializedName("HARMONIZED_CODE")
	private String hARMONIZEDCODE;

	@SerializedName("SKU")
	private String sKU;

	@SerializedName("IS_BATCH")
	private String iSBATCH;

	@SerializedName("ITEM_WEIGHT_KG")
	private String iTEMWEIGHTKG;

	public String getCOMMODITYDESC(){
		return cOMMODITYDESC;
	}

	public String getUPC(){
		return uPC;
	}

	public String getMFGCOUNTRY(){
		return mFGCOUNTRY;
	}

	public String getSHELFLIFEDAYS(){
		return sHELFLIFEDAYS;
	}

	public String getNSINTERNALID(){
		return nSINTERNALID;
	}

	public boolean isBV3PLITEM(){
		return bV3PLITEM;
	}

	public String getITEMLENGTHCM(){
		return iTEMLENGTHCM;
	}

	public String getITEMEXPIRES(){
		return iTEMEXPIRES;
	}

	public String getITEMWIDTHCM(){
		return iTEMWIDTHCM;
	}

	public String getMFG(){
		return mFG;
	}

	public String getITEMHEIGHTCM(){
		return iTEMHEIGHTCM;
	}

	public String getSTOCKLEVEL(){
		return sTOCKLEVEL;
	}

	public String getHARMONIZEDCODE(){
		return hARMONIZEDCODE;
	}

	public String getSKU(){
		return sKU;
	}

	public String getISBATCH(){
		return iSBATCH;
	}

	public String getITEMWEIGHTKG(){
		return iTEMWEIGHTKG;
	}
}