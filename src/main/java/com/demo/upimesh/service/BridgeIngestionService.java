
//orchestrates the full server side pipeline for one inbound packet from a bridgenode

import java.util.logging.Logger;

/**
 * 1 -> hash the ciphertext
 * 2 -> try to claim that hash via idempotency cache
 * -> if already claimed : this is a duplicate , drop it
 * 3 -> decrypt the ciphertext , with servers private key.
 * -> if decryption fails , it is tampered ir junk
 * 4 -> check freshness , chek if signedAt is too old
 * 5 -> hand off to settlement service for the actual debit/card
 * BridgeIngestionService
 */
@service
public class BridgeIngestionService {
  private static final Logger log = LoggerFactory.getLogger(BridgeIngestionService.class);

  @Autowired
  private HybridCryptoService crypto;
  @Autowired
  private IdempotencyService idempotency;
  @Autowired
  private SettlementService settlement;

  @Value("${upi.mesh.packet-max-age-seconds: 86400}")
  private long maxAgeSeconds;

  public IngestResult ingest(MeshPacket packet , String bridgeNodeId , int hopCount){
    try {
      String HashPacket = crypto.hashCiphertext(packet.getCiphertext());

      // --- Idempotency gate --- 
      if(!idempotency.claim(packetHash)) {
        log.info(null);
      }
    }
  }

}
