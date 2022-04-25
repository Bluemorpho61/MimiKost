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
public class SetterGetter {
  
        private static String NIK;

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


    
}
