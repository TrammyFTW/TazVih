package TAZBIH_UI;

import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class SQLite {
    public static int o;
    //Static Variables
    static java.sql.Connection conn  = null;
    static java.sql.Statement stmt = null;
//    static File temp = new File("db.sqlite");
//    static String url = "jdbc:sqlite:" + temp.getAbsolutePath().replace("\\","\\\\");
    static String url = "jdbc:sqlite:C:\\Users\\AJ\\Documents\\NetBeansProjects\\PM\\src\\TAZBIH_UI\\db.sqlite";
    static String error = "";
    public static int i;
    //Open DB Session Method
    public static boolean openDB(){
        boolean result = false;
        try{
            Class.forName("org.sqlite.JDBC");
            conn = java.sql.DriverManager.getConnection(url);

            System.out.println("");
            result = true;
        }
        catch(Exception e){
            error = e.getMessage();
            System.out.println("Open DB Error:" + e.getMessage());
        } 
        return result;
    }

    //Close DB Session Method
    public static boolean closeDB(){
        boolean result = false;
        try{
            conn.close();

            System.out.println("");
            result = true;
        }
        catch(Exception e){
            error = e.getMessage();
            System.out.println("Close DB Error: " + e.getMessage());
        }
        return result;
    }    

    //Create Record Method
    public static boolean create(String table, String values){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "INSERT INTO "+ table +" VALUES(" + values + ")";
            stmt.executeUpdate(query);
            //You can include exception handling here. (e.g. Duplicate Data, etc.)
            result = true;
        }
        catch(Exception e){
            System.out.println("Create Error: " + e.getMessage());
        }
        return result;
    }    
    
    public static boolean stockin(String table, String values){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "INSERT INTO "+ table +" (ProductID, Quantity) VALUES(" + values + ")";
            stmt.executeUpdate(query);
            //You can include exception handling here. (e.g. Duplicate Data, etc.)
            result = true;
        }
        catch(Exception e){
            System.out.println("Create Error: " + e.getMessage());
        }
        return result;
    }
    
    public static boolean createhist(String table, String values){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "INSERT INTO "+ table +" (Description) VALUES(" + values + ")";
            stmt.executeUpdate(query);
            //You can include exception handling here. (e.g. Duplicate Data, etc.)
            result = true;
        }
        catch(Exception e){
            System.out.println("Create Error: " + e.getMessage());
        }
        return result;
    } 

    // Record Method
    public static boolean update(String table, String set, String set2, int id){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "UPDATE "+ table +" SET " + set + ","+ set2 + " WHERE id=" + id;
            stmt.executeUpdate(query);
            //You can include exception handling here. (e.g. Duplicate Data, etc.)
            result = true;
        }
        catch(Exception e){
            System.out.println("Create Error: " + e.getMessage());
        }
        return result;
    }
    
    public static boolean update(){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "UPDATE Product SET Quantity = Quantity - Quantity where ProductID=ProductID" ;
            stmt.executeUpdate(query);
            //You can include exception handling here. (e.g. Duplicate Data, etc.)
            result = true;
        }
        catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"Update Error: " + e.getMessage());
        }
        return result;
    }
    
    public static boolean update(String table, String set, String id){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "UPDATE "+ table +" SET " + set + " WHERE ProductID=" + id;
            stmt.executeUpdate(query);
            //You can include exception handling here. (e.g. Duplicate Data, etc.)
            result = true;
        }
        catch(Exception e){
            System.out.println("Create Error: " + e.getMessage());
        }
        return result;
    }
    
    public static boolean updateproduct(String table, String set, String id){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "UPDATE "+ table +" SET " + set + " WHERE ProdID='" + id+"'";
            stmt.executeUpdate(query);
            //You can include exception handling here. (e.g. Duplicate Data, etc.)
            result = true;
        }
        catch(Exception e){
            System.out.println("Create Error: " + e.getMessage());
        }
        return result;
    }
    
    //Delete Record Method
    public static boolean delete(String table, int id){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "DELETE FROM "+ table + " WHERE StockID=" + id;
            stmt.executeUpdate(query);
            result = true;
        }
        catch(Exception e){
            System.out.println("Create Error: " + e.getMessage());
        }
        return result;
    }     
    
    public static boolean deletetrans(){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "DELETE FROM Transact";
            stmt.executeUpdate(query);
            result = true;
        }
        catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"Delete Error: " + e.getMessage());
        }
        return result;
    }
    
    public static boolean deletecust(){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "DELETE FROM Customer";
            stmt.executeUpdate(query);
            result = true;
        }
        catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null,"Delete Error: " + e.getMessage());
        }
        return result;
    }
    
    public static boolean deleteprod(String table, String id){
        boolean result = false;
        try{
            SQLite.stmt = conn.createStatement();
            String query = "DELETE FROM "+ table + " WHERE ProductID='" + id+"'";
            stmt.executeUpdate(query);
            result = true;
        }
        catch(Exception e){
            System.out.println("Create Error: " + e.getMessage());
        }
        return result;
    }    

    //Read Record Method
    public static String[][] read(String table){
        String[][] records = null;
        try{
            SQLite.stmt = conn.createStatement();

            //Count total rows
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + table);
            int totalRows = rs.getInt(1);
            i = totalRows;

            //Count total columns
            rs = stmt.executeQuery("SELECT * FROM " + table);
            ResultSetMetaData rsmd = rs.getMetaData();
            int totalColumns = rsmd.getColumnCount();

            //Initialize 2D Array "records" with totalRows by totalColumns
            records = new String[totalRows][totalColumns];

            //Retrieve the record and store it to 2D Array "records"
            int row=0;
            while(rs.next()){                
                for(int col=0,index=1;col<totalColumns;col++,index++){
                    records[row][col] = rs.getObject(index).toString();
                }
                row++;
            }            
        }
        catch(Exception e){
            System.out.println("Read Error: " + e.getMessage());
        }
        return records;
    }
    
    public static String[][] readstock(String table, String id){
        String[][] records = null;
        try{
            SQLite.stmt = conn.createStatement();

            //Count total rows
            ResultSet rs = stmt.executeQuery("SELECT Quantity FROM " + table + " Where ProductID = '"+id+"'");
            int totalRows = rs.getInt(1);
            i = totalRows;

            //Count total columns
            rs = stmt.executeQuery("SELECT Quantity FROM " + table + " Where ProductID = '"+id+"'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int totalColumns = rsmd.getColumnCount();

            //Initialize 2D Array "records" with totalRows by totalColumns
            records = new String[totalRows][totalColumns];

            //Retrieve the record and store it to 2D Array "records"
            int row=0;
            while(rs.next()){                
                for(int col=0,index=1;col<totalColumns;col++,index++){
                    records[row][col] = rs.getObject(index).toString(); 
                    o = Integer.parseInt(records[row][col]);
                }
//                o = Integer.parseInt(records[0][0]);
                row++;
            }            
        }
        catch(Exception e){
            System.out.println("Read Error: " + e.getMessage());
        }
        return records;
    }
    
     public static String[][] readhist(String table){
        String[][] records = null;
        try{
            SQLite.stmt = conn.createStatement();

            //Count total rows
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + table);
            int totalRows = rs.getInt(1);
            i = totalRows;

            //Count total columns
            rs = stmt.executeQuery("SELECT rowid,Description,Date FROM " + table);
            ResultSetMetaData rsmd = rs.getMetaData();
            int totalColumns = rsmd.getColumnCount();

            //Initialize 2D Array "records" with totalRows by totalColumns
            records = new String[totalRows][totalColumns];

            //Retrieve the record and store it to 2D Array "records"
            int row=0;
            while(rs.next()){                
                for(int col=0,index=1;col<totalColumns;col++,index++){
                    records[row][col] = rs.getObject(index).toString();
                }
                row++;
            }            
        }
        catch(Exception e){
            System.out.println("Read Error: " + e.getMessage());
        }
        return records;
    }
    
    public static String[][] readstocks(String table , String table2){
        String[][] records = null;
        try{
            SQLite.stmt = conn.createStatement();

            //Count total rows
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + table);
            int totalRows = rs.getInt(1);

            //Count total columns
            rs = stmt.executeQuery("SELECT StockID, ProdName, Price, Quantity FROM " + table+" INNER JOIN "+table2+" on stocks.ProdID = product.ProdID");
            ResultSetMetaData rsmd = rs.getMetaData();
            int totalColumns = rsmd.getColumnCount();

            //Initialize 2D Array "records" with totalRows by totalColumns
            records = new String[totalRows][totalColumns];

            //Retrieve the record and store it to 2D Array "records"
            int row=0;
            while(rs.next()){                
                for(int col=0,index=1;col<totalColumns;col++,index++){
                    records[row][col] = rs.getObject(index).toString();
                }
                row++;
            }            
        }
        catch(Exception e){
            System.out.println("Read Error: " + e.getMessage());
        }
        return records;
    }
    
    public static String[][] searchstocks(String table , String value){
        String[][] records = null;
        try{
            SQLite.stmt = conn.createStatement();

            //Count total rows
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + table);
            int totalRows = rs.getInt(1);

            //Count total columns
            rs = stmt.executeQuery("SELECT ProductID,ProductName,Description,Quantity FROM " + table+" Where ProductName like '"+value+"%' OR ProductID like '"+value+"%'");
            ResultSetMetaData rsmd = rs.getMetaData();
            int totalColumns = rsmd.getColumnCount();

            //Initialize 2D Array "records" with totalRows by totalColumns
            records = new String[totalRows][totalColumns];

            //Retrieve the record and store it to 2D Array "records"
            int row=0;
            while(rs.next()){                
                for(int col=0,index=1;col<totalColumns;col++,index++){
                    records[row][col] = rs.getObject(index).toString();
                }
                row++;
            }            
        }
        catch(Exception e){
            System.out.println("Read Error: " + e.getMessage());
        }
        return records;
    }
    /**
     * JDBC Routine
     * 
     * 1. Open a DB Session
     * 2. Execute a query
     * 3. Expect Return Value/s
     * 4. Close DB Session
     ***/
    
    public static void main(String[] args) {
        /*
        //Test a openDB and closeDB Methods
        if(SQLite.openDB()){
            //Execute a query...
            //Expect Return Value/s
            SQLite.closeDB();
        }
        */
        
        /*
        if(SQLite.openDB()){
            String id = "3";
            String name = "Mark";
            String values = id + "," + "'" + name + "'"; // 3,'Mark'
            SQLite.create("tblGuestbook", values);
            SQLite.closeDB();
        }
        */
        
        /** github.com/clydeatuic/java-todo
         * tblGuestbook
         *  ID          INT             Primary Key
         *  NAME        VARCHAR(50)     Not Null
         *  CONTACTNO   VARCHAR(50)     Not Null
         *  EMAIL       VARCHAR(50)     Not Null
         *  GENDER      CHAR(1)         Not Null
         */
        
        /*
        if(SQLite.openDB()){
            String updatedName = "Marky";
            String set = "name=" + "'" + updatedName + "'";
            SQLite.update("tblGuestbook", set, 3);
            SQLite.closeDB();
        }
        */
    }
   
}
