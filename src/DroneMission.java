public class DroneMission {
    //main field
    private final String missionId;
    private final String droneId;
    private final Coordinates targetCoordinates;
    private final double maxFlightAltitude;

    // optional field
    private final boolean enableGps;
    private final int batteryCapacity;
    private final String cameraResolution;
    private final double payloadWeight;
    private final boolean enableThermalImaging;
    private final boolean autoReturnHome;
    private final double windSpeed;
    private final boolean isRaining;

    private DroneMission(Builder builder) {
        this.missionId = builder.missionId;
        this.droneId = builder.droneId;
        this.targetCoordinates = builder.targetCoordinates;
        this.maxFlightAltitude = builder.maxFlightAltitude;
        this.enableGps = builder.enableGps;
        this.batteryCapacity = builder.batteryCapacity;
        this.cameraResolution = builder.cameraResolution;
        this.payloadWeight = builder.payloadWeight;
        this.enableThermalImaging = builder.enableThermalImaging;
        this.autoReturnHome = builder.autoReturnHome;
        this.windSpeed = builder.windSpeed;
        this.isRaining = builder.isRaining;

    }

    // getters
    public String getMissionId() { return missionId; }
    public String getDroneId() { return droneId; }
    public Coordinates getTargetCoordinates() { return targetCoordinates; }
    public double getMaxFlightAltitude() { return maxFlightAltitude; }
    public boolean isEnableGps() { return enableGps; }
    public int getBatteryCapacity() { return batteryCapacity; }
    public String getCameraResolution() { return cameraResolution; }
    public double getPayloadWeight() { return payloadWeight; }
    public boolean isEnableThermalImaging() { return enableThermalImaging; }
    public boolean isAutoReturnHome() { return autoReturnHome; }
    public double getWindSpeed() { return windSpeed; }
    public boolean isRaining() { return isRaining; }


    public static class Builder {
        // main
        private final String missionId;
        private final String droneId;
        private Coordinates targetCoordinates;
        private double maxFlightAltitude;

        // optional with default things
        private boolean enableGps = true;
        private int batteryCapacity = 100;
        private String cameraResolution = "1080p";
        private double payloadWeight = 0.0;
        private boolean enableThermalImaging = false;
        private boolean autoReturnHome = true;
        private double windSpeed = 0.0;
        private boolean isRaining = false;

        public Builder(String missionId, String droneId) {
            this.missionId = missionId;
            this.droneId = droneId;
        }

        public Builder targetCoordinates(Coordinates targetCoordinates) {
            this.targetCoordinates = targetCoordinates;
            return this;
        }

        public Builder maxFlightAltitude(double maxFlightAltitude) {
            this.maxFlightAltitude = maxFlightAltitude;
            return this;
        }

        public Builder withGps(boolean enableGps) {
            this.enableGps = enableGps;
            return this;
        }

        public Builder batteryCapacity(int batteryCapacity) {
            this.batteryCapacity = batteryCapacity;
            return this;
        }

        public Builder cameraResolution(String cameraResolution) {
            this.cameraResolution = cameraResolution;
            return this;
        }

        public Builder payloadWeight(double payloadWeight) {
            this.payloadWeight = payloadWeight;
            return this;
        }

        public Builder withThermalImaging(boolean enableThermalImaging) {
            this.enableThermalImaging = enableThermalImaging;
            return this;
        }

        public Builder withAutoReturnHome(boolean autoReturnHome) {
            this.autoReturnHome = autoReturnHome;
            return this;
        }
        public Builder windSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }
        public Builder withRain(boolean isRaining) {
            this.isRaining = isRaining;
            return this;
        }

        public DroneMission build() {
            // singlefield
            if (missionId == null || missionId.isBlank()) {
                throw new IllegalArgumentException("Mission ID cannot be empty");
            }
            if (targetCoordinates == null) {
                throw new IllegalArgumentException("Target coordinates are required");
            }

            //latitude -90..90, longtitude -180..180 validation
            if (targetCoordinates.getLatitude() < -90 || targetCoordinates.getLatitude() > 90 ||
                    targetCoordinates.getLongitude() < -180 || targetCoordinates.getLongitude() > 180) {
                throw new IllegalArgumentException("Invalid GPS coordinates! Latitude must be [-90, 90], Longitude must be [-180, 180].");
            }

            // altitude validation
            if (maxFlightAltitude <= 0 || maxFlightAltitude > 3000) {
                throw new IllegalArgumentException("Altitude must be between 1 and 3000 meters.");
            }

            // max weight of drone
            if (payloadWeight < 0 || payloadWeight > 5.0) {
                throw new IllegalArgumentException("Payload weight exceeds drone capacity (max 5.0 kg).");
            }

            // cross field

            // every mission high than 500m requires gps on and battery > 80
            if (maxFlightAltitude >= 500.0 && (!enableGps || batteryCapacity < 80)) {
                throw new IllegalStateException("High-altitude missions (>=500m) REQUIRE enabled GPS and at least 80% battery capacity!");
            }

            // thermal vision requires weight under 1.5kg
            if (enableThermalImaging && payloadWeight < 1.5) {
                throw new IllegalStateException("Thermal imaging camera requires carrying capacity of at least 1.5kg.");
            }

            return new DroneMission(this);

        }
    }
}