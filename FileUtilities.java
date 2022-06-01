/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sınıflar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtilities {
    
    public void accountWriter(String dosyaYolu, Account[] accounts){
        File dosya = new File(dosyaYolu+" .txt");
        
        try{
            if (!dosya.exists()) {
                dosya.createNewFile();
            }
            FileWriter dosyaYazici = new FileWriter(dosya);
            BufferedWriter tamponluYazici = new BufferedWriter(dosyaYazici);
            tamponluYazici.write(accounts.length+ "\n");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < accounts.length; i++) {
                sb.append(accounts[i].getUsername()).append(",")
                  .append(accounts[i].getPassword()).append(",")
                  .append(accounts[i].getName()).append(",")
                  .append(accounts[i].getSurname()).append(",")
                  .append(accounts[i].getYearOfBirth()).append(",")
                  .append(accounts[i].getMonthOfBirth()).append(",")
                  .append(accounts[i].getDayOfBirth()).append(",")
                  .append(accounts[i].isIsEnabled()).append(",")
                  .append(accounts[i].getLoginAttempt()).append(",")
                  .append(accounts[i].getDepositAccountId());
                        
                if (i < accounts.length-1) {
                   sb.append("\n");
                }
                       
            }
            tamponluYazici.append(sb.toString());
            tamponluYazici.close();
            dosyaYazici.close();
                    
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public Account[] readAccounts(String dosyaYolu){
        File file = new File(dosyaYolu+" .txt");
        Account[] hesaplar = new Account[0];
        try{
            FileReader dosyaOkuyucu = new FileReader(file);
            BufferedReader tamponluOkuyucu = new BufferedReader(dosyaOkuyucu);
           int  hesapSayisi = Integer.valueOf(tamponluOkuyucu.readLine());
           hesaplar = new Account[hesapSayisi];
            String[] icerik;
            String satir;
            String username,password,name,surname;
            int year,month,day, loginAttempt;
            long depositAccountId;
            boolean isEnabled;
            Account account;
            for (int i = 0; i < hesaplar.length; i++) {
                satir = tamponluOkuyucu.readLine();
                icerik = satir.split(",");
                username = icerik[0];
                password = icerik[1];
                name = icerik[2];
                surname = icerik[3];
                year = Integer.valueOf(icerik[4]);
                month = Integer.valueOf(icerik[5]);
                day = Integer.valueOf(icerik[6]);
                isEnabled = Boolean.valueOf(icerik[7]);
                loginAttempt = Integer.valueOf(icerik[8]);
                depositAccountId = Long.valueOf(icerik[9]);
                account = new Account( username,  password, name,  surname, year, month,  day,  isEnabled,  loginAttempt, depositAccountId);
                hesaplar[i] = account;
            }
            tamponluOkuyucu.close();
            dosyaOkuyucu.close();
            
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
            System.out.println("Dosya Bulunamadı");
        }catch(IOException ex){
              ex.printStackTrace();
              System.out.println("Dosya Bozuk veya Okunamadı.");
        }  
        return hesaplar; // null pointer exception almamak için
    }
    

   /* public void writeFile(String filePath, String[] metin) {

        File dosya = new File(filePath);
        try {
            if (!dosya.exists()) {
                dosya.createNewFile();
            }
            FileWriter dosyaYazici = new FileWriter(dosya, false);
            BufferedWriter tamponluYazici = new BufferedWriter(dosyaYazici);
            for (int i = 0; i < metin.length; i++) {
                tamponluYazici.write(i+ "\t" + metin[i]);
            }
           
            tamponluYazici.close();
            dosyaYazici.close();
        }catch (IOException ex){
            Logger.getLogger(FileUtilities.class.getName() ) .log(Level.SEVERE, null, ex);
        }      
         */  
    }

   


