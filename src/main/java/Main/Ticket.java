package Main;

import DB.TicketRepository;
import DB.UserRepository;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan({"Routes", "Security", "Service"})
@EntityScan("Service")
@Configuration
public class Ticket implements WebMvcConfigurer {

    final static private ConnectionString connString = new ConnectionString(
            "mongodb://localhost:27017/ticket"
    );

    public static MongoDatabase mongoDatabase;

    public static ExecutorService pool;
    public static ExecutorService unique_pool;
    public static UserRepository userRepository;
    public static TicketRepository ticketRepository;

    private static void setupDB() {
        try {

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connString)
                    .retryWrites(true)
                    .build();

            MongoClient mongoClient = MongoClients.create(settings);
            mongoDatabase = mongoClient.getDatabase("ticket");

            userRepository = new UserRepository();
            ticketRepository = new TicketRepository();

            pool = Executors.newFixedThreadPool(10);
            unique_pool = Executors.newSingleThreadExecutor();


        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    public static void main(String[] args) {

        setupDB();

//        nu.pattern.OpenCV.loadShared();
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");

        new SpringApplicationBuilder(Ticket.class)
                .run(args);
    }

}
