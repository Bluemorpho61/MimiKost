/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimikostswing.mainview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import mimikostswing.Config;
import mimikostswing.Konek;

/**
 *
 * @author Alkin PC
 */
public class PerpanjangKos extends javax.swing.JFrame {

    /**
     * Creates new form PerpanjangKos
     */
    public PerpanjangKos() {
        initComponents();
        showTable();
        showCombobox();
       showcombobox2();
       this.setResizable(false);
        
       jDateChooser_dari.setDate(Date.from(Instant.now()));
    }
    
    private void bayar(){
        SimpleDateFormat sdf = new SimpleDateFormat("M");
        SimpleDateFormat thn = new SimpleDateFormat("yyyy");
        String bulan = sdf.format(jDateChooser_hingga.getDate());
        String tahun = thn.format(jDateChooser_hingga.getDate());
        LocalDate ld = LocalDate.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String tgSkrg = df.format(ld);
        String kdKTP = jTextField_NIK.getText();
        try {
            String SQL="UPDATE `tb_tagihan_penyewa` SET `id_bulan` = '"+bulan+"', `tahun` = '"+thn+"', `jumlah_tagihan` = '"+jTextField_biaya.getText()+"', `status` = 'Terbayar' ,`tanggal_bayar` = '"+tgSkrg+"' WHERE `tb_tagihan_penyewa`.`kode_ktp` ="+kdKTP+";";
            java.sql.Connection con =(Connection)Config.configDB();
           java.sql.PreparedStatement ps =con.prepareStatement(SQL);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Transaksi Perpanjangan Sewa Berhasil!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
        
        
        System.out.println(bulan);
        System.out.println(tahun);
    }
    private void hitung(){
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String strtg11 = sdf.format(jDateChooser_dari.getDate());
        String strtgl2 =sdf.format(jDateChooser_hingga.getDate());
        Date tg11 = sdf.parse(strtg11);
        Date tgl2 =sdf.parse(strtgl2);
        long hari1 = tg11.getTime();
        long hari2 = tgl2.getTime();
        long diff = hari2 - hari1;
        long lama = diff / (24*60*60*1000);
        String total = (Long.toString(lama));
        jTextField_total.setText(total);
        
        
        
        int harga_blok = Integer.parseInt(jTextField_hargaBlok.getText());
        int lamaHari = Integer.parseInt(jTextField_total.getText());
        int TOTAL = harga_blok *lamaHari;
        String totall = Integer.toString(TOTAL);
        jTextField_biaya.setText(totall);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
        
    }
    public void showCombobox(){
       
        try {
            Statement stm = (Statement)Konek.getConnection().createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM tb_blok");
            java.sql.Connection conn=(Connection) Config.configDB();
            while (rs.next()) {
               jComboBox_blok.addItem(rs.getString("kode_blok"));                
                //rs.geto
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
        }
        
        
    }
    public void showTable(){
        DefaultTableModel tb = new DefaultTableModel();
        tb.addColumn("Kode Blok");
        tb.addColumn("Kode KTP");
        tb.addColumn("Nama");
        tb.addColumn("No. Kamar");
        jTable_Perpanjang.setModel(tb);
        try {
        String sql ="SELECT tb_blok.kode_blok, tb_penyewa.kode_ktp, tb_penyewa.nama_penyewa, tb_kamar.no_kamar FROM tb_blok JOIN tb_kamar ON tb_blok.kode_blok = tb_kamar.kode_blok JOIN tb_penyewa ON tb_penyewa.id_kamar = tb_kamar.id_kamar";
        Statement stm = (Statement)Konek.getConnection().createStatement();
        ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                tb.addRow(new Object[]{
                    rs.getString("kode_blok"),
                    rs.getString("kode_ktp"),
                    rs.getString("nama_penyewa"),
                    rs.getString("no_kamar")
                });
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
            if (r.next()) {
                jTextField_hargaBlok.setText(r.getString("harga"));
            }
            
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public void showcombobox2(){
        try {
            Statement statement=(Statement)Konek.getConnection().createStatement();
            ResultSet res=statement.executeQuery("SELECT * FROM `tb_bulan` GROUP BY id_bulan ORDER BY id_bulan ASC");
            while (res.next()) {
             //   jComboBox_bulan.addItem(res.getString("bulan"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e.getMessage());
        }
    }
    
    public void paymentLogic(){
        
        if (Integer.valueOf(jTextField_nom.getText()) == Integer.valueOf(jTextField_biaya.getText())) {
              LocalDate ld = LocalDate.now();
        DateTimeFormatter d =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String tglSKRG = d.format(ld);
        String NIK = jTextField_NIK.getText();
    //    String bln = jComboBox_bulan.getSelectedItem().toString();
     //   model.SetterGetter.setBulan(bln);
        String blnConver=model.SetterGetter.getBulan();
     //   String thn = jTextField_thn.getText();
        String ua=jTextField_nom.getText();
        
        
        try {
       // String sql ="INSERT INTO `tb_tagihan_penyewa` (`id_tagihan_penyewa`, `id_bulan`, `tahun`, `NIK`, `jumlah_tagihan`, `status`, `tanggal_bayar`) VALUES ("+Types.NULL+",'"+ blnConver+"', '"+thn+"', '"+NIK+"', '"+ua+"', 'Terbayar','"+ tglSKRG+"')";
      // String sqlUpdate="UPDATE `tb_tagihan_penyewa` SET `id_bulan` = '"+blnConver+"', `tahun` = '"+thn+"', `jumlah_tagihan` = '"+ua+"', `status` = 'Terbayar', `tanggal_bayar` = '"+tglSKRG+"' WHERE `tb_tagihan_penyewa`.`NIK` ="+NIK+";";
        Connection conn = (Connection)mimikostswing.Config.configDB();
      //  PreparedStatement pst =conn.prepareStatement(sqlUpdate);
      //  pst.execute();
        JOptionPane.showMessageDialog(this, "Transaksi Perpanjangan Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e);
        }
        } else if(Integer.valueOf(jTextField_nom.getText()) >= Integer.valueOf(jTextField_biaya.getText())) {
             LocalDate ld = LocalDate.now();
        DateTimeFormatter d =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String tglSKRG = d.format(ld);
        String NIK = jTextField_NIK.getText();
        //String bln = jComboBox_bulan.getSelectedItem().toString();
     //   model.SetterGetter.setBulan(bln);
        String blnConver=model.SetterGetter.getBulan();
      //  String thn = jTextField_thn.getText();
        String ua=jTextField_nom.getText();
        
        
        try {
       // String sql ="INSERT INTO `tb_tagihan_penyewa` (`id_tagihan_penyewa`, `id_bulan`, `tahun`, `NIK`, `jumlah_tagihan`, `status`, `tanggal_bayar`) VALUES ("+Types.NULL+",'"+ blnConver+"', '"+thn+"', '"+NIK+"', '"+ua+"', 'Terbayar','"+ tglSKRG+"')";
//       String sqlUpdate="UPDATE `tb_tagihan_penyewa` SET `id_bulan` = '"+blnConver+"', `tahun` = '"+thn+"', `jumlah_tagihan` = '"+ua+"', `status` = 'Terbayar', `tanggal_bayar` = '"+tglSKRG+"' WHERE `tb_tagihan_penyewa`.`NIK` ="+NIK+";";
        Connection conn = (Connection)mimikostswing.Config.configDB();
        //PreparedStatement pst =conn.prepareStatement(sqlUpdate);
      //  pst.execute();
        JOptionPane.showMessageDialog(this, "Transaksi Perpanjangan Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e);
        }
        } else{
               LocalDate ld = LocalDate.now();
        DateTimeFormatter d =DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String tglSKRG = d.format(ld);
        String NIK = jTextField_NIK.getText();
       // String bln = jComboBox_bulan.getSelectedItem().toString();
        //model.SetterGetter.setBulan(bln);
        String blnConver=model.SetterGetter.getBulan();
      //  String thn = jTextField_thn.getText();
        String ua=jTextField_nom.getText();
        
        
        try {
       // String sql ="INSERT INTO `tb_tagihan_penyewa` (`id_tagihan_penyewa`, `id_bulan`, `tahun`, `NIK`, `jumlah_tagihan`, `status`, `tanggal_bayar`) VALUES ("+Types.NULL+",'"+ blnConver+"', '"+thn+"', '"+NIK+"', '"+ua+"', 'Belum Lunas','"+ tglSKRG+"')";
     //  String sqlUpdate="UPDATE `tb_tagihan_penyewa` SET `id_bulan` = '"+blnConver+"', `tahun` = '"+thn+"', `jumlah_tagihan` = '"+ua+"', `status` = 'Belum Lunas', `tanggal_bayar` = '"+tglSKRG+"' WHERE `tb_tagihan_penyewa`.`kode_ktp` ="+NIK+";";
        Connection conn = (Connection)mimikostswing.Config.configDB();
      //  PreparedStatement pst =conn.prepareStatement(sqlUpdate);
    //    pst.execute();
        JOptionPane.showMessageDialog(this, "Transaksi telah berhasil, namun penyewa masih belum melunasi tagihan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error: "+ e);
        }
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
        jComboBox_blok = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField_NIK = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField_Nama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField_k_blok = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField_noKam = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField_nom = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Perpanjang = new javax.swing.JTable();
        jButton_bayar = new javax.swing.JButton();
        jTextField_criNM = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField_biaya = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextField_total = new javax.swing.JTextField();
        jDateChooser_dari = new com.toedter.calendar.JDateChooser();
        jDateChooser_hingga = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jTextField_hargaBlok = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField_Kembalian = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(236, 236, 236));

        jComboBox_blok.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---Silahkan Pilih Blok---" }));
        jComboBox_blok.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_blokItemStateChanged(evt);
            }
        });
        jComboBox_blok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_blokActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Saring Blok Penyewa");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Kode KTP");

        jTextField_NIK.setEditable(false);

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nama");

        jTextField_Nama.setEditable(false);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("K. Blok");

        jTextField_k_blok.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("No. Kamar");

        jTextField_noKam.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Perpanjang tagihan dari");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Hingga");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Masukkan Nominal");

        jTextField_nom.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jTextField_nom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nomActionPerformed(evt);
            }
        });

