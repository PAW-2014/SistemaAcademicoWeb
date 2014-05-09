package model.Enum;

public enum HTMLBaseTagsEnum {

	DIV("<{0}div {1}>"), B("<{0}b {1}>");
	
	String value;
	
	private HTMLBaseTagsEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
