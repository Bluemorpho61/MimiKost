/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import mimikostswing.Konek;

/**
 *
 * @author Alkin PC
 */
public class Enkapsulasi extends Model {
//    private static String NIK;
//    private static String jcomboBox;
//    
//    public static String getKodeBlok(){
//        return jcomboBox;
//    }
//    
//    public static void setKodeBlok(String kodeBlok){
//        try {
//        String SQL ="SELECT id_blok FROM tb_blok WHERE kode_blok='"+kodeBlok+"'";
//        Statement stm =(Statement)Konek.getConnection().createStatement();
//        ResultSet rs = stm.executeQuery(SQL);
//            if (rs.next()) {
//                Enkapsulasi.jcomboBox=(rs.getString("id_blok"));
//            }
//        } catch (Exception e) {
//            System.err.println(e);
//        }
//        
//    }
//    
//    public static String getNIK(){
//        return NIK;
//    }
//    public static void setNIK(String NIK){
//        Enkapsulasi.NIK=NIK;
//    }
    protected String table = "tb_blok";
    int id_blok, harga;
    String kode_blok, deskripsi;
    
    protected String tb_fas ="tb_fasilitas";
    

    public Enkapsulasi(int id_blok, int harga, String kode_blok, String deskripsi) {
        this.id_blok = id_blok;
        this.harga = harga;
        this.kode_blok = kode_blok;
        this.deskripsi = deskripsi;
    }
    
    public Enkapsulasi() {
        setTable(this.table);
    }
    
    public ResultSet getByKode(String kode) throws SQLException {
        String query = "SELECT * FROM " + this.table + " WHERE kode_blok = '" + kode + "'";
        return this.getQuery(query);
    }

    public int getId_blok() {
        return id_blok;
    }

    public int getHarga() {
        return harga;
    }

    public String getKode_blok() {
        return kode_blok;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setId_blok(int id_blok) {
        this.id_blok = id_blok;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setKode_blok(String kode_blok) {
        this.kode_blok = kode_blok;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
    
    
}
