/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mimikostswing.mainview.submenu;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import mimikostswing.Konek;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 *
 * @author Alkin PC
 */
public class LaporanTagihanPenyewa extends javax.swing.JFrame {

    /**
     * Creates new form DetailTagihanPenyewa
     */
    public LaporanTagihanPenyewa() {
        initComponents();
        showChart();
        showTableLunas();
        //SortLunas();
        showTableBelumLunas();
    }
    
//    public void SortLunas(){
//        String lunas ="Terbayar";
//        TableRowSorter tr = new TableRowSorter(jTable1.getModel());
//        jTable1.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(lunas, 4));
//    }

    public void showTableBelumLunas(){
        DefaultTableModel tL = new DefaultTableModel();
         tL.addColumn("No. KTP");
            tL.addColumn("Nama");
            tL.addColumn("Kode Blok");
            tL.addColumn("No Kamar");
            tL.addColumn("Status");
            jTable2.setModel(tL);
           // String sql ="SELECT tb_penyewa.kode_ktp, tb_penyewa.nama_penyewa, tb_blok.kode_blok, tb_kamar.no_kamar, tb_tagihan_penyewa.status, tb_tagihan_penyewa.tanggal_bayar FROM tb_penyewa INNER JOIN tb_tagihan_penyewa ON tb_penyewa.kode_ktp = tb_tagihan_penyewa.kode_ktp AND tb_tagihan_penyewa.status ='Belum Terbayar' LEFT JOIN tb_kamar ON tb_penyewa.id_kamar = tb_kamar.id_kamar LEFT JOIN tb_blok ON tb_penyewa.kode_blok = tb_blok.kode_blok";
           String sqll ="SELECT tb_penyewa.kode_ktp, tb_penyewa.nama_penyewa, tb_blok.kode_blok, tb_kamar.no_kamar, tb_transaksi.status FROM tb_penyewa INNER JOIN tb_transaksi ON tb_penyewa.kode_ktp = tb_transaksi.kode_ktp AND tb_transaksi.status='Belum Lunas' LEFT JOIN tb_kamar ON tb_penyewa.id_kamar = tb_kamar.id_kamar LEFT JOIN tb_blok ON tb_penyewa.kode_blok = tb_blok.kode_blok";
           try {
            Statement s = (Statement)Konek.getConnection().createStatement();
            ResultSet r =s.executeQuery(sqll);
               while (r.next()) {                   
                   tL.addRow(new Object[]{
                       r.getString("kode_ktp"),
                       r.getString("nama_penyewa"),
                       r.getString("kode_blok"),
                       r.getString("no_kamar"),
                       r.getString("status")
                   });
               }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }
    public void showTableLunas(){
       
        try {
            DefaultTableModel tb = new DefaultTableModel();
            tb.addColumn("Kode KTP");
            tb.addColumn("Nama");
            tb.addColumn("Kode Blok");
            tb.addColumn("No Kamar");
            tb.addColumn("Status");
           // tb.addColumn("Tanggal Bayar");
            jTable1.setModel(tb);
            String sql ="SELECT tb_penyewa.NIK, tb_penyewa.nama_penyewa, tb_blok.kode_blok, tb_kamar.no_kamar, tb_tagihan_penyewa.status, tb_tagihan_penyewa.tanggal_bayar FROM tb_penyewa INNER JOIN tb_tagihan_penyewa ON tb_penyewa.NIK = tb_tagihan_penyewa.NIK LEFT JOIN tb_kamar ON tb_penyewa.id_kamar = tb_kamar.id_kamar LEFT JOIN tb_blok ON tb_penyewa.kode_blok = tb_blok.kode_blok";
           // String sqlNew="SELECT tb_penyewa.kode_ktp, tb_penyewa.nama_penyewa, tb_blok.kode_blok, tb_kamar.no_kamar, tb_tagihan_penyewa.status, tb_tagihan_penyewa.tanggal_bayar FROM tb_penyewa INNER JOIN tb_tagihan_penyewa ON tb_penyewa.kode_ktp = tb_tagihan_penyewa.kode_ktp AND tb_tagihan_penyewa.status ='Terbayar' LEFT JOIN tb_kamar ON tb_penyewa.id_kamar = tb_kamar.id_kamar LEFT JOIN tb_blok ON tb_penyewa.kode_blok = tb_blok.kode_blok";
           String sqq="SELECT tb_penyewa.kode_ktp, tb_penyewa.nama_penyewa, tb_blok.kode_blok, tb_kamar.no_kamar, tb_transaksi.status FROM tb_penyewa INNER JOIN tb_transaksi ON tb_penyewa.kode_ktp = tb_transaksi.kode_ktp AND tb_transaksi.status='Terbayar' LEFT JOIN tb_kamar ON tb_penyewa.id_kamar = tb_kamar.id_kamar LEFT JOIN tb_blok ON tb_penyewa.kode_blok = tb_blok.kode_blok";
            Statement s = (Statement)Konek.getConnection().createStatement();
            ResultSet r = s.executeQuery(sqq);
            while (r.next()) {                
                tb.addRow(new Object[]{
                    r.getString("kode_ktp"),
                    r.getString("nama_penyewa"),
                    r.getString("kode_blok"),
                    r.getString("no_kamar"),
                    r.getString("status"),
                    //r.getString("tanggal_bayar")
                });
        
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    public void showChart(){
        try {
            
            DefaultPieDataset dp = new DefaultPieDataset();
            String sqlLunas="SELECT status, COUNT(kode_ktp) AS jumlah FROM tb_tagihan_penyewa GROUP BY status LIMIT 1";
            String sqlblmLunas="SELECT status, COUNT(NIK) AS jumlah FROM tb_tagihan_penyewa WHERE status ='Belum Terbayar' GROUP BY status LIMIT 1";
            try {
          Statement st =(Statement)Konek.getConnection().createStatement();
          ResultSet rLns =st.executeQuery(sqlLunas);
                while (rLns.next()) {                    
                    dp.setValue("Terbayar", rLns.getFloat("jumlah"));
                }
//                while (rBlm.next()) {                    
//                    dp.setValue("Belum Membayar", rBlm.getFloat("jumlah"));
//                }
        
        JFreeChart jf =ChartFactory.createPieChart("Laporan Tagihan Penyewa", dp, true, true, false);
        ChartPanel cpn = new ChartPanel(jf);
      jPanel_Chartpanel.removeAll();
       jPanel_Chartpanel.add(cpn);
        jPanel_Chartpanel.updateUI();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Error"+ e.getMessage());
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
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel_Chartpanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel2.setBackground(new java.awt.Color(41, 148, 0));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Laporan Tagihan Penyewa");

        jButton2.setBackground(new java.awt.Color(41, 148, 0));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mimikostswing/images/akar-icons_arrow-back-thick-fill (1).png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117)
                .addComponent(jLabel1)
                .addContainerGap(234, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "NIK", "Nama Penyewa", "Kode Blok", "No Kamar", "Status", "Tanggal Pembayaran"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Daftar Penyewa yang Telah Lunas Pada Bulan Ini");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Daftar Penyewa yang Belum Lunas Pada Bulan Ini");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "NIK", "Nama Penyewa", "Kode Blok", "No Kamar", "Status"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel_Chartpanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel_Chartpanel.setLayout(new javax.swing.BoxLayout(jPanel_Chartpanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Chartpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Chartpanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(LaporanTagihanPenyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaporanTagihanPenyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaporanTagihanPenyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaporanTagihanPenyewa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LaporanTagihanPenyewa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_Chartpanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
