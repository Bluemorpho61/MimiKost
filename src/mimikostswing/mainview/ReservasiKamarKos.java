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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import mimikostswing.Konek;

/**
 *
 * @author Alkin PC
 */
public class ReservasiKamarKos extends javax.swing.JFrame {

    /**
     * Creates new form ReservasiKamarKos
     */
    public ReservasiKamarKos() {
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
        }
        
        
    }
    public void showcomboBoxKamar(){
        try {
            String sql="SELECT tb_blok.kode_blok, tb_kamar.no_kamar FROM tb_blok,tb_kamar WHERE tb_blok.kode_blok ='"+jComboBox_Blok.getSelectedItem().toString()+"' AND tb_kamar.kode_blok ='"+jComboBox_Blok.getSelectedItem().toString()+"'";
            String qryNew="SELECT tb_blok.kode_blok, tb_kamar.no_kamar FROM tb_blok,tb_kamar WHERE tb_blok.kode_blok ='"+jComboBox_Blok.getSelectedItem().toString()+"' AND tb_kamar.kode_blok ='"+jComboBox_Blok.getSelectedItem().toString()+"' GROUP BY tb_kamar.no_kamar ORDER BY tb_kamar.no_kamar ASC";
            Statement st =(Statement)Konek.getConnection().createStatement();
            ResultSet rs = st.executeQuery(qryNew);
            while(rs.next()){
                jComboBox_kamar.addItem(rs.getString("no_kamar"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    public void showHarga(){
        try {
        String sql="SELECT harga FROM tb_blok WHERE kode_blok='"+jComboBox_Blok.getSelectedItem().toString()+"'";
        Statement stm=(Statement)Konek.getConnection().createStatement();
        ResultSet rs=stm.executeQuery(sql);
            while (rs.next()) {
                jLabel14.setText("Rp."+rs.getString("harga"));
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_NIK = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField_Nama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField_Usia = new javax.swing.JTextField();
        jTextField_asalkot = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_telp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField_email = new javax.swing.JTextField();
        jComboBox_Blok = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox_kamar = new javax.swing.JComboBox<>();
        jPanel_FOTO = new javax.swing.JPanel();
        jLabel_foto = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField_addressfoto = new javax.swing.JTextField();
        jButton_pilihFot = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextField_nominal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton_lakukanTransaksi = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(193, 222, 174));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        jPanel2.setBackground(new java.awt.Color(41, 148, 0));

        jButton1.setBackground(new java.awt.Color(232, 232, 166));
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("NIK");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Pilih Kamar");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Usia");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Asal Kota");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Telp/WA");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("E-Mail*");

        jComboBox_Blok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- SILAHKAN PILIH ITEM ---" }));
        jComboBox_Blok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_BlokItemStateChanged(evt);
            }
        });
        jComboBox_Blok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_BlokActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Nama Calon Penyewa");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Pilih Blok");

        jComboBox_kamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_kamarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_FOTOLayout = new javax.swing.GroupLayout(jPanel_FOTO);
        jPanel_FOTO.setLayout(jPanel_FOTOLayout);
        jPanel_FOTOLayout.setHorizontalGroup(
            jPanel_FOTOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_FOTOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_foto, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_FOTOLayout.setVerticalGroup(
            jPanel_FOTOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_FOTOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_foto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Foto KTP (Jika ada)");

        jTextField_addressfoto.setEditable(false);

        jButton_pilihFot.setText("Pilih Foto");
        jButton_pilihFot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_pilihFotActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Nominal yang dibayarkan");

        jTextField_nominal.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Biaya: ");

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("/bln");

        jButton_lakukanTransaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton_lakukanTransaksi.setText("Lakukan Transaksi dan Cetak Struk");
        jButton_lakukanTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_lakukanTransaksiActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("*Tidak wajib diisi");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jTextField_NIK)
                            .addComponent(jTextField_Nama, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField_Usia, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField_asalkot)
                            .addComponent(jTextField_telp)
                            .addComponent(jLabel7)
                            .addComponent(jTextField_email)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jComboBox_Blok, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                    .addComponent(jComboBox_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(123, 123, 123)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addGap(177, 177, 177))))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(166, 166, 166)
                                                .addComponent(jLabel3))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(99, 99, 99)
                                                .addComponent(jLabel13)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel15)))
                                        .addGap(150, 150, 150)))
                                .addComponent(jPanel_FOTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(219, 219, 219)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTextField_nominal))
                                        .addGap(83, 83, 83)
                                        .addComponent(jButton_lakukanTransaksi))
                                    .addComponent(jTextField_addressfoto, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jButton_pilihFot, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(197, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(327, 327, 327))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_NIK, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_Nama, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox_Blok, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_Usia, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_kamar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(13, 13, 13)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_asalkot, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_telp, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_FOTO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_email, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_addressfoto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_pilihFot, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_lakukanTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_nominal, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addGap(0, 148, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1317, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new MainMenu().setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox_BlokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_BlokItemStateChanged
        // TODO add your handling code here:
        jComboBox_kamar.removeAllItems();
        showcomboBoxKamar();
        showHarga();
    }//GEN-LAST:event_jComboBox_BlokItemStateChanged

    private void jComboBox_BlokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_BlokActionPerformed
        // TODO add your handling code here:
       
       
    }//GEN-LAST:event_jComboBox_BlokActionPerformed

    private void jComboBox_kamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_kamarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_kamarActionPerformed

    private void jButton_pilihFotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pilihFotActionPerformed
        // TODO add your handling code here:
        JFileChooser chsr = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE","jpg","png","jpeg");
        chsr.setFileFilter(filter);
        chsr.showOpenDialog(null);
        File f = chsr.getSelectedFile();
        String fileN =f.getAbsolutePath();
        jTextField_addressfoto.setText(fileN.toString());
        Image getAbsolutePath =null;
        ImageIcon ico = new ImageIcon(fileN);
        Image img = ico.getImage().getScaledInstance(jLabel_foto.getWidth(), jLabel_foto.getHeight(), Image.SCALE_SMOOTH);
        jLabel_foto.setIcon(new ImageIcon(img));
    }//GEN-LAST:event_jButton_pilihFotActionPerformed

    private void jButton_lakukanTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_lakukanTransaksiActionPerformed
        String Foto = jTextField_addressfoto.getText();
        String noKamar = jComboBox_kamar.getSelectedItem().toString();
        String kodB = jComboBox_Blok.getSelectedItem().toString();
        model.SetterGetter.setNoKam(noKamar);
        String convNoKam=model.SetterGetter.getNoKam();
        try {
        InputStream is = new FileInputStream(new File(Foto));
        String sql ="INSERT INTO tb_penyewa VALUES('"+jTextField_NIK.getText()+
                "','"+jTextField_Nama.getText()+"','"+jTextField_Usia.getText()+
                "','"+jTextField_asalkot.getText()+"','"+jTextField_telp.getText()+
                "','"+jTextField_telp.getText()+"','"+jTextField_email.getText()+"','"+is
                +"','"+kodB+"','"+convNoKam+"')";
        Connection conn =(Connection)mimikostswing.Config.configDB();
        PreparedStatement ps =conn.prepareStatement(sql);
        //ps.setBinaryStream(1, is);
        ps.setBlob(1, is);
        ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e);
        }
        
        
