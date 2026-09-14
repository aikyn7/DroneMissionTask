public class Main {
    public static void main(String[] args) {
        Coordinates target = new Coordinates(43.238949, 76.889709);
        MissionDirector director = new MissionDirector();

        // create mission with preset SAFE
        DroneMission safeMission = director.constructSafeMission(
                new DroneMission.Builder("M-SAFE-01", "DRONE-ALPHA"),
                target
        );
        System.out.println("SAFE Mission created! 🍌 Drone: " + safeMission.getDroneId());

        // create mission with preset SURVEILLANCE
        DroneMission surveillanceMission = director.constructSurveillanceMission(
                new DroneMission.Builder("M-SURV-02", "DRONE-BETA"),
                target
        );
        System.out.println("Surveillance Mission created! Drone: " + surveillanceMission.getDroneId() +
                ", Thermal: " + surveillanceMission.isEnableThermalImaging());

        // create mission with preset DELIVERY
        DroneMission deliveryMission = director.constructDeliveryMission(
                new DroneMission.Builder("M-DELIV-03", "DRONE-GAMMA"),
                target
        );
        System.out.println("Delivery Mission created! Payload weight: " + deliveryMission.getPayloadWeight() + " kg");
    }
}