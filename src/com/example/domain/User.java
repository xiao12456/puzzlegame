package com.example.domain;

public class User {
    private String username;
    private String password;
    private int count;

    public User() {
    }

    public User(String username, String password, int count) {
        this.username = username;
        this.password = password;
        this.count = count;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "username=" + username + "&password=" + password + "&count=" + count;
    }
}
