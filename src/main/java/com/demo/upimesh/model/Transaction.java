import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction", indexes = { @Index(name = "idx_packet_hash", columnList = "packatHash", unique = true) })
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 64)
  private String packetHash; // SHA-256 hex of the encrypted packet

  @Column(nullable = false)
  private String senderVpa;

  @Column(nullable = false)
  private String receiverVpa;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false)
  private Instant sighnedAt; // when the sender orignally signed it

  @Column(nullable = false)
  private Instant settledAt; // when the server actually processed it

  @Column(nullable = false)
  private String bridgeNodeId;

  @Column(nullable = false)
  private int hopCount; // how many devices it passed through

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Stauts status;

  private enum Status {
    SETTLED, REJECTED
  }

  public Transaction() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPacketHash() {
    return packetHash;
  }

  public void setPacketHash(String packetHash) {
    this.packetHash = packetHash;
  }

  public String getSenderVpa() {
    return senderVpa;
  }

  public void setSenderVpa(String senderVpa) {
    this.senderVpa = senderVpa;
  }

  public String getReceiverVpa() {
    return receiverVpa;
  }

  public void setReceiverVpa(String receiverVpa) {
    this.receiverVpa = receiverVpa;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Instant getSignedAt() {
    return signedAt;
  }

  public void setSignedAt(Instant signedAt) {
    this.signedAt = signedAt;
  }

  public Instant getSettledAt() {
    return settledAt;
  }

  public void setSettledAt(Instant settledAt) {
    this.settledAt = settledAt;
  }

  public String getBridgeNodeId() {
    return bridgeNodeId;
  }

  public void setBridgeNodeId(String bridgeNodeId) {
    this.bridgeNodeId = bridgeNodeId;
  }

  public int getHopCount() {
    return hopCount;
  }

  public void setHopCount(int hopCount) {
    this.hopCount = hopCount;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
