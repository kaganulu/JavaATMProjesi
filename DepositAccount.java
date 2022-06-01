/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sınıflar;

public class DepositAccount {
    private long id;
    private double balance;
    private DepositDetails[] history;
    
    public DepositAccount(long id) {
        this.id= id;
        this.balance = 0.0d;
        this.history = new DepositDetails[0];
    }
    
    public String historyString (){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.history.length; i++) {
            sb.append(this.history[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public DepositDetails[] getHistory() {
        return history;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setHistory(DepositDetails[] history) {
        this.history = history;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id)
                .append(" nolu hesapta ").append(balance).append(" TL bulunmaktadır\n")
                .append("Toplamda ").append(this.history.length).append(" işlem yaplmıştır.\n")
                .append(historyString());
        return sb.toString();
    }
    
}