//        String nama= jTextField_Nama.getText();
//        String NIK = jTextField_NIK.getText();
//        String usia = jTextField_Usia.getText();
//        String asalKot = jTextField_asalkot.getText();
//        String telp = jTextField_telp.getText();
//        String email = jTextField_email.getText();
//        String blok = jComboBox_Blok.getSelectedItem().toString();
//        String noKam = jComboBox_kamar.getSelectedItem().toString(); 
//        model.SetterGetter.setNoKam(noKam);
//        String convertedKam = model.SetterGetter.getNoKam();
//        //testing hasil inpt di console
//        System.out.println(NIK);
//        System.out.println(nama);
//        System.out.println(usia);
//        System.out.println(asalKot);
//        System.out.println(telp);
//        System.out.println(email);
//        System.out.println(blok);
//        System.out.println(noKam);
//        //System.out.println(email);
//        
//        try {
//            String foto=jTextField_addressfoto.getText();
//            InputStream is = new FileInputStream(new File(foto));
//            System.out.println(is);
//            String sql="INSERT INTO tb_penyewa (`NIK`, `nama_penyewa`, `usia`, `asal_kota`, `telp`, `email`, `foto`, `kode_blok`, `id_kamar`, `waktu_sewa_pertama`)"
//                    + "VALUES=('"+NIK+"','"+nama+"','"+usia+"','"+asalKot+"','"+telp+"','"+email+"','"+is+"','"+blok+"','"+convertedKam+"')";
//        Connection conn =(Connection)mimikostswing.Config.configDB();
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setBinaryStream(1, is);
//            ps.execute(sql);
//            JOptionPane.showMessageDialog(this, "Penyewa baru telah berhasil ditambahkan");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this,"Tambah Penyewa Gagal: "+ e.getMessage());
//            System.err.println(e);
//        }
        
    }//GEN-LAST:event_jButton_lakukanTransaksiActionPerformed

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
            java.util.logging.Logger.getLogger(ReservasiKamarKos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReservasiKamarKos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReservasiKamarKos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReservasiKamarKos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReservasiKamarKos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_lakukanTransaksi;
    private javax.swing.JButton jButton_pilihFot;
    private javax.swing.JComboBox<String> jComboBox_Blok;
    private javax.swing.JComboBox<String> jComboBox_kamar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_foto;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel_FOTO;
    private javax.swing.JTextField jTextField_NIK;
    private javax.swing.JTextField jTextField_Nama;
    private javax.swing.JTextField jTextField_Usia;
    private javax.swing.JTextField jTextField_addressfoto;
    private javax.swing.JTextField jTextField_asalkot;
    private javax.swing.JTextField jTextField_email;
    private javax.swing.JTextField jTextField_nominal;
    private javax.swing.JTextField jTextField_telp;
    // End of variables declaration//GEN-END:variables
}
