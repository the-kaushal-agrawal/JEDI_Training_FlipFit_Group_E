package com.flipfit.service;

import com.flipfit.model.FlipFitGym;
import com.flipfit.model.FlipFitGymOwner;
import com.flipfit.model.FlipFitUser;
import com.flipfit.dao.FlipFitAdminDAOImplementation;

import java.util.List;



/**
 * Implements the FlipFitAdminService interface, providing operations for viewing users, gym owners, and gyms,
 * as well as verifying gyms and gym owners. This class interacts with the DAO layer to perform database operations.
 *
 */
public class FlipFitAdminServiceOperations implements FlipFitAdminService {

    /**
     * Instance of the DAO implementation used to interact with the database.
     */
    FlipFitAdminDAOImplementation flipFitAdminDAOImplementation = new FlipFitAdminDAOImplementation();

    /**
     * Views the list of users.
     * <p>
     * Retrieves a list of users and displays them in a formatted table. If no users are found, a message is printed.
     *
     */
    public List<FlipFitUser> viewUsers() {
        return flipFitAdminDAOImplementation.viewUsers();
    }

    /**
     * Views the list of gym owners.
     * <p>
     * Retrieves a list of gym owners and displays them in a formatted table. If no gym owners are found, a message is printed.
     *
     */
    public List<FlipFitGymOwner> viewGymOwners() {
        return flipFitAdminDAOImplementation.viewGymOwners();
    }

    /**
     * Views the list of gyms.
     * <p>
     * Retrieves a list of gyms and displays them in a formatted table. If no gyms are found, a message is printed.
     *
     */
    public List<FlipFitGym> viewGyms() {
        return flipFitAdminDAOImplementation.viewGyms();
    }

    /**
     * Verifies the legitimacy of a gym.
     *
     * @param gymId the unique identifier of the gym to be verified
     * @return true if the gym is verified successfully; false otherwise
     */
    public boolean verifyGym(int gymId) {
        return flipFitAdminDAOImplementation.verifyGym(gymId);
    }

    /**
     * Verifies the legitimacy of a gym owner.
     *
     * @param gymOwnerId the unique identifier of the gym owner to be verified
     * @return true if the gym owner is verified successfully; false otherwise
     */
    public boolean verifyGymOwner(int gymOwnerId) {
        return flipFitAdminDAOImplementation.verifyGymOwner(gymOwnerId);
    }

    /**
     * Retrieves a list of unverified gym owners.
     *
     * @return a list of FlipFitGymOwner objects representing the gym owners who have not yet been verified
     */
    public List<FlipFitGymOwner> getUnverifiedGymOwners() {
        return flipFitAdminDAOImplementation.getUnverifiedGymOwners();
    }

    /**
     * Retrieves a list of unverified gyms.
     *
     * @return a list of FlipFitGym objects representing the gyms that have not yet been verified
     */
    public List<FlipFitGym> getUnverifiedGyms() {
        return flipFitAdminDAOImplementation.getUnverifiedGyms();
    }
}
