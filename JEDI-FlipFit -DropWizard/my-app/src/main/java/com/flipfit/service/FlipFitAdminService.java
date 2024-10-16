package com.flipfit.service;

import com.flipfit.model.FlipFitGym;
import com.flipfit.model.FlipFitGymOwner;
import com.flipfit.model.FlipFitUser;

import java.util.List;

/**
 * Interface representing the operations that an FlipFitAdmin can perform related to gyms and gym owners.
 * Includes methods for viewing and verifying gyms and gym owners, as well as retrieving lists of unverified entities.
 *
 */
public interface FlipFitAdminService {

    /**
     * Views the list of gym owners.
     *
     * This method retrieves and displays all registered gym owners.
     *
     */
    List<FlipFitGymOwner> viewGymOwners();

    /**
     * Views the list of gyms.
     *
     * This method retrieves and displays all registered gyms.
     *
     */
    List<FlipFitGym> viewGyms();

    /**
     * Returns the list of users.
     *
     * This method retrieves and returns all registered users.
     *
     */
    List<FlipFitUser> viewUsers();

    /**
     * Verifies the legitimacy of a gym.
     *
     * @param gymId the unique identifier of the gym to be verified
     * @return true if the gym is verified successfully; false otherwise
     */
    boolean verifyGym(int gymId);

    /**
     * Verifies the legitimacy of a gym owner.
     *
     * @param gymOwnerId the unique identifier of the gym owner to be verified
     * @return true if the gym owner is verified successfully; false otherwise
     */
    boolean verifyGymOwner(int gymOwnerId);

    /**
     * Retrieves a list of unverified gym owners.
     *
     * @return a list of FlipFitGymOwner objects representing the gym owners who have not yet been verified
     */
    List<FlipFitGymOwner> getUnverifiedGymOwners();

    /**
     * Retrieves a list of unverified gyms.
     *
     * @return a list of FlipFitGym objects representing the gyms that have not yet been verified
     */
    List<FlipFitGym> getUnverifiedGyms();
}
