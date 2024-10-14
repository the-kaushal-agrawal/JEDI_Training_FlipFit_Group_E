package com.flipkart.client;

import com.flipkart.bean.Gym;
import com.flipkart.bean.GymOwner;
import com.flipkart.bean.Slots;
import com.flipkart.business.GymOwnerServiceOperations;
import com.flipkart.validator.ValidateCredential;
import com.flipkart.validator.ValidateIdentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.flipkart.utils.DateAndTime.displayCurrentDate;

public class FlipFitGymOwnerClientMenu {
    private Scanner scanner = new Scanner(System.in);
    private GymOwnerServiceOperations gymOwnerServiceOperations = new GymOwnerServiceOperations();
    private ValidateCredential validateCredential = new ValidateCredential();
    private ValidateIdentity validateIdentity = new ValidateIdentity();

    /// Verifies if the provided email and password match a registered gym owner.
    /// @param email Gym owner's email address.
    /// @param password Gym owner's password.
    /// @return true if the credentials are valid, false otherwise.
    boolean verifyGymOwner(String email, String password) {
        return gymOwnerServiceOperations.validateGymOwner(email, password);
    }

    /// Handles the gym owner login process and displays the menu options.
    /// @param email Gym owner's email address.
    /// @param password Gym owner's password.
    /// @return true if logout is successful, false otherwise.
    public boolean gymOwnerLogin(String email, String password) {
        if (!verifyGymOwner(email, password)) {
            return false;
        }
        System.out.println( "Login Successful! (Gym Owner)" );
        displayCurrentDate();
        while (true) {
            System.out.println(  "-----------------Gym Owner Menu-----------------" );
            System.out.println( "Press 1 to add a gym");
            System.out.println("Press 2 to update gym details");
            System.out.println("Press 3 to view all gyms");
            System.out.println("Press 4 to add slots");
            System.out.println("Press 5 to update seat count by");
            System.out.println("Press 6 to update your details");
            System.out.println("Press 7 to logout" );

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addGym(email);
                    break;
                case 2:
                    updateGym(email);
                    break;
                case 3:
                    displayGyms(email);
                    break;
                case 4:
                    addSlots();
                    break;
                case 5:
                    updateSeatCount(email);
                    break;
                case 6:
                    if (updateGymOwnerDetails())
                        System.out.println( "Gym owner updated successfully!" );
                    else
                        System.out.println( "Gym owner not updated" );
                    break;
                case 7:
                    return true;
                default:
                    System.out.println( "Invalid option!" );
            }
        }
    }

    /// Adds a new gym to the system.
    /// @param email Gym owner's email address (used to fetch the owner ID).
    private void addGym(String email) {
        Gym gym = new Gym();
        int gymOwnerId = gymOwnerServiceOperations.getGymOwnerIdByEmail(email);
        if (gymOwnerId == -1) {
            System.out.println("No such gym owner exists with email: " + email);
            return;
        }
        gym.setOwnerId(gymOwnerId);

        System.out.println(  "Enter details of the gym: " );
        System.out.println( "Name: " );
        String gymName = scanner.nextLine();
        System.out.println( "Address: " );
        String address = scanner.nextLine();
        if (!validateIdentity.validateAddress(address)) {
            System.out.println( "Invalid address!" );
            return;
        }
        System.out.println( "Location: " );
        String location = scanner.nextLine();
        if (!validateIdentity.validateLocation(location)) {
            System.out.println( "Invalid location!" );
            return;
        }
        String gymStatus = "unverified";

        gym.setGymAddress(address);
        gym.setLocation(location);
        gym.setGymName(gymName);
        gym.setStatus(gymStatus);

        List<Slots> slots = new ArrayList<>();
        System.out.println( "Please enter number of slots: " );
        int slotCount = Integer.parseInt(scanner.nextLine());
        if (!validateIdentity.validateSlots(slotCount)) {
            System.out.println( "Invalid number of slots!" );
            return;
        }
        int currentCount = 1;
        while (currentCount <= slotCount) {
            System.out.println("Add details for slot number " + currentCount + ": ");
            System.out.println("Enter start time: ");
            int startTime = Integer.parseInt(scanner.nextLine());
            if (!validateIdentity.validateTime(startTime)) {
                System.out.println( "Invalid start time!" );
                continue;
            }
            System.out.println("Enter available seats: ");
            int seatCount = Integer.parseInt(scanner.nextLine());
            if (!validateIdentity.validateSlots(seatCount)) {
                System.out.println( "Invalid seat count!" );
                continue;
            }
            Slots slot = new Slots(-1, startTime, seatCount);
            slots.add(slot);
            currentCount++;
        }

        gym.setSlots(slots);
        if (gymOwnerServiceOperations.addGym(gym))
            System.out.println( "Gym added successfully!" );
        else
            System.out.println( "Gym could not be added!" );
    }

    private void addSlots() {
        System.out.println( "Gym ID: " );
        int gymId = Integer.parseInt(scanner.nextLine());
        if (!validateIdentity.validateId(gymId)) {
            System.out.println( "Gym ID invalid!" );
            return;
        }
        List<Slots> slots = new ArrayList<>();
        System.out.println( "Please enter number of slots: " );
        int slotCount = Integer.parseInt(scanner.nextLine());
        if (!validateIdentity.validateSlots(slotCount)) {
            System.out.println( "Invalid number of slots!" );
            return;
        }
        int currentCount = 1;
        while (currentCount <= slotCount) {
            System.out.println("Add details for slot number " + currentCount + ": ");
            System.out.println("Enter start time: ");
            int startTime = Integer.parseInt(scanner.nextLine());
            if (!validateIdentity.validateTime(startTime)) {
                System.out.println( "Invalid start time!" );
                continue;
            }
            System.out.println("Enter available seats: ");
            int seatCount = Integer.parseInt(scanner.nextLine());
            if (!validateIdentity.validateSlots(seatCount)) {
                System.out.println( "Invalid seat count!" );
                continue;
            }
            Slots slot = new Slots(-1, startTime, seatCount);
            slots.add(slot);
            currentCount++;
        }
        if(gymOwnerServiceOperations.addSlots(gymId, slots))
            System.out.println( "Gym added successfully!" );
        else
            System.out.println( "Gym could not be added!" );
    }

    /**
     * Handles the creation of a new gym owner account.
     */
    public void createGymOwner() {
        System.out.println(  "Enter gym owner details:" );
        System.out.println( "Email: " );
        String ownerEmail = scanner.nextLine();
        if (!validateCredential.validateEmail(ownerEmail)) {
            System.out.println( "Invalid Email address! Try Again!" );
            return;
        }
        System.out.println( "Name: " );
        String ownerName = scanner.nextLine();
        System.out.println( "Password: " );
        String password = scanner.nextLine();
        if (!validateCredential.validatePassword(password)) {
            System.out.println( "Password length should be between 10 to 20 characters and include numbers, lowercase, uppercase, and special characters." );
            return;
        }
        System.out.println( "Phone Number: " );
        String phoneNo = scanner.nextLine();
        if (!validateIdentity.validatePhoneNumber(phoneNo)) {
            System.out.println( "Phone Number invalid! Try again!" );
            return;
        }
        System.out.println( "National ID: " );
        String nationalId = scanner.nextLine();
        if (!ValidateIdentity.validateAadhaar(nationalId)) {
            System.out.println( "Aadhaar Card invalid! Try again!" );
            return;
        }

        if (nationalId.length() != 12) {
            System.out.println( "Invalid national ID! Length must be 12" );
            return;
        }

        System.out.println( "GST: " );
        String GST = scanner.nextLine();
        if (!ValidateIdentity.GSTValidator.validateGST(GST)) {
            System.out.println( "Invalid GST number!" );
            return;
        }
        System.out.println( "PAN Number: " );
        String PAN = scanner.nextLine();
        if (!ValidateIdentity.PANCardValidator.validatePAN(PAN)) {
            System.out.println( "PAN invalid! Try again!" );
            return;
        }
        if (PAN.length() != 10) {
            System.out.println( "Invalid PAN Card Number. Length must be 10" );
            return;
        }

        GymOwner gymOwner = new GymOwner();
        List<Gym> emptyGymList = new ArrayList<>();
        gymOwner.setOwnerEmail(ownerEmail);
        gymOwner.setPAN(PAN);
        gymOwner.setOwnerName(ownerName);
        gymOwner.setGST(GST);
        gymOwner.setPassword(password);
        gymOwner.setNationalId(nationalId);
        gymOwner.setPhoneNo(phoneNo);
        gymOwner.setGyms(emptyGymList);
        gymOwner.setVerificationStatus("unverified");

        if (gymOwnerServiceOperations.createGymOwner(gymOwner)) {
            System.out.println( "Gym owner created!" );
        } else {
            System.out.println( "Gym owner not created!" );
        }
    }

    /// Updates the details of an existing gym owner.
    /// @return true if the update is successful, false otherwise.
    private boolean updateGymOwnerDetails() {
        System.out.println(  "Enter gym owner details:" );
        System.out.println( "Email: " );
        String ownerEmail = scanner.nextLine();
        if (!validateCredential.validateEmail(ownerEmail)) {
            System.out.println( "Invalid Email address! Try Again!" );
            return false;
        }
        System.out.println( "Name: " );
        String ownerName = scanner.nextLine();
        System.out.println( "Phone Number: " );
        String phoneNo = scanner.nextLine();
        if (!validateIdentity.validatePhoneNumber(phoneNo)) {
            System.out.println( "Phone Number invalid! Try again!" );
            return false;
        }

        GymOwner gymOwner = new GymOwner();
        gymOwner.setOwnerEmail(ownerEmail);
        gymOwner.setOwnerName(ownerName);
        gymOwner.setPhoneNo(phoneNo);

        return gymOwnerServiceOperations.updateGymOwner(gymOwner);
    }

    /// Updates the gym owner's password.
    /// @param userMail Gym owner's email address.
    /// @param password Current password.
    /// @param updatedPassword New password.
    /// @return true if the password update is successful, false otherwise.
    public boolean updatePassword(String userMail, String password, String updatedPassword) {
        return gymOwnerServiceOperations.updateGymOwnerPassword(userMail, password, updatedPassword);
    }

    /// Displays all gyms owned by the logged-in gym owner.
    /// @param email Gym owner's email address.
    private void displayGyms(String email) {
        int gymOwnerId = gymOwnerServiceOperations.getGymOwnerIdByEmail(email);
        if (gymOwnerId == -1) {
            System.out.println("No such gym owner exists with email: " + email);
            return;
        }

        List<Gym> gymsList = gymOwnerServiceOperations.viewMyGyms(gymOwnerId);
        if (gymsList.isEmpty()) {
            System.out.println("No gyms found for the gym owner with email: " + email);
            return;
        }

        String gymLeftAlignFormat = "| %-5d | %-20s | %-40s | %-20s |%n";
        String slotLeftAlignFormat = "| %-5d | %-15s | %-5d |%n";

        for (Gym gym : gymsList) {
            System.out.format("+-------+----------------------+------------------------------------------+----------------------+\n");
            System.out.format("| Gym ID|     Name             |           Address                        |     Location         |\n");
            System.out.format("+-------+----------------------+------------------------------------------+----------------------+\n");
            System.out.format(gymLeftAlignFormat, gym.getGymId(), gym.getGymName(), gym.getGymAddress(), gym.getLocation());
            System.out.format("+-------+----------------------+------------------------------------------+----------------------+\n");
            System.out.println("Slots: ");
            System.out.format("+-------+---------------+-------+\n");
            System.out.format("|Slot ID|     Time      | Seats |\n");
            System.out.format("+-------+---------------+-------+\n");

            for (Slots slot : gym.getSlots()) {
                System.out.format(slotLeftAlignFormat, slot.getSlotsId(), slot.getStartTime() + " - " + (slot.getStartTime() + 1), slot.getSeatCount());
            }
            System.out.format("+-------+---------------+-------+\n");
            System.out.println();
        }
    }

    /**
     * Updates the seat count for a specific gym and slot.
     */
    private void updateSeatCount(String email) {
        displayGyms(email);
        System.out.println( "Enter gym ID: " );
        int gymId = Integer.parseInt(scanner.nextLine());

        System.out.println( "Enter start time: " );
        int startTime = Integer.parseInt(scanner.nextLine());

        System.out.println( "Update seat count by: " );
        int seatCount = Integer.parseInt(scanner.nextLine());

        if (gymOwnerServiceOperations.updateSeatCount(gymId, startTime, seatCount))
            System.out.println( "Seat count updated!" );
        else
            System.out.println( "Seat count not updated" );
    }

    /**
     * Updates the details of an existing gym.
     * @param email email of the gymOwner
     * @return true if gym details are updated successfully, false otherwise.
     * */
    public boolean updateGym(String email) {
        displayGyms(email);

        System.out.println( "Enter gym ID: " );
        int gymId = Integer.parseInt(scanner.nextLine());

        System.out.println( "Enter gym details: " );
        System.out.println( "Name: " );
        String gymName = scanner.nextLine();
        System.out.println( "Gym Address: " );
        String gymAddress = scanner.nextLine();
        if (!validateIdentity.validateAddress(gymAddress)) {
            System.out.println( "Address invalid! Try again!" );
            return false;
        }
        System.out.println( "Gym Location: " );
        String gymALocation = scanner.nextLine();
        if (!validateIdentity.validateLocation(gymALocation)) {
            System.out.println( "Location invalid! Try again!" );
            return false;
        }

        Gym gym = new Gym();
        gym.setGymName(gymName);
        gym.setGymAddress(gymAddress);
        gym.setLocation(gymALocation);
        gym.setGymId(gymId);

        return gymOwnerServiceOperations.updateGymDetails(gym);
    }
}