/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Payable;

/**
 *
 * @author jeanp
 */
public class Client extends Person implements Payable {
    private int memberId;
    private Amount balance;
    
    public static final int MEMBER_ID = 456;
    public static final Amount BALANCE = new Amount(50.00);

    public Client(String name) {
        super(name);
        this.memberId = MEMBER_ID;
        this.balance = BALANCE;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean pay(Amount amount) {
        if (balance.getValue() >= amount.getValue()) { // if balance is bigger than amount, subtracts balance
            balance.subtract(balance);
            return true;
        } else {
            return false;
        }
    }
}
