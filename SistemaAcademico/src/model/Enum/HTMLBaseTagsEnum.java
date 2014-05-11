package model.Enum;

public enum HTMLBaseTagsEnum {

	DIV("<{0}div{1}>"), B("<{0}b{1}>"),CENTER("<{0}center{1}>")
	,H1("<{0}h1{1}>"), H2("<{0}h2{1}>");
	
	private String value;
	
	private HTMLBaseTagsEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
