package service;

import com.google.gson.Gson;
import database.Currency;
import dto.CurrencyDto;
import http.HttpClient;
import mapper.CurrencyMapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CurrencyService {

    private final HttpClient httpClient;

    public CurrencyService(HttpClient httpClient) {
        this.httpClient = httpClient;
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

        // TODO: search in database

        String uri = "https://api.frankfurter.app/" + date + "?to=" + to + "&from=" + from;
        String json = httpClient.get(uri);

        Gson gson = new Gson();
        CurrencyDto currencyDto = gson.fromJson(json, CurrencyDto.class);

        List<Currency> entities = CurrencyMapper.mapCurrencyDtoToEntity(currencyDto);
        // TODO: save to database

        return currencyDto;
    }
}
