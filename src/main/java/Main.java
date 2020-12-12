import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        MathService mathService = new MathService();
        try {


            System.out.println("Wybierz opcję");
            System.out.println("1) wylosuj ciekawostkę");
            System.out.println("2) poznaj informację o liczbie");

            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            String result;

            switch (option) {
                case 1:
                     result = mathService.randomMath();
                     break;
                case 2:
                    System.out.println("Podaj liczbę");
                    int number = scanner.nextInt();
                    result = mathService.math(number);
                    break;
                default:
                    result = "Nie rozpoznano wyboru";
            }

            System.out.println(result);

        } catch (CustomException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}
