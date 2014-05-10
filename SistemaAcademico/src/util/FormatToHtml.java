package util;

import model.Enum.HTMLBaseTagsEnum;

import static util.Util.isNotValid;

public class FormatToHtml {
	
	protected static final String NEW_LINE = "<br/>";
	protected static final String HORIZONTAL_LINE = "<hr/>";

	protected static String HTMLTitle(String description){
		
		if(isNotValid(description))
			return "";
		
		StringBuilder HTMLTitle = new StringBuilder();
		
		HTMLTitle.append("<center>")
		    .append("<h1>")
		    .append(description)
		    .append("</h1>")
		    .append(HORIZONTAL_LINE)
		    .append("</center>");
		
		return HTMLTitle.toString();
	}
	
	protected static String HTMLSubTitle(String description){
		
		if(isNotValid(description))
			return "";
		
		StringBuilder HTMLSubTitle = new StringBuilder();
		
		HTMLSubTitle.append("<center>")
		    .append("<h2 class='h2'>")
		    .append(description)
		    .append("</h2>")
		    .append("</center>");
		
		return HTMLSubTitle.toString();
	}
	
	protected static String HTMLField(String fieldName, String fieldContent){
		
		if(isNotValid(fieldName) || isNotValid(fieldContent))
			return "";
		
		StringBuilder HTMLField = new StringBuilder();
		
		HTMLField.append("<b>")
	    	.append(fieldName)
	    	.append(": ")
	        .append("</b>")
	        .append(fieldContent);
		
		return HTMLField.toString();
	}
	
	protected static String TagWrapper(HTMLBaseTagsEnum envelopBase, String content){
		
		return TagWrapper(envelopBase, "", content);
	}
	
	protected static String TagWrapper(HTMLBaseTagsEnum envelopBase, String tagAtributes, String content){
		
		StringBuilder envelop = new StringBuilder();
		
		envelop.append(String.format(envelopBase.getValue(), "", tagAtributes));
		envelop.append(content);
		envelop.append(String.format(envelopBase.getValue(), "/", ""));
		
		return envelop.toString();
	}
	
	/*Template
	protected static String HTMLField(String fieldName, String fieldContent){
		
		StringBuilder HTMLField = new StringBuilder();
		
		return HTMLField.toString();
	} 
	*/ 
	
}
