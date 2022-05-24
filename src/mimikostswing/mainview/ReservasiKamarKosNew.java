/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimikostswing.mainview;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import mimikostswing.Config;
import mimikostswing.Konek;
import model.SetterGetter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Alkin PC
 */
public class ReservasiKamarKosNew extends javax.swing.JFrame {

    private Connection conn ;
    private PreparedStatement ps ;
    /**
     * Creates new form ReservasiKamarKosNew
     */
    public ReservasiKamarKosNew() {
        initComponents();
        showcomboBoxBlok();
        showcomboBoxKamar();
        showHarga();
    }
     public void showcomboBoxBlok(){
        try {
            String sql ="SELECT kode_blok FROM tb_blok";
            Statement stm =(Statement)Konek.getConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            
            while (rs.next()) {                
                jComboBox_Blok.addItem(rs.getString("kode_blok"));
            }
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error:"+ e.getMessage());
//         JOptionPane.showMessageDialog(this, "Silahkan pilih kamar");
        }
        
        
    }
      public void showcomboBoxKamar(){
        try {
            String sql="SELECT tb_blok.kode_blok, tb_kamar.no_kamar FROM tb_blok,tb_kamar WHERE tb_blok.kode_blok ='"+jComboBox_Blok.getSelectedItem().toString()+"' AND tb_kamar.kode_blok ='"+jComboBox_Blok.getSelectedItem().toString()+"'";
            String qryNew="SELECT tb_blok.kode_blok, tb_kamar.no_kamar FROM tb_blok,tb_kamar WHERE tb_blok.kode_blok ='"+jComboBox_Blok.getSelectedItem().toString()+"' AND tb_kamar.kode_blok ='"+jComboBox_Blok.getSelectedItem().toString()+"' GROUP BY tb_kamar.no_kamar ORDER BY tb_kamar.no_kamar ASC";
            Statement st =(Statement)Konek.getConnection().createStatement();
            ResultSet rs = st.executeQuery(qryNew);
            while(rs.next()){
                jComboBox_Kamar.addItem(rs.getString("no_kamar"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
//           JOptionPane.showMessageDialog(this, "Silahkan pilih blok");
        }
    }
      public void showStatus(){
          try {
              String kdBlok = jComboBox_Blok.getSelectedItem().toString();
              String noKam =jComboBox_Kamar.getSelectedItem().toString();
              model.SetterGetter.setNoKam(noKam);
              String convertedNoKam = model.SetterGetter.getNoKam();
              String sql="SELECT tb_blok.kode_blok, tb_kamar.no_kamar, tb_penyewa.nama_penyewa FROM tb_blok JOIN tb_kamar ON tb_blok.kode_blok = tb_kamar.kode_blok JOIN tb_penyewa ON tb_penyewa.id_kamar = tb_kamar.id_kamar";
              String sqll ="SELECT tb_blok.kode_blok, tb_kamar.id_kamar, tb_penyewa.nama_penyewa FROM tb_blok JOIN tb_kamar ON tb_blok.kode_blok = tb_kamar.kode_blok AND tb_kamar.kode_blok ='"+kdBlok+"' JOIN tb_penyewa ON tb_kamar.id_kamar = tb_penyewa.id_kamar AND tb_penyewa.id_kamar='"+convertedNoKam+"'";
              Statement st =(Statement)Konek.getConnection().createStatement();
              ResultSet r = st.executeQuery(sqll);
              if (r.next()) {
                  jLabel_warningKamar.setText("Kamar ini telah ditempati!");
              } else{
                  jLabel_warningKamar.setText(null);
              }
          } catch (Exception e) {
             // JOptionPane.showMessageDialog(this, e);
          }
          
          
          
      }

       public void showHarga(){
        try {
        String sql="SELECT harga FROM tb_blok WHERE kode_blok='"+jComboBox_Blok.getSelectedItem().toString()+"'";
        Statement stm=(Statement)Konek.getConnection().createStatement();
        ResultSet rs=stm.executeQuery(sql);
            while (rs.next()) {
                jLabel1_Harga.setText("Rp."+rs.getString("harga"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error:" +e.getMessage());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Body = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panelRound1 = new model.panelRound();
        jLabel8 = new javax.swing.JLabel();
        jComboBox_Blok = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBox_Kamar = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel1_Harga = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panelRound9 = new model.panelRound();
        jTextField_nominal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton_Konfir = new javax.swing.JButton();
        jLabel_warningKamar = new javax.swing.JLabel();
        panelRound2 = new model.panelRound();
        jLabel2 = new javax.swing.JLabel();
        panelRound3 = new model.panelRound();
        jTextField_NIK = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        panelRound4 = new model.panelRound();
        jTextField_NmCln = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panelRound6 = new model.panelRound();
        jTextField_telp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelRound7 = new model.panelRound();
        jTextField_email = new javax.swing.JTextField();
        jPanel_foto = new javax.swing.JPanel();
        jLabel_foto = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panelRound8 = new model.panelRound();
        jTextField_addresfoto = new javax.swing.JTextField();
        jButton_pilihFoto = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        panelRound10 = new model.panelRound();
        jTextField_asalKot = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Body.setBackground(new java.awt.Color(204, 204, 204));

        jPanel2.setBackground(new java.awt.Color(41, 148, 0));

        jButton1.setBackground(new java.awt.Color(41, 148, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/akar-icons_arrow-back-thick-fill (1).png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Reservasi Kamar Kos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(370, 370, 370)
                .addComponent(jLabel1)
                .addContainerGap(420, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelRound1.setBackground(new java.awt.Color(236, 236, 236));
        panelRound1.setRoundBottomLeft(100);
        panelRound1.setRoundBottomRight(100);
        panelRound1.setRoundTopRight(100);
        panelRound1.setRoundedTopLeft(100);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Pilih Blok yang Akan Ditempati");

        jComboBox_Blok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- SILAHKAN PILIH ITEM ---" }));
        jComboBox_Blok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_BlokItemStateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Pilih Kamar yang Akan Ditempati");

        jComboBox_Kamar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Pilih blok terlebih dahulu-" }));
        jComboBox_Kamar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_KamarItemStateChanged(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("/Bln");

        jLabel1_Harga.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1_Harga.setForeground(new java.awt.Color(0, 0, 0));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Biaya: ");

        panelRound9.setBackground(new java.awt.Color(204, 204, 204));
        panelRound9.setRoundBottomLeft(30);
        panelRound9.setRoundBottomRight(30);
        panelRound9.setRoundTopRight(30);
        panelRound9.setRoundedTopLeft(30);

        jTextField_nominal.setBackground(new java.awt.Color(204, 204, 204));
        jTextField_nominal.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jTextField_nominal.setBorder(null);
        jTextField_nominal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nominalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound9Layout = new javax.swing.GroupLayout(panelRound9);
        panelRound9.setLayout(panelRound9Layout);
        panelRound9Layout.setHorizontalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_nominal, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound9Layout.setVerticalGroup(
            panelRound9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_nominal, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Masukkan Nominal yang Dibayarkan");

        jButton_Konfir.setBackground(new java.awt.Color(102, 204, 0));
        jButton_Konfir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton_Konfir.setText("Konfirmasi Transaksi dan Cetak Struk");
        jButton_Konfir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_KonfirActionPerformed(evt);
            }
        });

        jLabel_warningKamar.setBackground(new java.awt.Color(236, 236, 236));
        jLabel_warningKamar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel_warningKamar.setForeground(new java.awt.Color(153, 51, 0));

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1_Harga, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_Blok, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_Kamar, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(panelRound9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jButton_Konfir))
                    .addComponent(jLabel_warningKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jComboBox_Blok, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox_Kamar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1_Harga, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_warningKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelRound9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(93, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_Konfir, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(105, 105, 105))))
        );

        panelRound2.setBackground(new java.awt.Color(236, 236, 236));
        panelRound2.setRoundBottomLeft(100);
        panelRound2.setRoundBottomRight(100);
        panelRound2.setRoundTopRight(100);
        panelRound2.setRoundedTopLeft(100);

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("No. KTP");

        panelRound3.setBackground(new java.awt.Color(204, 204, 204));
        panelRound3.setRoundBottomLeft(30);
        panelRound3.setRoundBottomRight(30);
        panelRound3.setRoundTopRight(30);
        panelRound3.setRoundedTopLeft(30);

        jTextField_NIK.setBackground(new java.awt.Color(204, 204, 204));
        jTextField_NIK.setBorder(null);
        jTextField_NIK.setOpaque(false);

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_NIK, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_NIK, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nama Calon Penyewa");

        panelRound4.setBackground(new java.awt.Color(204, 204, 204));
        panelRound4.setRoundBottomLeft(30);
        panelRound4.setRoundBottomRight(30);
        panelRound4.setRoundTopRight(30);
        panelRound4.setRoundedTopLeft(30);

        jTextField_NmCln.setBackground(new java.awt.Color(204, 204, 204));
        jTextField_NmCln.setBorder(null);
        jTextField_NmCln.setOpaque(false);

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_NmCln, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound4Layout.setVerticalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_NmCln, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Tanggal Lahir");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Telp");

        panelRound6.setBackground(new java.awt.Color(204, 204, 204));
        panelRound6.setRoundBottomLeft(30);
        panelRound6.setRoundBottomRight(30);
        panelRound6.setRoundTopRight(30);
        panelRound6.setRoundedTopLeft(30);

        jTextField_telp.setBackground(new java.awt.Color(204, 204, 204));
        jTextField_telp.setBorder(null);
        jTextField_telp.setOpaque(false);

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_telp, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_telp, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("E-Mail");

        panelRound7.setBackground(new java.awt.Color(204, 204, 204));
        panelRound7.setRoundBottomLeft(30);
        panelRound7.setRoundBottomRight(30);
        panelRound7.setRoundTopRight(30);
        panelRound7.setRoundedTopLeft(30);

        jTextField_email.setBackground(new java.awt.Color(204, 204, 204));
        jTextField_email.setBorder(null);
        jTextField_email.setOpaque(false);

        javax.swing.GroupLayout panelRound7Layout = new javax.swing.GroupLayout(panelRound7);
        panelRound7.setLayout(panelRound7Layout);
        panelRound7Layout.setHorizontalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_email, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound7Layout.setVerticalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_email, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jLabel_foto.setText("FOTO");

        javax.swing.GroupLayout jPanel_fotoLayout = new javax.swing.GroupLayout(jPanel_foto);
        jPanel_foto.setLayout(jPanel_fotoLayout);
        jPanel_fotoLayout.setHorizontalGroup(
            jPanel_fotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_foto, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
        );
        jPanel_fotoLayout.setVerticalGroup(
            jPanel_fotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_foto, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
        );

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Foto diri Penyewa (Jika ada)");

        panelRound8.setBackground(new java.awt.Color(204, 204, 204));
        panelRound8.setRoundBottomLeft(50);
        panelRound8.setRoundBottomRight(50);
        panelRound8.setRoundTopRight(50);
        panelRound8.setRoundedTopLeft(50);

        jTextField_addresfoto.setEditable(false);
        jTextField_addresfoto.setBackground(new java.awt.Color(204, 204, 204));
        jTextField_addresfoto.setBorder(null);

        javax.swing.GroupLayout panelRound8Layout = new javax.swing.GroupLayout(panelRound8);
        panelRound8.setLayout(panelRound8Layout);
        panelRound8Layout.setHorizontalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_addresfoto, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelRound8Layout.setVerticalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField_addresfoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButton_pilihFoto.setText("Pilih Foto");
        jButton_pilihFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_pilihFotoActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Alamat");

        panelRound10.setBackground(new java.awt.Color(204, 204, 204));
        panelRound10.setRoundBottomLeft(30);
        panelRound10.setRoundBottomRight(30);
        panelRound10.setRoundTopRight(30);
        panelRound10.setRoundedTopLeft(30);

        jTextField_asalKot.setBackground(new java.awt.Color(204, 204, 204));
        jTextField_asalKot.setBorder(null);
        jTextField_asalKot.setOpaque(false);

        javax.swing.GroupLayout panelRound10Layout = new javax.swing.GroupLayout(panelRound10);
        panelRound10.setLayout(panelRound10Layout);
        panelRound10Layout.setHorizontalGroup(
            panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_asalKot, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound10Layout.setVerticalGroup(
            panelRound10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_asalKot, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("*) Scan KTP dengan perangkat RFID");

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelRound10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRound2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                                        .addComponent(jPanel_foto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                                        .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton_pilihFoto)
                                        .addGap(8, 8, 8))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel_foto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRound10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_pilihFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(117, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_BodyLayout = new javax.swing.GroupLayout(jPanel_Body);
        jPanel_Body.setLayout(jPanel_BodyLayout);
        jPanel_BodyLayout.setHorizontalGroup(
            jPanel_BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_BodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel_BodyLayout.setVerticalGroup(
            jPanel_BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BodyLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling cjTextField_NIK       this.setVisible(false);
        new MainMenu().setVisible(true);
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox_BlokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_BlokItemStateChanged
        // TODO add your handling code here:
         jComboBox_Kamar.removeAllItems();
        showcomboBoxKamar();
        showHarga();
    }//GEN-LAST:event_jComboBox_BlokItemStateChanged

    private void jButton_pilihFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pilihFotoActionPerformed
        // TODO add your handling code here:
//         JFileChooser chsr = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE","jpg","png","jpeg");
//        chsr.setFileFilter(filter);
//        chsr.showOpenDialog(null);
//        File f = chsr.getSelectedFile();
//        String fileN =f.getAbsolutePath();
//        jTextField_addresfoto.setText(fileN.toString());
//        Image getAbsolutePath =null;
//        ImageIcon ico = new ImageIcon(fileN);
//        Image img = ico.getImage().getScaledInstance(jLabel_foto.getWidth(), jLabel_foto.getHeight(), Image.SCALE_SMOOTH);
//        jLabel_foto.setText(null);
//        jLabel_foto.setIcon(new ImageIcon(img));

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE","jpg","png","jpeg");
            fileChooser.setFileFilter(filter);
            int openDialog = fileChooser.showOpenDialog(null);
            if (openDialog == fileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String selectedImage =selectedFile.getAbsolutePath();
                jTextField_addresfoto.setText(selectedImage);
                ImageIcon ico = new ImageIcon(selectedImage);
                Image img =ico.getImage().getScaledInstance(jLabel_foto.getWidth(), jLabel_foto.getHeight(), Image.SCALE_SMOOTH);
                jLabel_foto.setText(null);
                jLabel_foto.setIcon(new ImageIcon(img));
        }
    }//GEN-LAST:event_jButton_pilihFotoActionPerformed

    private void jButton_KonfirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_KonfirActionPerformed
        // TODO add your handling code here:
        //Date date = new Date();
        //Object par = new Timestamp(date.getTime());
//       String DateFormatted =s.format(par);
//        DateTimeFormatter fm =DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
//        LocalDateTime dtIme =LocalDateTime.now();
//        String convDate = dtIme.format(fm);
         Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sInput = new SimpleDateFormat("yyyy-MM-dd");
        String skrg = s.format(d);
        String NIK = jTextField_NIK.getText();
        String nm = jTextField_NmCln.getText();
        String usi =  sInput.format(jDateChooser1.getDate()); //jDateChooser1.getDate().toString();
        String asalK = jTextField_asalKot.getText();
        String tel = jTextField_telp.getText();
        String email = jTextField_email.getText();
        String kodB =jComboBox_Blok.getSelectedItem().toString();
        String noKam = jComboBox_Kamar.getSelectedItem().toString();
        String foto = jTextField_addresfoto.getText();
        model.SetterGetter.setNoKam(noKam);
        String convNokam = model.SetterGetter.getNoKam();
        String nom =jTextField_nominal.getText();
        try {
            Connection conn = (Connection)Config.configDB();
            PreparedStatement ps;
            String query;
            try {
                conn.setAutoCommit(false);
                InputStream is = new FileInputStream(foto);
                //query = "INSERT INTO tb_penyewa (NIK, nama_penyewa, usia, asal_kota, telp, email, foto, kode_blok, id_kamar, waktu_sewa_pertama) VALUES"
                    //  + "('"+NIK+"', '"+nm+"', '"+usi+"', '"+asalK+"', '"+tel+"', '"+email+"','"+foto+"', " + Types.NULL + ", '"+kodB+"', '"+convNokam+"', '"+skrg+"')";     
                query="INSERT INTO tb_penyewa (kode_ktp, nama_penyewa, tanggal_lahir, asal_kota, telp, email, foto, kode_blok, id_kamar, waktu_sewa_pertama) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
               
                ps = conn.prepareStatement(query);
                //ps.setBinaryStream(1, is);
                ps.setString(1, NIK);
                ps.setString(2, nm);
                ps.setString(3, usi);
                ps.setString(4, asalK);
                ps.setString(5, tel);
                ps.setString(6, email);
                try {
                    ps.setBlob(7, is);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
                ps.setString(8, kodB);
                ps.setString(9, convNokam);
                ps.setDate(10, java.sql.Date.valueOf(LocalDate.now()));
                ps.execute();
                 
                  
                
                Calendar c =Calendar.getInstance();
                c.setTime(d);
            //untuk mengcatch bulan
                int idBln = c.get(Calendar.MONTH);
            //untuk mengcatch Tahun
                int thn =c.get(Calendar.YEAR);
                //Insert ke tabel tb_tagihan_penyewa
                query ="INSERT INTO tb_tagihan_penyewa VALUES("+Types.NULL+",'"+idBln+"','"+thn+"','"+NIK+"','"+nom+"','Terbayar','"+skrg+"')";
                ps = conn.prepareStatement(query);
                ps.execute();
//        ps.setBlob(1, is);
                conn.commit();
        
                JOptionPane.showMessageDialog(this, "Input berhasil");
            } catch(Exception e) {
                conn.rollback();
                
                e.printStackTrace();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            System.err.println(e);
            
        }
        
        try {
            Connection conn =(Connection)Config.configDB();
            JasperDesign jsDi =JRXmlLoader.load("F:\\Programming Project\\Java\\MimiKostSwing\\src\\struk\\reservasi_1.jrxml");
            HashMap hash = new HashMap();
            hash.put("kode", jTextField_NIK.getText());
            hash.put("Tunai", jTextField_nominal.getText());
                                                                                                                                                                                                                                                                                                                                                              //$P{kode}      
            String sql="SELECT tb_tagihan_penyewa.id_tagihan_penyewa, tb_penyewa.kode_ktp ,tb_penyewa.nama_penyewa, tb_blok.kode_blok,tb_blok.harga ,tb_kamar.no_kamar, tb_penyewa.waktu_sewa_pertama, tb_tagihan_penyewa.status FROM tb_penyewa JOIN tb_tagihan_penyewa ON tb_penyewa.kode_ktp = tb_tagihan_penyewa.kode_ktp AND tb_penyewa.kode_ktp ='"+jTextField_NIK.getText()+"' JOIN tb_blok ON tb_blok.kode_blok =tb_penyewa.kode_blok JOIN tb_kamar ON tb_blok.kode_blok = tb_kamar.kode_blok GROUP BY tb_penyewa.nama_penyewa";
            JRDesignQuery newQ = new JRDesignQuery();
            newQ.setText(sql);
            JasperReport js = JasperCompileManager.compileReport(jsDi);
            JasperPrint jp = JasperFillManager.fillReport(js, hash, conn );
            JasperViewer.viewReport(jp);
           // JasperPrintManager.printReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        //Input ke tb_tagihan_penyewa 
//       try {
//            //Untuk mengambil tgl lokal yang ada pada sistem 
//            //Date d = new Date();
//            Calendar c =Calendar.getInstance();
//            c.setTime(d);
//            //untuk mengcatch bulan
//           int idBln = c.get(Calendar.MONTH);
//            //untuk mengcatch Tahun
//            int thn =c.get(Calendar.YEAR);
//            String sql="INSERT INTO tb_tagihan_penyewa VALUES("+Types.NULL+",'"+idBln+"','"+thn+"','"+NIK+"','"+nom+"','Terbayar','"+par+"')";
//            conn =(Connection)Config.configDB();
//            ps =conn.prepareStatement(sql);
//            ps.execute();
//            
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, e);
//        }
    }//GEN-LAST:event_jButton_KonfirActionPerformed

    private void jTextField_nominalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nominalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_nominalActionPerformed

    private void jComboBox_KamarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_KamarItemStateChanged
        // TODO add your handling code here:
        showStatus();
    }//GEN-LAST:event_jComboBox_KamarItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReservasiKamarKosNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReservasiKamarKosNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReservasiKamarKosNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReservasiKamarKosNew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReservasiKamarKosNew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Konfir;
    private javax.swing.JButton jButton_pilihFoto;
    private javax.swing.JComboBox<String> jComboBox_Blok;
    private javax.swing.JComboBox<String> jComboBox_Kamar;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel1_Harga;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_foto;
    private javax.swing.JLabel jLabel_warningKamar;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_Body;
    private javax.swing.JPanel jPanel_foto;
    private javax.swing.JTextField jTextField_NIK;
    private javax.swing.JTextField jTextField_NmCln;
    private javax.swing.JTextField jTextField_addresfoto;
    private javax.swing.JTextField jTextField_asalKot;
    private javax.swing.JTextField jTextField_email;
    private javax.swing.JTextField jTextField_nominal;
    private javax.swing.JTextField jTextField_telp;
    private model.panelRound panelRound1;
    private model.panelRound panelRound10;
    private model.panelRound panelRound2;
    private model.panelRound panelRound3;
    private model.panelRound panelRound4;
    private model.panelRound panelRound6;
    private model.panelRound panelRound7;
    private model.panelRound panelRound8;
    private model.panelRound panelRound9;
    // End of variables declaration//GEN-END:variables
}
