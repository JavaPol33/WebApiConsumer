package dto;

import com.google.gson.internal.LinkedTreeMap;

public class CurrencyDto {

    private double amount;

    private String base;

    private String date;

    private LinkedTreeMap<String, Double> rates;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LinkedTreeMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(LinkedTreeMap<String, Double> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CurrencyDto{" +
                "amount=" + amount +
                ", base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}
