/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package struk;

import java.util.Collection;
import java.util.Vector;

/**
 *
 * @author Alkin PC
 */
public class KhususJasper {

    private String kodeKTP;

    /**
     * Get the value of kodeKTP
     *
     * @return the value of kodeKTP
     */
    public String getKodeKTP() {
        return kodeKTP;
    }

    /**
     * Set the value of kodeKTP
     *
     * @param kodeKTP new value of kodeKTP
     */
    public void setKodeKTP(String kodeKTP) {
        this.kodeKTP = kodeKTP;
    }

    private String Nama;

    /**
     * Get the value of Nama
     *
     * @return the value of Nama
     */
    public String getNama() {
        return Nama;
    }

    /**
     * Set the value of Nama
     *
     * @param Nama new value of Nama
     */
    public void setNama(String Nama) {
        this.Nama = Nama;
    }

    private String kodeBlok;

    /**
     * Get the value of kodeBlok
     *
     * @return the value of kodeBlok
     */
    public String getKodeBlok() {
        return kodeBlok;
    }

    /**
     * Set the value of kodeBlok
     *
     * @param kodeBlok new value of kodeBlok
     */
    public void setKodeBlok(String kodeBlok) {
        this.kodeBlok = kodeBlok;
    }

    private String Kamar;

    /**
     * Get the value of Kamar
     *
     * @return the value of Kamar
     */
    public String getKamar() {
        return Kamar;
    }

    /**
     * Set the value of Kamar
     *
     * @param Kamar new value of Kamar
     */
    public void setKamar(String Kamar) {
        this.Kamar = Kamar;
    }

    private String Biaya;

    /**
     * Get the value of Biaya
     *
     * @return the value of Biaya
     */
    public String getBiaya() {
        return Biaya;
    }

    /**
     * Set the value of Biaya
     *
     * @param Biaya new value of Biaya
     */
    public void setBiaya(String Biaya) {
        this.Biaya = Biaya;
    }

    private String Tunai;

    /**
     * Get the value of Tunai
     *
     * @return the value of Tunai
     */
    public String getTunai() {
        return Tunai;
    }

    /**
     * Set the value of Tunai
     *
     * @param Tunai new value of Tunai
     */
    public void setTunai(String Tunai) {
        this.Tunai = Tunai;
    }

    private String Kembalian;

    /**
     * Get the value of Kembalian
     *
     * @return the value of Kembalian
     */
    public String getKembalian() {
        return Kembalian;
    }

    /**
     * Set the value of Kembalian
     *
     * @param Kembalian new value of Kembalian
     */
    public void setKembalian(String Kembalian) {
        this.Kembalian = Kembalian;
    }

    /**
     * Get the value of getDataPenyewa
     *
     * @return the value of getDataPenyewa
     */
    public static Collection getGetDataPenyewa() {
        Vector Orang = new Vector();
        try {
            KhususJasper penyewa = new KhususJasper();
            
        } catch (Exception e) {
        }
        return Orang;
    }
    
    
}
