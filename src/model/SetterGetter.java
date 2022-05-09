/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import mimikostswing.Konek;
/**
 *
 * @author Alkin PC
 */
public class SetterGetter {
  
        private static String NIK;
        private static String NoKam;
        private static String Bulan;
    private static String getActualNoKam;

    /**
     * Get the value of getActualNoKam
     *
     * @return the value of getActualNoKam
     */
    public static String getGetActualNoKam() {
        return getActualNoKam;
    }

    /**
     * Set the value of getActualNoKam
     *
     * @param getActualNoKam new value of getActualNoKam
     */
    public static void setGetActualNoKam(String getActualNoKam) {
        SetterGetter.getActualNoKam = getActualNoKam;
    }

    private static String TotalPenyewaForChart;

    /**
     * Get the value of TotalPenyewaForChart
     *
     * @return the value of TotalPenyewaForChart
     */
    public static String getTotalPenyewaForChart() {
        return TotalPenyewaForChart;
    }

    /**
     * Set the value of TotalPenyewaForChart
     *
     * @param TotalPenyewaForChart new value of TotalPenyewaForChart
     */
    public static void setTotalPenyewaForChart(String sql) {
        try {
            Statement s =(Statement)Konek.getConnection().createStatement();
            ResultSet r =s.executeQuery(sql);
            if(r.next()){
                SetterGetter.TotalPenyewaForChart = r.getString("jumlah");
                        }          
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static String getBulan() {
        return Bulan;
    }

    public static void setBulan(String Bulan) {
        String sql="SELECT id_bulan FROM tb_bulan WHERE bulan='"+Bulan+"'";
        try {
            Statement s =(Statement)mimikostswing.Konek.getConnection().createStatement();
        ResultSet r =s.executeQuery(sql);
        if (r.next()) {
            SetterGetter.Bulan =r.getString("id_bulan");
        }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static String getNoKam() {
        return NoKam;
    }

    public static void setNoKam(String NoKam) {
        try {
            String sql="SELECT id_kamar FROM tb_kamar WHERE no_kamar='"+NoKam+"'";
            Statement st = (Statement)mimikostswing.Konek.getConnection().createStatement();
            ResultSet r = st.executeQuery(sql);
            if (r.next()) {
                SetterGetter.NoKam=r.getString("id_kamar");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
     
    }
       
    private String NIK2;

    /**
     * Get the value of NIK2
     *
     * @return the value of NIK2
     */
    public String getNIK2() {
        return NIK2;
    }

    /**
     * Set the value of NIK2
     *
     * @param NIK2 new value of NIK2
     */
    public void setNIK2(String NIK2) {
        this.NIK2 = NIK2;
    }

    /**
     * Get the value of NIK
     *
     * @return the value of NIK
     */
    public static String getNIK() {
        return NIK;
    }

    /**
     * Set the value of NIK
     *
     * @param NIK new value of NIK
     */
    public static void setNIK(String NIK) {
        SetterGetter.NIK = NIK;
        System.out.println(SetterGetter.NIK);
        
    }

    public String getCurDate(){
        LocalDate ld =LocalDate.now();
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy MM dd");
    return df.format(ld).toString();
    }
    
    
    
    
}
