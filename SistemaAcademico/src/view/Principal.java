package view;

import controller.CtrCoordenador;
import controller.CtrDiretor;
import controller.CtrDisciplina;
import controller.CtrExperienciaProfissional;
import controller.CtrFormacaoAcademica;
import controller.CtrProfessor;
import de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;
import javax.swing.text.html.HTMLEditorKit;

import model.Coordenador;
import model.Diretor;
import model.Disciplina;
import model.DisciplinaPreferencial;
import model.Enum.EnumStates;
import model.Enum.SituProfessor;
import model.Enum.TipoProfessor;
import model.ProfessionalExperience;
import model.AcademicFormation;
import model.Professor;
import util.FormatProfessorToHtml;
import util.MessageUtil;
import view.Model.DisciplinaDataModel;
import view.Model.DisciplinaPreferencialListModel;
import view.Model.DisciplinaListModel;
import view.Model.ExperienciaDataModel;
import view.Model.FormacaoDataModel;
import view.Model.ProfessorComboModel;
import view.Model.ProfessorDataModel;

public class Principal extends javax.swing.JFrame {

    private Professor prof = null;
    private AcademicFormation formacao;
    private ProfessionalExperience experiencia;
    private boolean isEdit = false;
    private boolean isExcluir = false;
    private boolean isPromover = false;
    private List<Disciplina> disciplinas;
    private DisciplinaPreferencial disciplinaPreferencial;
    private Disciplina disciplina;
    private Professor profSele;
    private boolean gerarCurriculo = false;
    private int progress = 0;

