package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import dao.CoordenadorDAO;
import dao.interfaces.ICoordenador;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import model.Disciplina;
import model.Professor;

public class CtrCoordenador {
private static ICoordenador dao = new CoordenadorDAO();
    /**
     * Método para gerar o currículo do Professor em formato PDF
     *
     * @param nome
     * @param html
     * @return
     */
    public static boolean gerarPDF(String nome, String html) {
        try {
            OutputStream file = new FileOutputStream(new File("curriculos\\" + nome + ".pdf"));
            Document document = new Document() {
            };
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            InputStream is = new ByteArrayInputStream(html.getBytes());
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
 * @param nome
 * @return 
 */
    public static List<Professor> consultarProfessor(String nome) {
        List<Professor> retorno = null;
        try {
          retorno = dao.filtrarProfessor(nome);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

  /**
 * Método para consultar uma disciplina na base de dados
 * @param nome
 * @return 
 */
    public static List<Disciplina> consultarDisciplina(String nome) {
        List<Disciplina> retorno = null;
        try {
          retorno = dao.filtrarDisciplina(nome);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }
}
