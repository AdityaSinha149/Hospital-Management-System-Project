
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HospitalManagementSystem extends Application {

    private TextField fullNameField;
    private TextField ageField;
    private DatePicker dobPicker;
    private ComboBox<String> bloodGroupDropdown;
    private ComboBox<String> genderDropdown;
    private ComboBox<String> stateDropdown;
    private TextField cityField;
    private TextField addressField;
    private TextField zipCodeField;
    private TextField mobileField;
    private TextField emailField;

    String[] patientUsernames = new String[100];
    String[] patientPasswords = new String[100];
    private String fullName, age, dob, bloodGroup, gender, state, city, address, zipCode, mobile, email;
    String[] doctorUsernames = new String[100];
    String[] doctorPasswords = new String[100];
    String[] doctorSpecializations = new String[100];
    ArrayList<String[]> appointments = new ArrayList<>();
    private HashMap<String, ArrayList<String>> patientReports = new HashMap<>();
    HashMap<String, List<String>> doctorTimeSlots = new HashMap<>();  // Tracks booked time slots for each doctor
    int patientCount = 10;
    int doctorCount = 10;
    String currentPatientUsername;
    String currentDoctorUsername;

    public HospitalManagementSystem() {
        String[] doctorNames = {"Amit", "Priya", "Rajesh", "Seema", "Vikas", "Sonal", "Anil", "Meera", "Naveen", "Pooja"};
        String[] specialties = {"Cardiology", "Neurology", "Orthopedics", "Dermatology", "Pediatrics", "Psychiatry", "Ophthalmology", "Radiology", "Oncology", "ENT"};
        for (int i = 0; i < 10; i++) {
            doctorUsernames[i] = doctorNames[i];
            doctorPasswords[i] = doctorNames[i];
            doctorSpecializations[i] = specialties[i];
            doctorTimeSlots.put(doctorNames[i], new ArrayList<>());  // Initialize empty list for time slots
        }

        String[] patientNames = {"Ravi", "Neha", "Vivek", "Kavita", "Suresh", "Anjali", "Mahesh", "Poonam", "Arun", "Ritika"};
        for (int i = 0; i < 10; i++) {
            patientUsernames[i] = patientNames[i];
            patientPasswords[i] = patientNames[i];
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize your UI components here
        fullNameField = new TextField();
        ageField = new TextField();
        dobPicker = new DatePicker();
        bloodGroupDropdown = new ComboBox<>();
        genderDropdown = new ComboBox<>();
        stateDropdown = new ComboBox<>();
        cityField = new TextField();
        addressField = new TextField();
        zipCodeField = new TextField();
        mobileField = new TextField();
        emailField = new TextField();

        // Add components to your layout and stage
        Button doctorButton = new Button("Doctor");
        Button patientButton = new Button("Patient");

        doctorButton.setOnAction(e -> showDoctorLogin(primaryStage));
        patientButton.setOnAction(e -> showPatientLogin(primaryStage));

        VBox layout = new VBox(20);
        layout.getChildren().addAll(doctorButton, patientButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setTitle("Hospital Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Patient Login
    private void showPatientLogin(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(200);

        Button loginButton = new Button("Login");
        Button signupButton = new Button("Signup");
        Button backButton = new Button("Back");

        loginButton.setOnAction(e -> validatePatientLogin(usernameField.getText(), passwordField.getText(), primaryStage));
        signupButton.setOnAction(e -> showPatientSignupPage(primaryStage));
        backButton.setOnAction(e -> start(primaryStage));

        layout.getChildren().addAll(new Label("Patient Login"), usernameField, passwordField, loginButton, signupButton, backButton);
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
    }

    // Doctor Login
    private void showDoctorLogin(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(200);

        Button loginButton = new Button("Login");
        Button signupButton = new Button("Signup");
        Button backButton = new Button("Back");

        loginButton.setOnAction(e -> validateDoctorLogin(usernameField.getText(), passwordField.getText(), primaryStage));
        signupButton.setOnAction(e -> showDoctorSignupPage(primaryStage));
        backButton.setOnAction(e -> start(primaryStage));

        layout.getChildren().addAll(new Label("Doctor Login"), usernameField, passwordField, loginButton, signupButton, backButton);
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
    }

    // Patient Signup Page
    private void showPatientSignupPage(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(200);

        Button signupButton = new Button("Signup");
        Button backButton = new Button("Back");

        signupButton.setOnAction(e -> {
            if (patientCount < 100) {
                patientUsernames[patientCount] = usernameField.getText();
                patientPasswords[patientCount] = passwordField.getText();
                patientCount++;
                showPatientLogin(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Patient limit reached");
                alert.show();
            }
        });

        backButton.setOnAction(e -> showPatientLogin(primaryStage));

        layout.getChildren().addAll(new Label("Patient Signup"), usernameField, passwordField, signupButton, backButton);
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
    }

    // Doctor Signup Page
    private void showDoctorSignupPage(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(200);

        TextField specializationField = new TextField();
        specializationField.setPromptText("Specialization");
        specializationField.setPrefWidth(200);

        Button signupButton = new Button("Signup");
        Button backButton = new Button("Back");

        signupButton.setOnAction(e -> {
            if (doctorCount < 100) {
                doctorUsernames[doctorCount] = usernameField.getText();
                doctorPasswords[doctorCount] = passwordField.getText();
                doctorSpecializations[doctorCount] = specializationField.getText();
                doctorTimeSlots.put(usernameField.getText(), new ArrayList<>());  // Initialize time slot list
                doctorCount++;
                showDoctorLogin(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Doctor limit reached");
                alert.show();
            }
        });

        backButton.setOnAction(e -> showDoctorLogin(primaryStage));

        layout.getChildren().addAll(new Label("Doctor Signup"), usernameField, passwordField, specializationField, signupButton, backButton);
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
    }

    // Validate Patient Login
    private void validatePatientLogin(String username, String password, Stage primaryStage) {
        for (int i = 0; i < patientCount; i++) {
            if (patientUsernames[i].equals(username) && patientPasswords[i].equals(password)) {
                currentPatientUsername = username;
                showPatientDashboard(primaryStage);
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials");
        alert.show();
    }

    // Validate Doctor Login
    private void validateDoctorLogin(String username, String password, Stage primaryStage) {
        for (int i = 0; i < doctorCount; i++) {
            if (doctorUsernames[i].equals(username) && doctorPasswords[i].equals(password)) {
                currentDoctorUsername = username;
                showDoctorDashboard(primaryStage);
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials");
        alert.show();
    }

    // Patient Dashboard
    private void showPatientDashboard(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        Button bookAppointmentButton = new Button("Book Appointment");
        Button viewReportsButton = new Button("View Reports");
        Button detailsButton = new Button("Details");  // New Details button
        Button logoutButton = new Button("Logout");

        bookAppointmentButton.setOnAction(e -> showBookAppointment(primaryStage));
        viewReportsButton.setOnAction(e -> showReports(primaryStage));
        detailsButton.setOnAction(e -> showPatientDetails(primaryStage));  // Navigate to details page
        logoutButton.setOnAction(e -> start(primaryStage));

        layout.getChildren().addAll(new Label("Patient Dashboard - " + currentPatientUsername), bookAppointmentButton, viewReportsButton, detailsButton, logoutButton);
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
    }

    // Doctor Dashboard
    private void showDoctorDashboard(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        Button viewAppointmentsButton = new Button("View Appointments");
        Button logoutButton = new Button("Logout");

        viewAppointmentsButton.setOnAction(e -> showDoctorAppointments(primaryStage));
        logoutButton.setOnAction(e -> start(primaryStage));

        layout.getChildren().addAll(new Label("Doctor Dashboard - " + currentDoctorUsername), viewAppointmentsButton, logoutButton);
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
    }

    // Book Appointment with time slot management
    private void showBookAppointment(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        ComboBox<String> doctorDropdown = new ComboBox<>();
        for (int i = 0; i < doctorCount; i++) {
            doctorDropdown.getItems().add(doctorUsernames[i] + " - " + doctorSpecializations[i]);
        }
        doctorDropdown.setPromptText("Select Doctor");
        doctorDropdown.setPrefWidth(200);

        DatePicker datePicker = new DatePicker();

        ComboBox<String> timeDropdown = new ComboBox<>();
        timeDropdown.setPrefWidth(200);
        timeDropdown.setPromptText("Select Time");

        // Populate available time slots upon doctor selection
        doctorDropdown.setOnAction(e -> {
            String selectedDoctor = doctorDropdown.getValue().split(" - ")[0];
            timeDropdown.getItems().clear();
            List<String> bookedSlots = doctorTimeSlots.get(selectedDoctor);
            String[] allSlots = {"10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "3:00 PM", "4:00 PM", "5:00 PM"};
            for (String slot : allSlots) {
                if (bookedSlots == null || !bookedSlots.contains(slot)) {
                    timeDropdown.getItems().add(slot);
                }
            }
        });

        TextField symptomsField = new TextField();
        symptomsField.setPromptText("Enter symptoms");

        Button submitButton = new Button("Submit");
        Button backButton = new Button("Back");

        // Confirmation step upon clicking Submit
        submitButton.setOnAction(e -> {
            if (doctorDropdown.getValue() != null && datePicker.getValue() != null && timeDropdown.getValue() != null && !symptomsField.getText().isEmpty()) {
                // Capture appointment details
                String selectedDoctor = doctorDropdown.getValue().split(" - ")[0];
                String selectedDate = datePicker.getValue().toString();
                String selectedTime = timeDropdown.getValue();
                String symptoms = symptomsField.getText();

                // Now the patient details are globally available
                String summary = "Patient Details:\n"
                        + "Full Name: " + fullName + "\n"
                        + "Age: " + age + "\n"
                        + "Date of Birth: " + dob + "\n"
                        + "Blood Group: " + bloodGroup + "\n"
                        + "Gender: " + gender + "\n"
                        + "State: " + state + "\n"
                        + "City: " + city + "\n"
                        + "Address: " + address + "\n"
                        + "ZIPCODE: " + zipCode + "\n"
                        + "Mobile: " + mobile + "\n"
                        + "Email: " + email + "\n\n"
                        + "Appointment Details:\n"
                        + "Doctor: " + selectedDoctor + "\n"
                        + "Date: " + selectedDate + "\n"
                        + "Time: " + selectedTime + "\n"
                        + "Symptoms: " + symptoms;

                // Show the confirmation with both patient and appointment details
                showAppointmentConfirmation(primaryStage, selectedDoctor, selectedDate, selectedTime, symptoms, fullName, age, dob, bloodGroup, gender, state, city, address, zipCode, mobile, email);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all fields");
                alert.show();
            }
        });

        backButton.setOnAction(e -> showPatientDashboard(primaryStage));

        layout.getChildren().addAll(new Label("Book Appointment"), doctorDropdown, datePicker, timeDropdown, symptomsField, submitButton, backButton);
        Scene scene = new Scene(layout, 500, 600);
        primaryStage.setScene(scene);

    }

// Patient Details Page
// Patient Details Page
    private void showPatientDetails(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        // Full Name
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        // Age
        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        // Date of Birth
        DatePicker dobPicker = new DatePicker();
        dobPicker.setPromptText("Date of Birth");

        // Blood Group Dropdown
        ComboBox<String> bloodGroupDropdown = new ComboBox<>();
        bloodGroupDropdown.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodGroupDropdown.setPromptText("Select Blood Group");

        // Gender Dropdown
        ComboBox<String> genderDropdown = new ComboBox<>();
        genderDropdown.getItems().addAll("Male", "Female", "Other");
        genderDropdown.setPromptText("Select Gender");

        // State Dropdown
        ComboBox<String> stateDropdown = new ComboBox<>();
        String[] states = {
            "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
            "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
            "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
            "Uttar Pradesh", "Uttarakhand", "West Bengal", "Delhi", "Jammu and Kashmir"
        };
        stateDropdown.getItems().addAll(states);
        stateDropdown.setPromptText("Select State");

        // City
        TextField cityField = new TextField();
        cityField.setPromptText("City");

        // Address Line 1
        TextField addressField = new TextField();
        addressField.setPromptText("Address Line 1");

        // ZIPCODE
        TextField zipCodeField = new TextField();
        zipCodeField.setPromptText("ZIPCODE");

        // Mobile Number
        TextField mobileField = new TextField();
        mobileField.setPromptText("Mobile Number");

        // Email Address
        TextField emailField = new TextField();
        emailField.setPromptText("Email Address");

        Button saveButton = new Button("Save Details");
        Button backButton = new Button("Back");

        saveButton.setOnAction(e -> {
            // Save patient details into global variables
            fullName = fullNameField.getText();
            age = ageField.getText();
            dob = dobPicker.getValue() != null ? dobPicker.getValue().toString() : "";
            bloodGroup = bloodGroupDropdown.getValue();
            gender = genderDropdown.getValue();
            state = stateDropdown.getValue();
            city = cityField.getText();
            address = addressField.getText();
            zipCode = zipCodeField.getText();
            mobile = mobileField.getText();
            email = emailField.getText();

            // Create a summary of the entered details
            String summary = "Full Name: " + fullName
                    + "\nAge: " + age
                    + "\nDate of Birth: " + dob
                    + "\nBlood Group: " + bloodGroup
                    + "\nGender: " + gender
                    + "\nState: " + state
                    + "\nCity: " + city
                    + "\nAddress: " + address
                    + "\nZIPCODE: " + zipCode
                    + "\nMobile Number: " + mobile
                    + "\nEmail Address: " + email;

            // Show confirmation dialog with the summary
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirm Patient Details");
            confirmationDialog.setHeaderText("Please confirm your details:");
            confirmationDialog.setContentText(summary);

            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            confirmationDialog.getButtonTypes().setAll(confirmButton, cancelButton);

            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == confirmButton) {
                    // Details are saved
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Details saved successfully!");
                    alert.show();
                }
                // If canceled, do nothing (user returns to the details page)
            });
        });

        backButton.setOnAction(e -> showPatientDashboard(primaryStage));

        // Add all fields to the layout
        layout.getChildren().addAll(
                new Label("Patient Details"),
                fullNameField,
                ageField,
                dobPicker,
                bloodGroupDropdown,
                genderDropdown,
                stateDropdown,
                cityField,
                addressField,
                zipCodeField,
                mobileField,
                emailField,
                saveButton,
                backButton
        );

        Scene scene = new Scene(layout, 500, 600);
        primaryStage.setScene(scene);
    }

    private void showAppointmentConfirmation(Stage primaryStage, String doctor, String date, String time, String symptoms,
            String fullName, String age, String dob, String bloodGroup, String gender,
            String state, String city, String address, String zipCode, String mobile, String email) {
        // Prepare a summary of patient and appointment details
        String confirmationMessage = "Appointment Details:\n"
                + "Doctor: " + doctor + "\n"
                + "Date: " + date + "\n"
                + "Time: " + time + "\n"
                + "Symptoms: " + symptoms + "\n\n"
                + "Patient Details:\n"
                + "Full Name: " + fullName + "\n"
                + "Age: " + age + "\n"
                + "Date of Birth: " + dob + "\n"
                + "Blood Group: " + bloodGroup + "\n"
                + "Gender: " + gender + "\n"
                + "State: " + state + "\n"
                + "City: " + city + "\n"
                + "Address: " + address + "\n"
                + "ZIPCODE: " + zipCode + "\n"
                + "Mobile: " + mobile + "\n"
                + "Email: " + email;

        // Show confirmation dialog
        Alert confirmationDialog = new Alert(Alert.AlertType.INFORMATION, confirmationMessage);
        confirmationDialog.setTitle("Appointment Confirmation");
        confirmationDialog.setHeaderText("Your Appointment has been booked successfully!");
        confirmationDialog.showAndWait();
    }

    // Show Doctor Appointments
    private void showDoctorAppointments(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        // Using VBox to store appointment items
        VBox appointmentsContainer = new VBox(10);
        Button backButton = new Button("Back");

        // Filter appointments for the current doctor
        for (String[] appointment : appointments) {
            if (appointment[1].equals(currentDoctorUsername)) {
                // Create HBox for each appointment to hold both text and button
                HBox appointmentRow = new HBox(15);
                appointmentRow.setAlignment(Pos.CENTER_LEFT);

                // Create label for appointment details
                Label appointmentLabel = new Label(
                        "Patient: " + appointment[0]
                        + ", Date: " + appointment[2]
                        + ", Time: " + appointment[3]
                        + ", Symptoms: " + appointment[4]
                );

                // Create Generate Report button for this appointment
                Button generateReportButton = new Button("Generate Report");
                generateReportButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

                // Add action for Generate Report button
                generateReportButton.setOnAction(e -> {
                    showGenerateReportDialog(appointment[0], appointment[2], appointment[3], appointment[4]);
                });

                // Add label and button to the row
                appointmentRow.getChildren().addAll(appointmentLabel, generateReportButton);

                // Add the row to the container
                appointmentsContainer.getChildren().add(appointmentRow);
            }
        }

        // Create a ScrollPane to handle overflow
        ScrollPane scrollPane = new ScrollPane(appointmentsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(300); // Set preferred height

        backButton.setOnAction(e -> showDoctorDashboard(primaryStage));

        layout.getChildren().addAll(
                new Label("Appointments for " + currentDoctorUsername),
                scrollPane,
                backButton
        );

        Scene scene = new Scene(layout, 600, 500);
        primaryStage.setScene(scene);
    }

// Method to show the Generate Report dialog
    private void showGenerateReportDialog(String patientName, String date, String time, String symptoms) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Generate Patient Report");
        dialog.setHeaderText("Generate Report for " + patientName);

        // Create the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Declare the text areas for diagnosis, prescription, and notes
        TextArea diagnosisArea = new TextArea();
        diagnosisArea.setPromptText("Enter diagnosis");
        diagnosisArea.setPrefRowCount(3);

        TextArea prescriptionArea = new TextArea();
        prescriptionArea.setPromptText("Enter prescription");
        prescriptionArea.setPrefRowCount(3);

        TextArea notesArea = new TextArea();
        notesArea.setPromptText("Additional notes");
        notesArea.setPrefRowCount(3);

        // Add the text areas to the grid
        grid.add(new Label("Diagnosis:"), 0, 0);
        grid.add(diagnosisArea, 1, 0);
        grid.add(new Label("Prescription:"), 0, 1);
        grid.add(prescriptionArea, 1, 1);
        grid.add(new Label("Notes:"), 0, 2);
        grid.add(notesArea, 1, 2);

        // Add buttons to the dialog
        ButtonType generateButtonType = new ButtonType("Generate", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(generateButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable generate button depending on whether text was entered
        Node generateButton = dialog.getDialogPane().lookupButton(generateButtonType);
        generateButton.setDisable(true);

        // Enable button when text is entered
        diagnosisArea.textProperty().addListener((observable, oldValue, newValue) -> {
            generateButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == generateButtonType) {
                // Generate the report
                String report = "MEDICAL REPORT\n\n"
                        + "Patient: " + patientName + "\n"
                        + "Date: " + date + "\n"
                        + "Time: " + time + "\n"
                        + "Symptoms: " + symptoms + "\n\n"
                        + "Diagnosis:\n" + diagnosisArea.getText() + "\n\n"
                        + "Prescription:\n" + prescriptionArea.getText() + "\n\n"
                        + "Additional Notes:\n" + notesArea.getText();

                // Save the report
                saveReport(patientName, report);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Report Generated");
                alert.setHeaderText(null);
                alert.setContentText("Report has been generated and saved successfully!");
                alert.showAndWait();
                return report;
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void saveReport(String patientName, String report) {
        if (!patientReports.containsKey(patientName)) {
            patientReports.put(patientName, new ArrayList<>());
        }
        patientReports.get(patientName).add(report);
    }

    // Placeholder method for showing reports
    private void showReports(Stage primaryStage) {
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new javafx.geometry.Insets(40, 40, 40, 40));

        ListView<String> reportList = new ListView<>();
        Button backButton = new Button("Back");

        if (patientReports.containsKey(currentPatientUsername)) {
            ArrayList<String> reports = patientReports.get(currentPatientUsername);
            for (int i = 0; i < reports.size(); i++) {
                int reportNumber = i + 1;
                reportList.getItems().add("Report " + reportNumber);
            }

            reportList.setOnMouseClicked(event -> {
                String selectedItem = reportList.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    int reportIndex = Integer.parseInt(selectedItem.split(" ")[1]) - 1;
                    String reportContent = reports.get(reportIndex);
                    showReportDetails(reportContent);
                }
            });
        } else {
            reportList.getItems().add("No reports available.");
        }

        backButton.setOnAction(e -> showPatientDashboard(primaryStage));

        layout.getChildren().addAll(new Label("Your Medical Reports"), reportList, backButton);
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setScene(scene);
    }

    private void showReportDetails(String reportContent) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Report Details");
        dialog.setHeaderText(null);

        TextArea textArea = new TextArea(reportContent);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(400, 300);

        dialog.getDialogPane().setContent(textArea);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        dialog.showAndWait();
    }
}
