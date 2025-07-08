/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 *
 * @author hugo
 */
public class SignatureGenerator {

    public static void main(String[] args) throws Exception {
        String payload = "{\"id\":123456789,\"type\":\"payment\",\"data\":{\"id\":\"123456789\"}}";
        String secret = "00d381ec22b26636c9998eb029c4ad86b7a10e8a6524136e04a88c9355f851ce"; // Reemplaza con tu WEBHOOK_SECRET

        Mac sha256 = Mac.getInstance("HmacSHA256");
        sha256.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
        String signature = Base64.getEncoder().encodeToString(sha256.doFinal(payload.getBytes()));

        System.out.println("X-Signature: " + signature);
    }
}
