import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class DemoService {
  private static final Logger log = LoggerFactory.getLogger(DemoService.class);

  @Autowired
  private AccountRepository accounts;
  @Autowired
  private HybridCryptoService crypto;
  @Autowired
  private ServerKeyHolder serverKey;

  @PostConstruct
  public void seedAccounts() {
    if (accounts.count() == 0) {
      accounts.save(new Account("amogh@demo", "Amogh", new BigDecimal("5000.00")));
      accounts.save(new Account("audrija@demo", "Audrija", new BigDecimal("1000.00")));
      accounts.save(new Account("rahul@demo", "Rahul", new BigDecimal("2500.00")));
      accounts.save(new Account("shubham@demo", "Shubham", new BigDecimal("500.00")));
      log.info("Seeded 4 demo accounts");
    }
  }

  public MeshPacket createPacket(String senderVpa, String receiverVpa, BigDecimal amount, String pin, int ttl)
      throws Exception {
    PaymentInstruction instruction = new PaymentInstruction(
        senderVpa,
        receiverVpa,
        amount,
        sha256Hex(pin),
        UUID.randomUUID().toString(),
        Instant.now().toEpochMilli());

    String ciphertext = crypto.encrypt(instruction, serverKey.getPublicKey());

    MeshPacket packet = new MeshPacket();
    packet.setPacketId(UUID.randomUUID().toString());
    packet.setTtl(ttl);
    packet.setCreatedAt(Instant.now().toEpochMilli());
    packet.setCiphertext(ciphertext);
    return packet;
  }

  private String sha256Hex(String input) throws Exception {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hash = md.digest(input.getBytes());
    StringBuilder hex = new StringBuilder();
    for (byte b : hash)
      hex.append(String.format("%02x", b));
    return hex.toString();
  }
}
