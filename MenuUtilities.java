/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sınıflar;

import java.util.Scanner;

public class MenuUtilities {

   private AccountUtility accountUtility;
    private BankingUtility bankingUtility;
    private Scanner sc;

    public MenuUtilities(FileUtilities fileUtility) {
        this.accountUtility = new AccountUtility(fileUtility);
        this.bankingUtility = new BankingUtility(accountUtility);
        this.sc = new Scanner(System.in);
    }

    public void menuProcess() {
        System.out.println("İEU Banka Hoşgeldiniz.\n");
        int loginAttempt = 0;
        boolean loginResult = false;
        int menuSecimi;
        do {
            System.out.println("1- Giris\n"
                    + "2- Yeni Hesap Oluştur\n"
                    + "9- Iletisim\n"
                    + "0- Cıkıs\n");
            menuSecimi = sc.nextInt();
            switch (menuSecimi) {
                case 1:
                    while (loginAttempt < 3) {
                        loginResult = accountUtility.login();
                        loginAttempt++;
                        if (!loginResult) {
                            System.out.printf("Hatali giriş yapıldı\n");

                        } else {
                            System.out.println(accountUtility.getCurrentAccount());
                            accountDetailsMenu();
                            break;
                        }
                    }
                    break;
                case 2:
                    accountUtility.createNewAccount();
                    break;
                case 9:
                    System.out.println("İletişim bilgileri\n0000-000-00-00");
                    break;
                case 0:

            }
        } while (menuSecimi != 0);

    }
    //Kullanıcı bilgilerini görüntüleme
    //Banka hesap bilgililerini görüntüleme
    //Mevduat hesabı oluştur.
    //Para çekme
    //Para yatirma
    //Para Transferi

    public void accountDetailsMenu() {
        int menuSelection;
        do {
            System.out.printf("Merhaba %s %s, aşağıdaki menüden işlem seçiniz.\n"
                    + "1- Kullanıcı bilgilerini görüntüleme\n"
                    + "2- Banka hesap bilgililerini görüntüleme.\n"
                    + "3- Mevduat hesabi olustur.\n"
                    + "4- Para çekme\n"
                    + "5- Para yatirma\n"
                    + "6- Para transferi\n"
                    + "0- Cıkıs.\n",
                    accountUtility.getGirisYapılanHesap().getName(), accountUtility.getGirisYapılanHesap().getSurname());
            menuSelection = sc.nextInt();
            switch (menuSelection) {
                case 1:
                    System.out.println(accountUtility.getCurrentAccount());
                    break;
                case 2:
                    bankingUtility.printDepositAccount(accountUtility.getGirisYapılanHesap().getDepositAccountId());
                    break;
                case 3:
                    accountUtility.getGirisYapılanHesap().setDepositAccountId(bankingUtility.appenNewDepositAccount());
                    break;
                case 4:
                    System.out.println("Çekmek istediğiniz miktarı giriniz.");
                    bankingUtility.withdrawCash(accountUtility.getGirisYapılanHesap(), sc.nextDouble());
                    break;
                case 5:
                    System.out.println("Yatırmak istediğiniz miktarı giriniz.");
                    bankingUtility.depositCash(accountUtility.getGirisYapılanHesap(), sc.nextDouble());
                    break;
                case 6:
                    System.out.println("Transfer edilecek miktari giriniz.");
                    bankingUtility.transferCash(accountUtility.getGirisYapılanHesap(), sc.nextDouble());
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        } while (menuSelection != 0);

    }
}
