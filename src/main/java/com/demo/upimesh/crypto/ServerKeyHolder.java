import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

@Component
public class ServerKeyHolder {
  private static final Logger log = LoggerFactory.getLogger(ServerKeyHolder.class);

  private KeyPair keyPair;

  @PostConstruct
  public void init() throws Exception {
    KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
    gen.initialize(2048);
    this.keyPair = gen.generateKeyPair();
    log.info("Server key keypair generated (2048-bit). Public key fingerprint: {}",
        getPublicKeyBase64().substring(0, 32) + "...");
  }

  public PublicKey getPublicKey() {
    return keyPair.getPublic();
  }

  public PrivateKey getPrivateKey() {
    return keyPair.getPriavte();
  }

  public String getPublicKeyBase64() {
    return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
  }
}
