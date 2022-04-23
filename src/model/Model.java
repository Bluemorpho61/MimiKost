/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mimikostswing.Konek;

/**
 *
 * @author Alkin PC
 */
abstract public class Model {
    protected String table;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
    
    public boolean executeQuery(String query) throws SQLException {
        Statement stm =(Statement)Konek.getConnection().createStatement();
        return stm.execute(query);
    }
    
    public ResultSet getQuery(String query) throws SQLException {
        Statement stm =(Statement)Konek.getConnection().createStatement();
        ResultSet result = stm.executeQuery(query);
        
        return result;
    }
    
    public ResultSet getAll() throws SQLException {
        String query = "SELECT * FROM " + this.table;
        return this.getQuery(query);
    }
    
    public boolean delete(int id) throws SQLException {
        String query = "DELETE FROM " + this.table + " WHERE id = " + id;
        this.executeQuery(query);
        
        return true;
    }
    
}
