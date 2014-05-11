package util.test;

import static org.junit.Assert.assertEquals;
import static util.FormatToHtml.HTMLField;
import static util.FormatToHtml.HTMLSubTitle;
import static util.FormatToHtml.HTMLTitle;
import static util.FormatToHtml.tagWrapper;
import model.enums.EnumHTMLBaseTags;

import org.junit.Test;

public class FormatToHtmlTest{

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testHTMLTitle_GoodCase() {
		
		String expected = "<center><h1>Teste</h1><hr/></center>";
		
		assertEquals(expected, HTMLTitle("Teste"));
	}
	
	@Test
	public void testHTMLTitle_CaseEmpty() {
		
		String expected = "";
		
		assertEquals(expected, HTMLTitle(""));
	}
	
	@Test
	public void testHTMLTitle_CaseNull() {
		
		String expected = "";
		
		assertEquals(expected, HTMLTitle(null));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testHTMLSubTitle_GoodCase() {
		
		String expected = "<center><h2 class='h2'>Teste</h2></center>";
		
		assertEquals(expected, HTMLSubTitle("Teste"));
	}
	
	@Test
	public void testHTMLSubTitle_CaseEmpty() {
		
		String expected = "";
		
		assertEquals(expected, HTMLSubTitle(""));
	}
	
	@Test
	public void testHTMLSubTitle_CaseNull() {
		
		String expected = "";
		
		assertEquals(expected, HTMLSubTitle(null));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testHTMLField_GoodCase() {
		
		String expected = "<b>Nome: </b>Arthur";
		
		assertEquals(expected, HTMLField("Nome", "Arthur"));
	}
	
	@Test
	public void testHTMLField_CaseEmptyFirstField() {
		
		String expected = "";
		
		assertEquals(expected, HTMLField("", "Arthur"));
	}
	
	@Test
	public void testHTMLField_CaseEmptySecondField() {
		
		String expected = "";
		
		assertEquals(expected, HTMLField("Nome", ""));
	}
	
	@Test
	public void testHTMLField_CaseNullFirstField() {
		
		String expected = "";
		
		assertEquals(expected, HTMLField("Nome", null));
	}
	
	@Test
	public void testHTMLField_CaseNullSecondField() {
		
		String expected = "";
		
		assertEquals(expected, HTMLField(null, "Arthur"));
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_GoodCase() {

		String expected  = "<b>Testing</b>";
		
		assertEquals(expected, tagWrapper(EnumHTMLBaseTags.B, "Testing"));
		
	}
	
	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_CaseEmpty() {

		String expected  = "";
		
		assertEquals(expected, tagWrapper(EnumHTMLBaseTags.B, ""));
		
	}
	
	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_CaseNullFirstField() {

		String expected  = "";
		
		assertEquals(expected, tagWrapper(null, "Testing"));
		
	}
	
	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_CaseNullSecondField() {

		String expected  = "";
		
		assertEquals(expected, tagWrapper(EnumHTMLBaseTags.B, null));
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_String_GoodCase() {
		
		String expected  = "<b class='test'>Testing</b>";
		
		assertEquals(expected, tagWrapper(EnumHTMLBaseTags.B, "class='test'", "Testing"));
		
	}
	
	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_String_CaseEmptySecondField() {
		
		String expected  = "<b>Testing</b>";
		
		assertEquals(expected, tagWrapper(EnumHTMLBaseTags.B, "", "Testing"));
		
	}
	
	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_String_CaseEmptyThirdField() {
		
		String expected  = "";
		
		assertEquals(expected, tagWrapper(EnumHTMLBaseTags.B, "class='test'", ""));
		
	}
	
	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_String_CaseNullFirstField() {
		
		String expected  = "";
		
		assertEquals(expected, tagWrapper(null, "", "Testing"));
		
	}
	
	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_String_CaseNullSecondField() {
		
		String expected  = "<b>Testing</b>";
		
		assertEquals(expected, tagWrapper(EnumHTMLBaseTags.B, null, "Testing"));
		
	}
	
	@Test
	public void testTagWrapper_HTMLBaseTagsEnum_String_String_CaseNullThirdField() {
		
		String expected  = "";
		
		assertEquals(expected, tagWrapper(EnumHTMLBaseTags.B, "class='test'", null));
		
	}

}
