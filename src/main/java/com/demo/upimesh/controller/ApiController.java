import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
  @Autowired
  private ServerKeyHolder serverKey;
  @Autowired
  private DemoService demo;
  @Autowired
  private MeshSimulatorService mesh;
  @Autowired
  private BridgeIngestionService bridge;
  @Autowired
  private AccountRepository accountRepo;
  @Autowired
  private TransactionRepository txRepo;
  @Autowired
  private IdempotencyService indempotency;

}
