/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JOptionPane;

import model.Professor;
import util.MessageUtil;
import controller.CtrProfessor;

/**
 *
 * @author Nathalia
 */
public class AlterarSenha extends javax.swing.JFrame {
	
	private static final long serialVersionUID = 3590354270221283790L;

	/**
     * Creates new form Index
     */
    public AlterarSenha(Professor prof) {
        this.prof = prof;
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        cNovaSenha = new javax.swing.JPasswordField();
        cConfirmacao = new javax.swing.JPasswordField();
        bOk = new javax.swing.JButton();
        bCancelar = new javax.swing.JButton();
        lNovaSenha = new javax.swing.JLabel();
        lConfirmacao = new javax.swing.JLabel();
        lMsg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Acessar Sistema");
        setBackground(new java.awt.Color(204, 204, 255));

        bOk.setText("Ok");
        bOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOkActionPerformed(evt);
            }
        });

        bCancelar.setText("Cancelar");
        bCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelarActionPerformed(evt);
            }
        });

        lNovaSenha.setText("Nova Senha");

        lConfirmacao.setText("Confirmação");

        lMsg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lMsg.setForeground(new java.awt.Color(255, 0, 0));
        lMsg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lNovaSenha)
                    .addComponent(lConfirmacao))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cNovaSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(cConfirmacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bCancelar)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bOk)
                        .addGap(57, 57, 57))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addComponent(lMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lNovaSenha)
                    .addComponent(bOk))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cConfirmacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lConfirmacao)
                    .addComponent(bCancelar))
                .addGap(58, 58, 58))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void bOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOkActionPerformed
        if (validarCamposObri()) {
            if (validarCamposSenha()) {
                if (CtrProfessor.alterarSenha(prof, getNovaSenha())) {
                    this.lMsg.setText(null);
                    JOptionPane.showMessageDialog(null, "Senha Alterada com Sucesso");
                    Principal p = new Principal(prof);
                    setVisible(false);
                    p.setVisible(true);
                } else{
                     this.lMsg.setText("Ocorreu um erro");
                }
            }
        }
    }//GEN-LAST:event_bOkActionPerformed
    
    private void bCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelarActionPerformed
        System.exit(1);
    }//GEN-LAST:event_bCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancelar;
    private javax.swing.JButton bOk;
    private javax.swing.JPasswordField cConfirmacao;
    private javax.swing.JPasswordField cNovaSenha;
    private javax.swing.JLabel lConfirmacao;
    private javax.swing.JLabel lMsg;
    private javax.swing.JLabel lNovaSenha;
    // End of variables declaration//GEN-END:variables
    Professor prof = null;

    public boolean validarCamposObri() {
        String msg = "<html><body>";
        boolean validate = true;
        if (getNovaSenha().equals("")) {
            validate &= false;
            msg += MessageUtil.msgCampoObrigatorio(this.lNovaSenha.getText());
        } 

        if (getConfirmacao().equals("")) {
            validate &= false;
            msg += MessageUtil.msgCampoObrigatorio(this.lConfirmacao.getText());
        }

        msg += "</body></html>";
        this.lMsg.setText(msg);
        return validate;
    }

    public boolean validarCamposSenha() {
        if(getNovaSenha().equals(prof.getLogin())){
            this.lMsg.setText("Senha deve ser diferente do Login");
            this.cNovaSenha.setText("");
            this.cConfirmacao.setText("");
            return false;
        }
        if (getNovaSenha().equals(getConfirmacao())) {
            this.lMsg.setText(null);
            return true;
        } else {
            this.lMsg.setText("Senhas diferentes");
            return false;
        }
    }

    public String getNovaSenha() {
        return new String(this.cNovaSenha.getPassword()).trim();
    }

    public String getConfirmacao() {
        return new String(this.cConfirmacao.getPassword()).trim();
    }
}
