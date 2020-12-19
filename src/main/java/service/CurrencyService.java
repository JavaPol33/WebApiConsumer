package service;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import database.Currency;
import database.CurrencyDao;
import dto.CurrencyDto;
import http.HttpClient;
import mapper.CurrencyMapper;

import java.io.*;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CurrencyService {

    private final HttpClient httpClient;
    private final CurrencyDao currencyDao;

    public CurrencyService(HttpClient httpClient) {
        this.httpClient = httpClient;
        currencyDao = new CurrencyDao();
    }

//    public CurrencyDto frankfurter() {
//        // 1. sprawdź, czy kusr waluty jest w db
//        // 2. jeśli tak, to zwróć
//        // Entity --> Dto --> zwrócimy
//        // 3. jeśli nie, to pobierz z web-service --> Dto
//        // 4. zapisz do db --> Dto --> map to Entity
//        // 5. zwróć Dto
//
//        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        return frankfurter(date);
//    }

    public CurrencyDto frankfurter(String date, String from, String to) {

        CurrencyDto currencyDto;
        Currency currency = currencyDao.getByDateAndByFromAndTo(date, from, to);

        if (currency != null) {
            currencyDto = CurrencyMapper.mapCurrencyToCurrencyDto(currency);
        } else {
            String uri = "https://api.frankfurter.app/" + date + "?to=" + to + "&from=" + from;
            String json = httpClient.get(uri);

            Gson gson = new Gson();
            currencyDto = gson.fromJson(json, CurrencyDto.class);

            List<Currency> entities = CurrencyMapper.mapCurrencyDtoToEntity(currencyDto);
            for (Currency entity : entities) {
                currencyDao.create(entity);
            }
        }

        return currencyDto;
    }

    public void exportCurrencyToTextFile() {

        List<Currency> currencies = currencyDao.getAll();

        try {
            PrintWriter writer = new PrintWriter("waluty.txt");
            for (Currency currency : currencies) {
                writer.printf("id = %d | amount = %.2f | base = %s | date = %s | rateName = %s | rateValue = %.2f\n",
                        currency.getId(),
                        currency.getAmount(),
                        currency.getBase(),
                        currency.getDate(),
                        currency.getRateName(),
                        currency.getRateValue());
            }

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void exportCurrencyToCsvFile() {

        List<Currency> currencies = currencyDao.getAll();

        try {
            PrintWriter writer = new PrintWriter("waluty.csv");
            writer.println("id;amount;base;date;rateName;rateValue");
            for (Currency currency : currencies) {
                writer.printf("%d;%.2f;%s;%s;%s;%.2f\n",
                        currency.getId(),
                        currency.getAmount(),
                        currency.getBase(),
                        currency.getDate(),
                        currency.getRateName(),
                        currency.getRateValue());
            }

            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String exportCurrencyToExcelFile() {
        List<Currency> currencies = currencyDao.getAll();

        Workbook workbook = new XSSFWorkbook(); // obiekt reprezentujący plik xlsx

        Sheet sheet = workbook.createSheet("Kursy walut"); // arkusz w excelu

        String[] columns = "id;amount;base;date;rateName;rateValue".split(";");

        // stworzenie nagłówka (wiersz o indeksie 0)
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // wypełniamny arkusz danymi
        int dataRowIndex = 1;
        for (Currency currency : currencies) {
            Row dataRow = sheet.createRow(dataRowIndex++);

            int dataColumnIndex = 0;
            dataRow.createCell(dataColumnIndex++).setCellValue(currency.getId());
            dataRow.createCell(dataColumnIndex++).setCellValue(currency.getAmount());
            dataRow.createCell(dataColumnIndex++).setCellValue(currency.getBase());
            dataRow.createCell(dataColumnIndex++).setCellValue(currency.getDate());
            dataRow.createCell(dataColumnIndex++).setCellValue(currency.getRateName());
            dataRow.createCell(dataColumnIndex).setCellValue(currency.getRateValue());
        }

        // zapisujemy excel do pliku
        FileOutputStream fileOutputStream = null;
        try {

            File myFile = new File("waluty.xlsx");

            fileOutputStream = new FileOutputStream(myFile);
            workbook.write(fileOutputStream);

            fileOutputStream.close();
            workbook.close();

            return myFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void exportCurrencyToPdf() throws FileNotFoundException, DocumentException {

        List<Currency> currencies = currencyDao.getAll();

        Document iText_xls_2_pdf = new Document();
        PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream("waluty.pdf"));
        iText_xls_2_pdf.open();
        //we have two columns in the Excel sheet, so we create a PDF table with two columns
        //Note: There are ways to make this dynamic in nature, if you want to.

        String[] columns = "id;amount;base;date;rateName;rateValue".split(";");

        PdfPTable my_table = new PdfPTable(columns.length);
        for (String str : columns) {
            my_table.addCell(new PdfPCell(new Phrase(str)));
        }

        //Loop through rows.
        for (Currency currency : currencies) {
            my_table.addCell(new PdfPCell(new Phrase(currency.getId() + "")));
            my_table.addCell(new PdfPCell(new Phrase(currency.getAmount() + "")));
            my_table.addCell(new PdfPCell(new Phrase(currency.getBase())));
            my_table.addCell(new PdfPCell(new Phrase(currency.getDate())));
            my_table.addCell(new PdfPCell(new Phrase(currency.getRateName())));
            my_table.addCell(new PdfPCell(new Phrase(currency.getRateValue() + "")));
        }

        //Finally add the table to PDF document
        iText_xls_2_pdf.add(my_table);
        iText_xls_2_pdf.close();
        //we created our pdf file..
    }
}
