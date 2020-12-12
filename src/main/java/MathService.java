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

    public String frankfurter() throws CustomException {
        String uri = "https://api.frankfurter.app/latest?to=USD&from=PLN";
        return get(uri);
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
