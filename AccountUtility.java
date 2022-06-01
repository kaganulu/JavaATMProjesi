/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sınıflar;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author kaganulu
 */
public class AccountUtility {
    private FileUtilities fileUtilities;
     private Scanner sc = new Scanner(System.in);
    private Account[] hesaplar;
    private Account girisYapılanHesap;

    public Account getGirisYapılanHesap() {
        return girisYapılanHesap;
    }

    public AccountUtility(FileUtilities fileUtil) {
       // hesaplar = new Account[hesapSayısı];
       
       this.fileUtilities = fileUtil;
       hesaplar = fileUtilities.readAccounts("accounts");
       // accountGenerator();
    }

    public String getCurrentAccount() {
        return girisYapılanHesap.toString();
    }

    public boolean login() {
        boolean girişYapıldı = false;
        String kullanıciAdi, sifre;
        System.out.println("Kullanıcı adı:");
        kullanıciAdi = sc.next();
        System.out.println("Sifre");
        sifre = sc.next();
        girisYapılanHesap = girisBilgiKontrolu(kullanıciAdi, sifre);
        if (girisYapılanHesap != null) {
            return true;
        } else {
            return false;
        }
    }

    private Account girisBilgiKontrolu(String username, String password) {
        int eslesenHesapNo = -1;
        for (int i = 0; i < hesaplar.length; i++) {
            if (hesaplar[i].isIsEnabled()) {
                if (hesaplar[i].getUsername().equalsIgnoreCase(username)) {
                    System.out.println("Kullanıcı Adı Eşleşti.");

                    if (hesaplar[i].getPassword().equalsIgnoreCase(password)) {
                        eslesenHesapNo = i;
                        hesaplar[i].resetLoginAttempt();
                        break;
                    } else {
                        System.out.println("Şifre hatalı.");
                        hesaplar[i].incrementLoginAttamept();
                        if (hesaplar[i].getLoginAttempt() == 3) {
                            disableAccount(hesaplar[i]);
                        }
                        break;
                    }

                }
            }
        }
        Account eslesenHesap = eslesenHesapNo >= 0 ? hesaplar[eslesenHesapNo] : null;
        return eslesenHesap;
    }

    private void accountGenerator() {
        Random r = new Random(0);
        for (int i = 0; i < hesaplar.length; i++) {
            Account a = new Account(r.nextInt(900) + 100, "Sifre" + i, "İsim" + i, "Soyisim" + i, r.nextInt(32) + 1990, r.nextInt(12) + 1, r.nextInt(30) + 1);
            hesaplar[i] = a;
        }
    }

    private void disableAccount(Account lastAttemptedAccount) {
        lastAttemptedAccount.setIsEnabled(false);
        System.out.printf("%s Kullanıcı isimli hesap bloke edilmiştir.\n", lastAttemptedAccount.getUsername());
    }

    public void createNewAccount() {
        String username, password;
        boolean usernameExist = true;
        do {
            System.out.println("Yeni kullanıcı ad giriniz.");
            username = sc.next();
            usernameExist = checkUsernameExist(username);
        } while (usernameExist && !(username.matches("\\w")));

        do {
            System.out.println("Sifre Giriniz.");
            password = sc.next();
        } while (!(password.matches("\\d{6}")));
        Account yeniHesap = new Account(username, password);
        this.hesaplar = enlargeAccounts(this.hesaplar);
        appendAccounts(yeniHesap);
        fileUtilities.accountWriter("accounts", hesaplar);
    }

    private Account[] enlargeAccounts(Account[] hesaplar) {
        Account[] yeniHesaplar = new Account[hesaplar.length + 1];
        for (int i = 0; i < hesaplar.length; i++) {
            yeniHesaplar[i] = hesaplar[i];
        }
        return yeniHesaplar;
    }

    private boolean checkUsernameExist(String username) {
        boolean userNameComparator = false;
        for (int i = 0; i < hesaplar.length; i++) {
            if (hesaplar[i].getUsername().equalsIgnoreCase(username)) {
                userNameComparator = true;
                break;
            }
        }
        return userNameComparator;
    }

    private void appendAccounts(Account yeniHesap) {
        this.hesaplar[this.hesaplar.length - 1] = yeniHesap;
    }

    public long getDepositAccountIdByUsername(String username) {
        for (int i = 0; i < hesaplar.length; i++) {
            if (hesaplar[i].getUsername().equalsIgnoreCase(username)) {
                return hesaplar[i].getDepositAccountId();
            }
        }
        return 0;
    }

    public Account searchAccount(String username) {
        for (int i = 0; i < hesaplar.length; i++) {
            if (username.equalsIgnoreCase(hesaplar[i].getUsername())) {
                return hesaplar[i];
            }
        }
        return null;
    }

    public Account searchAccount(String[] aramaMetni) {
        // girilen metni kelimelere parçala.
        // her kelimenin isim veya soyisim özellerinde bütün accountlarda taraması gerçekleştirilecek.
        // eşleşen sonuclar eğer bizim sonuc listemizde bulunmuyorsa listeye eklenecek. //örenek alican babacan
        // liste kullanıcıya gösterilerek kullanıcıdan doğru hesap için girdi alınacak.
        // seçilen hesap transfer alıcısı olarak kullanılacak.

        Account[] eslesenHesaplar = new Account[0];
        Account eslesenHesap;
        for (int i = 0; i < hesaplar.length; i++) {
            eslesenHesap = null;
            for (int j = 0; j < aramaMetni.length; j++) {
                if (hesaplar[i].getName().contains(aramaMetni[j]) || hesaplar[i].getSurname().contains(aramaMetni[j])) {
                    eslesenHesap = hesaplar[i];
                    break;
                }
            }
            if (eslesenHesap != null) {
                eslesenHesaplar = enlargeAccounts(eslesenHesaplar);
                eslesenHesaplar[eslesenHesaplar.length - 1] = eslesenHesap;
                eslesenHesap = null;
                continue;
            }
        }

        if (eslesenHesaplar.length == 0) {
            return null;
        } else {
            //kullanıcı seçimi yaptır.
            //seçilen hesabı işleme al.
            System.out.println("Hangi hesaba transfer işlemi yapmak istersiniz.\n"
                    + "1 ile " + eslesenHesaplar.length + " arasında sayı giriniz.");
            for (int i = 0; i < eslesenHesaplar.length; i++) {
                System.out.println((i+1) +" - "+ eslesenHesaplar[i].toString());
            }
            
            int hesapSecimi = sc.nextInt();
            while (hesapSecimi < 1 && hesapSecimi > eslesenHesaplar.length) {
                System.out.println("Tekrar hesap seçiniz.");
                hesapSecimi = sc.nextInt();
            }
            return eslesenHesaplar[hesapSecimi - 1];

        }
    }

    //kelime 1 içerisinde kelime 2nin bulunup bulunmadığını kontrol eder.
    public boolean containString(String kelime1, String kelime2) {
        int counter = 0;
        for (int i = 0; i < kelime1.length(); i++) {
            if (kelime1.charAt(i) == kelime2.charAt(0)) {
                counter = 0;
                for (int j = 0; j < kelime2.length(); j++) {
                    if (kelime2.charAt(j) == kelime1.charAt(i + j)) {
                        counter++;
                    } else {
                        break;
                    }
                }
                if (counter == kelime2.length()) {
                    return true;
                }
            }
        }
        return false;
    }

}
