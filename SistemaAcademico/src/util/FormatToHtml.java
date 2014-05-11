package util;

import java.text.MessageFormat;

import model.Enum.HTMLBaseTagsEnum;

import static model.Enum.HTMLBaseTagsEnum.*;
import static util.Util.*;

public class FormatToHtml {
	
	public static final String NEW_LINE = "<br/>";
	public static final String HORIZONTAL_LINE = "<hr/>";

	public static String HTMLTitle(String description){
		
		if(isNotValid(description))
			return "";
		
		return tagWrapper(
					CENTER
					, tagWrapper(H1, description) + HORIZONTAL_LINE);
	}
	
	public static String HTMLSubTitle(String description){
		
		if(isNotValid(description))
			return "";
		
		return tagWrapper(
				CENTER
				, tagWrapper(H2, "class='h2'", description));
	}
	
	public static String HTMLField(String fieldName, String fieldContent){
		
		if(isNotValid(fieldName) || isNotValid(fieldContent))
			return "";
		
		return tagWrapper(B, fieldName+": ")+fieldContent;
		
	}
	
	public static String tagWrapper(HTMLBaseTagsEnum envelopBase, String content){
		
		return tagWrapper(envelopBase, "", content);
	}
	
	public static String tagWrapper(HTMLBaseTagsEnum tagEnvelopBase, String tagAtributes, String content){
		
		if(isNotValid(tagEnvelopBase) || isNotValid(content))
			return "";
		
		StringBuilder envelop = new StringBuilder();

		tagAtributes = formatTagAtributes(tagAtributes);
		
		envelop.append(MessageFormat.format(tagEnvelopBase.getValue(), "", tagAtributes));
		envelop.append(content);
		envelop.append(MessageFormat.format(tagEnvelopBase.getValue(), "/", ""));
		
		return envelop.toString();
	}

	private static String formatTagAtributes(String tagAtributes){
		if(isNotValid(tagAtributes))
			return "";
		else
			if(startsWithSpace(tagAtributes))
				return tagAtributes;
			else
				return " "+tagAtributes;
	}
	
}
