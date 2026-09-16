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
            // validation

            // single field rules
            if (missionId == null || missionId.isBlank()) {
                throw new IllegalArgumentException("Mission ID cannot be empty");
            }
            if (targetCoordinates == null) {
                throw new IllegalArgumentException("Target coordinates are required");
            }
            if (maxFlightAltitude <= 0 || maxFlightAltitude > 5000) {
                throw new IllegalArgumentException("Altitude must be between 1 and 5000 meters");
            }

            //cross field
            // misiion >1000м gps and battery >= 80%
            if (maxFlightAltitude > 1000 && (!enableGps || batteryCapacity < 80)) {
                throw new IllegalStateException("High-altitude missions (>1000m) require GPS and at least 80% battery.");
            }

            // using thermal vision requires weight under 1,5kg
            if (enableThermalImaging && payloadWeight < 1.5) {
                throw new IllegalStateException("Thermal imaging camera requires carrying capacity of at least 1.5kg.");
            }
            // new weather rule
            // super strong wind 15m/s >
            if (windSpeed > 15.0) {
                throw new IllegalStateException("Mission aborted: Wind speed is too high (" + windSpeed + " m/s). Safe limit is 15 m/s.");
            }

            // 2. strong wind , 8 m/s > -  high 200
            if (windSpeed > 8.0 && maxFlightAltitude > 200.0) {
                throw new IllegalStateException("Mission aborted: High wind (" + windSpeed + " m/s) limits flight altitude to 200m.");
            }

            // 3. if raining weight under 1 kg
            if (isRaining && payloadWeight > 1.0) {
                throw new IllegalStateException("Mission aborted: Cannot carry heavy payload (>1.0kg) during rain.");
            }


            return new DroneMission(this);
        }
    }
}