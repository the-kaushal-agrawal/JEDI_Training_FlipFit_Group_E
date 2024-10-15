package com.flipfit.client;

import com.flipfit.bean.FlipFitGym;
import com.flipfit.bean.FlipFitGymOwner;
import com.flipfit.bean.FlipFitUser;
import com.flipfit.business.FlipFitAdminServiceOperations;


import java.util.List;

public class FlipFitGymAdminMenu {

    /**
     * Service operations for admin tasks.
     */
    FlipFitAdminServiceOperations adminServiceOperations = new FlipFitAdminServiceOperations();
    private static final String ADMIN_EMAIL  = "kaushal.agrawal@flipfit.com";
    private static final String ADMIN_PASSWORD = "kaushal";


    /**
     * Credentials for admin login.
     */


    /// Displays a list of gyms.
    /// Calls the viewGyms method from FlipFitAdminServiceOperations.
    ///
    public void viewGyms() {
        List<FlipFitGym> gyms = adminServiceOperations.viewGyms();
        if (gyms.isEmpty()) {
            System.out.println("No gyms found.");
            return;
        }

        String leftAlignFormat = "| %-6s | %-20s | %-40s | %-20s | %-10s | %-10s |%n";
        System.out.format(  "+--------+----------------------+----------------------------------------+----------------------+----------+----------+%n");
        System.out.format("| FlipFitGym ID |     Name             | Address                                | Location             | Owner ID | Status   |%n");
        System.out.format("+--------+----------------------+----------------------------------------+----------------------+----------+----------+%n");

        for (FlipFitGym gym : gyms) {
            System.out.format(leftAlignFormat, gym.getGymId(), gym.getGymName(), gym.getGymAddress(), gym.getLocation(), gym.getOwnerId(), gym.getStatus());
        }
        System.out.format("+--------+----------------------+----------------------------------------+----------------------+----------+----------+%n");

    }

    /// Displays a list of users.
    /// Calls the viewUsers method from FlipFitAdminServiceOperations.
    ///
    public void viewUsers() {
        List<FlipFitUser> users = adminServiceOperations.viewUsers();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        String leftAlignFormat = "| %-8s | %-20s | %-30s | %-15s | %-20s | %-40s |%n";
        System.out.format(  "+----------+----------------------+-------------------------------+---------------+--------------------+--------------------------------------+%n");
        System.out.format("| FlipFitUser ID  |     Name             | Email                         | Phone Number  | Location           | Address                              |%n");
        System.out.format("+----------+----------------------+-------------------------------+---------------+--------------------+--------------------------------------+%n"  );

        for (FlipFitUser user : users) {
            System.out.format(leftAlignFormat, user.getUserId(), user.getUserName(), user.getEmail(), user.getPhoneNumber(), user.getLocation(), user.getAddress());
        }
        System.out.format("+----------+----------------------+-------------------------------+---------------+--------------------+--------------------------------------+%n");
    }

    /// Displays a list of gym owners.
    /// Calls the viewGymOwners method from FlipFitAdminServiceOperations.
    ///
    public void viewGymOwners() {
        List<FlipFitGymOwner> gymOwners = adminServiceOperations.viewGymOwners();
        if (gymOwners.isEmpty()) {
            System.out.println("No gym owners found.");
            return;
        }

        String leftAlignFormat = "| %-13s | %-20s | %-30s | %-15s | %-10s | %-20s | %-20s | %-20s |%n";
        System.out.format( "+---------------+----------------------+-------------------------------+---------------+------------+----------------------+----------------------+----------------------+%n");
        System.out.format("| FlipFitGym Owner ID  |     Name             | Email                         | Phone Number  | GST        | National ID          | Verification Status  | PAN                  |%n");
        System.out.format("+---------------+----------------------+-------------------------------+---------------+------------+----------------------+----------------------+----------------------+%n"  );

        for (FlipFitGymOwner gymOwner : gymOwners) {
            System.out.format(leftAlignFormat, gymOwner.getOwnerId(), gymOwner.getOwnerName(), gymOwner.getOwnerEmail(), gymOwner.getPhoneNo(), gymOwner.getGST(), gymOwner.getNationalId(), gymOwner.getVerificationStatus(), gymOwner.getPAN());
        }
        System.out.format("+---------------+----------------------+-------------------------------+---------------+------------+----------------------+----------------------+----------------------+%n");

    }

