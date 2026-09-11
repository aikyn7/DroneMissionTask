public class DroneMission {
    // Required
    private String missionId;
    private String droneId;
    private Coordinates targetCoordinates;
    private double maxFlightAltitude;

    // Optional
    private boolean enableGps;
    private int batteryCapacity;
    private String cameraResolution;
    private double payloadWeight;
    private boolean enableThermalImaging;
    private boolean autoReturnHome;

    // tel.constructor ( anti pattern)
    public DroneMission(String missionId, String droneId, Coordinates targetCoordinates,
                        double maxFlightAltitude, boolean enableGps, int batteryCapacity,
                        String cameraResolution, double payloadWeight,
                        boolean enableThermalImaging, boolean autoReturnHome) {
        this.missionId = missionId;
        this.droneId = droneId;
        this.targetCoordinates = targetCoordinates;
        this.maxFlightAltitude = maxFlightAltitude;
        this.enableGps = enableGps;
        this.batteryCapacity = batteryCapacity;
        this.cameraResolution = cameraResolution;
        this.payloadWeight = payloadWeight;
        this.enableThermalImaging = enableThermalImaging;
        this.autoReturnHome = autoReturnHome;
    }
}