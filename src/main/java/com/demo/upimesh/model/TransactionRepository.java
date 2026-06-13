import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Tranaction, Long> {
  List<Transaction> findTop20ByOrderByIdDesc();

  boolean existsByPacketHash(String packetHash);
}