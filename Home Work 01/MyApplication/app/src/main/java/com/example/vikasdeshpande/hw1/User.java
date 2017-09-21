package com.example.vikasdeshpande.hw1;

/**
 * Created by sainishanthdilly on 9/17/17.
 */

public class User {

    private String fullName;
    private String userName;
    private String passWord;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null)
            return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null)
            return false;
        return passWord != null ? passWord.equals(user.passWord) : user.passWord == null;

    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (passWord != null ? passWord.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
