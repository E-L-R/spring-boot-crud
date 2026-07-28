import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@org.springframework.boot.autoconfigure.SpringBootApplication(scanBasePackages = {
        "controller",
        "dao",
        "service"
})
@EntityScan(basePackages = "model")
@EnableJpaRepositories(basePackages = "dao")
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}
