package Structures.SalesOrders;

import com.google.gson.annotations.SerializedName;

public class LinesItem{

	@SerializedName("ITEM_DESC")
	private String iTEMDESC;

	@SerializedName("SHIP_ADDRESSEE")
	private String sHIPADDRESSEE;

	@SerializedName("DOC_NUM")
	private String dOCNUM;

	@SerializedName("SHIP_ADDRESS_CITY")
	private String sHIPADDRESSCITY;

	@SerializedName("SHIP_ADDRESS_STATE")
	private String sHIPADDRESSSTATE;

	@SerializedName("ITEM")
	private String iTEM;

	@SerializedName("ORDER_QTY")
	private String oRDERQTY;

	@SerializedName("AMOUNT")
	private String aMOUNT;

	@SerializedName("SHIP_ADDRESS_COUNTRY")
	private String sHIPADDRESSCOUNTRY;

	@SerializedName("SHIP_ADDRESS_1")
	private String sHIPADDRESS1;

	@SerializedName("CUSTOMER")
	private String cUSTOMER;

	@SerializedName("MAIN_LINE")
	private String mAINLINE;

	@SerializedName("SHIP_METHOD")
	private String sHIPMETHOD;

	@SerializedName("UNITS")
	private String uNITS;

	@SerializedName("TYPE")
	private String tYPE;

	@SerializedName("SHIP_ADDRESS_3")
	private String sHIPADDRESS3;

	@SerializedName("SHIP_ADDRESS_ZIP")
	private String sHIPADDRESSZIP;

	@SerializedName("SHIP_ADDRESS_2")
	private String sHIPADDRESS2;

	@SerializedName("RATE")
	private String rate;

	@SerializedName("INTERNAL_ID")
	private String internalId;

	public String getRate() {
		return rate;
	}

	public String getInternalId() {
		return internalId;
	}

	public String getITEMDESC(){
		return iTEMDESC;
	}

	public String getSHIPADDRESSEE(){
		return sHIPADDRESSEE;
	}

	public String getDOCNUM(){
		return dOCNUM;
	}

	public String getSHIPADDRESSCITY(){
		return sHIPADDRESSCITY;
	}

	public String getSHIPADDRESSSTATE(){
		return sHIPADDRESSSTATE;
	}

	public String getITEM(){
		return iTEM;
	}

	public String getORDERQTY(){
		return oRDERQTY;
	}

	public String getAMOUNT(){
		return aMOUNT;
	}

	public String getSHIPADDRESSCOUNTRY(){
		return sHIPADDRESSCOUNTRY;
	}

	public String getSHIPADDRESS1(){
		return sHIPADDRESS1;
	}

	public String getCUSTOMER(){
		return cUSTOMER;
	}

	public String getMAINLINE(){
		return mAINLINE;
	}

	public String getSHIPMETHOD(){
		return sHIPMETHOD;
	}

	public String getUNITS(){
		return uNITS;
	}

	public String getTYPE(){
		return tYPE;
	}

	public String getSHIPADDRESS3(){
		return sHIPADDRESS3;
	}

	public String getSHIPADDRESSZIP(){
		return sHIPADDRESSZIP;
	}

	public String getSHIPADDRESS2(){
		return sHIPADDRESS2;
	}
}