        jTable_Perpanjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Kode Blok", "Kode KTP", "Nama", "No. Kamar"
            }
        ));
        jTable_Perpanjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_PerpanjangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Perpanjang);

        jButton_bayar.setText("Lakukan Transaksi & Cetak Struk");
        jButton_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_bayarActionPerformed(evt);
            }
        });

        jTextField_criNM.setFont(new java.awt.Font("Arial", 2, 11)); // NOI18N
        jTextField_criNM.setText("Cari Nama");
        jTextField_criNM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_criNMMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField_criNMMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTextField_criNMMouseReleased(evt);
            }
        });
        jTextField_criNM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_criNMKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Jumlah yang harus dibayarkan: ");

        jTextField_biaya.setEditable(false);
        jTextField_biaya.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(41, 148, 0));

        jButton3.setBackground(new java.awt.Color(41, 148, 0));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/akar-icons_arrow-back-thick-fill (1).png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Perpanjang Kos");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(349, 349, 349)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Rp.");

        jButton1.setText("Hitung");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Total Hari");

        jTextField_total.setEditable(false);
        jTextField_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_totalActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Harga Blok: ");

        jTextField_hargaBlok.setEditable(false);

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Kembalian: ");

        jTextField_Kembalian.setEditable(false);

        jLabel15.setText("jLabel15");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_NIK, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jComboBox_blok, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField_k_blok, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel13))
                                    .addComponent(jTextField_Nama, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_hargaBlok, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField_noKam, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(170, 170, 170)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_total, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jDateChooser_dari, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                        .addComponent(jDateChooser_hingga, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(93, 93, 93))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1)
                                    .addGap(32, 32, 32))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 131, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jTextField_nom, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(119, 119, 119)
                                .addComponent(jButton_bayar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jTextField_criNM, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(445, 445, 445))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(180, 180, 180)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addGap(3, 3, 3)
                                        .addComponent(jTextField_Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField_biaya, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32))))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField_criNM, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_blok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_NIK, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_Nama, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_k_blok, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_hargaBlok, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_noKam, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser_dari, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_total, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 54, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_nom, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_biaya, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField_Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDateChooser_hingga, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
        
        LocalDate curDate = LocalDate.now();
        int year=curDate.getYear();
        int bln = curDate.getMonthValue();
     //   jTextField_thn.setText(String.valueOf(year));
      //  jComboBox_bulan.setSelectedIndex(bln);
       showHarga();
        //jTextField3
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
           // jTextField_k_blok.setText(jComboBox_blok.getSelectedItem().toString());    
    }//GEN-LAST:event_jComboBox_blokItemStateChanged

    private void jComboBox_blokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_blokActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox_blokActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
      this.setVisible(false);
        new MainMenu().setVisible(true);
          dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_bayarActionPerformed
        // TODO add your handling code here:
       // paymentLogic();
       bayar();
