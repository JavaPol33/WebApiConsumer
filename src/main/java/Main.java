import com.itextpdf.text.DocumentException;
import dto.CarDto;
import dto.CurrencyDto;
import http.HttpClient;
import service.CurrencyService;
import service.MathService;
import service.ProductService;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HttpClient httpClient = new HttpClient();
        MathService mathService = new MathService(httpClient);
        CurrencyService currencyService = new CurrencyService(httpClient);

        System.out.println("Wybierz opcję");
        System.out.println("1) wylosuj ciekawostkę");
        System.out.println("2) poznaj informację o liczbie");
        System.out.println("3) Przelicz kurs waluty");
        System.out.println("4) Zapisz waluty do pliku");

        Scanner scannerNumber = new Scanner(System.in);
        Scanner scannerString = new Scanner(System.in);

        int option = scannerNumber.nextInt();
        String result;

        switch (option) {
            case 1:
                result = mathService.randomMath();
                break;
            case 2:
                System.out.println("Podaj liczbę");
                int number = scannerNumber.nextInt();
                result = mathService.math(number);
                break;
            case 3:
                System.out.println("Podaj datę (kliknij enter by wybrać bieżącą datę)");
                String date = scannerString.nextLine();
                if (date.equals("")) {
                    date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                }

                String from = "";
                while (from == "") {
                    System.out.println("Podaj walutę bazową (PLN, EUR, USD, GBP)");
                    from = scannerNumber.next();

                    if (!(from.equals("PLN") || from.equals("EUR") || from.equals("USD") || from.equals("GBP"))) {
                        from = "";
                    }
                }

                String to = "";
                while (to == "") {
                    System.out.println("Podaj walutę docelową (PLN, EUR, USD, GBP)");
                    to = scannerNumber.next();

                    if (!(to.equals("PLN") || to.equals("EUR") || to.equals("USD") || to.equals("GBP"))) {
                        to = "";
                    }
                }

                CurrencyDto currencyDto = currencyService.frankfurter(date, from, to);
                result = currencyDto.toString();
                break;
            case 4:
                currencyService.exportCurrencyToTextFile();
                currencyService.exportCurrencyToCsvFile();
                String excelPath = currencyService.exportCurrencyToExcelFile();

                System.out.println(excelPath);

                try {
                    currencyService.exportCurrencyToPdf();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                result = "eksport zakończony";
                break;
            case 5:
                ProductService productService = new ProductService();
                productService.parse();
            default:
                result = "Nie rozpoznano wyboru";
        }

        System.out.println(result);
    }
}
