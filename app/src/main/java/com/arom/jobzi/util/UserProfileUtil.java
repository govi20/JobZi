package com.arom.jobzi.util;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.profile.UserProfile;
import com.arom.jobzi.user.User;

import java.util.regex.Pattern;

public final class UserProfileUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+[0-9]{10,13}$");

    private static UserProfileUtil instance;

    private UserProfileUtil() {}

    public User createUser(String username, String email, String firstName, String lastName, AccountType accountType) {

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountType(accountType);

        return user;

    }

    public ServiceProviderProfile createServiceProviderProfile(String address, String phoneNumber, String description, boolean licensed) {

        ServiceProviderProfile serviceProviderProfile = new ServiceProviderProfile();

        serviceProviderProfile.setAddress(address);
        serviceProviderProfile.setPhoneNumber(phoneNumber);
        serviceProviderProfile.setDescription(description);
        serviceProviderProfile.setLicensed(licensed);

        return serviceProviderProfile;

    }

    public ValidationResult validateUserInfo(User user, String password) {

        if(user.getEmail().isEmpty()) {
            return new ValidationResult(ValidatedField.EMAIL, null);
        }

        if(password.isEmpty()) {
            return new ValidationResult(ValidatedField.PASSWORD, null);
        }

        if(user.getFirstName().isEmpty()) {
            return new ValidationResult(ValidatedField.FIRST_NAME, null);
        }

        if(user.getLastName().isEmpty()) {
            return new ValidationResult(ValidatedField.LAST_NAME, null);
        }

        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return new ValidationResult(null, ValidatedField.EMAIL);
        }

        if (!NAME_PATTERN.matcher(user.getFirstName()).matches()) {
            return new ValidationResult(null, ValidatedField.FIRST_NAME);
        }

        if (!NAME_PATTERN.matcher(user.getLastName()).matches()) {
            return new ValidationResult(null, ValidatedField.LAST_NAME);
        }

        if(user.getUserProfile() != null) {

            UserProfile userProfile = user.getUserProfile();

            if(user.getAccountType().equals(AccountType.SERVICE_PROVIDER) && userProfile instanceof ServiceProviderProfile) {

                return validateServiceProviderProfile((ServiceProviderProfile) userProfile);

            }

        }

        return new ValidationResult(null, null);

    }

    public ValidationResult validateServiceProviderProfile(ServiceProviderProfile profile) {

        if(profile.getAddress().isEmpty()) {
            return new ValidationResult(ValidatedField.ADDRESS, null);
        }

        if(profile.getPhoneNumber().isEmpty()) {
            return new ValidationResult(ValidatedField.PHONE_NUMBER, null);
        }

        if(!ADDRESS_PATTERN.matcher(profile.getAddress()).matches()) {
            return new ValidationResult(null, ValidatedField.ADDRESS);
        }

        if(!PHONE_NUMBER_PATTERN.matcher(profile.getPhoneNumber()).matches()) {
            return new ValidationResult(null, ValidatedField.PHONE_NUMBER);
        }

        return new ValidationResult(null, null);

    }

    public static UserProfileUtil getInstance() {

        if(instance == null) {
            instance = new UserProfileUtil();
        }

        return instance;

    }

    public enum ValidatedField {
        // General user field common to all users.
        USERNAME, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME,

        // Fields only found in the service provider's profile.
        ADDRESS, PHONE_NUMBER, GENERAL_INFO, LICENSED

    }

    public static class ValidationResult {

        private final ValidatedField emptyField;
        private final ValidatedField invalidField;

        public ValidationResult(ValidatedField emptyField, ValidatedField invalidField) {
            this.emptyField = emptyField;
            this.invalidField = invalidField;
        }

        public ValidatedField getEmptyField() {
            return emptyField;
        }

        public ValidatedField getInvalidField() {
            return invalidField;
        }

    }

}
