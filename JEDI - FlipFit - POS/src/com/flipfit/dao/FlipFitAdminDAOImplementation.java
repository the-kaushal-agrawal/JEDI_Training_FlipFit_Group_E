package com.flipfit.dao;

import com.flipfit.bean.FlipFitGym;
import com.flipfit.bean.FlipFitGymOwner;
import com.flipfit.bean.FlipFitUser;
import com.flipfit.constants.SQLConstants;
import com.flipfit.exception.VerificationFailedException;
import com.flipfit.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlipFitAdminDAOImplementation implements FlipFitAdminDAOInterface {

    @Override
    public List<FlipFitGym> viewGyms() {
        List<FlipFitGym> gyms = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.ADMIN_VIEW_ALL_GYMS);) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int gymId = resultSet.getInt("gymId");
                    String gymAddress = resultSet.getString("gymAddress");
                    String location = resultSet.getString("location");
                    String gymName = resultSet.getString("gymName");
                    String status = resultSet.getString("Status");
                    int ownerId = resultSet.getInt("ownerId");
                    FlipFitGym gym = new FlipFitGym();
                    gym.setGymId(gymId);
                    gym.setGymName(gymName);
                    gym.setGymAddress(gymAddress);
                    gym.setOwnerId(ownerId);
                    gym.setLocation(location);
                    gym.setStatus(status);

                    gyms.add(gym);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return gyms;
    }

    @Override
    public List<FlipFitUser> viewUsers() {
        List<FlipFitUser> users = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.ADMIN_VIEW_ALL_USERS);) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("userId");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    String userName = resultSet.getString("userName");
                    String address = resultSet.getString("address");
                    String location = resultSet.getString("location");
                    String email = resultSet.getString("email");

                    FlipFitUser user = new FlipFitUser();
                    user.setUserId(userId);
                    user.setPhoneNumber(phoneNumber);
                    user.setUserName(userName);
                    user.setAddress(address);
                    user.setLocation(location);
                    user.setEmail(email);

                    users.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return users;
    }

    @Override
    public List<FlipFitGymOwner> viewGymOwners() {
        List<FlipFitGymOwner> gymOwners = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.ADMIN_VIEW_ALL_GYM_OWNERS);) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ownerId = resultSet.getInt("ownerId");
                    String phoneNo = resultSet.getString("phoneNo");
                    String ownerName = resultSet.getString("ownerName");
                    String ownerEmail = resultSet.getString("ownerEmail");
                    String nationalId = resultSet.getString("nationalId");
                    String GST = resultSet.getString("GST");
                    String PAN = resultSet.getString("PAN");
                    String verificationStatus = resultSet.getString("verificationStatus");

                    FlipFitGymOwner gymOwner = new FlipFitGymOwner();
                    gymOwner.setOwnerId(ownerId);
                    gymOwner.setPhoneNo(phoneNo);
                    gymOwner.setOwnerName(ownerName);
                    gymOwner.setOwnerEmail(ownerEmail);
                    gymOwner.setNationalId(nationalId);
                    gymOwner.setGST(GST);
                    gymOwner.setPAN(PAN);
                    gymOwner.setVerificationStatus(verificationStatus);

                    gymOwners.add(gymOwner);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return gymOwners;
    }

    @Override
    public boolean verifyGymOwner(int gymOwnerId) {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.ADMIN_VERIFY_GYM_OWNER);) {

            preparedStatement.setInt(1, gymOwnerId);

            int rowsUpdated = preparedStatement.executeUpdate(); // execute update statement
            if (rowsUpdated > 0) {
                // System.out.println("FlipFitGym owner verified successfully!");
                return true;
            } else {
                throw new VerificationFailedException();
            }
        } catch (VerificationFailedException e) {
            // System.out.println("FlipFitGym owner " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean verifyGym(int gymId) {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.ADMIN_VERIFY_GYM);) {

            preparedStatement.setInt(1, gymId);

            int rowsUpdated = preparedStatement.executeUpdate(); // execute update statement
            if (rowsUpdated > 0) {
                // System.out.println("FlipFitGym verified successfully!");
                return true;
            } else {
                throw new VerificationFailedException();
            }
        } catch (VerificationFailedException e) {
            // System.out.println("FlipFitGym " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<FlipFitGym> getUnverifiedGyms() {
        List<FlipFitGym> gyms = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.ADMIN_VIEW_UNVERIFIED_GYMS);
             ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                int gymId = resultSet.getInt("gymId");
                String gymAddress = resultSet.getString("gymAddress");
                String location = resultSet.getString("location");
                String gymName = resultSet.getString("gymName");
                String status = resultSet.getString("Status");
                int ownerId = resultSet.getInt("ownerId");

                FlipFitGym gym = new FlipFitGym();
                gym.setGymId(gymId);
                gym.setGymName(gymName);
                gym.setGymAddress(gymAddress);
                gym.setOwnerId(ownerId);
                gym.setLocation(location);
                gym.setStatus(status);

                gyms.add(gym);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return gyms;
    }

    @Override
    public List<FlipFitGymOwner> getUnverifiedGymOwners() {
        List<FlipFitGymOwner> gymOwners = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.ADMIN_VIEW_UNVERIFIED_GYM_OWNERS);
             ResultSet resultSet = preparedStatement.executeQuery();) {

            while (resultSet.next()) {
                int ownerId = resultSet.getInt("ownerId");
                String phoneNo = resultSet.getString("phoneNo");
                String ownerName = resultSet.getString("ownerName");
                String ownerEmail = resultSet.getString("ownerEmail");
                String nationalId = resultSet.getString("nationalId");
                String GST = resultSet.getString("GST");
                String PAN = resultSet.getString("PAN");
                String verificationStatus = resultSet.getString("verificationStatus");

                FlipFitGymOwner gymOwner = new FlipFitGymOwner();
                gymOwner.setOwnerId(ownerId);
                gymOwner.setPhoneNo(phoneNo);
                gymOwner.setOwnerName(ownerName);
                gymOwner.setOwnerEmail(ownerEmail);
                gymOwner.setNationalId(nationalId);
                gymOwner.setGST(GST);
                gymOwner.setPAN(PAN);
                gymOwner.setVerificationStatus(verificationStatus);

                gymOwners.add(gymOwner);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return gymOwners;
    }
}