    /// Verifies a gym by its ID.
    ///
    /// @param gymId ID of the gym to be verified.
    /// @return true if the gym is verified, false otherwise.
    public boolean verifyGym(int gymId) {
        return adminServiceOperations.verifyGym(gymId);
    }

    /// Verifies a gym owner by their ID.
    ///
    /// @param gymOwnerId ID of the gym owner to be verified.
    /// @return true if the gym owner is verified, false otherwise.
    public boolean verifyGymOwner(int gymOwnerId) {
        return adminServiceOperations.verifyGymOwner(gymOwnerId);
    }

    /// Displays a list of unverified gyms.
    /// Calls getUnverifiedGyms from FlipFitAdminServiceOperations and formats the output for display.
    ///
    public void viewUnverifiedGyms() {
        List<FlipFitGym> gyms = adminServiceOperations.getUnverifiedGyms();
        if (gyms.isEmpty()) {
            System.out.println("No unverified gyms found.");
            return;
        }

        String leftAlignFormat = "| %-5d | %-20s | %-5d | %-40s | %-20s | %-15s |%n";
        System.out.format(  "+-------+----------------------+--------+------------------------------------------+----------------------+------------------+%n");
        System.out.format("| S.No  |     Name             | FlipFitGym ID |           Address                        |   Location           |     Status       |%n");
        System.out.format("+-------+----------------------+--------+------------------------------------------+----------------------+------------------+%n"  );

        int gymCounter = 1;
        for (FlipFitGym g : gyms) {
            System.out.format(leftAlignFormat, gymCounter, g.getGymName(), g.getGymId(), g.getGymAddress(), g.getLocation(), g.getStatus());
            gymCounter++;
        }
        System.out.format("+-------+----------------------+--------+------------------------------------------+----------------------+------------------+%n");
    }

    /// Displays a list of unverified gym owners.
    /// Calls getUnverifiedGymOwners from FlipFitAdminServiceOperations and formats the output for display.
    ///
    public void viewUnverifiedGymOwners() {
        List<FlipFitGymOwner> gymOwnerList = adminServiceOperations.getUnverifiedGymOwners();
        if (gymOwnerList.isEmpty()) {
            System.out.println("No unverified gym owners found.");
            return;
        }

        String leftAlignFormat = "| %-5d | %-10d | %-20s | %-20s | %-15s |%n";
        System.out.format(  "+-------+----------+----------------------+----------------------+------------------+%n");
        System.out.format("| S.No  | Owner ID |     Owner Name       |     Email            |     Status       |%n");
        System.out.format("+-------+----------+----------------------+----------------------+------------------+%n"  );

        int gymOwnerCounter = 1;
        for (FlipFitGymOwner gymOwner : gymOwnerList) {
            System.out.format(leftAlignFormat, gymOwnerCounter, gymOwner.getOwnerId(), gymOwner.getOwnerName(), gymOwner.getOwnerEmail(), gymOwner.getVerificationStatus());
            gymOwnerCounter++;
        }
        System.out.format("+-------+----------+----------------------+----------------------+------------------+%n");
    }

    /// Verifies admin credentials.
    ///
    /// @param userMail Email of the admin.
    /// @param password Password of the admin.
    /// @return true if credentials match the predefined admin credentials, false otherwise.
    public boolean verifyAdminCredentials(String userMail, String password) {
        try {
            return userMail.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