//        LocalDate ld = LocalDate.now();
//        DateTimeFormatter d =DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String tglSKRG = d.format(ld);
//        String NIK = jTextField_NIK.getText();
//        String bln = jComboBox_bulan.getSelectedItem().toString();
//        model.SetterGetter.setBulan(bln);
//        String blnConver=model.SetterGetter.getBulan();
//        String thn = jTextField_thn.getText();
//        String ua=jTextField_nom.getText();
//        
//        
//        try {
//        String sql ="INSERT INTO `tb_tagihan_penyewa` (`id_tagihan_penyewa`, `id_bulan`, `tahun`, `NIK`, `jumlah_tagihan`, `status`, `tanggal_bayar`) VALUES ("+Types.NULL+",'"+ blnConver+"', '"+thn+"', '"+NIK+"', '"+ua+"', 'Belum Terbayar','"+ tglSKRG+"')";
//        Connection conn = (Connection)mimikostswing.Config.configDB();
//        PreparedStatement pst =conn.prepareStatement(sql);
//        pst.execute();
//        JOptionPane.showMessageDialog(this, "Transaksi Perpanjangan Berhasil");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this,"Error: "+ e);
//        }
        
    }//GEN-LAST:event_jButton_bayarActionPerformed

    private void jTextField_criNMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_criNMMouseClicked
        // TODO add your handling code here:
        jTextField_criNM.setText(null);
    }//GEN-LAST:event_jTextField_criNMMouseClicked

    private void jTextField_criNMMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_criNMMouseExited
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jTextField_criNMMouseExited

    private void jTextField_criNMMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_criNMMouseReleased
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jTextField_criNMMouseReleased

    private void jTextField_criNMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_criNMKeyReleased
        // TODO add your handling code here:
        String find =jTextField_criNM.getText();
        TableRowSorter tr = new TableRowSorter(jTable_Perpanjang.getModel());
        jTable_Perpanjang.setRowSorter(tr);
        
        tr.setRowFilter(RowFilter.regexFilter(find, 2));
    }//GEN-LAST:event_jTextField_criNMKeyReleased

    private void jTextField_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_totalActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField_totalActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        hitung();
      //  bayar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField_nomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nomActionPerformed
        // TODO add your handling code here:
        try {
            int harga = Integer.parseInt(jTextField_biaya.getText());
            int nominal = Integer.parseInt(jTextField_nom.getText());
            int kembalian = nominal - harga;
            if (harga>nominal) {
                JOptionPane.showMessageDialog(this, "Tidak ada kembalian untuk ini");
            }else{
                jTextField_Kembalian.setText(String.valueOf(kembalian));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_jTextField_nomActionPerformed

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
            java.util.logging.Logger.getLogger(PerpanjangKos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PerpanjangKos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PerpanjangKos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PerpanjangKos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PerpanjangKos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton_bayar;
    private javax.swing.JComboBox<String> jComboBox_blok;
    private com.toedter.calendar.JDateChooser jDateChooser_dari;
    private com.toedter.calendar.JDateChooser jDateChooser_hingga;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Perpanjang;
    private javax.swing.JTextField jTextField_Kembalian;
    private javax.swing.JTextField jTextField_NIK;
    private javax.swing.JTextField jTextField_Nama;
    private javax.swing.JTextField jTextField_biaya;
    private javax.swing.JTextField jTextField_criNM;
    private javax.swing.JTextField jTextField_hargaBlok;
    private javax.swing.JTextField jTextField_k_blok;
    private javax.swing.JTextField jTextField_noKam;
    private javax.swing.JTextField jTextField_nom;
    private javax.swing.JTextField jTextField_total;
    // End of variables declaration//GEN-END:variables
}
