package com.flipfit.service;

import java.util.*;

import com.flipfit.model.FlipFitGym;
import com.flipfit.model.FlipFitGymOwner;
import com.flipfit.model.FlipFitSlots;
import com.flipfit.dao.FlipFitGymOwnerDAOImplementation;
import com.flipfit.dao.FlipFitUpdatePasswordDAOImplementation;

/**
 * Implementation of the FlipFitGymOwnerService interface, providing business logic for gym owner operations.
 * This class utilizes DAO implementations to interact with the data layer.
 *
 */
public class FlipFitGymOwnerServiceOperations implements FlipFitGymOwnerService {

    private final FlipFitGymOwnerDAOImplementation flipFitGymOwnerDAOImplementation = new FlipFitGymOwnerDAOImplementation();
    private final FlipFitUpdatePasswordDAOImplementation flipFitUpdatePasswordDAOImplementation = new FlipFitUpdatePasswordDAOImplementation();

    /**
     * Adds a new gym to the system.
     *
     * @param gym the FlipFitGym object to be added
     * @return true if the gym was added successfully; false otherwise
     */
    public boolean addGym(FlipFitGym gym) {
        return flipFitGymOwnerDAOImplementation.addGym(gym);
    }

    /**
     * Retrieves the list of gyms owned by a specific gym owner.
     *
     * @param gymOwnerId the unique identifier of the gym owner
     * @return a list of FlipFitGym objects owned by the specified gym owner
     */
    public List<FlipFitGym> viewMyGyms(int gymOwnerId) {
        return flipFitGymOwnerDAOImplementation.viewMyGyms(gymOwnerId);
    }

    /**
     * Creates a new gym owner in the system.
     *
     * @param gymOwner the FlipFitGymOwner object containing details of the new gym owner
     * @return true if the gym owner was created successfully; false otherwise
     */
    public boolean createGymOwner(FlipFitGymOwner gymOwner) {
        return flipFitGymOwnerDAOImplementation.createGymOwner(gymOwner);
    }

    /**
     * Validates the credentials of a gym owner.
     *
     * @param email    the email of the gym owner
     * @param password the current password of the gym owner
     * @return true if the email and password are valid; false otherwise
     */
    public boolean validateGymOwner(String email, String password) {
        return flipFitGymOwnerDAOImplementation.validateGymOwner(email, password);
    }

    /**
     * Updates the password of a gym owner.
     *
     * @param email           the email of the gym owner
     * @param password        the current password of the gym owner
     * @param updatedPassword the new password to be set
     * @return true if the password was updated successfully; false otherwise
     */
    public boolean updateGymOwnerPassword(String email, String password, String updatedPassword) {
        return flipFitUpdatePasswordDAOImplementation.updateGymOwnerPassword(email, password, updatedPassword);
    }

    /**
     * Updates the details of an existing gym owner.
     *
     * @param gymOwner the FlipFitGymOwner object containing updated details of the gym owner
     * @return true if the gym owner details were updated successfully; false otherwise
     */
    public boolean updateGymOwner(FlipFitGymOwner gymOwner) {
        return flipFitGymOwnerDAOImplementation.updateGymOwner(gymOwner);
    }

    /**
     * Retrieves the unique identifier of a gym owner based on their email.
     *
     * @param email the email of the gym owner
     * @return the unique identifier of the gym owner
     */
    public int getGymOwnerIdByEmail(String email) {
        return flipFitGymOwnerDAOImplementation.getGymOwnerIdByEmail(email);
    }

    /**
     * Updates the seat count for a specific gym slot.
     *
     * @param gymId     the unique identifier of the gym
     * @param startTime the start time of the slot
     * @param seatCount the new seat count to be set for the slot
     * @return true if the seat count was updated successfully; false otherwise
     */
    public boolean updateSeatCount(int gymId, int startTime, int seatCount) {
        return flipFitGymOwnerDAOImplementation.updateSeatCount(gymId, startTime, seatCount);
    }

    /**
     * Updates the details of a user.
     *
     * @param gym the gym object containing updated gym details
     * @return true if gym details update is successful, false otherwise
     */
    public boolean updateGymDetails(FlipFitGym gym) {
        return flipFitGymOwnerDAOImplementation.updateGymDetails(gym);
    }


    public boolean addSlots(int gymId, List<FlipFitSlots> slots) {
        return flipFitGymOwnerDAOImplementation.addSlots(gymId, slots);
    }
}
