/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author jeanp
 */
public class Amount {
    public double value;
    public static String currency = "$";

    public Amount(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    public Amount sum (Amount value) {
        return new Amount(this.value + value.getValue());
    }
    
    public Amount multiply (double value) {
        return new Amount(this.value * value);
    }
    
    public Amount subtract (Amount value) {
        return new Amount(this.value - value.getValue());
    }
    
    @Override
    public String toString() {
        return value + " " + currency;
    }
}
