package au.edu.uts.doccomm;

public class User {
    public String emailAddres;
    public String firstName;
    public String lastName;
    public String gender;
    public String weight;

    public User(String emailAddres, String firstName, String lastName, String gender, String weight) {
        this.emailAddres = emailAddres;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.weight = weight;
    }
    public User() {

    }

    public String getEmailAddres() {
        return emailAddres;
    }

    public void setEmailAddres(String emailAddres) {
        this.emailAddres = emailAddres;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}

