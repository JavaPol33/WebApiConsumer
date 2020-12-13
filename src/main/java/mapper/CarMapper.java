package mapper;

import database.Car;
import dto.CarDto;

public class CarMapper {

    public static Car mapCarDtoToCar(CarDto dto) {

        Car result = new Car();
        result.setBase(dto.getBase());
        result.setAmount(dto.getAmount());
        // result.setRateName(dto.getRates());

        return result;
    }
}