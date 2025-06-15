package aarcosb.config;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Locale;

@Configuration
public class PrettyTimeConfig {
    @Bean
    public PrettyTime prettyTime() {
        return new PrettyTime(Locale.forLanguageTag("en"));
    }
} 