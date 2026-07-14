import org.springframework.stereotype.Controller;

@Controller
public class DashboardController {
  @GetMapping("/")
  public String home() {
    return "dashboard";
  }
}
