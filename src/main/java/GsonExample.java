import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

public class GsonExample {

    public String parse() throws CustomException {

//        String json = "{\n" +
//                "    \"amount\": 1.0,\n" +
//                "    \"base\": \"PLN\",\n" +
//                "    \"date\": \"2020-12-11\",\n" +
//                "    \"rates\": {\n" +
//                "        \"USD\": 0.27339\n" +
//                "    },\n" +
//                "    \"age_min\": null" +
//                "}";

        MathService mathService = new MathService();
        String json = mathService.frankfurter();

        Gson gson = new Gson();
        Car car = gson.fromJson(json, Car.class);

        return car.toString();
    }

    private class Car {

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
            return "Car{" +
                    "brand='" + brand + '\'' +
                    ", doors=" + doors +
                    ", amount=" + amount +
                    ", base='" + base + '\'' +
                    '}';
        }
    }
}
