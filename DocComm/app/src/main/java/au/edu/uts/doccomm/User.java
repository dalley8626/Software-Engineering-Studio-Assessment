package au.edu.uts.doccomm;

public class User {
    public String userId;
    public static String firstName;
    public static String lastName;
    public String emailAddress;
    public String password;
    public static String gender;
    public String dateOfBirth;
    public String phoneNumber;
    public static String weight;
    public static String height;
    public static String medicalCondition;


    public User() {

    }

    public User(String userId, String firstName, String lastName, String emailAddress, String password, String gender, String phoneNumber,String dateOfBirth, String weight, String height, String medicalCondition) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.height = height;
        this.medicalCondition = medicalCondition;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }
}
