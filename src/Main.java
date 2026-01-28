import repositories.IParkingSpotRepository;
import repositories.IReservationRepository;
import repositories.ITariffRepository;
import repositories.IVehicleRepository;
import repositories.implementations.ParkingSpotRepositoryImpl;
import repositories.implementations.ReservationRepositoryImpl;
import repositories.implementations.TariffRepositoryImpl;
import repositories.implementations.VehicleRepositoryImpl;
import services.PricingService;
import services.ReservationService;

public class Main {
    public static void main(String[] args) {
        // 1. Инициализируем инфраструктуру
        IDB db = new PostgresDB();

        // 2. Создаем репозитории
        IParkingSpotRepository spotRepo = new ParkingSpotRepositoryImpl(db);
        IVehicleRepository vehicleRepo = new VehicleRepositoryImpl(db);
        IReservationRepository resRepo = new ReservationRepositoryImpl(db);
        ITariffRepository tariffRepo = new TariffRepositoryImpl(db);

        // 3. Создаем сервисы
        PricingService pricing = new PricingService();
        ReservationService service = new ReservationService(spotRepo, vehicleRepo, resRepo, tariffRepo, pricing);

        // 4. Тестируем
        try {
            service.reserveSpot("123ABC01");
            // ...
            service.releaseSpot("123ABC01");
        } catch (Exception e) {
            System.err.println("error: " + e.getMessage());
        }
    }
}