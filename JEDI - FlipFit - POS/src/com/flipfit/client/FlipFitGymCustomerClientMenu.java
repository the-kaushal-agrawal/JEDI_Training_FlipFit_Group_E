package com.flipfit.client;

import java.util.*;

import com.flipfit.bean.*;
import com.flipfit.business.FlipFitPaymentsServiceOperations;
import com.flipfit.business.FlipFitUserServiceOperations;
import com.flipfit.validator.*;

 
import static com.flipfit.utils.DateAndTime.displayCurrentDate;

import java.util.Scanner;

/// Handles the customer menu operations for the FlipFit application.
/// Provides functionality for user login, viewing and managing gym slots, bookings,
/// and updating user details.
///
public class FlipFitGymCustomerClientMenu {

    /**
     * Scanner instance for user input.
     */
    Scanner scanner = new Scanner(System.in);

    /**
     * Service operations for user-related tasks.
     */
    FlipFitUserServiceOperations userServiceOperations = new FlipFitUserServiceOperations();

    /**
     * Validator for user credentials.
     */
    ValidateCredential validateCredential = new ValidateCredential();

    /**
     * Service operations for payment-related tasks.
     */
    FlipFitPaymentsServiceOperations payerServiceOperations = new FlipFitPaymentsServiceOperations();

    /**
     * Validator for user identity.
     */
    ValidateIdentity validateIdentity = new ValidateIdentity();

    /**
     * Validator for card details.
     */
    ValidateCard validateCard = new ValidateCard();

