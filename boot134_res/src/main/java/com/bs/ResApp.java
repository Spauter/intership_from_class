package   com.bs;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@MapperScan("com.bs.mapper")
@EnableSwagger2
public class ResApp {
    public static void main(String[] args) {
        SpringApplication.run(ResApp.class,args);
        System.out.println("  ____");
        System.out.println(" /\\\\ / ___'_ __ _---_ __  _ _ _ _ _ ____  _____");
        System.out.println("( ( )\\___ | '_ | '_' | ' ' |\\_   _/|  __ | (_) )\\ \\");
        System.out.println(" \\\\/  ___)| |_)| | | | | | |  | |  |  __)|  -- \\");
        System.out.println("  '  |____| .__|_| |_ \\___/   |_|  |____)|_|  \\_\\ /");
        System.out.println(" =========|_|==============|___/=/_/_/_/==============");
    }
}

















































