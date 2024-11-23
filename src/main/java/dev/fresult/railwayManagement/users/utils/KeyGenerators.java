package dev.fresult.railwayManagement.users.utils;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

class KeyGenerator {
  public static void main(String[] args) {
    SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    String base64Key = Encoders.BASE64.encode(key.getEncoded());
    System.out.println("key: " + base64Key);
    System.out.println("base64 key:" + base64Key);
  }
}
