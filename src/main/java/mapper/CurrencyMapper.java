package mapper;

import com.google.gson.internal.LinkedTreeMap;
import database.Currency;
import dto.CurrencyDto;

import java.util.ArrayList;
import java.util.List;

public class CurrencyMapper {

    public static List<Currency> mapCurrencyDtoToEntity(CurrencyDto currencyDto) {

        List<Currency> result = new ArrayList<>();
        for (String key : currencyDto.getRates().keySet()) {
            Currency currency = new Currency();
            currency.setAmount(currencyDto.getAmount());
            currency.setBase(currencyDto.getBase());
            currency.setDate(currencyDto.getDate());
            currency.setRateName(key);
            currency.setRateValue(currencyDto.getRates().get(key));

            result.add(currency);
        }

        return result;
    }

    public static CurrencyDto mapCurrencyToCurrencyDto(Currency currency) {

        CurrencyDto result = new CurrencyDto();
        result.setAmount(currency.getAmount());
        result.setBase(currency.getBase());
        result.setDate(currency.getDate());

        LinkedTreeMap<String, Double> rates = new LinkedTreeMap();
        rates.put(currency.getRateName(), currency.getRateValue());

        result.setRates(rates);

        return result;
    }
}
