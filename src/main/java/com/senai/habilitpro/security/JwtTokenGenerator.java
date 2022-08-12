package com.senai.habilitpro.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.habilitpro.model.dto.response.UsuarioResponseLoginApiDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtTokenGenerator {

    private static ObjectMapper mapper = new ObjectMapper();

    public static final SecretKey KEY = Keys.hmacShaKeyFor("A!@#$%ASDFGHJKLQWERTYUIOMNBVCXJHGFDUYTR".getBytes(StandardCharsets.UTF_8));

    private static String jsonToken;

    private static int empresaId = 0;

    private static boolean isAdmin;
    public static String gerarLoginWebToken(UsuarioResponseLoginApiDTO usuarioResponseLoginApiDTO) {
        try {
            jsonToken = Jwts.builder()
                            .signWith(KEY, SignatureAlgorithm.HS256)
                            .setPayload(mapper.writeValueAsString(usuarioResponseLoginApiDTO))
                            .compact();
            empresaId = (int) Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jsonToken).getBody().get("empresaId");
            isAdmin = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(jsonToken).getBody().get("roles").toString().contains("ADMINISTRADOR");
        } catch (JsonProcessingException e) {
            e.getMessage();
        }
        return jsonToken;
    }

    public static String getJsonToken() {
        return jsonToken;
    }

    public static int getEmpresaId() {
        return empresaId;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }

}
