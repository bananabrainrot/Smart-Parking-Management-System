package repositories;

import entities.Tariff;

public interface ITariffRepository {
    Tariff getStandardTariff(); // Получить текущую стоимость часа
}