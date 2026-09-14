public class MissionDirector {

    // safe
    public DroneMission constructSafeMission(DroneMission.Builder builder, Coordinates target) {
        return builder
                .targetCoordinates(target)
                .maxFlightAltitude(150.0)
                .batteryCapacity(100)
                .withGps(true)
                .withAutoReturnHome(true)
                .build();
    }

    // surveiilance
    public DroneMission constructSurveillanceMission(DroneMission.Builder builder, Coordinates target) {
        return builder
                .targetCoordinates(target)
                .maxFlightAltitude(1500.0) //high fly
                .batteryCapacity(100)
                .withGps(true)
                .cameraResolution("4K")
                .payloadWeight(2.0)
                .withThermalImaging(true)
                .withAutoReturnHome(true)
                .build();
    }

    // delivery
    public DroneMission constructDeliveryMission(DroneMission.Builder builder, Coordinates target) {
        return builder
                .targetCoordinates(target)
                .maxFlightAltitude(300.0)
                .batteryCapacity(90)
                .withGps(true)
                .payloadWeight(3.5)
                .cameraResolution("720p")
                .withAutoReturnHome(true)
                .build();
    }
}