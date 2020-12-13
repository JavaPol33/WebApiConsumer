package dto;

import com.google.gson.internal.LinkedTreeMap;

public class CarDto {
    private String brand;
    private int doors;
    private double amount;
    private String base;
    private LinkedTreeMap<String, Double> rates;
    private Integer age_min;

    public Integer getAge_min() {
        return age_min;
    }

    public void setAge_min(Integer age_min) {
        this.age_min = age_min;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

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

    public LinkedTreeMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(LinkedTreeMap<String, Double> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CarDto{" +
                "brand='" + brand + '\'' +
                ", doors=" + doors +
                ", amount=" + amount +
                ", base='" + base + '\'' +
                ", rates=" + rates +
                ", age_min=" + age_min +
                '}';
    }
}
