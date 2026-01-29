    package entities;

    public class ParkingSpot {
        private int id;
        private String spotNumber;
        private Boolean isAvailable;

        public ParkingSpot(){
        }

        public ParkingSpot(int id, String spotNumber, Boolean isAvailable){
            this.id = id;
            this.spotNumber = spotNumber;
            this.isAvailable = isAvailable;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSpotNumber() {
            return spotNumber;
        }

        public void setSpotNumber(String spotNumber) {
            this.spotNumber = spotNumber;
        }

        public Boolean getAvailable() {
            return isAvailable;
        }

        public void setAvailable(Boolean available) {
            isAvailable = available;
        }
    }