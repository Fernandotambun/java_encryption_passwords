/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PV.evolution.util.models;

import PV.evolution.util.Encryptor;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class PasswordStore {

    public String name, username;
    private String password, hashkey;
    private double score;
    private int category;
    public static final int UNCATEGORIZED = 0;
    public static final int CAT_WEBAPP = 1;
    public static final int CAT_MOBILEAPP = 2;
    public static final int CAT_OTHER = 3;

    public PasswordStore(String name, String username, String plainPass, int category) throws NoSuchAlgorithmException, Exception {
        this.hashkey = Encryptor.generateKey();
        this.name = name;
        this.username = username;
        setPassword(plainPass);
        setCategory(category);
    }

    public PasswordStore(String name, String username, String plainPass) throws Exception {
        this.hashkey = Encryptor.generateKey();
        this.name = name;
        this.username = username;
        setPassword(plainPass);
        setCategory(UNCATEGORIZED);
    }

    public void setPassword(String plainPass) throws Exception {
        this.password = Encryptor.encrypt(plainPass, this.hashkey);
        calculateScore(plainPass);

    }

    public String getPassword() throws Exception {
        return Encryptor.decrypt(password, hashkey);
    }

    public void setCategory(int category) {
        if (this.category >= 0 && this.category <= 3) {
            this.category = category;
        } else {
            this.category = 0;
        }
    }

    public String getCategory() {
        switch (this.category) {
            case 0 -> {
                return "Belum terkategori";
            }
            case 1 -> {
                return "Aplikasi web";
            }
            case 2 -> {
                return "Aplikasi mobile";
            }
            case 3 -> {
                return "Akun lainnya";
            }

            default ->
                throw new AssertionError();
        }
    }

    private void calculateScore(String plainPass) {
        if (plainPass.length() > 15) {
            this.score = 10.0;
        } else {
            this.score = (plainPass.length() / 15.0) * 10;
        }
    }

    @Override
    public String toString() {
        return "Username : " + this.username + "\nPassword : " + this.password + "\nHaskkey : " + this.hashkey + "\nKategori : " + this.category + "\nScore : " + this.score;
    }
}
