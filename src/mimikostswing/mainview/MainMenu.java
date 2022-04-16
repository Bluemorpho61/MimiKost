/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimikostswing.mainview;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mimikostswing.Konek;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Alkin PC
 */
public class MainMenu extends javax.swing.JFrame {

    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
        this.setResizable(false);
        DisplayCount();
        comboBoxBlok();
        TableModelBlok();
        Desc();
        //TableModelDataPenyewa1();
    }

//    private void TableModelDataPenyewa1(){
//        DefaultTableModel tb = new DefaultTableModel();
//        tb.addColumn("NIK");
//        tb.addColumn("Nama");
//        tb.addColumn("Asal Kota");
//        tb.addColumn("Usia");
//        tb.addColumn("No.Telp");
//        tb.addColumn("E-mail");
//        jTable_DataPenyewa.setModel(tb);
//        try {
//            java.sql.Statement statement = (Statement)Konek.getConnection();
//            java.sql.ResultSet rs =statement.executeQuery("SELECT NIK, nama_penyewa, usia, asal_kota, telp, email FROM tb_penyewa");
//            while (rs.next()) {                
//                tb.addRow(new Object[]{
//                    rs.getString("NIK"),
//                    rs.getString("nama_penyewa"),
//                    rs.getString("usia"),
//                    rs.getString("asal_kota"),
//                    rs.getString("telp"),
//                    rs.getString("email")
//                });
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
//        }
//    }
    
    private void DisplayCount(){
        
        try {
        String sql="SELECT COUNT(*) AS 'jumlah' FROM tb_penyewa";
        java.sql.Connection conn = (Connection)Konek.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.ResultSet res = ps.executeQuery();
            if (res.next()) {
                jLabel1_sumPenyewa.setText(res.getString("jumlah"));
            } 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: "+e.getMessage());
        }
        
        
         try {
        String sqll="SELECT COUNT(*) AS 'jumlah' FROM tb_kamar";
        Connection conn=(Connection)Konek.getConnection();
        PreparedStatement ps=conn.prepareStatement(sqll);
             ResultSet res=ps.executeQuery();
             if (res.next()) {
                 jLabel1_CountKamar.setText(res.getString("jumlah"));
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error: "+e.getMessage());
         }
         
         try {
        String sqll="SELECT COUNT(*) AS 'jumlah' FROM tb_kamar";
        Connection conn=(Connection)Konek.getConnection();
        PreparedStatement ps=conn.prepareStatement(sqll);
             ResultSet res=ps.executeQuery();
             if (res.next()) {
                 jLabel1_CountKamar.setText(res.getString("jumlah"));
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error: "+e.getMessage());
         }
         
         try {
            String sql ="SELECT COUNT(NIK) AS lunas FROM tb_tagihan_penyewa WHERE status='Terbayar' ";
            Connection conn = (Connection)Konek.getConnection();
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet res=pst.executeQuery();
             if (res.next()) {
                 jLabel1_CountTelahLunas.setText(res.getString("lunas"));
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
         }
         try {
            String sql ="SELECT COUNT(NIK) AS blmlunas FROM tb_tagihan_penyewa WHERE status='Belum Terbayar' ";
            Connection conn = (Connection)Konek.getConnection();
            PreparedStatement pst=conn.prepareStatement(sql);
            ResultSet res=pst.executeQuery();
             if (res.next()) {
                 jLabel1_CountBelumLunas.setText(res.getString("blmlunas"));
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
         }
    }
    
    private void comboBoxBlok(){
        try {
            Statement statement = (Statement)Konek.getConnection().createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM tb_blok");
            while (res.next()) {                
                jComboBox_Blok.addItem(res.getString("kode_blok"));
                jComboBoxBlok_DataPenyewa.addItem(res.getString("kode_blok"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
        }
    }
    
    public void TableModelBlok(){
        //Tabel ModelBlok
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("No Kamar");
        tbl.addColumn("Jumlah Penghuni");
        jTable_K_basedOnBlok.setModel(tbl);
        try {
            Statement statement =(Statement)Konek.getConnection().createStatement();
            ResultSet res = statement.executeQuery("SELECT no_kamar,jumlah_penghuni FROM tb_kamar WHERE kode_blok='"+jComboBox_Blok.getSelectedItem().toString()+"'");
           
            
            while (res.next()) {                
                tbl.addRow(new Object[]{
                    res.getString("no_kamar"),
                    res.getString("jumlah_penghuni")+(" orang"),
                });
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"ERROR: " +e.getMessage());
        }
        
        
    }
    
    public void Desc(){
          try {
        java.sql.Statement stm = (Statement)Konek.getConnection().createStatement();
        java.sql.ResultSet rs = stm.executeQuery("SELECT deskripsi FROM tb_blok WHERE kode_blok='"+jComboBox_Blok.getSelectedItem().toString()+"'");
            if (rs.next()) {
        jTextField_desc.setText(rs.getString("deskripsi"));        
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
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
        jPanel_DropDown = new javax.swing.JPanel();
        jPanel_TambahPenyewa = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel_TambahBlok2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel_Transaksi = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel_Laporan = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel_MainP = new javax.swing.JPanel();
        jPanel_Dashboard = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1_sumPenyewa = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel1_CountKamar = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel1_CountTelahLunas = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel1_CountBelumLunas = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel_DataPeny = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_DataPenyewa = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jTextField_cariPenyewa = new javax.swing.JTextField();
        jComboBoxBlok_DataPenyewa = new javax.swing.JComboBox<>();
        jTextField_nik = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel_nik = new javax.swing.JLabel();
        jLabel_nik1 = new javax.swing.JLabel();
        jTextField_nik1 = new javax.swing.JTextField();
        jLabel_nik2 = new javax.swing.JLabel();
        jTextField_nik2 = new javax.swing.JTextField();
        jLabel_nik3 = new javax.swing.JLabel();
        jTextField_nik3 = new javax.swing.JTextField();
        jLabel_nik4 = new javax.swing.JLabel();
        jTextField_nik4 = new javax.swing.JTextField();
        jLabel_nik5 = new javax.swing.JLabel();
        jTextField_nik5 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jTextField_ImgAddress = new javax.swing.JTextField();
        jButton_ImageChooser = new javax.swing.JButton();
        jButton_batal = new javax.swing.JButton();
        jButton_konfedit1 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jPanel_lprn = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel_Report = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jPanel_BlkNK = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBox_Blok = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_K_basedOnBlok = new javax.swing.JTable();
        jButton_aturBlok = new javax.swing.JButton();
        jButton_aturKamar = new javax.swing.JButton();
        jTextField_desc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel_Trnsksee = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Body.setBackground(new java.awt.Color(149, 165, 166));

        jPanel_DropDown.setBackground(new java.awt.Color(242, 245, 200));
        jPanel_DropDown.setPreferredSize(new java.awt.Dimension(239, 735));

        jPanel_TambahPenyewa.setBackground(new java.awt.Color(242, 245, 200));
        jPanel_TambahPenyewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_TambahPenyewaMouseClicked(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/add-user 1.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Data Penyewa");

        javax.swing.GroupLayout jPanel_TambahPenyewaLayout = new javax.swing.GroupLayout(jPanel_TambahPenyewa);
        jPanel_TambahPenyewa.setLayout(jPanel_TambahPenyewaLayout);
        jPanel_TambahPenyewaLayout.setHorizontalGroup(
            jPanel_TambahPenyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TambahPenyewaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_TambahPenyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_TambahPenyewaLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel1))
                    .addComponent(jLabel12))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel_TambahPenyewaLayout.setVerticalGroup(
            jPanel_TambahPenyewaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TambahPenyewaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_TambahBlok2.setBackground(new java.awt.Color(242, 245, 200));
        jPanel_TambahBlok2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_TambahBlok2MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/office-buildings 1.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Blok & Kamar");

        javax.swing.GroupLayout jPanel_TambahBlok2Layout = new javax.swing.GroupLayout(jPanel_TambahBlok2);
        jPanel_TambahBlok2.setLayout(jPanel_TambahBlok2Layout);
        jPanel_TambahBlok2Layout.setHorizontalGroup(
            jPanel_TambahBlok2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TambahBlok2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_TambahBlok2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_TambahBlok2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel9))
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_TambahBlok2Layout.setVerticalGroup(
            jPanel_TambahBlok2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_TambahBlok2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap())
        );

        jPanel_Transaksi.setBackground(new java.awt.Color(242, 245, 200));
        jPanel_Transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_TransaksiMouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/bill 1.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Transaksi");

        javax.swing.GroupLayout jPanel_TransaksiLayout = new javax.swing.GroupLayout(jPanel_Transaksi);
        jPanel_Transaksi.setLayout(jPanel_TransaksiLayout);
        jPanel_TransaksiLayout.setHorizontalGroup(
            jPanel_TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TransaksiLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_TransaksiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );
        jPanel_TransaksiLayout.setVerticalGroup(
            jPanel_TransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TransaksiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11))
        );

        jPanel_Laporan.setBackground(new java.awt.Color(242, 245, 200));
        jPanel_Laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_LaporanMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/report 1.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Laporan");

        javax.swing.GroupLayout jPanel_LaporanLayout = new javax.swing.GroupLayout(jPanel_Laporan);
        jPanel_Laporan.setLayout(jPanel_LaporanLayout);
        jPanel_LaporanLayout.setHorizontalGroup(
            jPanel_LaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_LaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_LaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_LaporanLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3))
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_LaporanLayout.setVerticalGroup(
            jPanel_LaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_LaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel_DropDownLayout = new javax.swing.GroupLayout(jPanel_DropDown);
        jPanel_DropDown.setLayout(jPanel_DropDownLayout);
        jPanel_DropDownLayout.setHorizontalGroup(
            jPanel_DropDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DropDownLayout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addGroup(jPanel_DropDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_TambahPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_Laporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_TambahBlok2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_Transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        jPanel_DropDownLayout.setVerticalGroup(
            jPanel_DropDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DropDownLayout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addComponent(jPanel_TambahPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jPanel_Laporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel_TambahBlok2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel_Transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_MainP.setBackground(new java.awt.Color(193, 222, 174));
        jPanel_MainP.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white, java.awt.Color.white));
        jPanel_MainP.setLayout(new java.awt.CardLayout());

        jPanel_Dashboard.setBackground(new java.awt.Color(193, 222, 174));

        jPanel2.setBackground(new java.awt.Color(33, 159, 148));
        jPanel2.setPreferredSize(new java.awt.Dimension(348, 148));

        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel13.setText("Total Keseluruhan Penyewa");

        jLabel1_sumPenyewa.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1_sumPenyewa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_sumPenyewa.setText("Jml");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(69, 69, 69))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jLabel1_sumPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1_sumPenyewa, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(33, 159, 148));

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel14.setText("Jumlah Kamar Kos");

        jLabel1_CountKamar.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1_CountKamar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_CountKamar.setText("Jml");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1_CountKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1_CountKamar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(33, 159, 148));
        jPanel7.setPreferredSize(new java.awt.Dimension(348, 148));

        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel15.setText("Yang Telah Lunas");

        jLabel1_CountTelahLunas.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1_CountTelahLunas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_CountTelahLunas.setText("Jml");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel15))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel1_CountTelahLunas, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1_CountTelahLunas, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(33, 159, 148));
        jPanel8.setPreferredSize(new java.awt.Dimension(348, 148));

        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel16.setText("Yang Belum Lunas");

        jLabel1_CountBelumLunas.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1_CountBelumLunas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_CountBelumLunas.setText("Jml");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel16))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel1_CountBelumLunas)))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1_CountBelumLunas, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 0));
        jLabel17.setText("Dashboard");

        javax.swing.GroupLayout jPanel_DashboardLayout = new javax.swing.GroupLayout(jPanel_Dashboard);
        jPanel_Dashboard.setLayout(jPanel_DashboardLayout);
        jPanel_DashboardLayout.setHorizontalGroup(
            jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DashboardLayout.createSequentialGroup()
                .addGroup(jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_DashboardLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17))
                    .addGroup(jPanel_DashboardLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addGroup(jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(160, Short.MAX_VALUE))
        );
        jPanel_DashboardLayout.setVerticalGroup(
            jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DashboardLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addGroup(jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(240, 240, 240))
        );

        jPanel_MainP.add(jPanel_Dashboard, "card2");

        jPanel_DataPeny.setBackground(new java.awt.Color(193, 222, 174));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Data Penyewa");

        jTable_DataPenyewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "NIK", "Nama", "Asal Kota", "Usia", "No. Telp", "E-Mail"
            }
        ));
        jScrollPane2.setViewportView(jTable_DataPenyewa);

        jPanel9.setBackground(new java.awt.Color(193, 222, 174));

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/house 5.png"))); // NOI18N
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextField_cariPenyewa.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        jTextField_cariPenyewa.setText("Cari Nama");
        jTextField_cariPenyewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_cariPenyewaMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField_cariPenyewaMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField_cariPenyewaMouseReleased(evt);
            }
        });

        jComboBoxBlok_DataPenyewa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Pilih Blok---" }));

        jTextField_nik.setEditable(false);

        jButton1.setText("Detail Info Penyewa");

        jButton2.setText("Edit Data Diri Penyewa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Berhentikan Penyewa Kos");

        jLabel_nik.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel_nik.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_nik.setText("NIK");

        jLabel_nik1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel_nik1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_nik1.setText("Nama Penyewa");

        jTextField_nik1.setEditable(false);

        jLabel_nik2.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel_nik2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_nik2.setText("Usia");

        jTextField_nik2.setEditable(false);

        jLabel_nik3.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel_nik3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_nik3.setText("Asal Kota");

        jTextField_nik3.setEditable(false);

        jLabel_nik4.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel_nik4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_nik4.setText("No. Telp");

        jTextField_nik4.setEditable(false);

        jLabel_nik5.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel_nik5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel_nik5.setText("E-Mail");

        jTextField_nik5.setEditable(false);

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Tahun");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/profile-user (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTextField_ImgAddress.setEditable(false);

        jButton_ImageChooser.setText("Pilih File");
        jButton_ImageChooser.setEnabled(false);

        jButton_batal.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 12)); // NOI18N
        jButton_batal.setText("Batal");
        jButton_batal.setEnabled(false);
        jButton_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_batalActionPerformed(evt);
            }
        });

        jButton_konfedit1.setFont(new java.awt.Font("Malgun Gothic Semilight", 1, 12)); // NOI18N
        jButton_konfedit1.setText("Konfirmasi Edit Data");
        jButton_konfedit1.setEnabled(false);
        jButton_konfedit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_konfedit1ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel_DataPenyLayout = new javax.swing.GroupLayout(jPanel_DataPeny);
        jPanel_DataPeny.setLayout(jPanel_DataPenyLayout);
        jPanel_DataPenyLayout.setHorizontalGroup(
            jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addComponent(jComboBoxBlok_DataPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(64, 64, 64)
                                .addComponent(jTextField_cariPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 716, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52)
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_nik)
                                    .addComponent(jTextField_nik, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_nik2)
                                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                        .addComponent(jTextField_nik2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel7)))
                                .addGap(51, 51, 51)
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_nik4)
                                    .addComponent(jTextField_nik4, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_nik1)
                                    .addComponent(jTextField_nik1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_nik3)
                                    .addComponent(jTextField_nik3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_nik5)
                                    .addComponent(jTextField_nik5, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(93, 93, 93)
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addComponent(jTextField_ImgAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_ImageChooser))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_konfedit1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(258, 258, 258))
        );
        jPanel_DataPenyLayout.setVerticalGroup(
            jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_cariPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxBlok_DataPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addGap(73, 73, 73)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                                                .addComponent(jLabel_nik)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField_nik, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                                                .addComponent(jLabel_nik2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jTextField_nik2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel7)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                                                .addComponent(jLabel_nik4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField_nik4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                                .addComponent(jLabel_nik1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField_nik1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                                .addComponent(jLabel_nik3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField_nik3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                        .addComponent(jLabel_nik5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField_nik5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(151, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_konfedit1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jButton_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(125, 125, 125))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_ImgAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_ImageChooser))
                        .addGap(62, 62, 62))))
        );

        jPanel_MainP.add(jPanel_DataPeny, "card3");

        jPanel_lprn.setBackground(new java.awt.Color(193, 222, 174));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/home.png"))); // NOI18N
        jLabel18.setText("jLabel18");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        jButton4.setText("Detail Tagihan Penyewa");

        jButton5.setText("Detail Laporan Pelanggaran");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Detail Tagihan Fasilitas");

        jPanel_Report.setBackground(new java.awt.Color(102, 102, 102));
        jPanel_Report.setBorder(javax.swing.BorderFactory.createTitledBorder("Laporan"));
        jPanel_Report.setLayout(new javax.swing.BoxLayout(jPanel_Report, javax.swing.BoxLayout.LINE_AXIS));

        jLabel28.setText("Diagram batang (lurus) antara Yang Lunas/ Tidak");
        jPanel_Report.add(jLabel28);

        jLabel30.setText("Pie Chart Berupa perbandingan antara kamar yang kosong dan penghuni");
        jPanel_Report.add(jLabel30);

        jButton7.setText("Show Chart");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel_Report.add(jButton7);

        javax.swing.GroupLayout jPanel_lprnLayout = new javax.swing.GroupLayout(jPanel_lprn);
        jPanel_lprn.setLayout(jPanel_lprnLayout);
        jPanel_lprnLayout.setHorizontalGroup(
            jPanel_lprnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_lprnLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(172, 172, 172)
                .addGroup(jPanel_lprnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel_Report, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_lprnLayout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_lprnLayout.setVerticalGroup(
            jPanel_lprnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_lprnLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_lprnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_lprnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_Report, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jPanel_MainP.add(jPanel_lprn, "card4");

        jPanel_BlkNK.setBackground(new java.awt.Color(193, 222, 174));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/house 5.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Pilih Blok");

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

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Daftar Kamar berdasarkan Blok");

        jTable_K_basedOnBlok.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jScrollPane1.setViewportView(jTable_K_basedOnBlok);

        jButton_aturBlok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/Component 11.png"))); // NOI18N
        jButton_aturBlok.setBorder(null);
        jButton_aturBlok.setOpaque(false);
        jButton_aturBlok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aturBlokActionPerformed(evt);
            }
        });

        jButton_aturKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/Component 12.png"))); // NOI18N
        jButton_aturKamar.setBorder(null);
        jButton_aturKamar.setOpaque(false);
        jButton_aturKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aturKamarActionPerformed(evt);
            }
        });

        jTextField_desc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_descActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Deskripsi");

        javax.swing.GroupLayout jPanel_BlkNKLayout = new javax.swing.GroupLayout(jPanel_BlkNK);
        jPanel_BlkNK.setLayout(jPanel_BlkNKLayout);
        jPanel_BlkNKLayout.setHorizontalGroup(
            jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jComboBox_Blok, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel20))
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jButton_aturBlok, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_aturKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(jTextField_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel_BlkNKLayout.setVerticalGroup(
            jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(34, 34, 34)
                        .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_Blok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_desc, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_aturBlok, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_aturKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(235, Short.MAX_VALUE))
        );

        jPanel_MainP.add(jPanel_BlkNK, "card5");

        jPanel_Trnsksee.setBackground(new java.awt.Color(193, 222, 174));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/home.png"))); // NOI18N
        jLabel21.setText("jLabel21");

        jPanel1.setBackground(new java.awt.Color(33, 159, 148));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(33, 159, 148));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel_TrnskseeLayout = new javax.swing.GroupLayout(jPanel_Trnsksee);
        jPanel_Trnsksee.setLayout(jPanel_TrnskseeLayout);
        jPanel_TrnskseeLayout.setHorizontalGroup(
            jPanel_TrnskseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TrnskseeLayout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addGroup(jPanel_TrnskseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_TrnskseeLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        jPanel_TrnskseeLayout.setVerticalGroup(
            jPanel_TrnskseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TrnskseeLayout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addGroup(jPanel_TrnskseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(344, Short.MAX_VALUE))
        );

        jPanel_MainP.add(jPanel_Trnsksee, "card6");

        javax.swing.GroupLayout jPanel_BodyLayout = new javax.swing.GroupLayout(jPanel_Body);
        jPanel_Body.setLayout(jPanel_BodyLayout);
        jPanel_BodyLayout.setHorizontalGroup(
            jPanel_BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BodyLayout.createSequentialGroup()
                .addComponent(jPanel_DropDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel_MainP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_BodyLayout.setVerticalGroup(
            jPanel_BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_DropDown, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
            .addComponent(jPanel_MainP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
      jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_DataPeny);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
     
       
     
      
      
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jPanel_TambahPenyewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TambahPenyewaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel_TambahPenyewaMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_lprn);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
   
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jPanel_LaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_LaporanMouseClicked
        // TODO add your handling code here:
      jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_lprn);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
    
    }//GEN-LAST:event_jPanel_LaporanMouseClicked

    private void jPanel_TambahBlok2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TambahBlok2MouseClicked
        // TODO add your handling code here:
    
    }//GEN-LAST:event_jPanel_TambahBlok2MouseClicked

    private void jPanel_TransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_TransaksiMouseClicked
        // TODO add your handling code here:
          jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_Trnsksee);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
    }//GEN-LAST:event_jPanel_TransaksiMouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
         jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_Dashboard);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
     jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_BlkNK);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // TODO add your handling code here:
        jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_Trnsksee);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jComboBox_BlokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_BlokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_BlokActionPerformed

    private void jComboBox_BlokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_BlokItemStateChanged
        // TODO add your handling code here:
        TableModelBlok();
        Desc();
    }//GEN-LAST:event_jComboBox_BlokItemStateChanged

    private void jTextField_descActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_descActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_descActionPerformed

    private void jButton_aturKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aturKamarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new AturKamar().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton_aturKamarActionPerformed

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        // TODO add your handling code here:
           jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_Dashboard);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jButton_aturBlokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aturBlokActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new AturBlok().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton_aturBlokActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        dcd.setValue(78.80, "Marks", "Ganesh");
        dcd.setValue(68.80, "Marks", "Danesh");
        dcd.setValue(88.80, "Marks", "Kirek");
        dcd.setValue(78.80, "Marks", "Ngehe");
        dcd.setValue(78.80, "Marks", "wewe");
        
        JFreeChart jchart = ChartFactory.createBarChart("Rekapan Siswa", "Nama Siswa", "Nilai Siswa", dcd, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = jchart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.black);
        ChartFrame cF = new ChartFrame("Rekap Siswa", jchart,true);
        cF.setVisible(true);
        cF.setSize(500,400);
        ChartPanel chartPanel = new ChartPanel(jchart);
        jPanel_Report.removeAll();
        jPanel_Report.add(chartPanel);
        jPanel_Report.updateUI();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      jTextField_nik.setEditable(true);
      jTextField_nik1.setEditable(true);
      jTextField_nik2.setEditable(true);
      jTextField_nik3.setEditable(true);
      jTextField_nik4.setEditable(true);
      jTextField_nik5.setEditable(true);
      jButton_batal.setEnabled(true);
      jButton2.setEnabled(false);
      jLabel23.setText("Ubah foto baru");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_batalActionPerformed
        // TODO add your handling code here:
         jTextField_nik.setEditable(false);
      jTextField_nik1.setEnabled(false);
      jTextField_nik2.setEnabled(false);
      jTextField_nik3.setEnabled(false);
      jTextField_nik4.setEnabled(false);
      jTextField_nik5.setEnabled(false);
      jButton_batal.setEnabled(false);
      jButton2.setEnabled(true);
    }//GEN-LAST:event_jButton_batalActionPerformed

    private void jButton_konfedit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_konfedit1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_konfedit1ActionPerformed

    private void jTextField_cariPenyewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_cariPenyewaMouseClicked
        // TODO add your handling code here:
        jTextField_cariPenyewa.setText("");
    }//GEN-LAST:event_jTextField_cariPenyewaMouseClicked

    private void jTextField_cariPenyewaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_cariPenyewaMouseExited
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jTextField_cariPenyewaMouseExited

    private void jTextField_cariPenyewaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_cariPenyewaMouseReleased
        // TODO add your handling code here:
     
    }//GEN-LAST:event_jTextField_cariPenyewaMouseReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new DaftarPelanggaran().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton_ImageChooser;
    private javax.swing.JButton jButton_aturBlok;
    private javax.swing.JButton jButton_aturKamar;
    private javax.swing.JButton jButton_batal;
    private javax.swing.JButton jButton_konfedit1;
    private javax.swing.JComboBox<String> jComboBoxBlok_DataPenyewa;
    private javax.swing.JComboBox<String> jComboBox_Blok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel1_CountBelumLunas;
    private javax.swing.JLabel jLabel1_CountKamar;
    private javax.swing.JLabel jLabel1_CountTelahLunas;
    private javax.swing.JLabel jLabel1_sumPenyewa;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_nik;
    private javax.swing.JLabel jLabel_nik1;
    private javax.swing.JLabel jLabel_nik2;
    private javax.swing.JLabel jLabel_nik3;
    private javax.swing.JLabel jLabel_nik4;
    private javax.swing.JLabel jLabel_nik5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_BlkNK;
    private javax.swing.JPanel jPanel_Body;
    private javax.swing.JPanel jPanel_Dashboard;
    private javax.swing.JPanel jPanel_DataPeny;
    private javax.swing.JPanel jPanel_DropDown;
    private javax.swing.JPanel jPanel_Laporan;
    private javax.swing.JPanel jPanel_MainP;
    private javax.swing.JPanel jPanel_Report;
    private javax.swing.JPanel jPanel_TambahBlok2;
    private javax.swing.JPanel jPanel_TambahPenyewa;
    private javax.swing.JPanel jPanel_Transaksi;
    private javax.swing.JPanel jPanel_Trnsksee;
    private javax.swing.JPanel jPanel_lprn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_DataPenyewa;
    private javax.swing.JTable jTable_K_basedOnBlok;
    private javax.swing.JTextField jTextField_ImgAddress;
    private javax.swing.JTextField jTextField_cariPenyewa;
    private javax.swing.JTextField jTextField_desc;
    private javax.swing.JTextField jTextField_nik;
    private javax.swing.JTextField jTextField_nik1;
    private javax.swing.JTextField jTextField_nik2;
    private javax.swing.JTextField jTextField_nik3;
    private javax.swing.JTextField jTextField_nik4;
    private javax.swing.JTextField jTextField_nik5;
    // End of variables declaration//GEN-END:variables
}
