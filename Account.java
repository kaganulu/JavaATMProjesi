/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sınıflar;

/**
 *
 * @author kaganulu
 */
public class Account {

    private String username;
    private String password;
    private String name;
    private String surname;
    private int yearOfBirth;
    private int monthOfBirth;
    private int dayOfBirth;
    private boolean isEnabled;
    private int loginAttempt;
    private long depositAccountId;

    public Account(int username, String password, String name, String surname, int yearOfBirth, int monthOfBirth, int dayOfBirth) {
        //155 = BeşBeşBir
        setUsername(username);
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.yearOfBirth = yearOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.dayOfBirth = dayOfBirth;
        this.isEnabled = true;
        this.loginAttempt = 0;
    }
    
    public Account( String username, String password) {
        this.username = username;
        this.password = password;
        this.isEnabled = true;
        this.loginAttempt = 0;
    }

    public Account(String username, String password, String name, String surname, int yearOfBirth, int monthOfBirth, int dayOfBirth, boolean isEnabled, int loginAttempt, long depositAccountId) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.yearOfBirth = yearOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.dayOfBirth = dayOfBirth;
        this.isEnabled = isEnabled;
        this.loginAttempt = loginAttempt;
        this.depositAccountId = depositAccountId;
    }

    Account() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
    
    public long getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(long depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }

    public void resetLoginAttempt() {
        this.loginAttempt = 0;
    }
    
    public void incrementLoginAttamept() {
        this.loginAttempt++;
    }

    public boolean isIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    
    
//113 uc -> 11 UcBir -> 1 UcBirBir
    public void setUsername (int userNameAsNumber) {
        int deger;
        StringBuilder sb = new StringBuilder();
        while (userNameAsNumber > 0) {
            deger = userNameAsNumber%10;
            if (deger == 0 ) {
                sb.append("Sifir");
            } else if ( deger == 1 ) {
                sb.append("Bir");
            } else if ( deger == 2 ) {
                sb.append("Iki");
            } else if ( deger == 3 ) {
                sb.append("Uc");
            } else if ( deger == 4 ) {
                sb.append("Dort");
            } else if ( deger == 5 ) {
                sb.append("Bes");
            } else if ( deger == 6 ) {
                sb.append("Alti");
            } else if ( deger == 7 ) {
                sb.append("Yedi");
            } else if ( deger == 8 ) {
                sb.append("Sekiz");
            } else if ( deger == 9 ) {
                sb.append("Dokuz");
            }
            userNameAsNumber /= 10;
        }
        username = sb.toString();
    }
    
    public String getUsername() {
        return username;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public int getMonthOfBirth() {
        return monthOfBirth;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString(){
        return "Kullanıcı adi : "+username+" Ad : "+name+" Soyadı : "+surname;
    }
}









/* 
   
   private String username;
   private String password;
   private String[] name;
   private String surname;
   private int yearOfBirth;
   private int monthOfBirth;
   private int dayOfBirth;
   
   
   public Account(int isimSayısı, String isim, String soyisim){
       this.name = new String[isimSayısı];
       this.name[0]= isim;
       this.surname = soyisim;
   }
   
   
   public void setName(int pozisyon, String isim){
       this.name[pozisyon] = isim;
   }
   
   public String getName(int pozisyon){
       return name[pozisyon];
       
   }
   
   
    public String getUsername() {
        return username;
    }

    public void setUsername(int rakam) { // Kullanıcı adı rakam yapmak için algoritma rakamları tersten yazıyor
        int geciciDeger;
        StringBuilder kullaniciAdi = new StringBuilder();
        while(rakam > 0){
            geciciDeger = rakam%10;
            if (geciciDeger == 1) {
                kullaniciAdi.append("Bir");
                
            } else if (geciciDeger == 2) {
                kullaniciAdi.append("İki");
            } else {
                kullaniciAdi.append("Sıfır");
            }
                rakam /=10;
        }
        
        this.username = kullaniciAdi.toString();
   
   
   // diğer sayfada olacaklar  
        Account account1 = new Account(2," Kagan ", " Ulu ");
        account1.setUsername(221);
        System.out.println(account1.getUsername());
   
 */
