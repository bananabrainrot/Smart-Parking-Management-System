package repositories;

import entities.Tariff;

public interface ITariffRepository {
    Tariff getStandardTariff(); // Получить текущую стоимость часа

    double getRateByName(String standard);
}