/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sınıflar;

public class DepositDetails {
    private long id;
    private long time;
    private String sender;
    private String receiver;
    private double amount;
    
    public DepositDetails(long id, String sender, String receiver, double amount) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.time = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public double getAmount() {
        return amount;
    }
    
    @Override
    public String toString(){
        String islem="";
        if (sender == null && receiver != null) {
            islem = "Para Yatırma";
        } else if ( sender != null && receiver == null) {
            islem = "Para Çekme";
        } else if (sender != null && receiver != null) {
            islem = "Transfer";
        }
        String gonderen = sender == null ? "\t" : sender;
        String alici = receiver == null ? "\t" : receiver;
        
        return  id+"\t"+ islem + "\t" + gonderen + "\t" + alici + "\t" + amount;
    }
    
}
