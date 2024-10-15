package com.flipfit.client;

import com.flipfit.validator.ValidateCredential;

import java.util.Scanner;

import static com.flipfit.utils.DateAndTime.displayCurrentDate;

public class FlipFitApplicationMainClient {

    static Scanner scanner = new Scanner(System.in);

    /**
     * Menu for gym owners.
     */
    static FlipFitGymOwnerClientMenu gymOwnerMenu = new FlipFitGymOwnerClientMenu();

    /**
     * Menu for gym customers.
     */
    static FlipFitGymCustomerClientMenu customerMenu = new FlipFitGymCustomerClientMenu();

    /**
     * Menu for gym administrators.
     */
    static FlipFitGymAdminMenu adminMenu = new FlipFitGymAdminMenu();

    /**
     * Validator for user credentials.
     */
    static ValidateCredential validateCredential = new ValidateCredential();

    /// Entry point of the application. Handles the main menu and user interactions.
    ///
    /// @param args Command line arguments (not used)

    public static void main(String[] args) {
        System.out.println("!!!Welcome to FlipFit!!!");
        boolean inInApp = true;

        while (inInApp) {
            System.out.println("Press 1 for Login");
            System.out.println("Press 2 for Registration");
            System.out.println("Press 3 for Update Password");
            System.out.println("Press 4 for Exit");
            int optionSelected = Integer.parseInt(scanner.nextLine());

            switch (optionSelected) {
                case 1:
                    System.out.println("!!Login Page!!");
                    System.out.println("Select your role:");
                    System.out.println("Press 1 for admin");
                    System.out.println("Press 2 for gym owner");
                    System.out.println("Press 3 for gym customer");
                    int role = Integer.parseInt(scanner.nextLine());

                    if (role > 3 || role < 1) {
                        System.out.println("You have selected an invalid option.");
                        break;
                    }

                    System.out.println("Please enter your email:");
                    String userMail = scanner.nextLine();
                    if (!validateCredential.validateEmail(userMail)) {
                        System.out.println("Invalid Email address! Try Again!");
                        break;
                    }
                    System.out.println("Please enter your password:");
                    String password = scanner.nextLine();

                    switch (role) {
                        case 1:
                            if (!adminMenu.verifyAdminCredentials(userMail, password)) {
                                System.out.println("Invalid credentials! Please enter valid credentials to login");
                                break;
                            }
                            System.out.println("Login successful! (FlipFitAdmin)");
                            displayCurrentDate();
                            boolean isLoggedIn = true;

                            while (isLoggedIn) {
                                System.out.println("-----------------FlipFitAdmin Menu------------------");
                                System.out.println("Press 1 to view users");
                                System.out.println("Press 2 to view gyms");
                                System.out.println("Press 3 to view gym owners");
                                System.out.println("Press 4 to verify gym");
                                System.out.println("Press 5 to verify gym owners");
                                System.out.println("Press 6 to view pending gyms");
                                System.out.println("Press 7 to view pending gym owners");
                                System.out.println("Press 8 to exit");

                                optionSelected = Integer.parseInt(scanner.nextLine());

                                switch (optionSelected) {
                                    case 1:
                                        adminMenu.viewUsers();
                                        break;
                                    case 2:
                                        adminMenu.viewGyms();
                                        break;
                                    case 3:
                                        adminMenu.viewGymOwners();
                                        break;
                                    case 4:
                                        adminMenu.viewUnverifiedGyms();
                                        System.out.println("Enter the gym ID to be verified:");
                                        int gymId = Integer.parseInt(scanner.nextLine());
                                        if (adminMenu.verifyGym(gymId))
                                            System.out.println("FlipFitGym verified successfully!");
                                        else
                                            System.out.println("FlipFitGym with given Id does not exists.");
                                        break;
                                    case 5:
                                        adminMenu.viewUnverifiedGymOwners();
                                        System.out.println("Enter the gym owner ID to be verified:");
                                        int gymOwnerId = Integer.parseInt(scanner.nextLine());
                                        if (adminMenu.verifyGymOwner(gymOwnerId))
                                            System.out.println("FlipFitGym owner verified successfully!");
                                        else
                                            System.out.println("FlipFitGym owner with given ID does not exists.");
                                        break;
                                    case 6:
                                        adminMenu.viewUnverifiedGyms();
                                        break;
                                    case 7:
                                        adminMenu.viewUnverifiedGymOwners();
                                        break;
                                    case 8:
                                        isLoggedIn = false;
                                        break;
                                }
                                if (!isLoggedIn) break;
                            }
                            break;
                        case 2:
                            if (!gymOwnerMenu.gymOwnerLogin(userMail, password)) {
                                System.out.println("Invalid credentials! Please enter valid credentials to login");
                                break;
                            }
                            break;
                        case 3:
                            if (!customerMenu.userLogin(userMail, password)) {
                                System.out.println("Invalid credentials! Please enter valid credentials to login");
                                break;
                            }
                            break;
                        default:
                            System.out.println("You have selected an invalid option. Please select a valid option");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("-------------Registration Page--------------");
                    System.out.println("Select your choice:");
                    System.out.println("Press 1 for gym user");
                    System.out.println("Press 2 for gym owner");
                    System.out.println("Press 3 to go back");
                    role = Integer.parseInt(scanner.nextLine());

                    switch (role) {
                        case 1:
                            customerMenu.createCustomer();
                            break;
                        case 2:
                            gymOwnerMenu.createGymOwner();
                            break;
                        default:
                            break;
                    }
                    break;
                case 3:
                    System.out.println("-------------Reset Password Page--------------");
                    System.out.println("Select your role:");
                    System.out.println("Press 1 for gym user");
                    System.out.println("Press 2 for gym owner");
                    System.out.println("Press 3 for gym admin");
                    role = Integer.parseInt(scanner.nextLine());
                    System.out.println("Please enter your email:");
                    userMail = scanner.nextLine();
                    if (!validateCredential.validateEmail(userMail)) {
                        System.out.println("Invalid Email address! Try Again!");
                        break;
                    }
                    System.out.println("Please enter your current password:");
                    password = scanner.nextLine();
                    System.out.println("Please enter new password:");
                    String updatedPassword = scanner.nextLine();
                    if (!validateCredential.validatePassword(updatedPassword)) {
                        System.out.println("Password length should be in between 10 to 20");
                        System.out.println("It must also contain a number, lowercase, uppercase and special character.");
                        break;
                    }

                    switch (role) {
                        case 1:
                            if (!customerMenu.validateUser(userMail, password)) {
                                System.out.println("Invalid credentials! Please enter valid credentials");
                            } else {
                                if (customerMenu.updatePassword(userMail, password, updatedPassword))
                                    System.out.println("Password updated successfully!");
                                else
                                    System.out.println("Password doesn't match!");
                            }
                            break;
                        case 2:
                            if (!gymOwnerMenu.verifyGymOwner(userMail, password)) {
                                System.out.println("Invalid credentials! Please enter valid credentials");
                            } else {
                                if (gymOwnerMenu.updatePassword(userMail, password, updatedPassword))
                                    System.out.println("Password updated successfully!");
                                else
                                    System.out.println("Password doesn't match!");
                            }
                            break;
                        case 3:
                            System.out.println("Sorry! You don't have enough rights to do that.");
                            break;
                    }
                    break;
                case 4:
                    inInApp = false;
                    System.out.println("Thank you for using FlipFit!");
                    break;
                default:
                    System.out.println("Enter a valid option");
                    break;
            }
        }
    }
}