    /**
     * Manages user login and displays the customer menu.
     *
     * @param email    FlipFitUser's email address.
     * @param password FlipFitUser's password.
     * @return true if login is successful, false otherwise.
     */
    public boolean userLogin(String email, String password) {
        if (validateUser(email, password)) {
            boolean isLoggedIn = true;
            System.out.println( "Customer Login Successful!!" );
            displayCurrentDate();
            while (isLoggedIn) {
                System.out.println( "-------------CUSTOMER MENU-------------");
                System.out.println("Press 1 to view all gyms with slots");
                System.out.println("Press 2 to book slot");
                System.out.println("Press 3 to cancel slot");
                System.out.println("Press 4 to view all bookings");
                System.out.println("Press 5 to view all gyms by area");
                System.out.println("Press 6 to view a slot's availability");
                System.out.println("Press 7 to update your details");
                System.out.println("Press 8 to logout" );
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        List<FlipFitGym> gyms1 = viewAllGymsWithSlots();
                        printGyms(gyms1);
                        break;
                    case 2:
                        List<FlipFitGym> gyms2 = viewAllGymsWithSlots();
                        printGyms(gyms2);
                        if (gyms2.isEmpty()) {
                            break;
                        }
                        System.out.println(  "Enter the following: " );
                        System.out.println( "FlipFitGym ID: " );
                        int gymId = Integer.parseInt(scanner.nextLine());
                        if (!validateIdentity.validateId(gymId)) {
                            System.out.println( "FlipFitGym ID invalid!" );
                            break;
                        }
                        System.out.println( "Slot Time: " );
                        int time = Integer.parseInt(scanner.nextLine());
                        if (processPayments()) {
                            System.out.println("Payment was successful");
                            if (bookSlot(gymId, time, email)) {
                                System.out.println( "Slot booked successfully!" );
                            }
                        } else {
                            System.out.println("Payment failed. Please try again");
                        }
                        break;
                    case 3:
                        if (!viewAllBookings(email)) {
                            break;
                        }
                        System.out.println( "Enter Booking ID: " );
                        int bookingId = Integer.parseInt(scanner.nextLine());
                        if (!validateIdentity.validateId(bookingId)) {
                            System.out.println( "Booking ID invalid!" );
                            break;
                        }
                        if (cancelSlot(bookingId))
                            System.out.println( "Booking cancelled successfully!" );
                        else
                            System.out.println( "Booking cancellation failed." );
                        break;
                    case 4:
                        viewAllBookings(email);
                        break;
                    case 5:
                        System.out.println( "Enter location you want find gyms in: " );
                        String location = scanner.nextLine();
                        List<FlipFitGym> gyms3 = viewAllGymsByArea(location);
                        printGyms(gyms3);
                        break;
                    case 6:
                        System.out.println( "Enter gym ID: " );
                        int _gymId = Integer.parseInt(scanner.nextLine());
                        if (!validateIdentity.validateId(_gymId)) {
                            System.out.println( "FlipFitGym ID invalid!" );
                            break;
                        }
                        System.out.println( "Enter start time: " );
                        int _startTime = Integer.parseInt(scanner.nextLine());
                        int availableSeatCount = userServiceOperations.getSeatCount(_gymId, _startTime);
                        if (availableSeatCount == -1) {
                            System.out.println("Seat count is not available. Please try again." );
                            break;
                        }
                        System.out.println( "Available seat count: " + availableSeatCount );
                        break;
                    case 7:
                        if (updateUserDetails())
                            System.out.println( "FlipFitUser updated successfully!" );
                        else
                            System.out.println( "FlipFitUser update was unsuccessful" );
                        break;
                    case 8:
                        isLoggedIn = false;
                        break;
                    default:
                        System.out.println(  "Wrong choice!" );
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Prints the list of gyms and their slots.
     *
     * @param gyms List of gyms to be printed.
     */
    private void printGyms(List<FlipFitGym> gyms) {
        if (gyms.isEmpty()) {
            System.out.println("No gyms found.");
            return;
        }

        String gymLeftAlignFormat = "| %-5d | %-20s | %-20s | %-40s | %-15s |%n";
        System.out.println("FlipFitGym List:");

        for (FlipFitGym gym : gyms) {
            System.out.format("+-------+----------------------+----------------------+------------------------------------------+------------------+\n");
            System.out.format("| FlipFitGym ID|     Name             |     Location         |           Address                         |     Status       |\n");
            System.out.format("+-------+----------------------+----------------------+------------------------------------------+------------------+\n");
            System.out.format(gymLeftAlignFormat, gym.getGymId(), gym.getGymName(), gym.getLocation(), gym.getGymAddress(), gym.getStatus());
            System.out.format("+-------+----------------------+----------------------+------------------------------------------+------------------+\n");

            System.out.println("Slot List:");
            String slotLeftAlignFormat = "| %-15s | %-15s | %-15d |%n";
            System.out.format("+-----------------+-----------------+-----------------+\n");
            System.out.format("| Start Time      |   End Time      | Remaining Seats |\n");
            System.out.format("+-----------------+-----------------+-----------------+\n");

            for (FlipFitSlots slot : gym.getSlots()) {
                if (slot.getSeatCount() > 0)
                    System.out.format(slotLeftAlignFormat, slot.getStartTime(), (slot.getStartTime() + 1), slot.getSeatCount());
            }
            System.out.format("+-----------------+-----------------+-----------------+\n");
            System.out.println();
        }
    }

    /**
     * Validates user credentials.
     *
     * @param email    FlipFitUser's email address.
     * @param password FlipFitUser's password.
     * @return true if credentials are valid, false otherwise.
     */
    public boolean validateUser(String email, String password) {
        return userServiceOperations.validateUser(email, password);
    }

    /**
     * Collects and validates card details for payment.
     *
     * @return true if card details are valid, false otherwise.
     */
    public boolean collectAndValidateCardDetails() {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        if (!validateCard.validateCardNumber(cardNumber)) {
            System.out.println( "Card number invalid!" );
            return false;
        }
        System.out.print("Enter expiry date (MM/YY): ");
        String expiryDate = scanner.nextLine();
        if (!validateCard.validateExpiryDate(expiryDate)) {
            System.out.println( "Expiry Date invalid!" );
            return false;
        }

        System.out.print("Enter cardholder's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter CVV: ");
        String cvv = scanner.nextLine();
        if (!validateCard.validateCVV(cvv)) {
            System.out.println( "CVV invalid!" );
            return false;
        }

        FlipFitPayments payments = new FlipFitPayments();
        payments.setCardNumber(cardNumber);
        payments.setExpiryDate(expiryDate);
        payments.setName(name);
        payments.setCvv(cvv);

        return payerServiceOperations.validateCardDetails(payments);
    }

    /**
     * Processes the payment by collecting and validating card details.
     *
     * @return true if payment is successful, false otherwise.
     */
    public boolean processPayments() {
        return collectAndValidateCardDetails();
    }

    /**
     * Retrieves the list of all gyms with available slots.
     *
     * @return List of gyms with slots.
     */
    List<FlipFitGym> viewAllGymsWithSlots() {
        return userServiceOperations.viewAllGymsWithSlots();
    }

    /**
     * Books a slot in a gym.
     *
     * @param gymId     ID of the gym.
     * @param startTime Start time of the slot.
     * @param email     FlipFitUser's email address.
     * @return true if slot is booked successfully, false otherwise.
     */
    public boolean bookSlot(int gymId, int startTime, String email) {
        return userServiceOperations.bookSlot(gymId, startTime, email);
    }

    /**
     * Cancels a booking.
     *
     * @param bookingId ID of the booking to be cancelled.
     * @return true if booking is cancelled successfully, false otherwise.
     */
    public boolean cancelSlot(int bookingId) {
        return userServiceOperations.cancelSlot(bookingId);
    }

    /// Displays all bookings made by the user.
    ///
    /// @param email FlipFitUser's email address.
    public boolean viewAllBookings(String email) {
        System.out.println(  "My FlipFitBookings: " );
        int userId = userServiceOperations.getUserIdByEmail(email);
        if (userId == -1) {
            System.out.println("No such user exists with email: " + email);
            return false;
        }
        List<FlipFitBookings> bookings = userServiceOperations.viewAllBookings(userId);
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return false;
        } else {
            String leftAlignFormat = "| %-10s | %-15s | %-10s | %-10s | %n";
            System.out.format("+------------+---------------+------------+------------+\n");
            System.out.format("| Booking ID |     Status    |    Time    |  FlipFitGym ID    |\n");
            System.out.format("+------------+---------------+------------+------------+\n");

            for (FlipFitBookings booking : bookings) {
                System.out.format(leftAlignFormat, booking.getBookingId(), booking.getBookingStatus(), booking.getTime(), booking.getGymId());
            }
            System.out.format("+------------+---------------+------------+------------+\n");
        }
        return true;
    }

    /// Retrieves the list of all gyms by a specified area.
    ///
    /// @param location Location to filter gyms.
    /// @return List of gyms in the specified area.
    List<FlipFitGym> viewAllGymsByArea(String location) {
        return userServiceOperations.viewAllGymsByArea(location);
    }

    /// Creates a new customer account.
    public void createCustomer() {
        System.out.println( "Enter customer details: " );
        System.out.println( "Name: " );
        String ownerName = scanner.nextLine();
        System.out.println( "Phone Number: " );
        String phoneNo = scanner.nextLine();
        if (!validateIdentity.validatePhoneNumber(phoneNo)) {
            System.out.println( "Phone Number invalid! Try again!" );
            return;
        }
//        Address
        System.out.println( "Address: " );
        String address = scanner.nextLine();
        if (!validateIdentity.validateAddress(address)) {
            System.out.println( "Address invalid! Try again!" );
            return;
        }

//        Location
        System.out.println( "Location: " );
        String location = scanner.nextLine();
        if (!validateIdentity.validateLocation(location)) {
            System.out.println( "Location invalid! Try again!" );
            return;
        }
//        Email
        System.out.println("Email: ");
        String ownerEmail = scanner.nextLine();
        if (!validateCredential.validateEmail(ownerEmail)) {
            System.out.println( "Invalid Email address! Try Again!" );
            return;
        }
//        Password
        System.out.println( "Password: " );
        String password = scanner.nextLine();
        if (!validateCredential.validatePassword(password)) {
            System.out.println( "Password length should be in between 10 to 20" );
            System.out.println( "It must also contain a number, lowercase, uppercase and special character." );
            return;
        }





        FlipFitUser user = new FlipFitUser();
        user.setUserName(ownerName);
        user.setPhoneNumber(phoneNo);
        user.setAddress(address);
        user.setLocation(location);
        user.setEmail(ownerEmail);

        user.setPassword(password);


        if (userServiceOperations.createUser(user))
            System.out.println("FlipFitUser created!");
        else
            System.out.println("FlipFitUser not created!");
    }

    /// Updates the details of an existing user.
    ///
    /// @return true if user details are updated successfully, false otherwise.
    public boolean updateUserDetails() {
        System.out.println( "Enter customer details: " );
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

        FlipFitUser user = new FlipFitUser();
        user.setEmail(ownerEmail);
        user.setUserName(ownerName);
        user.setPhoneNumber(phoneNo);

        return userServiceOperations.updateUserDetails(user);
    }

    /// Updates the password of a user.
    ///
    /// @param userMail        FlipFitUser's email address.
    /// @param password        Current password.
    /// @param updatedPassword New password.
    /// @return true if password is updated successfully, false otherwise.
    public boolean updatePassword(String userMail, String password, String updatedPassword) {
        return userServiceOperations.updateGymUserPassword(userMail, password, updatedPassword);
    }
}
