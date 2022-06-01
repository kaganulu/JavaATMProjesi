/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sınıflar;

import java.util.Random;
import java.util.Scanner;

public class BankingUtility {

    private DepositAccount[] depositAccounts;
    private AccountUtility accountUtility;
    private Scanner scanner;

    public BankingUtility(AccountUtility accountUtility) {
        this.depositAccounts = new DepositAccount[0];
        this.accountUtility = accountUtility;
        this.scanner = new Scanner(System.in);
    }

    // Unique id oluştur.
    // Hesap oluştur
    // mevduat dizisini genişlet
    // mevduat dizisine yeni hesabi ekle
    public void printDepositAccount(long id) {
        DepositAccount currentDepositAccount = searchDepositAccount(id);
        if (currentDepositAccount != null) {
            System.out.println(currentDepositAccount);
        } else {
            System.out.println("Mevduat hesabiniz bulunmamaktadir.");
        }
    }

    private DepositAccount searchDepositAccount(long id) {
        for (int i = 0; i < depositAccounts.length; i++) {
            if (depositAccounts[i].getId() == id) {
                return depositAccounts[i];
            }
        }
        return null;
    }

    public long appenNewDepositAccount() {
        DepositAccount newDepositAccount = createDepositAccount();
        enlargeDepositAccounts();
        this.depositAccounts[this.depositAccounts.length - 1] = newDepositAccount;
        return newDepositAccount.getId();

    }

    private void enlargeDepositAccounts() {
        if (this.depositAccounts.length > 0) {
            DepositAccount[] newAccounts = new DepositAccount[this.depositAccounts.length + 1];
            for (int i = 0; i < this.depositAccounts.length; i++) {
                newAccounts[i] = this.depositAccounts[i];
            }
            this.depositAccounts = newAccounts;
        } else {
            this.depositAccounts = new DepositAccount[1];
        }
    }

    private DepositAccount createDepositAccount() {
        DepositAccount newDepositAccount = new DepositAccount(generateId());
        return newDepositAccount;
    }

    private long generateId() {
        boolean isMatched;
        long id;
        Random random = new Random(0);
        do {
            id = random.nextLong();
            while (id < 0) {
                id = random.nextLong();
            }
            isMatched = false;
            for (int i = 0; i < depositAccounts.length; i++) {
                if (id == depositAccounts[i].getId()) {
                    isMatched = true;
                    break;
                } else {
                    for (int j = 0; j < depositAccounts[i].getHistory().length; j++) {
                        if (id == depositAccounts[i].getHistory()[j].getId()) {
                            isMatched = true;
                            break;
                        }
                    }
                    if (isMatched) {
                        break;
                    }
                }
            }
        } while (isMatched);
        return id;
    }

    //Para çekme 
    //  Hesap varmi
    //  Bakiye kontrolü
    //Para yatirma
    //  Hesap varmi    
    //Para transferi
    //  Hesap varmi
    //  Bakiye kontrolü
    //  Alici hesap kontrolü
    private boolean checkDepositAccountExistance(long id) {
        return searchDepositAccount(id) == null ? false : true;
    }

    private boolean checkBalanceForValidAmount(long id, double amount) {
        DepositAccount currentAccount = searchDepositAccount(id);
        return currentAccount.getBalance() >= amount ? true : false;
    }

    private void enlargeDepositDetails(DepositAccount depositAccount) {
        if (depositAccount.getHistory().length > 0) {
            DepositDetails[] newDetails = new DepositDetails[depositAccount.getHistory().length + 1];
            for (int i = 0; i < depositAccount.getHistory().length; i++) {
                newDetails[i] = depositAccount.getHistory()[i];
            }
            depositAccount.setHistory(newDetails);
        } else {
            depositAccount.setHistory(new DepositDetails[1]);
        }
    }

    public void withdrawCash(Account account, double amount) {
        if (!checkDepositAccountExistance(account.getDepositAccountId())) {
            account.setDepositAccountId(appenNewDepositAccount());
        }
        if (!checkBalanceForValidAmount(account.getDepositAccountId(), amount)) {
            System.out.println("Hesabınızda yeterli bakiye bulunmamaktadır.");
        } else {
            //Dekont oluştur.
            DepositDetails dekont = new DepositDetails(generateId(), account.getUsername(), null, amount);
            //Dekont dizisini genişlet.
            DepositAccount currentAccount = searchDepositAccount(account.getDepositAccountId());
            enlargeDepositDetails(currentAccount);
            //Dekontu ekle.
            currentAccount.getHistory()[currentAccount.getHistory().length - 1] = dekont;
            //Bakiyeyi güncelle.
            updateBalance(dekont);
        }
    }

