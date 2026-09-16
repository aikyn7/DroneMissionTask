public class Main {
    public static void main(String[] args) {
        Coordinates target = new Coordinates(43.238949, 76.889709);
        MissionDirector director = new MissionDirector();

        System.out.println("=== 1. PRESET CONFIGURATIONS ===");

        // SAFE Preset
        DroneMission safeMission = director.constructSafeMission(
                new DroneMission.Builder("M-SAFE-01", "DRONE-ALPHA"),
                target
        );
        System.out.println("SAFE Mission created! 🍌 Drone: " + safeMission.getDroneId());

        // SURVEILLANCE Preset
        DroneMission surveillanceMission = director.constructSurveillanceMission(
                new DroneMission.Builder("M-SURV-02", "DRONE-BETA"),
                target
        );
        System.out.println("Surveillance Mission created! Drone: " + surveillanceMission.getDroneId() +
                ", Thermal: " + surveillanceMission.isEnableThermalImaging());

        // DELIVERY Preset
        DroneMission deliveryMission = director.constructDeliveryMission(
                new DroneMission.Builder("M-DELIV-03", "DRONE-GAMMA"),
                target
        );
        System.out.println("Delivery Mission created! Payload weight: " + deliveryMission.getPayloadWeight() + " kg");

        System.out.println("\n=== 2. REAL-TIME WEATHER MISSION ===");

        // get real time weather data from openweather
        double currentWind = WeatherService.getWindSpeed(target.getLatitude(), target.getLongitude());

        try {
            DroneMission weatherMission = new DroneMission.Builder("M-WEATHER-01", "DRONE-DELTA")
                    .targetCoordinates(target)
                    .maxFlightAltitude(150.0)
                    .batteryCapacity(95)
                    .payloadWeight(0.5)
                    .windSpeed(currentWind)
                    .withRain(false)
                    .build();

            System.out.println("Weather Mission created successfully! Current wind: " + weatherMission.getWindSpeed() + " m/s");

        } catch (IllegalStateException e) {
            System.err.println("Weather Validation Failed: " + e.getMessage());
        }

        System.out.println("\n=== 3. WEATHER VALIDATION FAILURE TEST ===");

        // demonstration of bad weather , wind 18 m/s
        try {
            DroneMission stormMission = new DroneMission.Builder("M-STORM-02", "DRONE-EPSILON")
                    .targetCoordinates(target)
                    .maxFlightAltitude(300.0)
                    .windSpeed(18.0) // Ветер 18 м/с превышает порог в 15 м/с
                    .build();

        } catch (IllegalStateException e) {
            System.out.println("Expected rejection: " + e.getMessage());
        }
    }
}