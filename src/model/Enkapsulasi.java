/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Alkin PC
 */
public class Enkapsulasi {
    private static String NIK;
    
    public static String getNIK(){
        return NIK;
    }
    public static void setNIK(String NIK){
        Enkapsulasi.NIK=NIK;
    }
}
