package service;

import com.google.gson.Gson;
import database.Currency;
import database.CurrencyDao;
import dto.CurrencyDto;
import http.HttpClient;
import mapper.CurrencyMapper;

import java.util.List;

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
}