    private void recalculateBalance(DepositAccount depositAccount, String username) {
        double balance = 0.0d;
        for (int i = 0; i < depositAccount.getHistory().length; i++) {
            if (depositAccount.getHistory()[i].getReceiver() != null
                    && depositAccount.getHistory()[i].getReceiver().equalsIgnoreCase(username)) {
                balance += depositAccount.getHistory()[i].getAmount();
            } else if (depositAccount.getHistory()[i].getSender() != null
                    && depositAccount.getHistory()[i].getSender().equalsIgnoreCase(username)) {
                balance -= depositAccount.getHistory()[i].getAmount();
            }
        }
    }

    private void updateBalance(DepositDetails dekont) {
        DepositAccount receiverAccount = null;
        if (dekont.getReceiver() != null) {
            receiverAccount = searchDepositAccount(accountUtility.getDepositAccountIdByUsername(dekont.getReceiver()));
        }

        DepositAccount senderAccount = null;
        if (dekont.getSender() != null) {
            senderAccount = searchDepositAccount(accountUtility.getDepositAccountIdByUsername(dekont.getSender()));
        }

        if (receiverAccount != null || senderAccount != null) {
            int durum = 3; //0 cekme , 1 yatırma, 2 transfer
            if (receiverAccount != null && senderAccount == null) {
                durum = 1;
            }
            if (receiverAccount == null && senderAccount != null) {
                durum = 0;
            }
            if (receiverAccount != null && senderAccount != null) {
                durum = 2;
            }

            switch (durum) {
                case 0:
                    senderAccount.setBalance(senderAccount.getBalance() - dekont.getAmount());
                    break;
                case 1:
                    receiverAccount.setBalance(receiverAccount.getBalance() + dekont.getAmount());
                    break;
                case 2:
                    senderAccount.setBalance(senderAccount.getBalance() - dekont.getAmount());
                    receiverAccount.setBalance(receiverAccount.getBalance() + dekont.getAmount());
                    break;
                default:
                    break;
            }

        }
    }

    //Para yatirma
    //  Hesap varm
    public void depositCash(Account account, double amount) {
        if (!checkDepositAccountExistance(account.getDepositAccountId())) {
            account.setDepositAccountId(appenNewDepositAccount());
        }
        DepositDetails dekont = new DepositDetails(generateId(), null, account.getUsername(), amount);
        DepositAccount currentAccount = searchDepositAccount(account.getDepositAccountId());
        enlargeDepositDetails(currentAccount);
        currentAccount.getHistory()[currentAccount.getHistory().length - 1] = dekont;
        updateBalance(dekont);
    }
    //Para transferi
    //  Hesap varmi, yeni hesap açılırsa bakie 0 olacağından işlem sonlandırılacak.
    //  Bakiye kontrolü, yeterli bakiye yoksa işlem sonlandırılacak.
    //  Alici hesap kontrolü, alici hesabı yoksa işlem sonlandırılacak
    //  alici ve gönderenin hesap bilgilgilerini güncelle.
    
    public void transferCash(Account account, double amount){
        DepositAccount gonderenMevduatHesabi = searchDepositAccount(account.getDepositAccountId());
        if (gonderenMevduatHesabi == null) {
            System.out.println("Mevduat hesabınız bulunmamaktadır.");
            return;
        }
        if (!checkBalanceForValidAmount(account.getDepositAccountId(), amount)) {
            System.out.println("Yeterli bakiyeniz bulunmamaktadır.");
            return;
        }
        System.out.println("Alıcı hesap seçimi yapınız\n"
                + "1 - Kullanıcı adı arama.\n"
                + "2 - Ad, soyad ile arama.");
        int aramaYontemi = scanner.nextInt();
        Account aliciHesabi = null;
        switch (aramaYontemi) {
            case 1: 
                System.out.println("Kullanıcı adı giriniz.");
                aliciHesabi = accountUtility.searchAccount(scanner.next());
                break;
            case 2:
                System.out.println("Arama metnini giriniz.");
                String aramaMetni = scanner.next();
                String[] aranacakKelimeler = aramaMetni.split(" ");
                aliciHesabi = accountUtility.searchAccount(aranacakKelimeler);
                break;
            default:
                break;
        }
        if ( aliciHesabi == null) {
            System.out.println("Alici hesabı bulunamadi");
            return;
        }
        DepositAccount aliciMevduatHesabi = searchDepositAccount(aliciHesabi.getDepositAccountId());
        if (aliciMevduatHesabi == null) {
            aliciHesabi.setDepositAccountId(appenNewDepositAccount());
            aliciMevduatHesabi = searchDepositAccount(aliciHesabi.getDepositAccountId());
        }
        DepositDetails transferDekontu = new DepositDetails(generateId(), account.getUsername(), aliciHesabi.getUsername(), amount);
        enlargeDepositDetails(gonderenMevduatHesabi);
        gonderenMevduatHesabi.getHistory()[gonderenMevduatHesabi.getHistory().length - 1] = transferDekontu;
        enlargeDepositDetails(aliciMevduatHesabi);
        aliciMevduatHesabi.getHistory()[aliciMevduatHesabi.getHistory().length - 1] = transferDekontu;
        updateBalance(transferDekontu);
        System.out.println("İşleminiz gerçekleşmiştir.");
    }
}
