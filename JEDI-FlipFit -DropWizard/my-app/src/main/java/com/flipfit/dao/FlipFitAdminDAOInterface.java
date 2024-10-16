package com.flipfit.dao;

import com.flipfit.model.FlipFitGym;
import com.flipfit.model.FlipFitGymOwner;
import com.flipfit.model.FlipFitUser;

import java.util.List;

public interface FlipFitAdminDAOInterface {
    /**
     * FlipFitAdmin Can View All the FlipFitGym Owners
     *@return A list of FlipFitGymOwner objects representing all registered gym owners. If no gym owners are found, an empty list is returned.*/
    List<FlipFitGymOwner> viewGymOwners();
    /**
     * FlipFitAdmin Can View All the Gyms
     * @return A list of FlipFitGym objects representing all registered gyms. If no gyms are found, an empty list is returned.
     */
    List<FlipFitGym> viewGyms();
    /**
     * FlipFitAdmin Can View All the Users
     * @return A list of FlipFitUser objects representing all registered users. If no users are found, an empty list is returned.
     */
    List<FlipFitUser> viewUsers();
    /**
     * FlipFitAdmin Can Verify the Gyms and change their Status Like Verified Profile
     * @return  True if the gym with the specified ID exists and is valid otherwise false if the gym is not found or is invalid.
     * @param gymId- The ID of the gym to be verified
     */
    boolean verifyGym(int gymId);
    /**
     * FlipFitAdmin Can Verify the FlipFitGym Owners and change their Status Like Verified Profile
     * @return  True if the gym owner with the specified ID is valid and exists otherwise false if the gym owner is not found or is invalid.
     * @param gymOwnerId - The ID of the gym owner to be verified
     */
    boolean verifyGymOwner(int gymOwnerId);
    /**
     * Get a list of unverified gyms
     * @return List of unverified gyms
     */
    List<FlipFitGymOwner> getUnverifiedGymOwners();
    /**
     * Get a list of unverified gym owners
     * @return List of unverified gym owners
     */
    List<FlipFitGym> getUnverifiedGyms();
}
