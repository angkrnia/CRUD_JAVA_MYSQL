/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latihan_database;

import java.sql.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author angga
 */

public class Latihan_Database {
    static Connection conn;
    static Statement stm;
    static ResultSet rs;
    
    static String nama = null;
    static String alamat = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner inputInt = new Scanner(System.in);
        
        // instansiasi kelas koneksi dan method konek
        conn = new Koneksi().connect();
        
        int pilihan;
        
        try {
            
            // Prepare statement
            stm = conn.createStatement();
            
            boolean lagi = true;
            
            while(lagi){
            System.out.println("BELAJAR CRUD JAVA");
            System.out.println("Silahkan pilih menu berikut");
            System.out.println("1. Lihat data");
            System.out.println("2. Tambah data");
            System.out.println("3. Update data");
            System.out.println("4. Delete data");
            System.out.println("5. Exit");
            System.out.println("==============");
            System.out.print("Pilih (1-5) : ");
            pilihan = inputInt.nextInt();
            
            switch(pilihan){
                case 1 : 
                    showData();
                    lagi = lagi("Kembali ke menu utama? [Y/T] : ");
                    break;
                case 2 :
                    insertData();
                    lagi = lagi("Kembali ke menu utama? [Y/T] : ");
                    break;
                case 3 :
                    updateData();
                    lagi = lagi("Kembali ke menu utama? [Y/T] : ");
                    break;
                case 4 :
                    deleteData();
                    lagi = lagi("Kembali ke menu utama? [Y/T] : ");
                    break;
                default :
                    System.exit(0);
            }
            
            }
            
            stm.close();
            conn.close();
            
        } catch (SQLException e) {
            System.out.println("Ada kesalahan.\n->"+e);
        }
    }
    
    static void insertData(){
        int nim;
        
        Scanner input = new Scanner(System.in);
        Scanner inputInt = new Scanner(System.in);
        
        System.out.println("=> TAMBAH DATA MAHASISWA <=");
        System.out.print("NIM : ");
        nim = inputInt.nextInt();
        String sql_nim = "SELECT nim FROM mahasiswa WHERE nim="+nim;
        try{
            rs = stm.executeQuery(sql_nim);
            while(rs.next()){
                if(nim==rs.getInt("nim")){
                    System.out.println("NIM sudah terdaftar di Database!");
                    return;
                }
            }
        }catch(SQLException e){
            System.err.println(e);
        }
        
        System.out.print("Nama mahasiswa : ");
        nama = input.nextLine();
        System.out.print("Alamat mahasiswa : ");
        alamat = input.nextLine();
        
        try{
            String sql = "INSERT INTO mahasiswa VALUES('20"+nim+"','"+nama+"','"+alamat+"')";
            stm.executeUpdate(sql);
            System.err.println("Data berhasil ditambahkan.");
        } catch(SQLException e){
            System.err.println("Data gagal ditambah!");
        }
        nim=0;
        nama=null;
        alamat=null;
    }
    
    static void showData(){
        System.out.println("=> DATA-DATA MAHASISWA <=");
        System.out.println("=======================================================");
        System.out.println("| No | NIM\t| Nama Mahasiswa  | Alamat Mahasiswa  |");
        System.out.println("=======================================================");
        
        try{  
            rs = stm.executeQuery("SELECT * FROM mahasiswa");
            int no=1;
            while(rs.next()){
                System.out.println("| "+no+"  | "+rs.getInt("nim")+"\t| "+rs.getString("nama")+"\t| "+rs.getString("alamat")+"\t|");
                no++;
            }
            System.out.println("=======================================================");
        } catch (SQLException ex) {
            Logger.getLogger(Latihan_Database.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    static void updateData(){
        
        Scanner inputInt = new Scanner(System.in);
        Scanner inputStr = new Scanner(System.in);
        
        int pilihan_update;
        
        int nim;
        showData();
        System.out.println("=> UPDATE DATA MAHASISWA <=");
        System.out.print("Masukan NIM mahasiswa yang ingin DIUPDATE : ");
        nim = inputInt.nextInt();
        
        getMahasiswaByNim(nim);
        
        // jika nim tidak ditemukan, tampil error
        if(nama==null && alamat==null){
            System.err.println("NIM "+nim+" Tidak Ditemukan!");
            return;
        } 
        
        System.out.println("Data yang ingin diubah?");
            System.out.println("1. Data Nama");
            System.out.println("2. Data Alamat");
            System.out.println("3. Nama dan Alamat");
            System.out.print("Pilih 1-3 : ");
            pilihan_update = inputInt.nextInt();
            
            switch(pilihan_update){
                case 1 :
                    System.out.print("Nama Baru : ");
                    nama = inputStr.nextLine();
                    break;
                case 2 :
                    System.out.print("Alamat Baru : ");
                    alamat = inputStr.nextLine();
                    break;
                case 3 :
                    System.out.print("Nama Baru : ");
                    nama = inputStr.nextLine();
                    System.out.print("Alamat Baru : ");
                    alamat = inputStr.nextLine();
                    break;
                default:
                    System.exit(0);
            }
            
            String sql2 = "UPDATE mahasiswa SET nama='"+nama+"',alamat='"+alamat+"' WHERE nim="+nim;
            
            try{
                stm.executeUpdate(sql2);
                System.err.println("Data berhasil diupdate.");
            }catch(SQLException e){
                System.err.println("Datat gagal diupdate.");
            }
            
            nama = null;
            alamat = null;
    }
    
    static void deleteData(){
        Scanner inputInt = new Scanner(System.in);
        Scanner inputStr = new Scanner(System.in);
        int nim;
        String hapus = null;
        
        showData();
        
        System.out.println("=> HAPUS DATA <=");
        System.out.print("NIM yang ingin DIHAPUS? : ");
        nim = inputInt.nextInt();
        
        getMahasiswaByNim(nim);
        
        // jika nim tidak ditemukan, tampil error
        if(nama==null && alamat==null){
            System.err.println("NIM "+nim+" Tidak Ditemukan!");
        } else {
            System.out.println("Data "+ nama +" akan dihapus dari Database");
            System.out.print("Apakah anda yakin? [y/t] : ");
            hapus = inputStr.next();
        
            if(hapus.equalsIgnoreCase("y")){
                String sql = "DELETE FROM mahasiswa WHERE nim="+nim;
                try{
                    stm.executeUpdate(sql);
                    System.err.println("Data "+nama+" BERHASIL dihapus.");
                }catch(SQLException e){
                    System.err.println(e);
                }
            } else {
                System.err.println("Data GAGAL dihapus.");
            }
        }
        nama = null;
        alamat = null;
    }
    
    static void getMahasiswaByNim(int nim){
        String sql = "SELECT * FROM mahasiswa WHERE nim="+nim;
        try {
            rs = stm.executeQuery(sql);
            while(rs.next()){
                System.out.println("NIM : "+nim);
                System.out.println("Nama : "+ (nama = rs.getString("nama")));
                System.out.println("Alamat : "+(alamat = rs.getString("alamat")));
                System.out.println("=========================");
            }  
        } catch (SQLException ex) {
            Logger.getLogger(Latihan_Database.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    static boolean lagi(String pesan){
        Scanner input = new Scanner(System.in);
        String lagi;
        System.out.print(pesan);
        lagi = input.next();
        while(!lagi.equalsIgnoreCase("y") && !lagi.equalsIgnoreCase("t")){
            System.err.println("Masukan huruf Y dan T saja!");
            System.out.print(pesan);
            lagi = input.next();
        }
        return lagi.equalsIgnoreCase("y");
    }
}