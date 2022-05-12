/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimikostswing.mainview;

import java.awt.Color;
import mimikostswing.mainview.submenu.Foto;
import mimikostswing.mainview.submenu.DetailInfoPenyewa;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import mimikostswing.Konek;
import mimikostswing.mainview.submenu.LaporanTagihanPenyewa;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Alkin PC
 */
public class MainMenu extends javax.swing.JFrame {

    ResultSet r;
    Statement s;
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
        //ChartlilinDiagram();
        showTableDataPenyewa();
        pieChart();
    }

    
  
public void showTableDataPenyewa(){
    DefaultTableModel tb = new DefaultTableModel();
    tb.addColumn("Kode Blok");
    tb.addColumn("NIK");
    tb.addColumn("Nama");
    tb.addColumn("Asal Kota");
    tb.addColumn("Usia");
    tb.addColumn("No. Telp");
    tb.addColumn("E-Mail");
    jTable_DataPenyewa.setModel(tb);
    try {
        String sql ="SELECT tb_penyewa.NIK, tb_penyewa.nama_penyewa, tb_penyewa.asal_kota, tb_penyewa.usia, tb_penyewa.telp, tb_penyewa.email, tb_blok.kode_blok FROM tb_penyewa JOIN tb_blok ON tb_blok.kode_blok=tb_penyewa.kode_blok";
        String newQuery="SELECT tb_penyewa.NIK, tb_penyewa.nama_penyewa, tb_penyewa.asal_kota, tb_penyewa.usia, tb_penyewa.telp, tb_penyewa.email, tb_blok.kode_blok FROM tb_penyewa JOIN tb_blok ON tb_blok.kode_blok=tb_penyewa.kode_blok WHERE tb_blok.kode_blok='"+jComboBox_Blok.getSelectedItem().toString()+"' AND tb_blok.kode_blok='"+jComboBox_Blok.getSelectedItem().toString()+"'";
        java.sql.Statement stm = (Statement)Konek.getConnection().createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while (rs.next()) {            
            tb.addRow(new Object[]{
                rs.getString("kode_blok"),
                rs.getString("NIK"),
                rs.getString("nama_penyewa"),
                rs.getString("asal_kota"),
                rs.getString("usia"),
                rs.getString("telp"),
                rs.getString("email")
            });
            
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
    }
    
}
    

//    private void ChartlilinDiagram(){
//        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
//        dcd.setValue(78.80, "Mark", "Yudhi");
//        dcd.setValue(60, "Mark", "Yunus");
//        dcd.setValue(59, "Mark", "Yusron");
//        JFreeChart jChart = ChartFactory.createBarChart("Kunyuk", "Nama Siswa", "Nilai", dcd, PlotOrientation.VERTICAL, true, true, false);
//        CategoryPlot plot = jChart.getCategoryPlot();
//        plot.setRangeCrosshairPaint(Color.BLACK);
//        
//        ChartFrame chartFrame = new ChartFrame("Nilai", jChart,Boolean.TRUE);
//        chartFrame.setVisible(false);
//        chartFrame.setSize(jPanel1_laporan.getWidth(), jPanel1_laporan.getHeight());
//        ChartPanel chartPanel = new ChartPanel(jChart);
//        jPanel1_laporan.removeAll();
//        jPanel1_laporan.add(chartPanel);
//        jPanel1_laporan.updateUI();
//    }
    
    
    private void pieChart(){
        DefaultPieDataset dps = new DefaultPieDataset();
        
//        String TotalPenyewa ="SELECT COUNT(*) AS 'jumlah' FROM tb_penyewa";
//        model.SetterGetter.setTotalPenyewaForChart(TotalPenyewa);
//        dps.setValue("Total Penghuni", Float.valueOf(SetterGetter.getTotalPenyewaForChart()));
        String countTotalPenyewaBasedOnBlok="SELECT tb_blok.kode_blok, COUNT(tb_penyewa.NIK) AS jumlah FROM tb_penyewa LEFT JOIN tb_blok ON tb_blok.kode_blok = tb_penyewa.kode_blok GROUP BY tb_blok.kode_blok";
        try { 
            Statement s =(Statement)Konek.getConnection().createStatement();
            ResultSet r =s.executeQuery(countTotalPenyewaBasedOnBlok);
            while (r.next()) {                
                dps.setValue(r.getString("kode_blok"), Float.valueOf(r.getString("jumlah")));
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        JFreeChart chart = ChartFactory.createPieChart("Presentasi Perbandingan Jumlah Penghuni Berdasarkan Blok", dps,true, true, false);
        ChartPanel cpnl = new ChartPanel(chart);
        jPanel5.removeAll();
        jPanel5.add(cpnl);
        jPanel5.updateUI();
    }
    
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
             } else{
                 jLabel1_CountBelumLunas.setText("Kosong");
             }
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
         }
    }
    
    // bwt ngisi combobox (blok) yang ada di form
    private void comboBoxBlok(){
        
        try {
            Statement statement = (Statement)Konek.getConnection().createStatement();
            ResultSet res = statement.executeQuery("SELECT kode_blok FROM tb_blok");
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
        tbl.addColumn("Kode Blok");
        tbl.addColumn("No Kamar");
        tbl.addColumn("Jumlah Penghuni");
        jTable_K_basedOnBlok.setModel(tbl);
        
        try {
            Statement statement =(Statement)Konek.getConnection().createStatement();
            String sql ="SELECT no_kamar,jumlah_penghuni FROM tb_kamar WHERE kode_blok='"+jComboBox_Blok.getSelectedItem().toString()+"'";
            String sqq ="SELECT tb_blok.kode_blok, tb_kamar.no_kamar , COUNT( tb_penyewa.nama_penyewa) AS jumlah FROM tb_blok INNER JOIN tb_kamar ON tb_blok.kode_blok = tb_kamar.kode_blok RIGHT JOIN tb_penyewa ON tb_penyewa.id_kamar = tb_kamar.id_kamar GROUP BY tb_kamar.no_kamar";
            ResultSet res = statement.executeQuery(sqq);
            while (res.next()) {                
                tbl.addRow(new Object[]{
                    res.getString("kode_blok"),
                    res.getString("no_kamar"),
                    res.getString("jumlah")+(" orang"),
                });
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"ERROR: " +e.getMessage());
        }
        
        
    }
    //Todo Harus beres besok!!!!!
    private void DetectorLunasTagihan(){
      
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy MM dd");
    
        try {
            LocalDate ld =LocalDate.now();
            String sql="SELECT tanggal_bayar FROM tb_tagihan_penyewa";
            s =(Statement)Konek.getConnection().createStatement();
            r = s.executeQuery(sql);
            if (r.next()) {
//                if ((r.getDate("tanggal_bayar")) => (LocalDate.now())) {
//                    
//                }
            }
 {
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    
    }
    
    public void Desc(){
          try {
        java.sql.Statement stm = (Statement)Konek.getConnection().createStatement();
        java.sql.ResultSet rs = stm.executeQuery("SELECT deskripsi, harga FROM tb_blok WHERE kode_blok='"+jComboBox_Blok.getSelectedItem().toString()+"'");
            if (rs.next()) {
        jTextArea_Deskripsi.setText(rs.getString("deskripsi"));
        jTextField1.setText(rs.getString("harga"));
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
        jLabel21 = new javax.swing.JLabel();
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
        jPanel12 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel_DataPeny = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_DataPenyewa = new javax.swing.JTable();
        jTextField_cariPenyewa = new javax.swing.JTextField();
        jButton_DetailInfoPenyewa = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
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
        jButton_batal = new javax.swing.JButton();
        jButton_konfedit1 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jComboBoxBlok_DataPenyewa = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel_lprn = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel_BlkNK = new javax.swing.JPanel();
        jComboBox_Blok = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_K_basedOnBlok = new javax.swing.JTable();
        jButton_aturBlok = new javax.swing.JButton();
        jButton_aturKamar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea_Deskripsi = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel_Trnsksee = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel_Body.setBackground(new java.awt.Color(149, 165, 166));

        jPanel_DropDown.setBackground(new java.awt.Color(41, 148, 0));
        jPanel_DropDown.setPreferredSize(new java.awt.Dimension(239, 735));

        jPanel_TambahPenyewa.setBackground(new java.awt.Color(41, 148, 0));
        jPanel_TambahPenyewa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_TambahPenyewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_TambahPenyewaMouseClicked(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/Mask group.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanel_TambahBlok2.setBackground(new java.awt.Color(41, 148, 0));
        jPanel_TambahBlok2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_TambahBlok2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_TambahBlok2MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/Mask group (2).png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
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

        jPanel_Transaksi.setBackground(new java.awt.Color(41, 148, 0));
        jPanel_Transaksi.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_Transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_TransaksiMouseClicked(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/Mask group (3).png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
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

        jPanel_Laporan.setBackground(new java.awt.Color(41, 148, 0));
        jPanel_Laporan.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_Laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_LaporanMouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/Mask group (1).png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
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

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Eras Bold ITC", 2, 36)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("MimiKost");

        javax.swing.GroupLayout jPanel_DropDownLayout = new javax.swing.GroupLayout(jPanel_DropDown);
        jPanel_DropDown.setLayout(jPanel_DropDownLayout);
        jPanel_DropDownLayout.setHorizontalGroup(
            jPanel_DropDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DropDownLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
            .addGroup(jPanel_DropDownLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel_DropDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel_TambahPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_DropDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel_Transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel_TambahBlok2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel_Laporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_DropDownLayout.setVerticalGroup(
            jPanel_DropDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DropDownLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel_TambahPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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

        jPanel_Dashboard.setBackground(new java.awt.Color(236, 236, 236));

        jPanel2.setBackground(new java.awt.Color(33, 159, 148));
        jPanel2.setPreferredSize(new java.awt.Dimension(348, 148));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Total Keseluruhan Penyewa");

        jLabel1_sumPenyewa.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1_sumPenyewa.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1_sumPenyewa.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1_sumPenyewa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_sumPenyewa.setText("Jml");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jLabel1_sumPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13)))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1_sumPenyewa, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(33, 159, 148));

        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Jumlah Kamar Kos");

        jLabel1_CountKamar.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1_CountKamar.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1_CountKamar.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1_CountKamar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_CountKamar.setText("Jml");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabel1_CountKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)))
                .addContainerGap(134, Short.MAX_VALUE))
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

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Yang Telah Lunas");

        jLabel1_CountTelahLunas.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1_CountTelahLunas.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1_CountTelahLunas.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1_CountTelahLunas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_CountTelahLunas.setText("Jml");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel15))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel1_CountTelahLunas, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(115, Short.MAX_VALUE))
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

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Yang Belum Lunas");

        jLabel1_CountBelumLunas.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1_CountBelumLunas.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel1_CountBelumLunas.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1_CountBelumLunas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1_CountBelumLunas.setText("Jml");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel1_CountBelumLunas))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)))
                .addContainerGap(131, Short.MAX_VALUE))
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

        jPanel12.setBackground(new java.awt.Color(108, 108, 108));
        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Dashboard");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel19.setFont(new java.awt.Font("Dialog", 2, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Aplikasi masih dalam tahap pengembangan, hasil akhir mungkin berbeda");

        javax.swing.GroupLayout jPanel_DashboardLayout = new javax.swing.GroupLayout(jPanel_Dashboard);
        jPanel_Dashboard.setLayout(jPanel_DashboardLayout);
        jPanel_DashboardLayout.setHorizontalGroup(
            jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DashboardLayout.createSequentialGroup()
                .addContainerGap(130, Short.MAX_VALUE)
                .addGroup(jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(232, 232, 232))
            .addGroup(jPanel_DashboardLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_DashboardLayout.setVerticalGroup(
            jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DashboardLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(155, 155, 155)
                .addGroup(jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jPanel_MainP.add(jPanel_Dashboard, "card2");

        jPanel_DataPeny.setBackground(new java.awt.Color(236, 236, 236));

        jTable_DataPenyewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "kode_blok", "NIK", "Nama", "Asal Kota", "Usia", "No. Telp", "E-Mail"
            }
        ));
        jTable_DataPenyewa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_DataPenyewaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_DataPenyewa);
        if (jTable_DataPenyewa.getColumnModel().getColumnCount() > 0) {
            jTable_DataPenyewa.getColumnModel().getColumn(4).setPreferredWidth(25);
        }

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
        jTextField_cariPenyewa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_cariPenyewaKeyReleased(evt);
            }
        });

        jButton_DetailInfoPenyewa.setText("Detail Info Penyewa");
        jButton_DetailInfoPenyewa.setEnabled(false);
        jButton_DetailInfoPenyewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_DetailInfoPenyewaActionPerformed(evt);
            }
        });

        jButton2.setText("Edit Data Diri Penyewa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Berhentikan Penyewa Kos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

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
            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 198, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
        );

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

        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setText("*untuk foto, silahkan pilih dengan extensi jpg dan png ");

        jComboBoxBlok_DataPenyewa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Silahkan Pilih Blok---" }));
        jComboBoxBlok_DataPenyewa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxBlok_DataPenyewaItemStateChanged(evt);
            }
        });
        jComboBoxBlok_DataPenyewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBlok_DataPenyewaActionPerformed(evt);
            }
        });

        jButton7.setText("Klik disini untuk mengubah foto");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(108, 108, 108));

        jPanel9.setBackground(new java.awt.Color(108, 108, 108));

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

        jLabel31.setBackground(new java.awt.Color(255, 255, 255));
        jLabel31.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Data Penyewa");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99)
                .addComponent(jLabel31)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_DataPenyLayout = new javax.swing.GroupLayout(jPanel_DataPeny);
        jPanel_DataPeny.setLayout(jPanel_DataPenyLayout);
        jPanel_DataPenyLayout.setHorizontalGroup(
            jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jComboBoxBlok_DataPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jTextField_cariPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_DetailInfoPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_nik1)
                                    .addComponent(jTextField_nik1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                    .addComponent(jLabel_nik3)
                                    .addComponent(jTextField_nik3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_nik5)
                                    .addComponent(jTextField_nik5, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton_konfedit1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(171, Short.MAX_VALUE))
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel27)
                .addGap(324, 324, 324))
        );
        jPanel_DataPenyLayout.setVerticalGroup(
            jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxBlok_DataPenyewa, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(jTextField_cariPenyewa))
                .addGap(55, 55, 55)
                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addComponent(jButton_DetailInfoPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                                .addComponent(jLabel_nik2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField_nik2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                                .addComponent(jLabel_nik4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_nik4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_DataPenyLayout.createSequentialGroup()
                                .addComponent(jLabel_nik1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_nik1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_DataPenyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addComponent(jLabel_nik3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_nik3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                                .addComponent(jLabel_nik5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField_nik5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_DataPenyLayout.createSequentialGroup()
                        .addComponent(jButton_konfedit1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel_MainP.add(jPanel_DataPeny, "card3");

        jPanel_lprn.setBackground(new java.awt.Color(236, 236, 236));

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jLabel25.setText("Pie Diagram ");
        jPanel5.add(jLabel25);

        jButton4.setText("Detail Tagihan Penyewa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Laporan Pelanggaran");
        jButton5.setPreferredSize(new java.awt.Dimension(150, 25));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Detail Tagihan Fasilitas");
        jButton6.setPreferredSize(new java.awt.Dimension(150, 25));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jPanel13.setBackground(new java.awt.Color(108, 108, 108));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Laporan");

        jPanel14.setBackground(new java.awt.Color(108, 108, 108));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/house 3.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel_lprnLayout = new javax.swing.GroupLayout(jPanel_lprn);
        jPanel_lprn.setLayout(jPanel_lprnLayout);
        jPanel_lprnLayout.setHorizontalGroup(
            jPanel_lprnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_lprnLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_lprnLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 933, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154))
        );
        jPanel_lprnLayout.setVerticalGroup(
            jPanel_lprnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_lprnLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addGroup(jPanel_lprnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jPanel_MainP.add(jPanel_lprn, "card4");

        jPanel_BlkNK.setBackground(new java.awt.Color(236, 236, 236));

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
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Kode Blok", "No. Kamar", "Jumlah Penghuni"
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

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Fasilitas");

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 0, 0));
        jLabel24.setText("Sewa Per-bulan");

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        jTextArea_Deskripsi.setColumns(20);
        jTextArea_Deskripsi.setRows(5);
        jScrollPane3.setViewportView(jTextArea_Deskripsi);

        jPanel15.setBackground(new java.awt.Color(108, 108, 108));

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Blok dan Kamar");

        jPanel16.setBackground(new java.awt.Color(108, 108, 108));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/house 3.png"))); // NOI18N
        jLabel32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel32MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Rp. ");

        javax.swing.GroupLayout jPanel_BlkNKLayout = new javax.swing.GroupLayout(jPanel_BlkNK);
        jPanel_BlkNK.setLayout(jPanel_BlkNKLayout);
        jPanel_BlkNKLayout.setHorizontalGroup(
            jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                                .addComponent(jComboBox_Blok, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jButton_aturBlok, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton_aturKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4)))))
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel22)))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        jPanel_BlkNKLayout.setVerticalGroup(
            jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox_Blok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_BlkNKLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_BlkNKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton_aturBlok, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_aturKamar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(205, 205, 205))
        );

        jPanel_MainP.add(jPanel_BlkNK, "card5");

        jPanel_Trnsksee.setBackground(new java.awt.Color(236, 236, 236));

        jLabel30.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setText("Silahkan Pilih Salah Satu Transaksi yang Ingin Anda Lakukan");

        jPanel17.setBackground(new java.awt.Color(108, 108, 108));

        jLabel33.setBackground(new java.awt.Color(255, 255, 255));
        jLabel33.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Transaksi");

        jPanel18.setBackground(new java.awt.Color(108, 108, 108));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/house 3.png"))); // NOI18N
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jButton1.setBackground(new java.awt.Color(33, 159, 148));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton1.setText("Perpanjang Sewa Kos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(33, 159, 148));
        jButton8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton8.setText("Reservasi Kamar untuk Penyewa Baru");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_TrnskseeLayout = new javax.swing.GroupLayout(jPanel_Trnsksee);
        jPanel_Trnsksee.setLayout(jPanel_TrnskseeLayout);
        jPanel_TrnskseeLayout.setHorizontalGroup(
            jPanel_TrnskseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_TrnskseeLayout.createSequentialGroup()
                .addGap(0, 145, Short.MAX_VALUE)
                .addComponent(jLabel30)
                .addGap(248, 248, 248))
            .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_TrnskseeLayout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jButton8)
                .addGap(38, 38, 38)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_TrnskseeLayout.setVerticalGroup(
            jPanel_TrnskseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TrnskseeLayout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(176, 176, 176)
                .addGroup(jPanel_TrnskseeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(168, Short.MAX_VALUE))
        );

        jPanel_MainP.add(jPanel_Trnsksee, "card6");

        javax.swing.GroupLayout jPanel_BodyLayout = new javax.swing.GroupLayout(jPanel_Body);
        jPanel_Body.setLayout(jPanel_BodyLayout);
        jPanel_BodyLayout.setHorizontalGroup(
            jPanel_BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_BodyLayout.createSequentialGroup()
                .addComponent(jPanel_DropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_MainP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_BodyLayout.setVerticalGroup(
            jPanel_BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_DropDown, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
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
     // ChartlilinDiagram();
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
       // TableModelBlok();
//       String kodeBl =jComboBox_Blok.getSelectedItem().toString();
//        TableRowSorter tr = new TableRowSorter(jTable_K_basedOnBlok.getModel());
//        jTable_K_basedOnBlok.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(kodeBl, 0));
        Desc();
    }//GEN-LAST:event_jComboBox_BlokItemStateChanged

    private void jButton_aturKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aturKamarActionPerformed
        // TODO add your handling code here:
        new AturKamarNew().setVisible(true);
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
        new mimikostswing.mainview.submenu.BlokNFasilitas().setVisible(true);
        
    }//GEN-LAST:event_jButton_aturBlokActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      //jTextField_nik.setEditable(true);
      jTextField_nik1.setEditable(true);
      jTextField_nik2.setEditable(true);
      jTextField_nik3.setEditable(true);
      jTextField_nik4.setEditable(true);
      jTextField_nik5.setEditable(true);
      jButton_batal.setEnabled(true);
      //jButton_ImageChooser.setEnabled(true);
      
      jButton3.setEnabled(false);
      jButton2.setEnabled(false);
      jButton_konfedit1.setEnabled(true);
      //jLabel23.setText("Ubah foto baru");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_batalActionPerformed
        // TODO add your handling code here:
         //jTextField_nik.setEditable(false);
      jTextField_nik1.setEditable(false);
      jTextField_nik2.setEditable(false);
      jTextField_nik3.setEditable(false);
      jTextField_nik4.setEditable(false);
      jTextField_nik5.setEditable(false);
//      jButton_ImageChooser.setEnabled(false);
      jButton_batal.setEnabled(false);
      jButton_konfedit1.setEnabled(false);
      jButton2.setEnabled(true);
      jButton3.setEnabled(true);
    }//GEN-LAST:event_jButton_batalActionPerformed

    private void jButton_konfedit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_konfedit1ActionPerformed
        // TODO add your handling code here:
        int row = jTable_DataPenyewa.getSelectedRow();
        String NIK =jTable_DataPenyewa.getValueAt(row, 1).toString();
        String Nama = jTextField_nik1.getText();
        String usia = jTextField_nik2.getText();
        String asal=jTextField_nik3.getText();
        String email=jTextField_nik5.getText();
        String no=jTextField_nik4.getText();
       // String gmbr =jTextField_ImgAddress.getText();
        String kodBlok =jTable_DataPenyewa.getValueAt(row, 0).toString();
      //jTextField_nik.setEditable(false);
      jTextField_nik1.setEditable(false);
      jTextField_nik2.setEditable(false);
      jTextField_nik3.setEditable(false);
      jTextField_nik4.setEditable(false);
      jTextField_nik5.setEditable(false);
     // jButton_ImageChooser.setEnabled(false);
      jButton_batal.setEnabled(false);
        try {
      //InputStream is = new FileInputStream(new File(gmbr));
      String SQL ="UPDATE tb_penyewa SET nama_penyewa='"+Nama+"', usia='"+usia+"', asal_kota='"+asal+"', telp='"+no+"', email='"+jTextField_nik5.getText()+"' WHERE NIK='"+NIK+"'";
      Connection conn =(Connection)mimikostswing.Config.configDB();
      PreparedStatement pst =conn.prepareStatement(SQL);
      //pst.setBinaryStream(1, is);
      pst.execute();
      JOptionPane.showMessageDialog(this, "Data telah berhasil di edit");
      showTableDataPenyewa();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
            System.err.print(e);
        }
      
      jButton_konfedit1.setEnabled(false);
      
      jButton2.setEnabled(true);
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

    private void jTable_DataPenyewaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_DataPenyewaMouseClicked
        // TODO add your handling code here:
        
        //Kode bwt bkin klo semisal ngeklik record data di tabel, jd lgsung ke-isi di textfield 
        int row = jTable_DataPenyewa.getSelectedRow();
        String NIK = jTable_DataPenyewa.getValueAt(row, 1).toString();
        String Nama = jTable_DataPenyewa.getValueAt(row, 2).toString();
        String alamat =jTable_DataPenyewa.getValueAt(row, 3).toString();
        String usia =jTable_DataPenyewa.getValueAt(row, 4).toString();
        String telp =jTable_DataPenyewa.getValueAt(row, 5).toString();
        String email =jTable_DataPenyewa.getValueAt(row, 6).toString();
        if (jTable_DataPenyewa.getValueAt(row, 6)==null) {
            jTextField_nik5.setText("");
        }
        //String email=jTable_DataPenyewa.getValueAt(row, 5).toString();
       
        //jTextField_nik.setText(NIK);
        jTextField_nik1.setText(Nama);
        jTextField_nik2.setText(usia);
        jTextField_nik3.setText(alamat);
        jTextField_nik4.setText(telp);
        jTextField_nik5.setText(email);
        jButton_DetailInfoPenyewa.setEnabled(true);
        //jTextField_nik5.setText(email);
        
        
        try {
        String sql="SELECT foto FROM tb_penyewa WHERE NIK='"+NIK+"'";
        java.sql.Connection conn=(Connection)mimikostswing.Config.configDB();
        java.sql.Statement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs =pst.executeQuery(sql);
            if (rs.next()) {
                BufferedImage im = ImageIO.read(rs.getBinaryStream("foto"));
                ImageIcon imic = new ImageIcon(im);
                Image imm = imic.getImage();
                Image setImage = imm.getScaledInstance(jLabel8.getWidth(), jLabel8.getHeight(), Image.SCALE_SMOOTH);
                jLabel8.setIcon(new ImageIcon(setImage));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+e.getMessage());
        }
        
    }//GEN-LAST:event_jTable_DataPenyewaMouseClicked

    private void jComboBoxBlok_DataPenyewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxBlok_DataPenyewaActionPerformed
        // TODO add your handling code here:
       
       //RowFilter<DefaultTableModel,Object> rf =RowFilter.regexFilter(string, ints)
        //System.out.println(test);
    }//GEN-LAST:event_jComboBoxBlok_DataPenyewaActionPerformed

    private void jComboBoxBlok_DataPenyewaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxBlok_DataPenyewaItemStateChanged
        // TODO add your handling code here:
        String kodeBl =jComboBoxBlok_DataPenyewa.getSelectedItem().toString();
        TableRowSorter tr = new TableRowSorter(jTable_DataPenyewa.getModel());
        jTable_DataPenyewa.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(kodeBl, 0));
        if (jComboBoxBlok_DataPenyewa.getSelectedItem().equals("---Silahkan Pilih Blok---")) {
            jTable_DataPenyewa.setRowSorter(new TableRowSorter<>(jTable_DataPenyewa.getModel()));
        }
    }//GEN-LAST:event_jComboBoxBlok_DataPenyewaItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new LaporanTagihanPenyewa().setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        
        int row = jTable_DataPenyewa.getSelectedRow();
        String NIK = jTable_DataPenyewa.getValueAt(row, 1).toString();
        model.SetterGetter.setNIK(NIK);
        new Foto().setVisible(true);
       // jButton7.setEnabled(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton_DetailInfoPenyewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_DetailInfoPenyewaActionPerformed
        // TODO add your handling code here:
        int row = jTable_DataPenyewa.getSelectedRow();
        String NIK = jTable_DataPenyewa.getValueAt(row, 1).toString();
        model.SetterGetter.setNIK(NIK);
        new DetailInfoPenyewa().setVisible(true);
       
        
    }//GEN-LAST:event_jButton_DetailInfoPenyewaActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       int response = JOptionPane.showConfirmDialog(this,"Apakah anda benar-benar ingin mengeluarkan penyewa ini dari sistem kos?", "Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        
        if (response==JOptionPane.YES_OPTION) {
        try{    
          int row = jTable_DataPenyewa.getSelectedRow();
        String NIK = jTable_DataPenyewa.getValueAt(row, 1).toString();
        String SQL ="DELETE FROM tb_penyewa WHERE NIK='"+NIK+"'";
        Connection conn=(Connection)mimikostswing.Config.configDB();
        PreparedStatement ps = conn.prepareStatement(SQL);
        ps.execute(SQL);
        JOptionPane.showMessageDialog(this, "Data telah berhasil dihapus");
        showTableDataPenyewa();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
        }
        }
       
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
         this.setVisible(false);
        new ReservasiKamarKosNew().setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
          this.setVisible(false);
        new PerpanjangKos().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        new LaporanPelanggaran().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        new TagihanFasilitas().setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField_cariPenyewaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_cariPenyewaKeyReleased
        // TODO add your handling code here:
          String find =jTextField_cariPenyewa.getText();
        TableRowSorter tr = new TableRowSorter(jTable_DataPenyewa.getModel());
        jTable_DataPenyewa.setRowSorter(tr);
        
        tr.setRowFilter(RowFilter.regexFilter(find, 2));
    }//GEN-LAST:event_jTextField_cariPenyewaKeyReleased

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        // TODO add your handling code here:
           jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_Dashboard);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
    }//GEN-LAST:event_jLabel34MouseClicked

    private void jLabel32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel32MouseClicked
        // TODO add your handling code here:
           jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_Dashboard);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
    }//GEN-LAST:event_jLabel32MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
           jPanel_MainP.removeAll();
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
      
      jPanel_MainP.add(jPanel_Dashboard);
      jPanel_MainP.repaint();
      jPanel_MainP.revalidate();
    }//GEN-LAST:event_jLabel5MouseClicked

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
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton_DetailInfoPenyewa;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_nik1;
    private javax.swing.JLabel jLabel_nik2;
    private javax.swing.JLabel jLabel_nik3;
    private javax.swing.JLabel jLabel_nik4;
    private javax.swing.JLabel jLabel_nik5;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
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
    private javax.swing.JPanel jPanel_TambahBlok2;
    private javax.swing.JPanel jPanel_TambahPenyewa;
    private javax.swing.JPanel jPanel_Transaksi;
    private javax.swing.JPanel jPanel_Trnsksee;
    private javax.swing.JPanel jPanel_lprn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable_DataPenyewa;
    private javax.swing.JTable jTable_K_basedOnBlok;
    private javax.swing.JTextArea jTextArea_Deskripsi;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField_cariPenyewa;
    private javax.swing.JTextField jTextField_nik1;
    private javax.swing.JTextField jTextField_nik2;
    private javax.swing.JTextField jTextField_nik3;
    private javax.swing.JTextField jTextField_nik4;
    private javax.swing.JTextField jTextField_nik5;
    // End of variables declaration//GEN-END:variables
}
