package util;

import model.Enum.HTMLBaseTagsEnum;

public class FormatToHtml {
	
	protected static final String NEW_LINE = "<br/>";

	protected static String HTMLTitle(String description){
		
		StringBuilder HTMLTitle = new StringBuilder();
		
		HTMLTitle.append("<center>")
		    .append("<h1>")
		    .append(description)
		    .append("</h1>")
		    .append("<hr/>")
		    .append("</center>");
		
		return HTMLTitle.toString();
	}
	
	protected static String HTMLSubTitle(String description){
		
		StringBuilder HTMLSubTitle = new StringBuilder();
		
		HTMLSubTitle.append("<center>")
		    .append("<h2 class='h2'>")
		    .append(description)
		    .append("</h2>")
		    .append("</center>");
		
		return HTMLSubTitle.toString();
	}
	
	protected static String HTMLField(String fieldName, String fieldContent){
		
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
