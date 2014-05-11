package util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.AcademicFormation;
import model.Address;
import model.ProfessionalExperience;
import model.Professor;

public class FormatProfessorToHtml extends FormatToHtml {

	public static String format(Professor professor) {
		
        StringBuilder html = new StringBuilder();
        
        if (Util.isValid(professor.getCpf())) {
        	
            html.append(MessageUtil.initHtml());
            
            mainInformationsToHTML(professor);
            
            if (Util.isValid(professor.getAcademicFormations()))
                html.append(academicFormationsToHTML(professor.getAcademicFormations()));

            if (Util.isValid(professor.getProfessionalExperiences()))
                html.append(professionalExperiencesToHTML(professor.getProfessionalExperiences()));

            if (Util.isValid(professor.getOtherInformations()))
                html.append(otherInformationsToHTML(professor.getOtherInformations()));
                
            html.append(MessageUtil.endHtml());
        }
        
        return html.toString();
    }
	
	private static String mainInformationsToHTML(Professor professor){
		
		StringBuilder mainInformationsHTML = new StringBuilder();
		
		mainInformationsHTML.append(HTMLTitle(professor.getName()));
	    mainInformationsHTML.append("<div align='center'>");
	    mainInformationsHTML.append(HTMLFieldAddress(professor.getAddress()));
	    mainInformationsHTML.append(NEW_LINE);
	    mainInformationsHTML.append(HTMLField("Email", professor.getEmail())); // TODO I18N
	    mainInformationsHTML.append(NEW_LINE);
	    mainInformationsHTML.append(HTMLField("Telefone", professor.getPhoneNumber())); // TODO I18N
	    mainInformationsHTML.append(NEW_LINE)
	        .append("</div>");
		
		return mainInformationsHTML.toString();
	}
	
	private static String HTMLFieldAddress(Address address){
		
		StringBuilder addressContent = new StringBuilder();
		
        addressContent.append(address.getStreet())
	        .append(", ")
	        .append(address.getNumber())
	        .append(" - ")
	        .append(address.getCity())
	        .append(" - ")
	        .append(address.getZipCode());
        
        return HTMLField("Endere�o", addressContent.toString()); // TODO I18N
	}
	
	private static String academicFormationsToHTML(List<AcademicFormation> academicFormations){
		
		StringBuilder academicFormationsHTML = new StringBuilder();
		
		academicFormationsHTML.append(HTMLSubTitle("Forma��o")); // TODO I18N
    
	    for (AcademicFormation academicFormation : academicFormations)
	        academicFormationsHTML.append("<li>")
	        	.append(academicFormation.getCourseName())
	        	.append(" na instiuição de ensino ")
	        	.append(academicFormation.getInstitute())
	        	.append("</li>");
	    
	    academicFormationsHTML.append("</ul>")
	    	.append("<br/>");
    
	    return academicFormationsHTML.toString();
    
	}
	
	private static String professionalExperiencesToHTML(List<ProfessionalExperience> professionalExperiences){
	
		StringBuilder professionalExperiencesHTML = new StringBuilder();
		Calendar cal;
		
		professionalExperiencesHTML.append(HTMLSubTitle("Experi�ncias Profissionais")); // TODO I18N
	
		for (ProfessionalExperience professionalExperience : professionalExperiences) {
			
		    cal = new GregorianCalendar();
		    cal.setTime(professionalExperience.getStartDate());
		    professionalExperiencesHTML.append("<li>")
		    	.append(cal.get(Calendar.YEAR))
		    	.append(" - ")
		    	.append(professionalExperience.getFunction())
		    	.append(" na empresa ")
		    	.append(professionalExperience.getFirm())
		    	.append("</li>");
		}
		
		professionalExperiencesHTML.append("</ul>")
		    .append("<br/>");
		
		return professionalExperiencesHTML.toString();
	}
	
	private static String otherInformationsToHTML(String otherInformations){
	
		StringBuilder otherInformationsHTML = new StringBuilder();
		
		otherInformationsHTML.append(HTMLSubTitle("Outras Informa��es")) // TODO I18N
		    .append("<ul>")
		    .append("<li>")
		    .append(otherInformations)
		    .append("</li>")
		    .append("</ul>")
		    .append(NEW_LINE);
	
		return otherInformationsHTML.toString();
		
	}
	
}