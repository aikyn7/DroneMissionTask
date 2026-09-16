import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DroneMissionGUI extends JFrame {

    private JTextField txtMissionId;
    private JTextField txtDroneId;
    private JTextField txtLat;
    private JTextField txtLon;
    private JTextField txtAltitude;
    private JTextField txtBattery;
    private JTextField txtPayload;
    private JCheckBox chkGps;
    private JCheckBox chkThermal;
    private JCheckBox chkAutoReturn;
    private JComboBox<String> comboCamera;

    // presets choose
    private JComboBox<String> comboPresets;

    public DroneMissionGUI() {
        setTitle("Drone Mission Constructor (Builder Pattern)");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // header
        JLabel lblTitle = new JLabel("Drone Mission Planning System", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        // panel form
        JPanel panelForm = new JPanel(new GridLayout(12, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelForm.add(new JLabel("Mission ID:"));
        txtMissionId = new JTextField("M-GUI-01");
        panelForm.add(txtMissionId);

        panelForm.add(new JLabel("Drone ID:"));
        txtDroneId = new JTextField("DRONE-SWING");
        panelForm.add(txtDroneId);

        panelForm.add(new JLabel("Latitude:"));
        txtLat = new JTextField("43.238949");
        panelForm.add(txtLat);

        panelForm.add(new JLabel("Longitude:"));
        txtLon = new JTextField("76.889709");
        panelForm.add(txtLon);

        panelForm.add(new JLabel("Max Altitude (m):"));
        txtAltitude = new JTextField("300.0");
        panelForm.add(txtAltitude);

        panelForm.add(new JLabel("Battery Capacity (%):"));
        txtBattery = new JTextField("100");
        panelForm.add(txtBattery);

        panelForm.add(new JLabel("Payload Weight (kg):"));
        txtPayload = new JTextField("1.0");
        panelForm.add(txtPayload);

        panelForm.add(new JLabel("Camera Resolution:"));
        comboCamera = new JComboBox<>(new String[]{"720p", "1080p", "4K"});
        comboCamera.setSelectedItem("1080p");
        panelForm.add(comboCamera);

        chkGps = new JCheckBox("Enable GPS", true);
        panelForm.add(chkGps);

        chkThermal = new JCheckBox("Enable Thermal Imaging", false);
        panelForm.add(chkThermal);

        chkAutoReturn = new JCheckBox("Auto Return Home", true);
        panelForm.add(chkAutoReturn);

        // presets
        panelForm.add(new JLabel("Quick Preset (Director):"));
        comboPresets = new JComboBox<>(new String[]{"Custom", "SAFE Preset", "SURVEILLANCE Preset", "DELIVERY Preset"});
        panelForm.add(comboPresets);

        add(panelForm, BorderLayout.CENTER);

        // buttons
        JPanel panelButtons = new JPanel(new FlowLayout());
        JButton btnBuild = new JButton("Build Mission (Builder)");
        btnBuild.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuild.setBackground(new Color(60, 140, 220));
        btnBuild.setForeground(Color.WHITE);

        panelButtons.add(btnBuild);
        add(panelButtons, BorderLayout.SOUTH);

        // director
        comboPresets.addActionListener(e -> applyPreset());

        // build
        btnBuild.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buildMissionFromForm();
            }
        });
    }

    private void applyPreset() {
        String selected = (String) comboPresets.getSelectedItem();
        if ("SAFE Preset".equals(selected)) {
            txtAltitude.setText("150.0");
            txtBattery.setText("100");
            chkGps.setSelected(true);
            chkAutoReturn.setSelected(true);
            chkThermal.setSelected(false);
            txtPayload.setText("0.5");
        } else if ("SURVEILLANCE Preset".equals(selected)) {
            txtAltitude.setText("1500.0");
            txtBattery.setText("100");
            chkGps.setSelected(true);
            comboCamera.setSelectedItem("4K");
            chkThermal.setSelected(true);
            txtPayload.setText("2.0");
        } else if ("DELIVERY Preset".equals(selected)) {
            txtAltitude.setText("300.0");
            txtBattery.setText("90");
            chkGps.setSelected(true);
            txtPayload.setText("3.5");
            chkThermal.setSelected(false);
        }
    }

    private void buildMissionFromForm() {
        try {
            String missionId = txtMissionId.getText();
            String droneId = txtDroneId.getText();
            double lat = Double.parseDouble(txtLat.getText());
            double lon = Double.parseDouble(txtLon.getText());
            double alt = Double.parseDouble(txtAltitude.getText());
            int battery = Integer.parseInt(txtBattery.getText());
            double payload = Double.parseDouble(txtPayload.getText());

            Coordinates coords = new Coordinates(lat, lon);

            // using builder from gui
            DroneMission mission = new DroneMission.Builder(missionId, droneId)
                    .targetCoordinates(coords)
                    .maxFlightAltitude(alt)
                    .batteryCapacity(battery)
                    .payloadWeight(payload)
                    .cameraResolution((String) comboCamera.getSelectedItem())
                    .withGps(chkGps.isSelected())
                    .withThermalImaging(chkThermal.isSelected())
                    .withAutoReturnHome(chkAutoReturn.isSelected())
                    .build();

            // if mission creater without problem with validation
            JOptionPane.showMessageDialog(this,
                    "Mission Successfully Created! 🍌\n" +
                            "Mission ID: " + mission.getMissionId() + "\n" +
                            "Drone ID: " + mission.getDroneId() + "\n" +
                            "Altitude: " + mission.getMaxFlightAltitude() + "m\n" +
                            "Battery: " + mission.getBatteryCapacity() + "%\n" +
                            "Thermal Imaging: " + (mission.isEnableThermalImaging() ? "YES" : "NO"),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numerical values for Coordinates, Altitude, Battery, and Payload.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            // error handling from builder
            JOptionPane.showMessageDialog(this, "Builder Validation Failed:\n" + ex.getMessage(), "Validation Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DroneMissionGUI().setVisible(true);
        });
    }
}