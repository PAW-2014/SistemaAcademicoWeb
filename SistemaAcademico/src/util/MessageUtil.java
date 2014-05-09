package util;

public class MessageUtil {

    public static String initHtml() {
        return "<html><body>";
    }

    public static String endHtml() {
        return "</body></html>";
    }

    public static String msgCampoObrigatorio(String campo) {
        return "O Campo [" + campo + "] é obrigatório!<br>";
    }

    public static String msgOnlyNumbers(String campo) {
        return "O Campo [" + campo + "] só aceita números!<br>";
    }

    public static String msgCampoInvalido(String campo) {
        return "[" + campo + "] inválido!<br>";
    }

    public static String msgSucesso(String reg, String acao) {
        return "O registro " + reg + " foi " + acao + " com sucesso.";
    }

    public static String msgSucessoMult(String acao) {
        return "Os registros foram " + acao + " com sucesso.";
    }

    public static String msgConfirmacao(String reg, String acao) {
        return "Deseja realmente " + acao + " o registro " + reg + "?";
    }
    
     public static String msgAcao(String reg, String acao) {
        return "Deseja realmente " + acao + " o professor " + reg + "?";
    }
     
     public static String msgSucessoPromocao(String reg) {
        return "Professor " + reg + " promovido com sucesso!";
    }

    public static String msgJaCadastrada(String obj) {
        return obj + " já cadastrado(a)!";
    }
    
    public static String msgCurriculoCriadoSucesso(String path) {
        return "Currículo: "+ path + ".pdf " + " criado com sucesso.!";
    }
    
     public static String msgDatasInvalidas(String inicio,String fim) {
        return "O Campo "+ inicio + "deve ser menor que o campo " + fim +" !";
    }
}
