package components.dataaccess.api;

import entities.Tariff;

import java.util.Optional;

public interface TariffGateway {
    Optional<Tariff> findById(int id);
}
