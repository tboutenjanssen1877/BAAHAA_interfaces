package Structures.PurchaseOrders;

import com.google.gson.annotations.SerializedName;

public class LinesItem{

	@SerializedName("VENDOR_ADDRESS_2")
	private String vENDORADDRESS2;

	@SerializedName("VENDOR_ADDRESS_3")
	private String vENDORADDRESS3;

	@SerializedName("VENDOR_ADDRESS_COUNTRY")
	private String vENDORADDRESSCOUNTRY;

	@SerializedName("VENDOR_ADDRESS_CITY")
	private String vENDORADDRESSCITY;

	@SerializedName("VAT")
	private String vAT;

	@SerializedName("ITEM_DESC")
	private String iTEMDESC;

	@SerializedName("EXP_RECEIPT_DATE")
	private String eXPRECEIPTDATE;

	@SerializedName("DOC_NUM")
	private String dOCNUM;

	@SerializedName("DATE")
	private String dATE;

	@SerializedName("ITEM")
	private String iTEM;

	@SerializedName("ORDER_QTY")
	private String oRDERQTY;

	@SerializedName("VENDOR_ADDRESS_STATE")
	private String vENDORADDRESSSTATE;

	@SerializedName("AMOUNT")
	private String aMOUNT;

	@SerializedName("VENDOR_ADDRESS_ZIP")
	private String vENDORADDRESSZIP;

	@SerializedName("VENDOR")
	private String vENDOR;

	@SerializedName("MAIN_LINE")
	private String mAINLINE;

	@SerializedName("SHIP_METHOD")
	private String sHIPMETHOD;

	@SerializedName("TYPE")
	private String tYPE;

	@SerializedName("VENDOR_ADDRESS_1")
	private String vENDORADDRESS1;

	@SerializedName("INTERNAL_ID")
	private String internalId;

	@SerializedName("RATE")
	private String rate;

	public String getRate() {
		return rate;
	}

	public String getInternalId() {
		return internalId;
	}

	public String getVENDORADDRESS2(){
		return vENDORADDRESS2;
	}

	public String getVENDORADDRESS3(){
		return vENDORADDRESS3;
	}

	public String getVENDORADDRESSCOUNTRY(){
		return vENDORADDRESSCOUNTRY;
	}

	public String getVENDORADDRESSCITY(){
		return vENDORADDRESSCITY;
	}

	public String getVAT(){
		return vAT;
	}

	public String getITEMDESC(){
		return iTEMDESC;
	}

	public String getEXPRECEIPTDATE(){
		return eXPRECEIPTDATE;
	}

	public String getDOCNUM(){
		return dOCNUM;
	}

	public String getDATE(){
		return dATE;
	}

	public String getITEM(){
		return iTEM;
	}

	public String getORDERQTY(){
		return oRDERQTY;
	}

	public String getVENDORADDRESSSTATE(){
		return vENDORADDRESSSTATE;
	}

	public String getAMOUNT(){
		return aMOUNT;
	}

	public String getVENDORADDRESSZIP(){
		return vENDORADDRESSZIP;
	}

	public String getVENDOR(){
		return vENDOR;
	}

	public String getMAINLINE(){
		return mAINLINE;
	}

	public String getSHIPMETHOD(){
		return sHIPMETHOD;
	}

	public String getTYPE(){
		return tYPE;
	}

	public String getVENDORADDRESS1(){
		return vENDORADDRESS1;
	}
}