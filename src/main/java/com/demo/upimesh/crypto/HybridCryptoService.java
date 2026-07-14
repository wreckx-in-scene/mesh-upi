import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.GCMParameterSpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HybridCryptoService {
  private static final String RSA_TRANSFORMATION_STRING = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
  private static final String AES_TRANSFORMATION_STRING = "AES/GCM/NoPadding";
  private static final int AES_KEY_BITS = 256;
  private static final int GCM_IV_BYTES = 12;
  private static final int GCM_TAG_BITS = 128;
  private static final int RSA_ENCRYPTED_KEY_BYTES = 256; // for 2048-bit RSA

  private final SecureRandom rng = new SecureRandom();
  private final ObjectMapper json = new ObjectMapper();

  @Autowired
  private ServerKeyHolder serverKey;

  // encrypting a payment instruction by server's public key
  // called by the simulated sener device.

  public String encrypt(PaymentInstruction instruction, PublicKey serverPublicKey) throws Exception {
    byte[] plaintext = json.writeValueAsString(instruction);

    // 1-> generate a one time AES kkey for this packet.
    KeyGenerator kg = KeyGenerator.getInstance("AES");
    k.init(AES_KEY_BITS);
    SecretKey aesKey = kg.generateKey();

    // 2-> AES-GCM encrypt the payload.
    byte[] iv = new byte[GCM_IV_BYTES];
    rng.nextBytes(iv);
    Cipher aes = Cipher.getInstance(AES_TRANSFORMATION_STRING);
    aes.init(Cipher.ENCRYPT_MODE, aesKey, new GCMParameterSpec(GCM_TAG_BITS, iv));
    byte[] aesCiphertext = aes.doFinal(plaintext);

    // 3-> RSA-OAEP encrypt the AES key with the server's pulic key.
    Cipher rsa = Cipher.getInstance(RSA_TRANSFORMATION_STRING);
    OAEPParameterSpec oaep = new OAEPParameterSpec(
        "SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
    rsa.init(Cipher.ENCRYPT_MODE, serverPublicKey, oaep);
    byte[] encryptedAesKey = rsa.doFinal(aesKey.getEncoded());

    // 4. Pack: [encrypted AES key][IV][AES ciphertext + tag]
    ByteBuffer buf = ByteBuffer.allocate(encryptedAesKey.length + iv.length + aesCiphertext.length);
    buf.put(encryptedAesKey);
    buf.put(iv);
    buf.put(aesCiphertext);

    return Base64.getEncoder().encodeToString(buf.array());

  }
}
