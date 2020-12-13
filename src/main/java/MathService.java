import com.google.gson.Gson;
import database.Car;
import dto.CarDto;
import mapper.CarMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MathService {

    public String randomMath() throws CustomException {
        String uri = "http://numbersapi.com/random/math";
        return get(uri);
    }

    public String math(int number) throws CustomException {
        String uri = "http://numbersapi.com/" + number;
        return get(uri);
    }

    public CarDto frankfurter() throws CustomException {
        // 1. sprawdź, czy kusr waluty jest w db
        // 2. jeśli tak, to zwróć
        // Entity --> Dto --> zwrócimy
        // 3. jeśli nie, to pobierz z web-service --> Dto
        // 4. zapisz do db --> Dto --> map to Entity
        // 5. zwróć Dto

        String uri = "https://api.frankfurter.app/latest?to=USD,EUR&from=PLN";
        String json = get(uri);

        Gson gson = new Gson();
        CarDto car = gson.fromJson(json, CarDto.class);

        Car entity = CarMapper.mapCarDtoToCar(car);
        // TODO: save to database

        return car;
    }

    private String get(String uri) throws CustomException {
        try {
            URL url = new URL(uri);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = in.readLine()) != null) {
                stringBuilder.append(line);
            }
            in.close();

            if (stringBuilder.toString() == "") {
                throw new CustomException("Jakiś inny komunikat obłędzie");
            }

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            System.out.println("jakiś błąd");
            throw new CustomException("jakiś błąd");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
