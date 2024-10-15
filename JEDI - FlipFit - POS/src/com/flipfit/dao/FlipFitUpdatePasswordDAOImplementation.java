package com.flipfit.dao;

import com.flipfit.constants.SQLConstants;
import com.flipfit.exception.WrongCredentialsException;
import com.flipfit.utils.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class FlipFitUpdatePasswordDAOImplementation implements FlipFitUpdatePasswordDAOInterface {

    public boolean updateGymOwnerPassword(String email, String password, String updatedPassword) {
        try (Connection conn = DatabaseConnector.getConnection();
             Statement statement = conn.createStatement();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.GYM_OWNER_UPDATE_PASSWORD, statement.RETURN_GENERATED_KEYS);) {

            preparedStatement.setString(1, updatedPassword);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                // System.out.println("Password updated successfully!");
                return true;
            } else {
                throw new WrongCredentialsException();
            }
        } catch (WrongCredentialsException e) {
            // System.out.println("(FlipFitGym owner) " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public boolean updateGymUserPassword(String email, String password, String updatedPassword) {
        try (Connection conn = DatabaseConnector.getConnection();
             Statement statement = conn.createStatement();
             PreparedStatement preparedStatement = conn.prepareStatement(SQLConstants.GYM_USER_UPDATE_PASSWORD);) {

            preparedStatement.setString(1, updatedPassword);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                // System.out.println("Password updated successfully!");
                return true;
            } else {
                throw new WrongCredentialsException();
            }
        } catch (WrongCredentialsException e) {
            // System.out.println("(FlipFitGym user) " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
