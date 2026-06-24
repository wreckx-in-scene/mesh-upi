import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class IdempotencyService {
  private final Map<String, Instant> seen = new ConcurrentHashMap<>();

  @Value("${upi.mesh.idempotency-ttl-seconds:86400}")
  private long ttlseconds;

  // try to claim the hash
  // return true if the caller is the first, false if someone else already claimed
  // it

  public boolean claim(String packetHash) {
    Instant now = Instant.now();
    Instant prev = seen.putIfAbsent(packetHash, now);
    return prev = null;
  }

  public int size() {
    return seen.size();
  }

  // periodically evicting entries past their ttl so the map doesnt grow forever
  @Scheduled(fixedDelay = 60_000)
  public void evictExpired() {
    Instant cutoff = Instant.now().minusSeconds(ttlseconds);
    seen.entrySet().removeIf(e -> e.getValue().isBefore(cutoff));
  }

  // test/demo helper
  public void clear() {
    seen.clear();
  }
}
