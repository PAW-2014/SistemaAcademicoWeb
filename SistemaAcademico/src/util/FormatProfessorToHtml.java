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
            
            if (Util.isValid(professor.getFormacoesAcademicas()))
                html.append(academicFormationsToHTML(professor.getFormacoesAcademicas()));

            if (Util.isValid(professor.getExperienciasProfissionais()))
                html.append(professionalExperiencesToHTML(professor.getExperienciasProfissionais()));

            if (Util.isValid(professor.getOutrasInformacoes()))
                html.append(otherInformationsToHTML(professor.getOutrasInformacoes()));
                
            html.append(MessageUtil.endHtml());
        }
        
        return html.toString();
    }
	
	private static String mainInformationsToHTML(Professor professor){
		
		StringBuilder mainInformationsHTML = new StringBuilder();
		
		mainInformationsHTML.append(HTMLTitle(professor.getNome()));
	    mainInformationsHTML.append("<div align='center'>");
	    mainInformationsHTML.append(HTMLFieldAddress(professor.getEndereco()));
	    mainInformationsHTML.append(NEW_LINE);
	    mainInformationsHTML.append(HTMLField("Email", professor.getEmail())); // TODO I18N
	    mainInformationsHTML.append(NEW_LINE);
	    mainInformationsHTML.append(HTMLField("Telefone", professor.getTelefone())); // TODO I18N
	    mainInformationsHTML.append(NEW_LINE)
	        .append("</div>");
		
		return mainInformationsHTML.toString();
	}
	
	private static String HTMLFieldAddress(Address address){
		
		StringBuilder addressContent = new StringBuilder();
		
        addressContent.append(address.getLogradouro())
	        .append(", ")
	        .append(address.getNumero())
	        .append(" - ")
	        .append(address.getCidade())
	        .append(" - ")
	        .append(address.getCep());
        
        return HTMLField("Endereço", addressContent.toString()); // TODO I18N
	}
	
	private static String academicFormationsToHTML(List<AcademicFormation> academicFormations){
		
		StringBuilder academicFormationsHTML = new StringBuilder();
		
		academicFormationsHTML.append(HTMLSubTitle("Formação")); // TODO I18N
    
	    for (AcademicFormation academicFormation : academicFormations)
	        academicFormationsHTML.append("<li>")
	        	.append(academicFormation.getNomeCurso())
	        	.append(" na instiuiÃ§Ã£o de ensino ")
	        	.append(academicFormation.getInstituicao())
	        	.append("</li>");
	    
	    academicFormationsHTML.append("</ul>")
	    	.append("<br/>");
    
	    return academicFormationsHTML.toString();
    
	}
	
	private static String professionalExperiencesToHTML(List<ProfessionalExperience> professionalExperiences){
	
		StringBuilder professionalExperiencesHTML = new StringBuilder();
		Calendar cal;
		
		professionalExperiencesHTML.append(HTMLSubTitle("Experiências Profissionais")); // TODO I18N
	
		for (ProfessionalExperience professionalExperience : professionalExperiences) {
			
		    cal = new GregorianCalendar();
		    cal.setTime(professionalExperience.getDataInicio());
		    professionalExperiencesHTML.append("<li>")
		    	.append(cal.get(Calendar.YEAR))
		    	.append(" - ")
		    	.append(professionalExperience.getFuncao())
		    	.append(" na empresa ")
		    	.append(professionalExperience.getEmpresa())
		    	.append("</li>");
		}
		
		professionalExperiencesHTML.append("</ul>")
		    .append("<br/>");
		
		return professionalExperiencesHTML.toString();
	}
	
	private static String otherInformationsToHTML(String otherInformations){
	
		StringBuilder otherInformationsHTML = new StringBuilder();
		
		otherInformationsHTML.append(HTMLSubTitle("Outras Informações")) // TODO I18N
		    .append("<ul>")
		    .append("<li>")
		    .append(otherInformations)
		    .append("</li>")
		    .append("</ul>")
		    .append(NEW_LINE);
	
		return otherInformationsHTML.toString();
		
	}
	
}