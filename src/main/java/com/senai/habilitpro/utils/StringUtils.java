package com.senai.habilitpro.utils;

import com.senai.habilitpro.model.dto.request.LoginRequestApiDTO;
import com.senai.habilitpro.model.entity.Usuario;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String hashPasswordWithMD5(String password) {
        return DigestUtils.md5Hex(password).toUpperCase();
    }

    public static boolean validatePassword(String hashedPassword, String password) {
        return hashPasswordWithMD5(password).equalsIgnoreCase(hashedPassword);
    }

    public static boolean validateCredentials(List<Usuario> user, LoginRequestApiDTO login) {
        return !user.isEmpty() && (user.get(0).getSenha().equals(login.getSenha())||
                validatePassword(user.get(0).getSenha(),login.getSenha()));
    }

}