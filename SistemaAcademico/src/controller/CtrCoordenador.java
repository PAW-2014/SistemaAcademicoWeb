package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import dao.CoordenadorDAO;
import dao.interfaces.ICoordinator;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import model.Discipline;
import model.Professor;

public class CtrCoordenador {
	private static ICoordinator coordenadorDAO = new CoordenadorDAO();

	/**
	 * Método para gerar o currículo do Professor em formato PDF
	 * 
	 * @param documentName
	 * @param documentContent
	 * @return
	 */
	public static boolean generatePDFFromHtml(String documentName,String documentContent) {
		try {
			OutputStream file = new FileOutputStream(new File("curriculos\\"+ documentName + ".pdf"));
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, file);
			document.open();
			InputStream is = new ByteArrayInputStream(documentContent.getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
			document.close();
			file.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Método para consultar um professor na base de dados
	 * 
	 * @param nome
	 * @return
	 */
	public static List<Professor> consultarProfessor(String nome) {
		List<Professor> retorno = null;
		try {
			retorno = coordenadorDAO.filtrarProfessor(nome);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * Método para consultar uma disciplina na base de dados
	 * 
	 * @param nome
	 * @return
	 */
	public static List<Discipline> consultarDisciplina(String nome) {
		List<Discipline> retorno = null;
		try {
			retorno = coordenadorDAO.filtrarDisciplina(nome);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
}
