import com.google.gson.Gson;

import java.util.List;

public class GsonExample {

    public String parse() {

        String json = "{\n" +
                "    \"amount\": 1.0,\n" +
                "    \"base\": \"PLN\",\n" +
                "    \"date\": \"2020-12-11\",\n" +
                "    \"rates\": {\n" +
                "        \"USD\": 0.27339\n" +
                "    }\n" +
                "}";

        Gson gson = new Gson();

        Car car = gson.fromJson(json, Car.class);

        return car.toString();
    }

    private class Car {

        private String brand;
        private int doors;
        private double amount;
        private String base;
        // private List<String> rates;

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

//        public List<String> getRates() {
//            return rates;
//        }
//
//        public void setRates(List<String> rates) {
//            this.rates = rates;
//        }

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

    private class Rate {

    }
}
