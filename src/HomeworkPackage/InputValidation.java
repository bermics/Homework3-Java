package HomeworkPackage;

import java.util.regex.Pattern;

public class InputValidation {

    public static ValidationResult validateName(String name) {
        if (name.trim().isEmpty()) {
            return new ValidationResult(false, "Name cannot be empty.");
        }
        if (!Pattern.matches("^[a-zA-Z0-9 ]+$", name)) {
            return new ValidationResult(false, "Name can only contain letters, numbers, and spaces.");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validateEmail(String email) {
        String pattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
        if (!Pattern.matches(pattern, email)) {
            return new ValidationResult(false, "Email must be a valid format (example@email.com).");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validatePhone(String phone) {
        if (!phone.matches("\\d{10,}")) {
            return new ValidationResult(false, "Phone number must contain at least 10 digits.");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validateAddress(String address) {
        if (address.trim().isEmpty()) {
            return new ValidationResult(false, "Address cannot be empty.");
        }
        return new ValidationResult(true, "");
    }
}
