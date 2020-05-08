package sample;

public class User {
    private Integer id;
    private String name;
    private String surname;
    private String password;
    private String mail;
    private String birthDate;

    public User(Integer id, String name, String surname, String password, String mail, String birthDate) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.mail = mail;
        this.birthDate= birthDate;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}