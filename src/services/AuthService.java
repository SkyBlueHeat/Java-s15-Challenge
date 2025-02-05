package services;

import models.User;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private Map<String, User> users = new HashMap<>();

    public void register(String name, String email, String password) {
        if (users.containsKey(email)) {
            System.out.println("Bu e-posta adresi zaten kullanılıyor.");
            return;
        }
        users.put(email, new User(email, name, password)); // Kullanıcıyı email ile kayıt etme
    }

    public User login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.checkPassword(password)) { // şifreyi doğrulama
            System.out.println("Giriş başarılı! Hoş geldiniz, " + user.getName());
            return user;
        }
        System.out.println("Hatalı e-posta veya şifre.");
        return null;
    }
}
