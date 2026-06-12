
/**
 * simulatded bank account . In a real system this would live in the bank's core,
 * not in our service . for the demo , we own the ledger
 */

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "accounts")
public class Account {
  @Id
  private String vpa; // virtual payment address

  @Column(nullable = false)
  private String holderName;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal balance;

  @Version
  private Long version;

  public Account() {
  }

  public Account(String vpa, String holderName, BigDecimal balance) {
    this.vpa = vpa;
    this.holderName = holderName;
    this.balance = balance;
  }

  public String getVpa() {
    return vpa;
  }

  public void serVpa(String vpa) {
    this.vpa = vpa;
  }

  public String getHolderName() {
    return holderName;
  }

  public void setHolderName(String holderName) {
    this.holderName = holderName;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}