public class Main {
    public static void main(String[] args) {
        Coordinates target = new Coordinates(43.238949, 76.889709);

        DroneMission mission = new DroneMission.Builder("MISSION-001", "DRONE-X")
                .targetCoordinates(target)
                .maxFlightAltitude(500.0)
                .withGps(true)
                .batteryCapacity(90)
                .cameraResolution("4K")
                .payloadWeight(2.0)
                .withThermalImaging(true)
                .build();

        System.out.println("Mission created for drone: " + mission.getDroneId());
    }
}