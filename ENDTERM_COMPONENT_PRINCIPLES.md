# Endterm Defense Notes: Components & Component Principles

## 1) Component decomposition in this project

Even though the project is a CLI monolith, the codebase is already organized into **logical components**.

### ReservationComponent
**Responsibility:** manage parking lifecycle (reserve/release spot).

- `services/ReservationService.java` orchestrates reserve/release flow.
- `services/ParkingLotManager.java` exposes one entry point for reservation actions.
- `entities/Reservation.java` is the domain model.

### PaymentComponent
**Responsibility:** calculate and represent payment.

- `services/PricingService.java` calculates cost from start/end time + rate.
- `entities/Invoice.java` represents payment output and uses Builder.
- `entities/Tariff.java` represents tariff data.

### MonitoringComponent
**Responsibility:** show current parking availability and state.

- `repositories/IParkingSpotRepository.java#getAllFreeSpots()` provides availability data.
- `entities/ListResult.java` wraps returned list + metadata.
- `src/Main.java` menu option "Show All Free Spots" renders monitoring output.

### ReportingComponent
**Responsibility:** produce finished-reservation financial result.

- `repositories/IReservationRepository.java#finishReservation(...)` stores final cost.
- `services/ReservationService.java#releaseSpot(...)` computes total and creates invoice.
- In CLI mode, invoice total is printed; this can evolve into CSV/PDF reporting.

---

## 2) DataAccessComponent encapsulation (CRP)

Database access is reused through a dedicated data-access layer:

- `edu/aitu/oop3/db/DatabaseConnection.java` centralizes JDBC connection creation.
- `repositories/implementations/*` isolate SQL and row mapping.
- `services/*` depend on repository interfaces (`IParkingSpotRepository`, `IReservationRepository`, `IVehicleRepository`), not SQL.

This is a practical **DataAccessComponent**: all components reuse the same access mechanisms and should not import JDBC directly in service logic.

**Why this supports CRP (Common Reuse Principle):**
- Classes that are reused together are packaged together: DB setup/connection + repository implementations.
- Reservation/Payment/Monitoring logic reuses these same data-access abstractions instead of duplicating DB code.

---

## 3) Adding new parking zones or tariffs with minimal change (CCP, OCP)

### New parking zone/spot category
Current code already supports subtype creation via Factory:
- `entities/SpotType.java`
- `entities/SpotFactory.java`
- subtype classes (`StandardSpot`, `DisabledSpot`, `ElectricSpot`)

To add a new zone/type (example: `VIP`):
1. Add `VIP` to `SpotType` and detection rule in `fromSpotNumber(...)`.
2. Create `VipSpot extends ParkingSpot`.
3. Add one branch in `SpotFactory#createSpot(...)`.
4. No changes required in `ReservationService` logic.

This is **OCP**: extend by adding new type classes/branches, not rewriting business workflow.

### New tariff type
To add tariff (example: Night tariff):
1. Insert new row into `tariffs` table.
2. Pass new `tariffId` from UI/API.
3. (Optional enhancement) repository reads tariff rate by id instead of fixed constant in `releaseSpot`.

This aligns with **CCP** and **OCP**:
- Tariff-related behavior changes are concentrated in pricing/tariff area.
- Reservation lifecycle remains stable.

---

## 4) Defense-ready one-minute summary

"In the endterm architecture, I split responsibilities into Reservation, Payment, Monitoring, and Reporting components. Database access is encapsulated in a reusable DataAccessComponent (`DatabaseConnection` + repository implementations), so services depend on repository interfaces instead of JDBC details. For extensibility, new parking zones are added through `SpotType` + `SpotFactory` + new subtype classes, which keeps reservation logic unchanged (OCP). New tariff types are introduced mostly as data/config changes with localized pricing updates, so change impact remains focused (CCP)."
