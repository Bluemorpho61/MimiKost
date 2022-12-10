/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimikostswing.mainview;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import mimikostswing.Config;
import mimikostswing.Konek;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Alkin PC
 */
public class PerpanjangSewa extends javax.swing.JFrame {

    /**
     * Creates new form PerpanjangSewa
     */
    public PerpanjangSewa() {
        initComponents();
        showTable();
       // hitung();
        showCombobox();
         jDateChooser_dari.setDate(Date.from(Instant.now()));
    }
    public void bayar(){
         SimpleDateFormat sdf = new SimpleDateFormat("M");
        SimpleDateFormat thn = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfF = new SimpleDateFormat("yyyy-MM-dd");
        String bulan = sdf.format(jDateChooser_hingga.getDate());
        String tahun = thn.format(jDateChooser_hingga.getDate());
        String tglAwal=sdfF.format(jDateChooser_dari.getDate());
        String tglAkhir =sdfF.format(jDateChooser_hingga.getDate());
        
        LocalDate ld = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String tgSkrg = df.format(ld);
        String kdKTP = jTextField_NIK.getText();
        try {
          //  String SQL="UPDATE `tb_tagihan_penyewa` SET `id_bulan` = '"+bulan+"', `tahun` = '"+tahun+"', `jumlah_tagihan` = '"+jTextField_nominal.getText()+"', `status` = 'Terbayar' ,`tanggal_bayar` = '"+tgSkrg+"' WHERE `tb_tagihan_penyewa`.`kode_ktp` ="+kdKTP+";";
            //String sqll="UPDATE 'tb_transaksi' SET start_tgl_sewa='"+tglAwal+"', end_tgl_sewa='"+tglAkhir+"', total_hari='"+Integer.parseInt(jTextField_total.getText())+"', totalHarga='"+Integer.parseInt(jTextField_nominal.getText())+"', status='Terbayar' WHERE kode_ktp="+kdKTP+";";
            String sql ="UPDATE `tb_transaksi` SET `start_tgl_sewa` = '"+tglAwal+"', `end_tgl_sewa` = '"+tglAkhir+"', `status` = 'Terbayar' WHERE `tb_transaksi`.`kode_ktp` = "+kdKTP+";";
            java.sql.Connection con =(Connection)Config.configDB();
           java.sql.PreparedStatement ps =con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Transaksi Perpanjangan Sewa Berhasil!");
            showTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            System.err.println(e.getMessage());
        }
    }
      public void showCombobox(){
       
        try {
            Statement stm = (Statement)Konek.getConnection().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM tb_blok");
            
            while (rs.next()) {
               jComboBox_blok.addItem(rs.getString("kode_blok"));                
                //rs.geto
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
        }
        
        
    }
     public void showHarga(){
        try {
            int row = jTable_Perpanjang.getSelectedRow();
        String kb =jTable_Perpanjang.getValueAt(row, 0).toString();
        String sql ="SELECT harga FROM tb_blok WHERE kode_blok='"+kb+"'";
        Statement st =(Statement)Konek.getConnection().createStatement();
        ResultSet r =st.executeQuery(sql);
            while (r.next()) {
                //jTextField_hargaBlok.setText(r.getString("harga"));
                jLabel_BiayaBlok.setText(r.getString("harga"));
            }
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public void showTable(){
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("Kode Blok");
        tb.addColumn("Kode KTP");
        tb.addColumn("Nama");
        tb.addColumn("No. Kamar");
        tb.addColumn("Status Bulan Ini");
        jTable_Perpanjang.setModel(tb);
        try {
        String sql ="SELECT tb_blok.kode_blok, tb_penyewa.kode_ktp, tb_penyewa.nama_penyewa, tb_kamar.no_kamar FROM tb_blok JOIN tb_kamar ON tb_blok.kode_blok = tb_kamar.kode_blok JOIN tb_penyewa ON tb_penyewa.id_kamar = tb_kamar.id_kamar";
        String sqll="SELECT tb_blok.kode_blok, tb_penyewa.kode_ktp, tb_penyewa.nama_penyewa, tb_kamar.no_kamar FROM tb_penyewa JOIN tb_blok ON tb_penyewa.kode_blok = tb_blok.kode_blok JOIN tb_kamar ON tb_blok.kode_blok = tb_kamar.kode_blok GROUP BY tb_penyewa.nama_penyewa";
        String sqq="SELECT tb_blok.kode_blok, tb_penyewa.kode_ktp, tb_penyewa.nama_penyewa, tb_kamar.no_kamar, tb_transaksi.status FROM tb_penyewa JOIN tb_blok ON tb_penyewa.kode_blok = tb_blok.kode_blok JOIN tb_transaksi ON tb_penyewa.kode_ktp = tb_transaksi.kode_ktp JOIN tb_kamar ON tb_blok.kode_blok = tb_kamar.kode_blok GROUP BY tb_penyewa.nama_penyewa";
        Statement stm = (Statement)Konek.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sqq);
            while (rs.next()) {
                tb.addRow(new Object[]{
                    rs.getString("kode_blok"),
                    rs.getString("kode_ktp"),
                    rs.getString("nama_penyewa"),
                    rs.getString("no_kamar"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
        }
       
                 
        
    }

    private void hitung(){
         try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strtg11 = sdf.format(jDateChooser_dari.getDate());
        String strtgl2 =sdf.format(jDateChooser_hingga.getDate());
        Date tg11 = sdf.parse(strtg11);
        Date tgl2 =sdf.parse(strtgl2);
        long hari1 = tg11.getTime();
             System.out.println("Hari1: "+hari1);
        long hari2 = tgl2.getTime();
             System.out.println("Hari2: " +hari2);
        long diff = hari2 - hari1;
        long lama = diff / (24*60*60*1000);
        String total = (Long.toString(lama));
        jTextField_total.setText(total);
        
        
        
        int harga_blok = Integer.parseInt(jLabel_BiayaBlok.getText());
        int lamaHari = Integer.parseInt(jTextField_total.getText());
        int TOTAL = harga_blok *lamaHari;
        String totall = Integer.toString(TOTAL);
        jLabel_txtTotalBiaya.setText("Total biaya yang harus dibayarkan: ");
        jLabel_txtTotalBiaya1.setText("Rp. ");
        jLabel_txtTotalBiaya2.setText(totall);
        
        //jTextField_biaya.setText(totall);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Perpanjang = new javax.swing.JTable();
        panelRound1 = new model.panelRound();
        jTextField_cariNM = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBox_blok = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        panelRound2 = new model.panelRound();
        jTextField_NIK = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        panelRound3 = new model.panelRound();
        jTextField_Nama = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        panelRound4 = new model.panelRound();
        jTextField_k_blok = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelRound5 = new model.panelRound();
        jTextField_noKam = new javax.swing.JTextField();
        jLabel_BiayaBlok = new javax.swing.JLabel();
        jLabel_Rp = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser_dari = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser_hingga = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jTextField_total = new javax.swing.JTextField();
        panelRound6 = new model.panelRound();
        jTextField_nominal = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel_txtTotalBiaya = new javax.swing.JLabel();
        jLabel_txtTotalBiaya1 = new javax.swing.JLabel();
        jLabel_txtTotalBiaya2 = new javax.swing.JLabel();
        jLabel_tknEnter = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel_txtKembalian = new javax.swing.JLabel();
        jLabel_RpKembalian = new javax.swing.JLabel();
        jLabel1_NomKembali = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(236, 236, 236));

        jPanel2.setBackground(new java.awt.Color(41, 148, 0));

        jButton1.setBackground(new java.awt.Color(41, 148, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/akar-icons_arrow-back-thick-fill (1).png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Perpanjang Waktu Sewa Kos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(300, 300, 300)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTable_Perpanjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Blok", "Kode KTP", "Nama", "No. Kamar", "Status Bulan Ini"
            }
        ));
        jTable_Perpanjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_PerpanjangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Perpanjang);

        panelRound1.setBackground(new java.awt.Color(204, 204, 204));
        panelRound1.setRoundBottomLeft(50);
        panelRound1.setRoundBottomRight(50);
        panelRound1.setRoundTopRight(50);
        panelRound1.setRoundedTopLeft(50);

        jTextField_cariNM.setBackground(new java.awt.Color(204, 204, 204));
        jTextField_cariNM.setFont(new java.awt.Font("Dialog", 2, 11)); // NOI18N
        jTextField_cariNM.setForeground(new java.awt.Color(0, 0, 0));
        jTextField_cariNM.setText("Cari Nama Disini...");
        jTextField_cariNM.setBorder(null);
        jTextField_cariNM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_cariNMMouseClicked(evt);
            }
        });
        jTextField_cariNM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_cariNMKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jTextField_cariNM, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_cariNM, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Filter Kode Blok");

        jComboBox_blok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Silahkan Pilih Blok---" }));
        jComboBox_blok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_blokItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Kode KTP");

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.setRoundBottomLeft(30);
        panelRound2.setRoundBottomRight(30);
        panelRound2.setRoundTopRight(30);
        panelRound2.setRoundedTopLeft(30);

        jTextField_NIK.setEditable(false);
        jTextField_NIK.setBackground(new java.awt.Color(255, 255, 255));
        jTextField_NIK.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTextField_NIK.setForeground(new java.awt.Color(0, 0, 0));
        jTextField_NIK.setBorder(null);

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_NIK, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_NIK, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nama Penyewa");

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundBottomLeft(30);
        panelRound3.setRoundBottomRight(30);
        panelRound3.setRoundTopRight(30);
        panelRound3.setRoundedTopLeft(30);

        jTextField_Nama.setEditable(false);
        jTextField_Nama.setBackground(new java.awt.Color(255, 255, 255));
        jTextField_Nama.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTextField_Nama.setForeground(new java.awt.Color(0, 0, 0));
        jTextField_Nama.setBorder(null);

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_Nama, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_Nama, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Kode Blok");

        panelRound4.setBackground(new java.awt.Color(255, 255, 255));
        panelRound4.setRoundBottomLeft(30);
        panelRound4.setRoundBottomRight(30);
        panelRound4.setRoundTopRight(30);
        panelRound4.setRoundedTopLeft(30);

        jTextField_k_blok.setEditable(false);
        jTextField_k_blok.setBackground(new java.awt.Color(255, 255, 255));
        jTextField_k_blok.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTextField_k_blok.setForeground(new java.awt.Color(0, 0, 0));
        jTextField_k_blok.setBorder(null);

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_k_blok, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound4Layout.setVerticalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_k_blok, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("No. Kamar");

        panelRound5.setBackground(new java.awt.Color(255, 255, 255));
        panelRound5.setRoundBottomLeft(30);
        panelRound5.setRoundBottomRight(30);
        panelRound5.setRoundTopRight(30);
        panelRound5.setRoundedTopLeft(30);

        jTextField_noKam.setEditable(false);
        jTextField_noKam.setBackground(new java.awt.Color(255, 255, 255));
        jTextField_noKam.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTextField_noKam.setForeground(new java.awt.Color(0, 0, 0));
        jTextField_noKam.setBorder(null);

        javax.swing.GroupLayout panelRound5Layout = new javax.swing.GroupLayout(panelRound5);
        panelRound5.setLayout(panelRound5Layout);
        panelRound5Layout.setHorizontalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_noKam, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRound5Layout.setVerticalGroup(
            panelRound5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField_noKam, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jLabel_BiayaBlok.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel_BiayaBlok.setForeground(new java.awt.Color(0, 0, 0));

        jLabel_Rp.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel_Rp.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Perpanjang Sewa Dari..");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("...Hingga");

        jButton2.setText("Hitung hari");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField_total.setEditable(false);

        panelRound6.setBackground(new java.awt.Color(255, 255, 255));
        panelRound6.setRoundBottomLeft(30);
        panelRound6.setRoundBottomRight(30);
        panelRound6.setRoundTopRight(30);
        panelRound6.setRoundedTopLeft(30);

        jTextField_nominal.setBackground(new java.awt.Color(255, 255, 255));
        jTextField_nominal.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jTextField_nominal.setForeground(new java.awt.Color(0, 0, 0));
        jTextField_nominal.setBorder(null);
        jTextField_nominal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_nominalMouseClicked(evt);
            }
        });
        jTextField_nominal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nominalActionPerformed(evt);
            }
        });
        jTextField_nominal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_nominalKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_nominal, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_nominal, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton3.setBackground(new java.awt.Color(102, 204, 0));
        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setText("Lakukan Transaksi & Cetak Struk");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel_txtTotalBiaya.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel_txtTotalBiaya.setForeground(new java.awt.Color(102, 0, 0));

        jLabel_txtTotalBiaya1.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel_txtTotalBiaya1.setForeground(new java.awt.Color(102, 0, 0));

        jLabel_txtTotalBiaya2.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel_txtTotalBiaya2.setForeground(new java.awt.Color(102, 0, 0));

        jLabel_tknEnter.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel_tknEnter.setForeground(new java.awt.Color(0, 0, 0));

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Rp.");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Masukkan nominal pembayaran disini");

        jLabel_txtKembalian.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel_txtKembalian.setForeground(new java.awt.Color(0, 102, 51));

        jLabel_RpKembalian.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel_RpKembalian.setForeground(new java.awt.Color(0, 102, 51));

        jLabel1_NomKembali.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel1_NomKembali.setForeground(new java.awt.Color(0, 102, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jDateChooser_dari, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel_txtTotalBiaya)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel_txtTotalBiaya1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel_txtTotalBiaya2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 96, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jButton2)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextField_total, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel8)
                                            .addComponent(jDateChooser_hingga, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel10)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton3)
                                    .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_tknEnter)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel_txtKembalian)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel_RpKembalian)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1_NomKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(panelRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel_Rp)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel_BiayaBlok, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jComboBox_blok, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(235, 235, 235))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox_blok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(panelRound4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelRound5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel_BiayaBlok, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_Rp, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel_tknEnter)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11))
                            .addComponent(jDateChooser_dari, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jDateChooser_hingga, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_total, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel_txtTotalBiaya)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jLabel_txtTotalBiaya1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                            .addComponent(jLabel_txtTotalBiaya2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_RpKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1_NomKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_cariNMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_cariNMMouseClicked
        // TODO add your handling code here:
        jTextField_cariNM.setText(null);
    }//GEN-LAST:event_jTextField_cariNMMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        hitung();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable_PerpanjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_PerpanjangMouseClicked
        // TODO add your handling code here:
         int row = jTable_Perpanjang.getSelectedRow();
        String NIK = jTable_Perpanjang.getValueAt(row, 1).toString();
        String nama = jTable_Perpanjang.getValueAt(row, 2).toString();
        String noKamar = jTable_Perpanjang.getValueAt(row, 3).toString();
        String kdBlok = jTable_Perpanjang.getValueAt(row, 0).toString();
        jTextField_NIK.setText(NIK);
        jTextField_Nama.setText(nama);
        jTextField_k_blok.setText(kdBlok);
        jTextField_noKam.setText(noKamar);
        
        jLabel_Rp.setText("Rp.");
        try {
            String sql="SELECT harga FROM tb_blok WHERE kode_blok='"+jTextField_k_blok.getText()+"'";
            Statement s =(Statement)Konek.getConnection().createStatement();
            ResultSet rs =s.executeQuery(sql);
            while (rs.next()) {
                jLabel_BiayaBlok.setText(rs.getString("harga"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        
       
//        showHarga();
    }//GEN-LAST:event_jTable_PerpanjangMouseClicked

    private void jComboBox_blokItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_blokItemStateChanged
        // TODO add your handling code here:
         String jcombo =jComboBox_blok.getSelectedItem().toString();
        TableRowSorter tr =new TableRowSorter(jTable_Perpanjang.getModel());
        jTable_Perpanjang.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(jcombo, 0));
        if (jComboBox_blok.getSelectedItem().equals("---Silahkan Pilih Blok---")) {
            jTable_Perpanjang.setRowSorter(new TableRowSorter<>(jTable_Perpanjang.getModel()));
        }
    }//GEN-LAST:event_jComboBox_blokItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new MainMenu().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField_cariNMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_cariNMKeyReleased
        // TODO add your handling code here:
         String find =jTextField_cariNM.getText();
        TableRowSorter tr = new TableRowSorter(jTable_Perpanjang.getModel());
        jTable_Perpanjang.setRowSorter(tr);
        
        tr.setRowFilter(RowFilter.regexFilter(find, 2));
    }//GEN-LAST:event_jTextField_cariNMKeyReleased

    private void jTextField_nominalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_nominalKeyReleased
        // TODO add your handling code here:
        String getNominal =jTextField_nominal.getText();
        int nilai = Integer.parseInt(getNominal);
        int nilai2 = Integer.parseInt(jLabel_txtTotalBiaya2.getText());
        if (nilai > nilai2) {
            jLabel_tknEnter.setText("*Tekan enter untuk melihat kembalian");
            
        } else{
            jLabel_tknEnter.setText("");
        }
    }//GEN-LAST:event_jTextField_nominalKeyReleased

    private void jTextField_nominalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_nominalMouseClicked
        // TODO add your handling code here:
        jLabel11.setText(null);
    }//GEN-LAST:event_jTextField_nominalMouseClicked

    private void jTextField_nominalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nominalActionPerformed
        // TODO add your handling code here:
          try {
            int harga = Integer.parseInt(jLabel_txtTotalBiaya2.getText());
            int nominal = Integer.parseInt(jTextField_nominal.getText());
            int kembalian = nominal - harga;
            if (harga>nominal) {
                JOptionPane.showMessageDialog(this, "Tidak ada kembalian untuk ini");
            }else{
                jLabel_txtKembalian.setText("Kembalian: ");
                jLabel_RpKembalian.setText("Rp.");
                jLabel1_NomKembali.setText(String.valueOf(kembalian));
                //jTextField_Kembalian.setText(String.valueOf(kembalian));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jTextField_nominalActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        bayar();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            JasperDesign jsD = JRXmlLoader.load("F:\\Programming Project\\Java\\MimiKostSwing\\src\\struk\\tr_PerpanjangSewa.jrxml");
            HashMap hash = new HashMap();
            hash.put("kodeKTP", jTextField_NIK.getText());
            hash.put("Nama", jTextField_Nama.getText());
            hash.put("kdBlok", jTextField_k_blok.getText());
            hash.put("noKam",jTextField_noKam.getText());
            hash.put("transaksiMulai", sdf.format(jDateChooser_dari.getDate()));
            hash.put("transaksiSampai", sdf.format(jDateChooser_hingga.getDate()));
            hash.put("TotalHari", jTextField_total.getText());
            hash.put("Harga Blok", jLabel_BiayaBlok.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(PerpanjangSewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PerpanjangSewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PerpanjangSewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PerpanjangSewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PerpanjangSewa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox_blok;
    private com.toedter.calendar.JDateChooser jDateChooser_dari;
    private com.toedter.calendar.JDateChooser jDateChooser_hingga;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel1_NomKembali;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_BiayaBlok;
    private javax.swing.JLabel jLabel_Rp;
    private javax.swing.JLabel jLabel_RpKembalian;
    private javax.swing.JLabel jLabel_tknEnter;
    private javax.swing.JLabel jLabel_txtKembalian;
    private javax.swing.JLabel jLabel_txtTotalBiaya;
    private javax.swing.JLabel jLabel_txtTotalBiaya1;
    private javax.swing.JLabel jLabel_txtTotalBiaya2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Perpanjang;
    private javax.swing.JTextField jTextField_NIK;
    private javax.swing.JTextField jTextField_Nama;
    private javax.swing.JTextField jTextField_cariNM;
    private javax.swing.JTextField jTextField_k_blok;
    private javax.swing.JTextField jTextField_noKam;
    private javax.swing.JTextField jTextField_nominal;
    private javax.swing.JTextField jTextField_total;
    private model.panelRound panelRound1;
    private model.panelRound panelRound2;
    private model.panelRound panelRound3;
    private model.panelRound panelRound4;
    private model.panelRound panelRound5;
    private model.panelRound panelRound6;
    // End of variables declaration//GEN-END:variables
}