    public Principal(Professor prof) {
        this.prof = prof;
        initComponents();
        tabPrincipal.setEnabledAt(1, false);
        tabPrincipal.setEnabledAt(2, false);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dim);
        this.setSize((int) dim.getWidth(), (int) dim.getHeight() - 40);
        listarUfs();
        initDadosGerais();
        controlePermissao();
    }

    public void controlePermissao() {
        if (prof.getTipo().equals(TipoProfessor.Professor)) {
            MenuBar.setVisible(false);
        } else if (prof.getTipo().equals(TipoProfessor.Coordenador)) {
            Importar.setVisible(false);
            Situ.setVisible(false);
            ExcluirPro.setVisible(false);
            Promover.setVisible(false);
        } else {
            MenuBar.setVisible(true);
        }
    }

    //##################################### DADOS GERAIS ############################################# 
    public void initDadosGerais() {
        this.lMsg.setVisible(false);
        criarMascaras();
        carregarDadosProfessor();
        resetColorsDados();
        rendCamposDados();
    }

    public void listarUfs() {
        this.cUf.setModel(new javax.swing.DefaultComboBoxModel(EnumStates.values()));
        this.cUf.setSelectedIndex(0);
    }

    public void carregarDadosProfessor() {
        cNome.setText(prof.getNome());
        cRg.setText(prof.getRg());
        cDataNascimento.setDate(prof.getDataNascimento());
        cBairro.setText(prof.getEndereco().getBairro());
        cCep.setText(prof.getEndereco().getCep());
        cCidade.setText(prof.getEndereco().getCidade());
        cUf.setSelectedItem(prof.getEndereco().getUf());
        cCep.setText(prof.getEndereco().getCep());
        cCpf.setText(prof.getCpf());
        cEmail.setText(prof.getEmail());
        cTelefone.setText(prof.getTelefone());
        if (prof.getFoto() != null) {
            cImg.setIcon(new ImageIcon(prof.getFoto()));
        }
        cLogradouro.setText(prof.getEndereco().getLogradouro());
        try {
            cNumero.setText(prof.getEndereco().getNumero().toString());
        } catch (NullPointerException e) {
            cNumero.setText("");
        }
        cObserva.setText(prof.getOutrasInformacoes());
    }

    public void setarDados() {
        prof.setNome(getNome());
        prof.setRg(getRg());
        prof.setCpf(getCpf());
        prof.setEmail(getEmail());
        prof.setDataNascimento(getDataNascimento());
        prof.setTelefone(getTelefone());
        prof.getEndereco().setLogradouro(getEndereco());
        prof.getEndereco().setBairro(getBairro());
        prof.getEndereco().setCidade(getCidade());
        prof.getEndereco().setNumero(getNumero());
        prof.getEndereco().setUf(getUf());
        prof.getEndereco().setCep(getCep());
        prof.setOutrasInformacoes(getObservacoes());
    }

    public void criarMascaras() {
        MaskFormatter maskCpf;
        try {
            maskCpf = new MaskFormatter("###.###.###-##");
            maskCpf.setPlaceholderCharacter('_');
            maskCpf.install(cCpf);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        MaskFormatter maskCep;
        try {
            maskCep = new MaskFormatter("#####-###");
            maskCep.setPlaceholderCharacter('_');
            maskCep.install(cCep);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        MaskFormatter maskRg;
        try {
            maskRg = new MaskFormatter("#.###.###");
            maskRg.setPlaceholderCharacter('_');
            maskRg.install(cRg);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        MaskFormatter maskTelefone;
        try {
            maskTelefone = new MaskFormatter("(##) ####-####");
            maskTelefone.setPlaceholderCharacter('_');
            maskTelefone.install(cTelefone);
        } catch (ParseException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ##################################### FORMAÇÂO ACADÊMICA ##########################################
    public void initFormacaoAcademica() {
        this.lMsg1.setVisible(false);
        formacao = new AcademicFormation();
        bExcluirFormaSele.setVisible(false);
        isEdit = false;
        carregarFormacoes();
        rendCamposFormacao();
        resetColorsFormacao();
        enableBotoesFormacao();
        limparCamposFormacao();
    }

    public void setarFormacao() {
        formacao.setNomeCurso(getNomeCurso());
        formacao.setInstituicao(getInstituicao());
        formacao.setDataInicio(getDataInicial());
        formacao.setDataFim(getDataFim());
    }

    public void carregarFormacoes() {
        try {
            prof.setFormacoesAcademicas(CtrFormacaoAcademica.getFormacoes(prof.getId()));
            FormacaoDataModel tf = new FormacaoDataModel(prof.getFormacoesAcademicas());
            cTabela.setModel(tf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void preencherCamposFormacao() {
        cNomeFormacao.setText(formacao.getNomeCurso());
        cInstituicao.setText(formacao.getInstituicao());
        cDataInicial.setDate(formacao.getDataInicio());
        cDataFim.setDate(formacao.getDataFim());
    }

    public void resetColorsFormacao() {
        lMsg1.setVisible(false);
        lNomeFormacao.setForeground(Color.black);
        lInstituicao.setForeground(Color.black);
        lDataInicial.setForeground(Color.black);
        lDataFim.setForeground(Color.black);
    }

    // ##################################### EXPERIÊNCIA PROFICIONAL ########################################## 
    public void initExpProfissional() {
        this.lMsg2.setVisible(false);
        experiencia = new ProfessionalExperience();
        bExcluirExpSele.setVisible(false);
        isEdit = false;
        carregarExperiencias();
        rendCamposExperiencia();
        resetColorsExperiencia();
        enableBotoesExperiencia();
        limparCamposExperiencia();
    }

    public void setarExperiencia() {
        experiencia.setEmpresa(getEmpresa());
        experiencia.setFuncao(getFuncao());
        experiencia.setDataInicio(getDataInicialExp());
        experiencia.setDataFim(getDataFimExp());
    }

    public void carregarExperiencias() {
        try {
            prof.setExperienciasProfissionais(CtrExperienciaProfissional.getExperiencias(prof.getId()));
            ExperienciaDataModel ed = new ExperienciaDataModel(prof.getExperienciasProfissionais());
            cTabelaExp.setModel(ed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void preencherCamposExperiencia() {
        cEmpresa.setText(experiencia.getEmpresa());
        cFuncao.setText(experiencia.getFuncao());
        cDataInicialExp.setDate(experiencia.getDataInicio());
        cDataFImExp.setDate(experiencia.getDataFim());
    }

    public void resetColorsExperiencia() {
        lMsg2.setVisible(false);
        lEmpresa.setForeground(Color.black);
        lFuncao.setForeground(Color.black);
        lDataInicialExp.setForeground(Color.black);
        lDataFimExp.setForeground(Color.black);
    }

    // ##################################### DISCIPLINA ########################################## 
    public void initDisciplinas() {
        disciplinaPreferencial = new DisciplinaPreferencial();
        disciplinas = CtrDisciplina.listarDisciplinas();
        prof.setDisciplinasPreferenciais(CtrDisciplina.getDisciplinas(prof.getId()));
        intersecaoListas();
        DisciplinaListModel dlm = new DisciplinaListModel(disciplinas);
        cListaAll.setModel(dlm);
        DisciplinaPreferencialListModel dplm = new DisciplinaPreferencialListModel(prof.getDisciplinasPreferenciais());
        cListaSele.setModel(dplm);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogConsulta = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cTipoPesquisa = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        bConsultaPesq = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        cTabelaConsulta = new javax.swing.JTable();
        cPesquisa = new javax.swing.JTextField();
        dialogoCurriculo = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        editor = new javax.swing.JEditorPane();
        cSeleProfCurricu = new javax.swing.JComboBox();
        bGerarCurriculo = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        bSalvarPDF = new javax.swing.JMenu();
        dialogoAcao = new javax.swing.JDialog();
        cListaProfessores = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        bAcaoConfirmar = new javax.swing.JButton();
        dialogoImportar = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cEnderecoWeb = new javax.swing.JTextField();
        cLoginImportar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cSenhaImportar = new javax.swing.JPasswordField();
        bImportar = new javax.swing.JButton();
        dialogoProgressBar = new javax.swing.JDialog();
        progressBar = new javax.swing.JProgressBar();
        dialogoStatus = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        cListaInativar = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        rAtivo = new javax.swing.JRadioButton();
        rInativo = new javax.swing.JRadioButton();
        bConfirmarInativacao = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        Menu = new javax.swing.JPanel();
        bLogout = new javax.swing.JButton();
        tabPrincipal = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        Cadastro = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        tabDadosGerais = new javax.swing.JPanel();
        bCancelar = new javax.swing.JButton();
        bGravar = new javax.swing.JButton();
        divMenu = new javax.swing.JPanel();
        lMsg = new javax.swing.JLabel();
        bEditar = new javax.swing.JButton();
        divAll = new javax.swing.JPanel();
        divOne = new javax.swing.JPanel();
        cNome = new javax.swing.JTextField();
        lNome = new javax.swing.JLabel();
        cRg = new javax.swing.JFormattedTextField();
        lRg = new javax.swing.JLabel();
        lCpf = new javax.swing.JLabel();
        cCpf = new javax.swing.JFormattedTextField();
        lEndereco = new javax.swing.JLabel();
        lCep = new javax.swing.JLabel();
        lBairro = new javax.swing.JLabel();
        lCidade = new javax.swing.JLabel();
        lUf = new javax.swing.JLabel();
        cUf = new javax.swing.JComboBox();
        cCidade = new javax.swing.JTextField();
        cBairro = new javax.swing.JTextField();
        cCep = new javax.swing.JFormattedTextField();
        cLogradouro = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        cObserva = new javax.swing.JTextArea();
        lObservacoes = new javax.swing.JLabel();
        lTelefone = new javax.swing.JLabel();
        lEmail = new javax.swing.JLabel();
        cEmail = new javax.swing.JTextField();
        lNumero = new javax.swing.JLabel();
        lData = new javax.swing.JLabel();
        cTelefone = new javax.swing.JFormattedTextField();
        cNumero = new javax.swing.JFormattedTextField();
        bProcurar = new javax.swing.JButton();
        cDataNascimento = new de.javasoft.swing.DateComboBox();
        lFoto = new javax.swing.JLabel();
        cImg = new javax.swing.JLabel();
        tabFormacaoAcademica = new javax.swing.JPanel();
        divAll1 = new javax.swing.JPanel();
        divOne1 = new javax.swing.JPanel();
        cNomeFormacao = new javax.swing.JTextField();
        lNomeFormacao = new javax.swing.JLabel();
        cInstituicao = new javax.swing.JTextField();
        lInstituicao = new javax.swing.JLabel();
        lDataFim = new javax.swing.JLabel();
        lDataInicial = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        cTabela = new javax.swing.JTable();
        bExcluirFormaSele = new javax.swing.JButton();
        cDataInicial = new de.javasoft.swing.DateComboBox();
        cDataFim = new de.javasoft.swing.DateComboBox();
        divMenu1 = new javax.swing.JPanel();
        bExcluirForm = new javax.swing.JButton();
        lMsg1 = new javax.swing.JLabel();
        bEditarForm = new javax.swing.JButton();
        bCancelarForm = new javax.swing.JButton();
        bGravarForm = new javax.swing.JButton();
        bIncluirForm = new javax.swing.JButton();
        tabExperienciaPro = new javax.swing.JPanel();
        divAll2 = new javax.swing.JPanel();
        divOne2 = new javax.swing.JPanel();
        cEmpresa = new javax.swing.JTextField();
        lEmpresa = new javax.swing.JLabel();
        cFuncao = new javax.swing.JTextField();
        lFuncao = new javax.swing.JLabel();
        lDataFimExp = new javax.swing.JLabel();
        lDataInicialExp = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        cTabelaExp = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        bExcluirExpSele = new javax.swing.JButton();
        cDataInicialExp = new de.javasoft.swing.DateComboBox();
        cDataFImExp = new de.javasoft.swing.DateComboBox();
        divMenu2 = new javax.swing.JPanel();
        bExcluirExp = new javax.swing.JButton();
        lMsg2 = new javax.swing.JLabel();
        bEditarExp = new javax.swing.JButton();
        bCancelarExp = new javax.swing.JButton();
        bGravarExp = new javax.swing.JButton();
        bIncluirExp = new javax.swing.JButton();
        tabDisciplinas = new javax.swing.JPanel();
        divAll3 = new javax.swing.JPanel();
        bPassarAll = new javax.swing.JButton();
        bPassarOne = new javax.swing.JButton();
        bVoltarOne = new javax.swing.JButton();
        bVoltarAll = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        cListaSele = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        cListaAll = new javax.swing.JList();
        divMenu3 = new javax.swing.JPanel();
        bGravarDisci = new javax.swing.JButton();
        bCancelarDisci = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        MenuBar = new javax.swing.JMenuBar();
        Consultar = new javax.swing.JMenu();
        GerarCurriculo = new javax.swing.JMenu();
        Importar = new javax.swing.JMenu();
        Situ = new javax.swing.JMenu();
        Promover = new javax.swing.JMenu();
        ExcluirPro = new javax.swing.JMenu();

        dialogConsulta.setTitle("Consultar Registros");
        dialogConsulta.setAlwaysOnTop(true);
        dialogConsulta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        dialogConsulta.setMinimumSize(new java.awt.Dimension(730, 500));
        dialogConsulta.setPreferredSize(new java.awt.Dimension(730, 500));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Tipo Consulta");

        cTipoPesquisa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Disciplina", "Professor" }));
        cTipoPesquisa.setSelectedIndex(1);
        cTipoPesquisa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cTipoPesquisaItemStateChanged(evt);
            }
        });

        jLabel4.setText("Conteúdo da Consulta");

        bConsultaPesq.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/search.png"))); // NOI18N
        bConsultaPesq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConsultaPesqActionPerformed(evt);
            }
        });

        cTabelaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(cTabelaConsulta);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bConsultaPesq))
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bConsultaPesq)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cTipoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout dialogConsultaLayout = new javax.swing.GroupLayout(dialogConsulta.getContentPane());
        dialogConsulta.getContentPane().setLayout(dialogConsultaLayout);
        dialogConsultaLayout.setHorizontalGroup(
            dialogConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dialogConsultaLayout.setVerticalGroup(
            dialogConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogConsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dialogConsulta.getAccessibleContext().setAccessibleParent(null);

        dialogoCurriculo.setAlwaysOnTop(true);
        dialogoCurriculo.setMinimumSize(new java.awt.Dimension(730, 600));

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        editor.setEditable(false);
        jScrollPane5.setViewportView(editor);

        cSeleProfCurricu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cSeleProfCurricu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cSeleProfCurricuItemStateChanged(evt);
            }
        });

        bGerarCurriculo.setText("Gerar Currículo");
        bGerarCurriculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGerarCurriculoActionPerformed(evt);
            }
        });

        jLabel5.setText("Selecione o Professor");

        bSalvarPDF.setForeground(new java.awt.Color(255, 255, 255));
        bSalvarPDF.setText("Salvar em PDF");
        bSalvarPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bSalvarPDFMousePressed(evt);
            }
        });
        jMenuBar1.add(bSalvarPDF);

        dialogoCurriculo.setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout dialogoCurriculoLayout = new javax.swing.GroupLayout(dialogoCurriculo.getContentPane());
        dialogoCurriculo.getContentPane().setLayout(dialogoCurriculoLayout);
        dialogoCurriculoLayout.setHorizontalGroup(
            dialogoCurriculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogoCurriculoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(dialogoCurriculoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(dialogoCurriculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cSeleProfCurricu, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bGerarCurriculo)
                .addGap(33, 33, 33))
        );
        dialogoCurriculoLayout.setVerticalGroup(
            dialogoCurriculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogoCurriculoLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(dialogoCurriculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dialogoCurriculoLayout.createSequentialGroup()
                        .addComponent(bGerarCurriculo)
                        .addGap(28, 28, 28))
                    .addGroup(dialogoCurriculoLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cSeleProfCurricu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)))
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        dialogoCurriculo.getAccessibleContext().setAccessibleParent(null);

        dialogoAcao.setTitle("Promover Professor");
        dialogoAcao.setAlwaysOnTop(true);
        dialogoAcao.setMinimumSize(new java.awt.Dimension(480, 148));
        dialogoAcao.setResizable(false);

        cListaProfessores.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Selecione um professor");

        bAcaoConfirmar.setText("Confirmar");
        bAcaoConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAcaoConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dialogoAcaoLayout = new javax.swing.GroupLayout(dialogoAcao.getContentPane());
        dialogoAcao.getContentPane().setLayout(dialogoAcaoLayout);
        dialogoAcaoLayout.setHorizontalGroup(
            dialogoAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogoAcaoLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(dialogoAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dialogoAcaoLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dialogoAcaoLayout.createSequentialGroup()
                        .addComponent(cListaProfessores, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(bAcaoConfirmar)
                        .addGap(27, 27, 27))))
        );
        dialogoAcaoLayout.setVerticalGroup(
            dialogoAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogoAcaoLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dialogoAcaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cListaProfessores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bAcaoConfirmar))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        dialogoImportar.setTitle("Importar Dados RH");
        dialogoImportar.setAlwaysOnTop(true);
        dialogoImportar.setMinimumSize(new java.awt.Dimension(533, 204));

        jLabel7.setText("Endereço Web");

        jLabel8.setText("Login");

        jLabel9.setText("Senha");

        bImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/check.png"))); // NOI18N
        bImportar.setText("Importar");
        bImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bImportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cEnderecoWeb)
                    .addComponent(jLabel7)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(cLoginImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(cSenhaImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                .addComponent(bImportar)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cEnderecoWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cLoginImportar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cSenhaImportar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bImportar))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dialogoImportarLayout = new javax.swing.GroupLayout(dialogoImportar.getContentPane());
        dialogoImportar.getContentPane().setLayout(dialogoImportarLayout);
        dialogoImportarLayout.setHorizontalGroup(
            dialogoImportarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogoImportarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        dialogoImportarLayout.setVerticalGroup(
            dialogoImportarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogoImportarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        dialogoProgressBar.setTitle("Processando");
        dialogoProgressBar.setAlwaysOnTop(true);
        dialogoProgressBar.setMinimumSize(new java.awt.Dimension(339, 79));

        javax.swing.GroupLayout dialogoProgressBarLayout = new javax.swing.GroupLayout(dialogoProgressBar.getContentPane());
        dialogoProgressBar.getContentPane().setLayout(dialogoProgressBarLayout);
        dialogoProgressBarLayout.setHorizontalGroup(
            dialogoProgressBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogoProgressBarLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        dialogoProgressBarLayout.setVerticalGroup(
            dialogoProgressBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogoProgressBarLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        dialogoStatus.setTitle("Inativar Professor");
        dialogoStatus.setAlwaysOnTop(true);
        dialogoStatus.setMinimumSize(new java.awt.Dimension(385, 230));

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setMinimumSize(new java.awt.Dimension(365, 171));

        jLabel10.setText("Selecione um Professor");

        cListaInativar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cListaInativar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cListaInativarItemStateChanged(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Situação"));

        buttonGroup1.add(rAtivo);
        rAtivo.setText("Ativo");
        rAtivo.setEnabled(false);

        buttonGroup1.add(rInativo);
        rInativo.setText("Inativo");
        rInativo.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(rAtivo)
                .addGap(34, 34, 34)
                .addComponent(rInativo)
                .addGap(16, 16, 16))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rAtivo)
                    .addComponent(rInativo))
                .addContainerGap())
        );

        bConfirmarInativacao.setText("Confirmar");
        bConfirmarInativacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConfirmarInativacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(cListaInativar, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(bConfirmarInativacao)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cListaInativar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(bConfirmarInativacao)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dialogoStatusLayout = new javax.swing.GroupLayout(dialogoStatus.getContentPane());
        dialogoStatus.getContentPane().setLayout(dialogoStatusLayout);
        dialogoStatusLayout.setHorizontalGroup(
            dialogoStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogoStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dialogoStatusLayout.setVerticalGroup(
            dialogoStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogoStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema Acadêmico");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        bLogout.setText("Logout");
        bLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLogoutActionPerformed(evt);
            }
        });

        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Cadastro.setPreferredSize(new java.awt.Dimension(1566, 500));

        tabs.setPreferredSize(new java.awt.Dimension(1146, 500));
        tabs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabsStateChanged(evt);
            }
        });

        tabDadosGerais.setMaximumSize(new java.awt.Dimension(32767, 500));
        tabDadosGerais.setPreferredSize(new java.awt.Dimension(800, 624));

        bCancelar.setLabel("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });

        bGravar.setText("Gravar");
        bGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGravarActionPerformed(evt);
            }
        });

        lMsg.setForeground(new java.awt.Color(255, 0, 0));
        lMsg.setPreferredSize(new java.awt.Dimension(25, 100));

        bEditar.setText("Editar");
        bEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout divMenuLayout = new javax.swing.GroupLayout(divMenu);
        divMenu.setLayout(divMenuLayout);
        divMenuLayout.setHorizontalGroup(
            divMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(divMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bEditar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        divMenuLayout.setVerticalGroup(
            divMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bEditar)
                .addContainerGap())
        );

        divAll.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados Gerais"));
        divAll.setPreferredSize(new java.awt.Dimension(733, 500));

        divOne.setPreferredSize(new java.awt.Dimension(733, 500));

        cNome.setForeground(new java.awt.Color(0, 102, 255));
        cNome.setMinimumSize(new java.awt.Dimension(8, 20));

        lNome.setText("Nome Completo:");

        cRg.setForeground(new java.awt.Color(0, 102, 255));

        lRg.setText("RG:");

        lCpf.setText("CPF:");

        cCpf.setForeground(new java.awt.Color(0, 102, 255));

        lEndereco.setText("Endereço:");

        lCep.setText("CEP:");

        lBairro.setText("Bairro:");

        lCidade.setText("Cidade:");

        lUf.setText("UF:");

        cUf.setForeground(new java.awt.Color(0, 102, 255));
        cUf.setMaximumRowCount(5);
        cUf.setAutoscrolls(true);

        cCidade.setForeground(new java.awt.Color(0, 102, 255));
        cCidade.setMinimumSize(new java.awt.Dimension(8, 20));

        cBairro.setForeground(new java.awt.Color(0, 102, 255));
        cBairro.setMinimumSize(new java.awt.Dimension(8, 20));

        cCep.setForeground(new java.awt.Color(0, 102, 255));

        cLogradouro.setForeground(new java.awt.Color(0, 102, 255));
        cLogradouro.setMinimumSize(new java.awt.Dimension(8, 20));

        cObserva.setColumns(20);
        cObserva.setForeground(new java.awt.Color(0, 102, 255));
        cObserva.setRows(5);
        jScrollPane1.setViewportView(cObserva);

        lObservacoes.setText("Outras Informações: ");

        lTelefone.setText("Telefone:");

        lEmail.setText("Email:");

        cEmail.setForeground(new java.awt.Color(0, 102, 255));

        lNumero.setText("Número:");

        lData.setText("Data Nascimento:");

        cTelefone.setForeground(new java.awt.Color(0, 102, 255));

        cNumero.setForeground(new java.awt.Color(0, 102, 255));

        bProcurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/search.png"))); // NOI18N
        bProcurar.setText("Procurar");
        bProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bProcurarActionPerformed(evt);
            }
        });

        cDataNascimento.setForeground(new java.awt.Color(0, 102, 255));
        cDataNascimento.setSelectedItem(null);

        lFoto.setText("Foto:");

        cImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/empty.gif"))); // NOI18N

        javax.swing.GroupLayout divOneLayout = new javax.swing.GroupLayout(divOne);
        divOne.setLayout(divOneLayout);
        divOneLayout.setHorizontalGroup(
            divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divOneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lObservacoes)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(divOneLayout.createSequentialGroup()
                            .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                                .addComponent(cNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lNome, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lEmail, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(divOneLayout.createSequentialGroup()
                                    .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(cNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lNumero))
                                    .addGap(27, 27, 27)
                                    .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lCep)
                                        .addComponent(cCep, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(divOneLayout.createSequentialGroup()
                                    .addGap(29, 29, 29)
                                    .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(divOneLayout.createSequentialGroup()
                                            .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lData)
                                                .addComponent(cRg, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lRg, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(27, 27, 27)
                                            .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(divOneLayout.createSequentialGroup()
                                                    .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGap(119, 119, 119))
                                                .addComponent(cCpf)))
                                        .addGroup(divOneLayout.createSequentialGroup()
                                            .addComponent(cDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divOneLayout.createSequentialGroup()
                                    .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lBairro))
                                    .addGap(175, 175, 175)
                                    .addComponent(lUf)
                                    .addGap(36, 36, 36))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divOneLayout.createSequentialGroup()
                            .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divOneLayout.createSequentialGroup()
                                    .addComponent(cLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(373, 373, 373))
                                .addGroup(divOneLayout.createSequentialGroup()
                                    .addComponent(lEndereco)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lCidade)
                                .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(divOneLayout.createSequentialGroup()
                                        .addComponent(cCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(cUf, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(cTelefone))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divOneLayout.createSequentialGroup()
                            .addComponent(cImg, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(61, 61, 61))
                        .addGroup(divOneLayout.createSequentialGroup()
                            .addComponent(lFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap()))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divOneLayout.createSequentialGroup()
                        .addComponent(bProcurar)
                        .addGap(143, 143, 143))))
        );
        divOneLayout.setVerticalGroup(
            divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divOneLayout.createSequentialGroup()
                .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(divOneLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lRg)
                            .addComponent(lNome, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(divOneLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lCpf)
                            .addComponent(lFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(divOneLayout.createSequentialGroup()
                        .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lEmail)
                            .addComponent(lData)
                            .addComponent(lTelefone))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lEndereco)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lCep)
                                .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lBairro)
                                    .addComponent(lUf)
                                    .addComponent(lCidade)
                                    .addComponent(lNumero))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(divOneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cUf, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lObservacoes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                    .addGroup(divOneLayout.createSequentialGroup()
                        .addComponent(cImg, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(bProcurar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout divAllLayout = new javax.swing.GroupLayout(divAll);
        divAll.setLayout(divAllLayout);
        divAllLayout.setHorizontalGroup(
            divAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divAllLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(divOne, javax.swing.GroupLayout.PREFERRED_SIZE, 1127, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        divAllLayout.setVerticalGroup(
            divAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divAllLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(divOne, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout tabDadosGeraisLayout = new javax.swing.GroupLayout(tabDadosGerais);
        tabDadosGerais.setLayout(tabDadosGeraisLayout);
        tabDadosGeraisLayout.setHorizontalGroup(
            tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                        .addComponent(divAll, javax.swing.GroupLayout.PREFERRED_SIZE, 1149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(13, Short.MAX_VALUE))
                    .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                        .addComponent(divMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(449, 449, 449)
                        .addComponent(bGravar)
                        .addGap(38, 38, 38)
                        .addComponent(bCancelar)
                        .addGap(29, 29, 29))))
        );
        tabDadosGeraisLayout.setVerticalGroup(
            tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(divMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabDadosGeraisLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(tabDadosGeraisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bGravar)
                            .addComponent(bCancelar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(divAll, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        tabs.addTab("Dados Gerais", tabDadosGerais);

        divAll1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados da Formação"));
        divAll1.setMaximumSize(new java.awt.Dimension(32767, 500));
        divAll1.setPreferredSize(new java.awt.Dimension(733, 500));

        divOne1.setPreferredSize(new java.awt.Dimension(100, 393));
        divOne1.setRequestFocusEnabled(false);

        cNomeFormacao.setForeground(new java.awt.Color(0, 102, 255));
        cNomeFormacao.setMinimumSize(new java.awt.Dimension(8, 20));

        lNomeFormacao.setText("Nome da Formação:");

        cInstituicao.setForeground(new java.awt.Color(0, 102, 255));
        cInstituicao.setMinimumSize(new java.awt.Dimension(8, 20));

        lInstituicao.setText("Instituição:");

        lDataFim.setText("Data Fim:");

        lDataInicial.setText("Data Inicial:");

        jLabel1.setText("Formações Cadastradas:");

        cTabela.setModel(new FormacaoDataModel(prof.getFormacoesAcademicas()));
        cTabela.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        cTabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        cTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cTabelaMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(cTabela);

        bExcluirFormaSele.setText("Excluir");
        bExcluirFormaSele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExcluirFormaSeleActionPerformed(evt);
            }
        });

        cDataInicial.setForeground(new java.awt.Color(0, 102, 255));
        cDataInicial.setSelectedItem(null);

        cDataFim.setForeground(new java.awt.Color(0, 102, 255));
        cDataFim.setSelectedItem(null);

        javax.swing.GroupLayout divOne1Layout = new javax.swing.GroupLayout(divOne1);
        divOne1.setLayout(divOne1Layout);
        divOne1Layout.setHorizontalGroup(
            divOne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divOne1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(divOne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
                    .addGroup(divOne1Layout.createSequentialGroup()
                        .addGroup(divOne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(divOne1Layout.createSequentialGroup()
                                .addGroup(divOne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lDataInicial))
                                .addGap(18, 18, 18)
                                .addGroup(divOne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lDataFim)
                                    .addGroup(divOne1Layout.createSequentialGroup()
                                        .addComponent(cDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(bExcluirFormaSele))))
                            .addComponent(lNomeFormacao)
                            .addComponent(lInstituicao)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(divOne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cInstituicao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                                .addComponent(cNomeFormacao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        divOne1Layout.setVerticalGroup(
            divOne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divOne1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lNomeFormacao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cNomeFormacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lInstituicao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cInstituicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(divOne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(divOne1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cDataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bExcluirFormaSele))
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout divAll1Layout = new javax.swing.GroupLayout(divAll1);
        divAll1.setLayout(divAll1Layout);
        divAll1Layout.setHorizontalGroup(
            divAll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divAll1Layout.createSequentialGroup()
                .addComponent(divOne1, javax.swing.GroupLayout.PREFERRED_SIZE, 1068, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        divAll1Layout.setVerticalGroup(
            divAll1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divAll1Layout.createSequentialGroup()
                .addComponent(divOne1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        bExcluirForm.setLabel("Excluir");
        bExcluirForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExcluirFormActionPerformed(evt);
            }
        });

        lMsg1.setForeground(new java.awt.Color(255, 0, 0));
        lMsg1.setPreferredSize(new java.awt.Dimension(25, 100));

        bEditarForm.setText("Editar");
        bEditarForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditarFormActionPerformed(evt);
            }
        });

        bCancelarForm.setLabel("Cancelar");
        bCancelarForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarFormActionPerformed(evt);
            }
        });

        bGravarForm.setLabel("Gravar");
        bGravarForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGravarFormActionPerformed(evt);
            }
        });

        bIncluirForm.setText("Incluir");
        bIncluirForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIncluirFormActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout divMenu1Layout = new javax.swing.GroupLayout(divMenu1);
        divMenu1.setLayout(divMenu1Layout);
        divMenu1Layout.setHorizontalGroup(
            divMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divMenu1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(divMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(divMenu1Layout.createSequentialGroup()
                        .addComponent(lMsg1, javax.swing.GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
                        .addGap(425, 425, 425))
                    .addGroup(divMenu1Layout.createSequentialGroup()
                        .addComponent(bIncluirForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bEditarForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bExcluirForm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bGravarForm)
                        .addGap(24, 24, 24)
                        .addComponent(bCancelarForm, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67))))
        );
        divMenu1Layout.setVerticalGroup(
            divMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divMenu1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lMsg1, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(divMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(divMenu1Layout.createSequentialGroup()
                        .addGap(0, 10, Short.MAX_VALUE)
                        .addGroup(divMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(bExcluirForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bEditarForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bIncluirForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(divMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bCancelarForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bGravarForm)))
                .addContainerGap())
        );

        javax.swing.GroupLayout tabFormacaoAcademicaLayout = new javax.swing.GroupLayout(tabFormacaoAcademica);
        tabFormacaoAcademica.setLayout(tabFormacaoAcademicaLayout);
        tabFormacaoAcademicaLayout.setHorizontalGroup(
            tabFormacaoAcademicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabFormacaoAcademicaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabFormacaoAcademicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(divMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tabFormacaoAcademicaLayout.createSequentialGroup()
                        .addComponent(divAll1, javax.swing.GroupLayout.PREFERRED_SIZE, 1092, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 60, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tabFormacaoAcademicaLayout.setVerticalGroup(
            tabFormacaoAcademicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabFormacaoAcademicaLayout.createSequentialGroup()
                .addComponent(divMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(divAll1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        divAll1.getAccessibleContext().setAccessibleName("Formação Acadêmica");

        tabs.addTab("Formação Acadêmica", tabFormacaoAcademica);

        divAll2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Dados da Experiência Profissional"));
        divAll2.setMaximumSize(new java.awt.Dimension(32767, 500));
        divAll2.setPreferredSize(new java.awt.Dimension(733, 500));

        divOne2.setPreferredSize(new java.awt.Dimension(1039, 500));

        cEmpresa.setForeground(new java.awt.Color(0, 102, 255));
        cEmpresa.setMinimumSize(new java.awt.Dimension(8, 20));

        lEmpresa.setText("Empresa:");

        cFuncao.setForeground(new java.awt.Color(0, 102, 255));
        cFuncao.setMinimumSize(new java.awt.Dimension(8, 20));

        lFuncao.setText("Função:");

        lDataFimExp.setText("Data Fim:");

        lDataInicialExp.setText("Data Inicial:");

        cTabelaExp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cTabelaExp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        cTabelaExp.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        cTabelaExp.setSurrendersFocusOnKeystroke(true);
        cTabelaExp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cTabelaExpMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(cTabelaExp);

        jLabel2.setText("Experiências Profissionais Cadastradas:");

        bExcluirExpSele.setText("Excluir");
        bExcluirExpSele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExcluirExpSeleActionPerformed(evt);
            }
        });

        cDataInicialExp.setForeground(new java.awt.Color(0, 102, 255));

        cDataFImExp.setForeground(new java.awt.Color(0, 102, 255));

        javax.swing.GroupLayout divOne2Layout = new javax.swing.GroupLayout(divOne2);
        divOne2.setLayout(divOne2Layout);
        divOne2Layout.setHorizontalGroup(
            divOne2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divOne2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(divOne2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)
                    .addGroup(divOne2Layout.createSequentialGroup()
                        .addGroup(divOne2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cFuncao, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                            .addComponent(cEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lEmpresa)
                            .addComponent(lFuncao)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(divOne2Layout.createSequentialGroup()
                                .addGroup(divOne2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lDataInicialExp)
                                    .addComponent(cDataInicialExp, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addGroup(divOne2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lDataFimExp)
                                    .addGroup(divOne2Layout.createSequentialGroup()
                                        .addComponent(cDataFImExp, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(bExcluirExpSele)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        divOne2Layout.setVerticalGroup(
            divOne2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divOne2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(divOne2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lDataInicialExp, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lDataFimExp, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(divOne2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bExcluirExpSele)
                    .addComponent(cDataInicialExp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cDataFImExp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(186, 186, 186))
        );

        javax.swing.GroupLayout divAll2Layout = new javax.swing.GroupLayout(divAll2);
        divAll2.setLayout(divAll2Layout);
        divAll2Layout.setHorizontalGroup(
            divAll2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divAll2Layout.createSequentialGroup()
                .addComponent(divOne2, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
                .addContainerGap())
        );
        divAll2Layout.setVerticalGroup(
            divAll2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divAll2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(divOne2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        bExcluirExp.setLabel("Excluir");
        bExcluirExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExcluirExpActionPerformed(evt);
            }
        });

        lMsg2.setForeground(new java.awt.Color(255, 0, 0));
        lMsg2.setPreferredSize(new java.awt.Dimension(25, 100));

        bEditarExp.setText("Editar");
        bEditarExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditarExpActionPerformed(evt);
            }
        });

        bCancelarExp.setLabel("Cancelar");
        bCancelarExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarExpActionPerformed(evt);
            }
        });

        bGravarExp.setLabel("Gravar");
        bGravarExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGravarExpActionPerformed(evt);
            }
        });

        bIncluirExp.setText("Incluir");
        bIncluirExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bIncluirExpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout divMenu2Layout = new javax.swing.GroupLayout(divMenu2);
        divMenu2.setLayout(divMenu2Layout);
        divMenu2Layout.setHorizontalGroup(
            divMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divMenu2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(divMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lMsg2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(divMenu2Layout.createSequentialGroup()
                        .addComponent(bIncluirExp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bEditarExp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bExcluirExp)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bGravarExp)
                .addGap(18, 18, 18)
                .addComponent(bCancelarExp)
                .addGap(44, 44, 44))
        );
        divMenu2Layout.setVerticalGroup(
            divMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divMenu2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lMsg2, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(divMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bExcluirExp)
                    .addComponent(bEditarExp)
                    .addComponent(bIncluirExp))
                .addContainerGap())
            .addGroup(divMenu2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(divMenu2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bCancelarExp)
                    .addComponent(bGravarExp))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabExperienciaProLayout = new javax.swing.GroupLayout(tabExperienciaPro);
        tabExperienciaPro.setLayout(tabExperienciaProLayout);
        tabExperienciaProLayout.setHorizontalGroup(
            tabExperienciaProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabExperienciaProLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabExperienciaProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(divMenu2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tabExperienciaProLayout.createSequentialGroup()
                        .addComponent(divAll2, javax.swing.GroupLayout.PREFERRED_SIZE, 981, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 171, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tabExperienciaProLayout.setVerticalGroup(
            tabExperienciaProLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabExperienciaProLayout.createSequentialGroup()
                .addComponent(divMenu2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(divAll2, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        tabs.addTab("Experiência Profissional", tabExperienciaPro);

        divAll3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Disciplinas aptas a serem lecionadas"));
        divAll3.setMaximumSize(new java.awt.Dimension(32767, 500));
        divAll3.setPreferredSize(new java.awt.Dimension(733, 500));

        bPassarAll.setText(">>");
        bPassarAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPassarAllActionPerformed(evt);
            }
        });

        bPassarOne.setText(">");
        bPassarOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPassarOneActionPerformed(evt);
            }
        });

        bVoltarOne.setText("<");
        bVoltarOne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVoltarOneActionPerformed(evt);
            }
        });

        bVoltarAll.setText("<<");
        bVoltarAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bVoltarAllActionPerformed(evt);
            }
        });

        cListaSele.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        cListaSele.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(cListaSele);

        cListaAll.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        cListaAll.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(cListaAll);

        javax.swing.GroupLayout divAll3Layout = new javax.swing.GroupLayout(divAll3);
        divAll3.setLayout(divAll3Layout);
        divAll3Layout.setHorizontalGroup(
            divAll3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divAll3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(divAll3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bPassarOne, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPassarAll, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bVoltarOne, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bVoltarAll, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );
        divAll3Layout.setVerticalGroup(
            divAll3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(divAll3Layout.createSequentialGroup()
                .addGroup(divAll3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(divAll3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(divAll3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                            .addComponent(jScrollPane6)))
                    .addGroup(divAll3Layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(bPassarAll)
                        .addGap(18, 18, 18)
                        .addComponent(bPassarOne)
                        .addGap(18, 18, 18)
                        .addComponent(bVoltarOne)
                        .addGap(18, 18, 18)
                        .addComponent(bVoltarAll)))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        bGravarDisci.setLabel("Gravar");
        bGravarDisci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGravarDisciActionPerformed(evt);
            }
        });

        bCancelarDisci.setLabel("Cancelar");
        bCancelarDisci.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarDisciActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout divMenu3Layout = new javax.swing.GroupLayout(divMenu3);
        divMenu3.setLayout(divMenu3Layout);
        divMenu3Layout.setHorizontalGroup(
            divMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divMenu3Layout.createSequentialGroup()
                .addContainerGap(929, Short.MAX_VALUE)
                .addComponent(bGravarDisci)
                .addGap(18, 18, 18)
                .addComponent(bCancelarDisci)
                .addContainerGap())
        );
        divMenu3Layout.setVerticalGroup(
            divMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, divMenu3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(divMenu3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bCancelarDisci, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bGravarDisci))
                .addContainerGap())
        );

        javax.swing.GroupLayout tabDisciplinasLayout = new javax.swing.GroupLayout(tabDisciplinas);
        tabDisciplinas.setLayout(tabDisciplinasLayout);
        tabDisciplinasLayout.setHorizontalGroup(
            tabDisciplinasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabDisciplinasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(divMenu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(tabDisciplinasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(divAll3, javax.swing.GroupLayout.PREFERRED_SIZE, 1079, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );
        tabDisciplinasLayout.setVerticalGroup(
            tabDisciplinasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDisciplinasLayout.createSequentialGroup()
                .addComponent(divMenu3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(divAll3, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        tabs.addTab("Disciplinas", tabDisciplinas);

        javax.swing.GroupLayout CadastroLayout = new javax.swing.GroupLayout(Cadastro);
        Cadastro.setLayout(CadastroLayout);
        CadastroLayout.setHorizontalGroup(
            CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastroLayout.createSequentialGroup()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 1177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 389, Short.MAX_VALUE))
        );
        CadastroLayout.setVerticalGroup(
            CadastroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CadastroLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jScrollPane9.setViewportView(Cadastro);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1578, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 1311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(257, 257, 257)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tabPrincipal.addTab("Cadastro", jPanel3);

        jPanel1.setPreferredSize(new java.awt.Dimension(1566, 500));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1270, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 658, Short.MAX_VALUE)
        );

        tabPrincipal.addTab("Quadro de Horários", jPanel1);

        jPanel2.setPreferredSize(new java.awt.Dimension(1566, 500));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1270, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 658, Short.MAX_VALUE)
        );

        tabPrincipal.addTab("Reservas", jPanel2);

        javax.swing.GroupLayout MenuLayout = new javax.swing.GroupLayout(Menu);
        Menu.setLayout(MenuLayout);
        MenuLayout.setHorizontalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(MenuLayout.createSequentialGroup()
                        .addComponent(tabPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15))
                    .addComponent(bLogout))
                .addGap(495, 495, 495))
        );
        MenuLayout.setVerticalGroup(
            MenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bLogout)
                .addGap(13, 13, 13)
                .addComponent(tabPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabPrincipal.getAccessibleContext().setAccessibleName("Cadastro");

        Consultar.setText("Consultar");
        Consultar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ConsultarMousePressed(evt);
            }
        });
        MenuBar.add(Consultar);

        GerarCurriculo.setText("Gerar Currículo");
        GerarCurriculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                GerarCurriculoMousePressed(evt);
            }
        });
        MenuBar.add(GerarCurriculo);

        Importar.setText("Importar Dados RH");
        Importar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ImportarMousePressed(evt);
            }
        });
        MenuBar.add(Importar);

        Situ.setText("Ativar/Inativar Professor");
        Situ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SituMousePressed(evt);
            }
        });
        MenuBar.add(Situ);

        Promover.setText("Promover Professor");
        Promover.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PromoverMousePressed(evt);
            }
        });
        MenuBar.add(Promover);

        ExcluirPro.setText("Excluir Professor");
        ExcluirPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ExcluirProMousePressed(evt);
            }
        });
        MenuBar.add(ExcluirPro);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 1347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ConsultarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ConsultarMousePressed
        if (!dialogConsulta.isVisible()) {
            dialogConsulta.setLocationRelativeTo(null);
            dialogConsulta.setVisible(true);
            cPesquisa.setText("");
           cPesquisa.setEditable(true);
            ProfessorDataModel model;
            model = new ProfessorDataModel(new ArrayList<Professor>());
            cTabelaConsulta.setModel(model);
        }
    }//GEN-LAST:event_ConsultarMousePressed

    private void GerarCurriculoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GerarCurriculoMousePressed
        if (!dialogoCurriculo.isVisible()) {
            editor.setText("");
            profSele = null;
            ProfessorComboModel model = new ProfessorComboModel(listarTodos());
            System.out.println(listarTodos().size());
            cSeleProfCurricu.setModel(model);
            dialogoCurriculo.setLocationRelativeTo(null);
            dialogoCurriculo.setVisible(true);
        }
    }//GEN-LAST:event_GerarCurriculoMousePressed

    private void bGerarCurriculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGerarCurriculoActionPerformed
        if (cSeleProfCurricu.getSelectedItem() != null) {
            profSele = (Professor) cSeleProfCurricu.getModel().getSelectedItem();
            profSele = CtrProfessor.getProfessor(profSele.getId());
            profSele.setDisciplinasPreferenciais(CtrDisciplina.getDisciplinas(profSele.getId()));
            profSele.setExperienciasProfissionais(CtrExperienciaProfissional.getExperiencias(profSele.getId()));
            profSele.setFormacoesAcademicas(CtrFormacaoAcademica.getFormacoes(profSele.getId()));
            HTMLEditorKit kit = new HTMLEditorKit();
            editor.setEditorKit(kit);
            String html = FormatProfessorToHtml.format(profSele);
            if (html.length() != 0) {
                kit.getStyleSheet().addRule("h2.h2{background-color: #4daec1; width: 450px;}");
                kit.getStyleSheet().addRule("h2.noinfo{font-color: red;}");
                Document doc = kit.createDefaultDocument();
                editor.setDocument(doc);
                editor.setText(html);
                gerarCurriculo = true;
            }

        }
    }//GEN-LAST:event_bGerarCurriculoActionPerformed

    private void bSalvarPDFMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSalvarPDFMousePressed
        if (profSele != null && gerarCurriculo) {
            if (CtrCoordenador.generatePDFFromHtml(profSele.getNome(), gerarStringHtml())) {
                JOptionPane.showMessageDialog(dialogoCurriculo, MessageUtil.msgCurriculoCriadoSucesso(profSele.getNome()));
                gerarCurriculo = false;
            }
            dialogoProgressBar.setVisible(false);
            progress = 0;
        }
    }//GEN-LAST:event_bSalvarPDFMousePressed

    private void cSeleProfCurricuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cSeleProfCurricuItemStateChanged
        gerarCurriculo = false;
        editor.setText("");
    }//GEN-LAST:event_cSeleProfCurricuItemStateChanged

    private void PromoverMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PromoverMousePressed
        if (!dialogoAcao.isVisible()) {
            profSele = null;
            isPromover = true;
            isExcluir = false;
            dialogoAcao.setLocationRelativeTo(null);
            ProfessorComboModel model = new ProfessorComboModel(listarApenasProfessores());
            cListaProfessores.setModel(model);
            dialogoAcao.setVisible(true);
            dialogoAcao.setTitle("Promover Professor");

        }
    }//GEN-LAST:event_PromoverMousePressed

    private void bAcaoConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAcaoConfirmarActionPerformed
        profSele = (Professor) cListaProfessores.getModel().getSelectedItem();
        if (profSele != null) {
            if (isPromover) {
                if (JOptionPane.showConfirmDialog(dialogoAcao, MessageUtil.msgAcao(profSele.getNome(), "promover")) == 0) {
                    if (CtrDiretor.promoverProfessor(profSele)) {
                        JOptionPane.showMessageDialog(dialogoAcao, MessageUtil.msgSucesso(profSele.getNome(), "promovido"));
                        dialogoAcao.setVisible(false);
                    }
                }
            }
            if (isExcluir) {
                profSele = CtrProfessor.getProfessor(profSele.getId());
                if (JOptionPane.showConfirmDialog(dialogoAcao, MessageUtil.msgAcao(profSele.getNome(), "excluir")) == 0) {
                    if (CtrDiretor.excluirProfessor(profSele)) {
                        JOptionPane.showMessageDialog(dialogoAcao, MessageUtil.msgSucesso(profSele.getNome(), "excluído"));
                    }
                    dialogoAcao.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_bAcaoConfirmarActionPerformed
    private void ExcluirProMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExcluirProMousePressed
        if (!dialogoAcao.isVisible()) {
            profSele = null;
            isPromover = false;
            isExcluir = true;
            ProfessorComboModel model = new ProfessorComboModel(listarApenasProfessores());
            cListaProfessores.setModel(model);
            dialogoAcao.setVisible(true);
            dialogoAcao.setLocationRelativeTo(null);
            dialogoAcao.setTitle("Excluir Professor");
        }
    }//GEN-LAST:event_ExcluirProMousePressed

    private void ImportarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImportarMousePressed
        if (!dialogoImportar.isVisible()) {
            cLoginImportar.setText("");
            cSenhaImportar.setText("");
            cEnderecoWeb.setText("");
            dialogoImportar.setVisible(true);
        }
    }//GEN-LAST:event_ImportarMousePressed

    private void bImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bImportarActionPerformed
        if (cLoginImportar.getText().equals("admin") && new String(cSenhaImportar.getPassword()).equals("admin")) {
            if (CtrDiretor.importarDados()) {
                JOptionPane.showMessageDialog(cLoginImportar, MessageUtil.msgSucessoMult("importados"));
            } else {
                JOptionPane.showMessageDialog(cLoginImportar, "Registros já foram importados");
            }
        }
    }//GEN-LAST:event_bImportarActionPerformed

    private void SituMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SituMousePressed
        if (!dialogoStatus.isVisible()) {
            dialogoStatus.setLocationRelativeTo(null);
            profSele = null;
            ProfessorComboModel model = new ProfessorComboModel(listarApenasProfessores());
            cListaInativar.setModel(model);
            dialogoStatus.setVisible(true);
            dialogoStatus.setResizable(false);
            rAtivo.setSelected(false);
            rInativo.setSelected(false);
            bConfirmarInativacao.setEnabled(true);
        }
    }//GEN-LAST:event_SituMousePressed

    private void bConfirmarInativacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConfirmarInativacaoActionPerformed
        profSele = (Professor) cListaInativar.getModel().getSelectedItem();
        String msg = new String();
        String acao = new String();
        if (profSele != null) {
            if (profSele.getStatus().equals(SituProfessor.Ativo)) {
                profSele.setStatus(SituProfessor.Inativo);
                acao = "Inativar";
                msg = "inativado";
            } else {
                profSele.setStatus(SituProfessor.Ativo);
                acao = "Ativar";
                msg = "ativado";
            }

            if (JOptionPane.showConfirmDialog(dialogoStatus, MessageUtil.msgConfirmacao(profSele.getNome(), acao)) == 0) {
                if (CtrProfessor.alterarProfessor(profSele)) {
                    JOptionPane.showMessageDialog(dialogoStatus, MessageUtil.msgSucesso(profSele.getNome(), msg));
                }
            }
        }
    }//GEN-LAST:event_bConfirmarInativacaoActionPerformed

    private void cListaInativarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cListaInativarItemStateChanged
        profSele = (Professor) cListaInativar.getModel().getSelectedItem();
        if (profSele.getStatus().equals(SituProfessor.Ativo)) {
            rAtivo.setSelected(true);
        } else {
            rInativo.setSelected(true);
        }
    }//GEN-LAST:event_cListaInativarItemStateChanged

    private void bLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLogoutActionPerformed
        this.prof = new Professor();
        setVisible(false);
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_bLogoutActionPerformed

    private void tabsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabsStateChanged
        int i = tabs.getSelectedIndex();
        switch (i) {
            case 0:
                initDadosGerais();
                break;
            case 1:
                initFormacaoAcademica();
                break;
            case 2:
                initExpProfissional();
                break;
            case 3:
                initDisciplinas();
                break;
            default:
                return;
        }
    }//GEN-LAST:event_tabsStateChanged

    private void bGravarDisciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGravarDisciActionPerformed
        if (prof.getDisciplinasPreferenciais().size() > 0 || CtrDisciplina.getDisciplinas(prof.getId()).size() > 0) {
            List<DisciplinaPreferencial> listaExcluir = new ArrayList<DisciplinaPreferencial>();
            listaExcluir = CtrDisciplina.getDisciplinas(prof.getId());
            listaExcluir.removeAll(prof.getDisciplinasPreferenciais());
            if (CtrDisciplina.salvarDisciplinasPreferenciais(prof.getDisciplinasPreferenciais(), listaExcluir)) {
                JOptionPane.showMessageDialog(null, MessageUtil.msgSucessoMult("incluídos"));
            }
            initDisciplinas();
        }
    }//GEN-LAST:event_bGravarDisciActionPerformed

    private void bCancelarDisciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarDisciActionPerformed
        prof.setDisciplinasPreferenciais(CtrDisciplina.getDisciplinas(prof.getId()));
        initDisciplinas();
    }//GEN-LAST:event_bCancelarDisciActionPerformed

    private void bVoltarAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVoltarAllActionPerformed
        prof.getDisciplinasPreferenciais().clear();
        DisciplinaListModel dlm = new DisciplinaListModel(disciplinas);
        cListaAll.setModel(dlm);
        DisciplinaPreferencialListModel dplm = new DisciplinaPreferencialListModel(prof.getDisciplinasPreferenciais());
        cListaSele.setModel(dplm);
    }//GEN-LAST:event_bVoltarAllActionPerformed

    private void bVoltarOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bVoltarOneActionPerformed
        disciplinaPreferencial = new DisciplinaPreferencial();
        disciplinaPreferencial = (DisciplinaPreferencial) cListaSele.getSelectedValue();
        if (disciplinaPreferencial != null) {
            disciplina = disciplinaPreferencial.getDisciplina();
            prof.removeDisciplinaPreferencial(disciplinaPreferencial);
            disciplinas.add(disciplina);
            atualizarListsDisciplinas();
        }
    }//GEN-LAST:event_bVoltarOneActionPerformed

    private void bPassarOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPassarOneActionPerformed
        disciplina = new Disciplina();
        disciplina = (Disciplina) cListaAll.getSelectedValue();
        if (disciplina != null) {
            disciplinas.remove(disciplina);
            disciplinaPreferencial = new DisciplinaPreferencial();
            disciplinaPreferencial.setDisciplina(disciplina);
            prof.addDisciplinaPreferencial(disciplinaPreferencial);
            atualizarListsDisciplinas();
        }
    }//GEN-LAST:event_bPassarOneActionPerformed

    private void bPassarAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPassarAllActionPerformed
        for (Disciplina disci : disciplinas) {
            disciplinaPreferencial = new DisciplinaPreferencial();
            disciplinaPreferencial.setDisciplina(disci);
            prof.addDisciplinaPreferencial(disciplinaPreferencial);
        }

        disciplinas.clear();
        atualizarListsDisciplinas();
    }//GEN-LAST:event_bPassarAllActionPerformed

    private void bIncluirExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIncluirExpActionPerformed
        habilitarCamposExperiencia();
        disableBotoesExperiencia();
        cTabelaExp.setEnabled(false);
        experiencia = new ProfessionalExperience();
        experiencia.setProfessor(new Professor());
        experiencia.getProfessor().setId(prof.getId());
    }//GEN-LAST:event_bIncluirExpActionPerformed

    private void bGravarExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGravarExpActionPerformed
        if (isEdit) {
            if (validarCamposExperiencia()) {
                setarExperiencia();
                if (CtrExperienciaProfissional.verificarExperiencia(experiencia)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgJaCadastrada("Experiência Profissional"));
                } else if (CtrExperienciaProfissional.salvarOuAtualizarExperiencia(experiencia)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgSucesso(experiencia.getEmpresa(), "editado"));
                    initExpProfissional();
                }
            }
        } else {
            if (validarCamposExperiencia()) {
                setarExperiencia();;
                if (CtrExperienciaProfissional.verificarExperiencia(experiencia)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgJaCadastrada("Experiência Profissional"));
                } else if (CtrExperienciaProfissional.salvarOuAtualizarExperiencia(experiencia)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgSucesso(experiencia.getEmpresa(), "incluído"));
                    initExpProfissional();
                }
            }
        }
        cTabelaExp.setModel(new ExperienciaDataModel(CtrExperienciaProfissional.getExperiencias(prof.getId())));
    }//GEN-LAST:event_bGravarExpActionPerformed

    private void bCancelarExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarExpActionPerformed
        initExpProfissional();
        carregarExperiencias();
    }//GEN-LAST:event_bCancelarExpActionPerformed

    private void bEditarExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditarExpActionPerformed
        isEdit = true;
        bExcluirExpSele.setVisible(false);
        habilitarCamposExperiencia();
        disableBotoesExperiencia();
        cTabelaExp.setEnabled(true);
    }//GEN-LAST:event_bEditarExpActionPerformed

    private void bExcluirExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcluirExpActionPerformed
        isEdit = true;
        disableBotoesExperiencia();
        cTabelaExp.setEnabled(true);
        bExcluirExpSele.setVisible(true);
        bCancelarExp.setVisible(true);
        experiencia = null;
    }//GEN-LAST:event_bExcluirExpActionPerformed

    private void bExcluirExpSeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcluirExpSeleActionPerformed
        if (experiencia != null) {
            if (JOptionPane.showConfirmDialog(null, MessageUtil.msgConfirmacao(experiencia.getEmpresa(), "excluir")) == 0) {
                if (CtrExperienciaProfissional.excluir(experiencia)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgSucesso(experiencia.getEmpresa(), "excluido"));
                    CtrProfessor.alterarProfessor(prof);
                    limparCamposExperiencia();
                    carregarExperiencias();
                }
            }
        }
    }//GEN-LAST:event_bExcluirExpSeleActionPerformed

    private void cTabelaExpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cTabelaExpMousePressed
        if (isEdit) {
            experiencia = getExperienciaModel().getExperiencia(cTabelaExp.getSelectedRow());
            preencherCamposExperiencia();
        }
    }//GEN-LAST:event_cTabelaExpMousePressed

    private void bIncluirFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bIncluirFormActionPerformed
        habilitarCamposFormacao();
        disableBotoesFormacao();
        cTabela.setEnabled(false);
        formacao = new AcademicFormation();
        formacao.setProfessor(new Professor());
        formacao.getProfessor().setId(prof.getId());
    }//GEN-LAST:event_bIncluirFormActionPerformed

    private void bGravarFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGravarFormActionPerformed
        if (isEdit) {
            if (validarCamposFormacao()) {
                setarFormacao();
                if (CtrFormacaoAcademica.verificarFormacao(formacao)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgJaCadastrada("Formação"));
                } else if (CtrFormacaoAcademica.salvarOuAtualizarFormacao(formacao)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgSucesso(formacao.getNomeCurso(), "editado"));
                    initFormacaoAcademica();
                }
            }
        } else {
            if (validarCamposFormacao()) {
                setarFormacao();
                if (CtrFormacaoAcademica.verificarFormacao(formacao)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgJaCadastrada("Formação"));
                } else if (CtrFormacaoAcademica.salvarOuAtualizarFormacao(formacao)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgSucesso(formacao.getNomeCurso(), "incluído"));
                    initFormacaoAcademica();
                }
            }
        }
        cTabela.setModel(new FormacaoDataModel(CtrFormacaoAcademica.getFormacoes(prof.getId())));
    }//GEN-LAST:event_bGravarFormActionPerformed

    private void bCancelarFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarFormActionPerformed
        initFormacaoAcademica();
        carregarFormacoes();
    }//GEN-LAST:event_bCancelarFormActionPerformed

    private void bEditarFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditarFormActionPerformed
        isEdit = true;
        bExcluirFormaSele.setVisible(false);
        habilitarCamposFormacao();
        disableBotoesFormacao();
        cTabela.setEnabled(true);
    }//GEN-LAST:event_bEditarFormActionPerformed

    private void bExcluirFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcluirFormActionPerformed
        isEdit = true;
        disableBotoesFormacao();
        cTabela.setEnabled(true);
        bExcluirFormaSele.setVisible(true);
        bCancelarForm.setVisible(true);
        formacao = null;
    }//GEN-LAST:event_bExcluirFormActionPerformed

    private void bExcluirFormaSeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExcluirFormaSeleActionPerformed
        if (formacao != null) {
            if (JOptionPane.showConfirmDialog(null, MessageUtil.msgConfirmacao(formacao.getNomeCurso(), "excluir")) == 0) {
                if (CtrFormacaoAcademica.excluir(formacao)) {
                    JOptionPane.showMessageDialog(null, MessageUtil.msgSucesso(formacao.getNomeCurso(), "excluido"));
                    carregarFormacoes();
                    limparCamposFormacao();
                    formacao = new AcademicFormation();
                }
            }
        }
    }//GEN-LAST:event_bExcluirFormaSeleActionPerformed

    private void cTabelaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cTabelaMousePressed
        if (isEdit) {
            formacao = getFormacaoModel().getFormacao(cTabela.getSelectedRow());
            preencherCamposFormacao();
        }
    }//GEN-LAST:event_cTabelaMousePressed

    private void bProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bProcurarActionPerformed
        JFileChooser arquivo = new JFileChooser("C:\\Users\\Nathalia\\Desktop\\Sistema Acad\\src\\view\\images");
        String pathImg;
        int retorno = arquivo.showOpenDialog(null);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            pathImg = arquivo.getSelectedFile().getAbsolutePath();
            if (pathImg.endsWith(".jpg") || pathImg.endsWith(".JPG")
                    || pathImg.endsWith(".PNG") || pathImg.endsWith(".png")
                    || pathImg.endsWith(".gif") || pathImg.endsWith(".GIF")) {
                ImageIcon imagem = new ImageIcon(pathImg);
                if (imagem.getIconHeight() <= 200 && imagem.getIconWidth() <= 200) {
                    this.cImg.setIcon(new ImageIcon(pathImg));
                    makeFoto(pathImg);
                } else {
                    JOptionPane.showMessageDialog(null, "Tamanho máximo permitido: 200 x 200 px");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Extensão inválida");
            }
        }
    }//GEN-LAST:event_bProcurarActionPerformed

    private void bEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditarActionPerformed
        habilitarCampos();
    }//GEN-LAST:event_bEditarActionPerformed

    private void bGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bGravarActionPerformed
        if (validarCamposDados()) {
            setarDados();
            if (CtrProfessor.alterarProfessor(prof)) {
                JOptionPane.showMessageDialog(null, MessageUtil.msgSucesso(prof.getNome(), "editado"));
                if (prof instanceof Professor) {
                    rendCamposDados();
                }

            }
        }
    }//GEN-LAST:event_bGravarActionPerformed

    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        rendCamposDados();
        resetColorsDados();

        //        prof = CtrProfessor.getProfessor(prof.getId());
        carregarDadosProfessor();
    }//GEN-LAST:event_bCancelarActionPerformed

    private void bConsultaPesqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConsultaPesqActionPerformed
        if (!(getTipoConsulta() == null)) {
            if (getTipoConsulta().toString().equals("Disciplina")) {
                DisciplinaDataModel model;
                model = new DisciplinaDataModel(filtroDisciplinas(getPesquisa()));
                cTabelaConsulta.setModel(model);
                cPesquisa.setText("");
            } else if (getTipoConsulta().equals("Professor")) {
                ProfessorDataModel model;
                model = new ProfessorDataModel(filtroProfessor(getPesquisa()));
                cTabelaConsulta.setModel(model);
                cPesquisa.setText("");
            }
        }
    }//GEN-LAST:event_bConsultaPesqActionPerformed

    private void cTipoPesquisaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cTipoPesquisaItemStateChanged
        if (!(getTipoConsulta() == null)) {
            if (getTipoConsulta().toString().equals("Disciplina")) {
                DisciplinaDataModel model;
                model = new DisciplinaDataModel(new ArrayList<Disciplina>());
                cTabelaConsulta.setModel(model);
            } else if (getTipoConsulta().equals("Professor")) {
                ProfessorDataModel model;
                model = new ProfessorDataModel(new ArrayList<Professor>());
                cTabelaConsulta.setModel(model);
            }
        }
    }//GEN-LAST:event_cTipoPesquisaItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new SyntheticaAluOxideLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Cadastro;
    private javax.swing.JMenu Consultar;
    private javax.swing.JMenu ExcluirPro;
    private javax.swing.JMenu GerarCurriculo;
    private javax.swing.JMenu Importar;
    private javax.swing.JPanel Menu;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu Promover;
    private javax.swing.JMenu Situ;
    private javax.swing.JButton bAcaoConfirmar;
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bCancelarDisci;
    private javax.swing.JButton bCancelarExp;
    private javax.swing.JButton bCancelarForm;
    private javax.swing.JButton bConfirmarInativacao;
    private javax.swing.JButton bConsultaPesq;
    private javax.swing.JButton bEditar;
    private javax.swing.JButton bEditarExp;
    private javax.swing.JButton bEditarForm;
    private javax.swing.JButton bExcluirExp;
    private javax.swing.JButton bExcluirExpSele;
    private javax.swing.JButton bExcluirForm;
    private javax.swing.JButton bExcluirFormaSele;
    private javax.swing.JButton bGerarCurriculo;
    private javax.swing.JButton bGravar;
    private javax.swing.JButton bGravarDisci;
    private javax.swing.JButton bGravarExp;
    private javax.swing.JButton bGravarForm;
    private javax.swing.JButton bImportar;
    private javax.swing.JButton bIncluirExp;
    private javax.swing.JButton bIncluirForm;
    private javax.swing.JButton bLogout;
    private javax.swing.JButton bPassarAll;
    private javax.swing.JButton bPassarOne;
    private javax.swing.JButton bProcurar;
    private javax.swing.JMenu bSalvarPDF;
    private javax.swing.JButton bVoltarAll;
    private javax.swing.JButton bVoltarOne;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cBairro;
    private javax.swing.JFormattedTextField cCep;
    private javax.swing.JTextField cCidade;
    private javax.swing.JFormattedTextField cCpf;
    private de.javasoft.swing.DateComboBox cDataFImExp;
    private de.javasoft.swing.DateComboBox cDataFim;
    private de.javasoft.swing.DateComboBox cDataInicial;
    private de.javasoft.swing.DateComboBox cDataInicialExp;
    private de.javasoft.swing.DateComboBox cDataNascimento;
    private javax.swing.JTextField cEmail;
    private javax.swing.JTextField cEmpresa;
    private javax.swing.JTextField cEnderecoWeb;
    private javax.swing.JTextField cFuncao;
    private javax.swing.JLabel cImg;
    private javax.swing.JTextField cInstituicao;
    private javax.swing.JList cListaAll;
    private javax.swing.JComboBox cListaInativar;
    private javax.swing.JComboBox cListaProfessores;
    private javax.swing.JList cListaSele;
    private javax.swing.JTextField cLoginImportar;
    private javax.swing.JTextField cLogradouro;
    private javax.swing.JTextField cNome;
    private javax.swing.JTextField cNomeFormacao;
    private javax.swing.JFormattedTextField cNumero;
    private javax.swing.JTextArea cObserva;
    private javax.swing.JTextField cPesquisa;
    private javax.swing.JFormattedTextField cRg;
    private javax.swing.JComboBox cSeleProfCurricu;
    private javax.swing.JPasswordField cSenhaImportar;
    private javax.swing.JTable cTabela;
    private javax.swing.JTable cTabelaConsulta;
    private javax.swing.JTable cTabelaExp;
    private javax.swing.JFormattedTextField cTelefone;
    private javax.swing.JComboBox cTipoPesquisa;
    private javax.swing.JComboBox cUf;
    private javax.swing.JDialog dialogConsulta;
    private javax.swing.JDialog dialogoAcao;
    private javax.swing.JDialog dialogoCurriculo;
    private javax.swing.JDialog dialogoImportar;
    private javax.swing.JDialog dialogoProgressBar;
    private javax.swing.JDialog dialogoStatus;
    private javax.swing.JPanel divAll;
    private javax.swing.JPanel divAll1;
    private javax.swing.JPanel divAll2;
    private javax.swing.JPanel divAll3;
    private javax.swing.JPanel divMenu;
    private javax.swing.JPanel divMenu1;
    private javax.swing.JPanel divMenu2;
    private javax.swing.JPanel divMenu3;
    private javax.swing.JPanel divOne;
    private javax.swing.JPanel divOne1;
    private javax.swing.JPanel divOne2;
    private javax.swing.JEditorPane editor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lBairro;
    private javax.swing.JLabel lCep;
    private javax.swing.JLabel lCidade;
    private javax.swing.JLabel lCpf;
    private javax.swing.JLabel lData;
    private javax.swing.JLabel lDataFim;
    private javax.swing.JLabel lDataFimExp;
    private javax.swing.JLabel lDataInicial;
    private javax.swing.JLabel lDataInicialExp;
    private javax.swing.JLabel lEmail;
    private javax.swing.JLabel lEmpresa;
    private javax.swing.JLabel lEndereco;
    private javax.swing.JLabel lFoto;
    private javax.swing.JLabel lFuncao;
    private javax.swing.JLabel lInstituicao;
    private javax.swing.JLabel lMsg;
    private javax.swing.JLabel lMsg1;
    private javax.swing.JLabel lMsg2;
    private javax.swing.JLabel lNome;
    private javax.swing.JLabel lNomeFormacao;
    private javax.swing.JLabel lNumero;
    private javax.swing.JLabel lObservacoes;
    private javax.swing.JLabel lRg;
    private javax.swing.JLabel lTelefone;
    private javax.swing.JLabel lUf;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JRadioButton rAtivo;
    private javax.swing.JRadioButton rInativo;
    private javax.swing.JPanel tabDadosGerais;
    private javax.swing.JPanel tabDisciplinas;
    private javax.swing.JPanel tabExperienciaPro;
    private javax.swing.JPanel tabFormacaoAcademica;
    private javax.swing.JTabbedPane tabPrincipal;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables

    // ######################################## UTIL DADOS GERAIS ######################################################
    public String getNome() {
        return cNome.getText().trim();
    }

    public String getRg() {
        return cRg.getText().trim();
    }

    public String getCpf() {
        return cCpf.getText().trim();
    }

    public String getEndereco() {
        return cLogradouro.getText().trim();
    }

    public String getCep() {
        return cCep.getText().trim();
    }

    public String getBairro() {
        return cBairro.getText().trim();
    }

    public String getCidade() {
        return cCidade.getText().trim();
    }

    public String getUf() {
        try {
            return cUf.getSelectedItem().toString();
        } catch (NullPointerException e) {
            return "";
        }
    }

    public void makeFoto(String path) {
        File image = new File(path);
        FileInputStream fis;
        try {
            BufferedImage bImage = ImageIO.read(image);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", baos);
            byte[] res = baos.toByteArray();
            prof.setFoto(res);
        } catch (Exception ex) {
            prof.setFoto(null);
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getObservacoes() {
        return cObserva.getText().trim();
    }

    public Integer getNumero() {
        try {
            if (cNumero.getText().equals("")) {
                return null;
            }
            return Integer.parseInt(cNumero.getText());
        } catch (NumberFormatException e) {
            return new Integer(-1);
        }
    }

    public String getEmail() {
        return cEmail.getText();
    }

    public String getTelefone() {
        return cTelefone.getText();
    }

    public Date getDataNascimento() {
        return cDataNascimento.getDate();
    }

    public boolean validarCamposDados() {
        String msg = MessageUtil.initHtml();
        boolean validate = true;
        if (getNome().equals("")) {
            validate &= false;
            this.lNome.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lNome.getText());
        } else {
            this.lNome.setForeground(Color.black);
        }

        if (getRg().contains("_")) {
            validate &= false;
            this.lRg.setForeground(Color.red);
            msg += MessageUtil.msgCampoInvalido(this.lRg.getText());
        } else {
            this.lRg.setForeground(Color.black);
        }

        if (getCpf().contains("_")) {
            validate &= false;
            this.lCpf.setForeground(Color.red);
            msg += MessageUtil.msgCampoInvalido(this.lCpf.getText());
        } else {
            this.lCpf.setForeground(Color.black);
        }

        if (getTelefone().contains("_")) {
            validate &= false;
            this.lTelefone.setForeground(Color.red);
            msg += MessageUtil.msgCampoInvalido(this.lTelefone.getText());
        } else {
            this.lTelefone.setForeground(Color.black);
        }

        if (getDataNascimento() == null) {
            validate &= false;
            this.lData.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lData.getText());
        } else {
            this.lData.setForeground(Color.black);
        }

        if (getEndereco().equals("")) {
            validate &= false;
            this.lEndereco.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lEndereco.getText());
        } else {
            this.lEndereco.setForeground(Color.black);
        }

        if (getNumero() == null) {
            validate &= false;
            this.lNumero.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lNumero.getText());
        } else if (getNumero() == -1) {
            validate &= false;
            this.lNumero.setForeground(Color.red);
            msg += MessageUtil.msgOnlyNumbers(this.lNumero.getText());
        } else {
            this.lNumero.setForeground(Color.black);
        }

        if (getCep().contains("_")) {
            validate &= false;
            this.lCep.setForeground(Color.red);
            msg += MessageUtil.msgCampoInvalido(this.lCep.getText());
        } else {
            this.lCep.setForeground(Color.black);
        }

        if (getBairro().equals("")) {
            validate &= false;
            this.lBairro.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lBairro.getText());
        } else {
            this.lBairro.setForeground(Color.black);
        }


        if (getCidade().equals("")) {
            validate &= false;
            this.lCidade.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lCidade.getText());
        } else {
            this.lCidade.setForeground(Color.black);
        }

        if (getUf().equals("")) {
            validate &= false;
            this.lUf.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lUf.getText());
        } else {
            this.lUf.setForeground(Color.black);
        }
        if (cImg.getIcon().toString().contains("empty")) {
            validate &= false;
            this.lFoto.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lFoto.getText());
        } else {
            this.lFoto.setForeground(Color.black);
        }

        msg += MessageUtil.endHtml();
        lMsg.setVisible(true);
        this.lMsg.setText(msg);
        return validate;
    }

    public void rendCamposDados() {
        cNome.setEnabled(false);
        cRg.setEnabled(false);
        cCpf.setEnabled(false);
        cEmail.setEnabled(false);
        cLogradouro.setEnabled(false);
        cNumero.setEnabled(false);
        cCep.setEnabled(false);
        cDataNascimento.setEnabled(false);
        cTelefone.setEnabled(false);
        cBairro.setEnabled(false);
        cCidade.setEnabled(false);
        cUf.setEnabled(false);
        cObserva.setEnabled(false);
        bProcurar.setEnabled(false);
        cImg.setEnabled(true);
        bEditar.setVisible(true);
        bGravar.setVisible(false);
        bCancelar.setVisible(false);
    }

    public void habilitarCampos() {
        cNome.setEnabled(true);
        cRg.setEnabled(true);
        cCpf.setEnabled(true);
        cEmail.setEnabled(true);
        cLogradouro.setEnabled(true);
        cNumero.setEnabled(true);
        cCep.setEnabled(true);
        cDataNascimento.setEnabled(true);
        cTelefone.setEnabled(true);
        cBairro.setEnabled(true);
        cCidade.setEnabled(true);
        cUf.setEnabled(true);
        cObserva.setEnabled(true);
        bProcurar.setEnabled(true);
        cImg.setEnabled(true);

        bEditar.setVisible(false);
        bGravar.setVisible(true);
        bCancelar.setVisible(true);
    }

    public void resetColorsDados() {
        lMsg.setText(null);
        lNome.setForeground(Color.black);
        lCpf.setForeground(Color.black);
        lRg.setForeground(Color.black);
        lBairro.setForeground(Color.black);
        lCidade.setForeground(Color.black);
        lCep.setForeground(Color.black);
        lData.setForeground(Color.black);
        lEmail.setForeground(Color.black);
        lEndereco.setForeground(Color.black);
        lFoto.setForeground(Color.black);
        lNumero.setForeground(Color.black);
        lUf.setForeground(Color.black);
        lTelefone.setForeground(Color.black);
    }

    // ######################################## UTIL FORMACAO ACADEMICA ############################################# 
    public String getNomeCurso() {
        return cNomeFormacao.getText().trim();
    }

    public String getInstituicao() {
        return cInstituicao.getText().trim();
    }

    public Date getDataInicial() {
        return cDataInicial.getDate();
    }

    public Date getDataFim() {
        return cDataFim.getDate();
    }

    public void rendCamposFormacao() {
        cNomeFormacao.setEnabled(false);
        cInstituicao.setEnabled(false);
        cDataInicial.setEnabled(false);
        cDataFim.setEnabled(false);
        cTabela.setEnabled(false);

        bGravarForm.setVisible(false);
        bCancelarForm.setVisible(false);
    }

    public void habilitarCamposFormacao() {
        cNomeFormacao.setEnabled(true);
        cInstituicao.setEnabled(true);
        cDataInicial.setEnabled(true);
        cDataFim.setEnabled(true);
        cTabela.setEnabled(true);

        bGravarForm.setVisible(true);
        bCancelarForm.setVisible(true);
    }

    public void disableBotoesFormacao() {
        bIncluirForm.setVisible(false);
        bEditarForm.setVisible(false);
        bExcluirForm.setVisible(false);
    }

    public void enableBotoesFormacao() {
        bIncluirForm.setVisible(true);
        bEditarForm.setVisible(true);
        bExcluirForm.setVisible(true);
    }

    public void limparCamposFormacao() {
        cNomeFormacao.setText("");
        cInstituicao.setText("");
        Date date = null;
        cDataInicial.setDate(date);
        cDataFim.setDate(date);
    }

    public boolean validarCamposFormacao() {
        String msg = MessageUtil.initHtml();
        boolean validate = true;
        if (getNomeCurso().equals("")) {
            validate &= false;
            this.lNomeFormacao.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lNomeFormacao.getText());
        } else {
            this.lNomeFormacao.setForeground(Color.black);
        }

        if (getInstituicao().equals("")) {
            validate &= false;
            this.lInstituicao.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lInstituicao.getText());
        } else {
            this.lInstituicao.setForeground(Color.black);
        }

        if (getDataInicial() == null) {
            validate &= false;
            this.lDataInicial.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lDataInicial.getText());
        } else {
            this.lDataInicial.setForeground(Color.black);
        }

        if (getDataFim() == null) {
            validate &= false;
            this.lDataFim.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lDataFim.getText());
        } else {
            this.lDataFim.setForeground(Color.black);
        }

        if (getDataInicial() != null && getDataFim() != null) {
            if (getDataInicial().compareTo(getDataFim()) > 0) {
                validate &= false;
                this.lDataFim.setForeground(Color.red);
                this.lDataInicial.setForeground(Color.red);
                msg += MessageUtil.msgDatasInvalidas(this.lDataInicial.getText(), this.lDataFim.getText());
            } else {
                this.lDataFim.setForeground(Color.black);
                this.lDataInicial.setForeground(Color.black);
            }
        }

        msg += MessageUtil.endHtml();
        lMsg1.setVisible(true);
        this.lMsg1.setText(msg);
        return validate;
    }

    public FormacaoDataModel getFormacaoModel() {
        return (FormacaoDataModel) cTabela.getModel();
    }

    // ######################################## UTIL EXPERIENCIA PROFISSIONAL ############################################# 
    public String getEmpresa() {
        return cEmpresa.getText().trim();
    }

    public String getFuncao() {
        return cFuncao.getText().trim();
    }

    public Date getDataInicialExp() {
        return cDataInicialExp.getDate();
    }

    public Date getDataFimExp() {
        return cDataFImExp.getDate();
    }

    public void rendCamposExperiencia() {
        cEmpresa.setEnabled(false);
        cFuncao.setEnabled(false);
        cDataInicialExp.setEnabled(false);
        cDataFImExp.setEnabled(false);
        cTabelaExp.setEnabled(false);

        bGravarExp.setVisible(false);
        bCancelarExp.setVisible(false);
    }

    public void habilitarCamposExperiencia() {
        cEmpresa.setEnabled(true);
        cFuncao.setEnabled(true);
        cDataInicialExp.setEnabled(true);
        cDataFImExp.setEnabled(true);
        cTabelaExp.setEnabled(true);

        bGravarExp.setVisible(true);
        bCancelarExp.setVisible(true);
    }

    public void disableBotoesExperiencia() {
        bIncluirExp.setVisible(false);
        bEditarExp.setVisible(false);
        bExcluirExp.setVisible(false);
    }

    public void enableBotoesExperiencia() {
        bIncluirExp.setVisible(true);
        bEditarExp.setVisible(true);
        bExcluirExp.setVisible(true);
    }

    public void limparCamposExperiencia() {
        cEmpresa.setText("");
        cFuncao.setText("");
        Date date = null;
        cDataInicialExp.setDate(date);
        cDataFImExp.setDate(date);
    }

    public boolean validarCamposExperiencia() {
        String msg = MessageUtil.initHtml();
        boolean validate = true;
        if (getEmpresa().equals("")) {
            validate &= false;
            this.lEmpresa.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lEmpresa.getText());
        } else {
            this.lEmpresa.setForeground(Color.black);
        }

        if (getFuncao().equals("")) {
            validate &= false;
            this.lFuncao.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lFuncao.getText());
        } else {
            this.lFuncao.setForeground(Color.black);
        }

        if (getDataInicialExp() == null) {
            validate &= false;
            this.lDataInicialExp.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lDataInicialExp.getText());
        } else {
            this.lDataInicialExp.setForeground(Color.black);
        }

        if (getDataFimExp() == null) {
            validate &= false;
            this.lDataFimExp.setForeground(Color.red);
            msg += MessageUtil.msgCampoObrigatorio(this.lDataFimExp.getText());
        } else {
            this.lDataFimExp.setForeground(Color.black);
        }

        if (getDataInicialExp() != null && getDataFimExp() != null) {
            if (getDataInicialExp().compareTo(getDataFimExp()) > 0) {
                validate &= false;
                this.lDataFimExp.setForeground(Color.red);
                this.lDataInicialExp.setForeground(Color.red);
                msg += MessageUtil.msgDatasInvalidas(this.lDataInicialExp.getText(), this.lDataFimExp.getText());
            } else {
                this.lDataFimExp.setForeground(Color.black);
                this.lDataInicialExp.setForeground(Color.black);
            }
        }

        msg += MessageUtil.endHtml();
        lMsg2.setVisible(true);
        this.lMsg2.setText(msg);
        return validate;
    }

    public ExperienciaDataModel getExperienciaModel() {
        return (ExperienciaDataModel) cTabelaExp.getModel();
    }

    //######################################### DISCIPLINA UTIL ############################################
    public DisciplinaListModel getDisciplinaListModel() {
        return (DisciplinaListModel) cListaAll.getModel();
    }

    public DisciplinaPreferencialListModel getDisciplinaPrefeListModel() {
        return (DisciplinaPreferencialListModel) cListaSele.getModel();
    }

    public void atualizarListsDisciplinas() {
        cListaSele.setModel(new DisciplinaPreferencialListModel(prof.getDisciplinasPreferenciais()));
        Collections.sort(disciplinas);
        cListaAll.setModel(new DisciplinaListModel(disciplinas));
    }

    public void intersecaoListas() {
        List<Disciplina> lista = new ArrayList<Disciplina>();
        for (DisciplinaPreferencial dp : prof.getDisciplinasPreferenciais()) {
            lista.add(dp.getDisciplina());
        }
        disciplinas.removeAll(lista);
    }

    //######################################### MENU UTIL ############################################
    //############ CONSULTA #################### 
    public String getTipoConsulta() {
        try {
            return cTipoPesquisa.getSelectedItem().toString();
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String getPesquisa() {
        return cPesquisa.getText().trim();
    }

    public List<Disciplina> filtroDisciplinas(String filtro) {
       List<Disciplina> lista = null;
        lista = CtrCoordenador.consultarDisciplina(filtro);
        return lista;
    }

    public List<Professor> filtroProfessor(String filtro) {
        List<Professor> lista = null;
        lista = CtrCoordenador.consultarProfessor(filtro);
        return lista;
    }

    //############ PROMOVER UTIL #################### 
    public List<Professor> listarApenasProfessores() {
        List<Professor> lista = CtrProfessor.listarApenasProfessores();
        return lista;
    }

    public List<Professor> listarTodos() {
        List<Professor> lista = CtrProfessor.listarTodos();
        return lista;
    }
}
