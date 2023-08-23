package com.trade.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Bean
    Jackson2ObjectMapperBuilder objectMapperBuilder(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.ALWAYS)
                .serializerByType(LocalDateTime.class, new JacksonLocalDateTimeSerializer());

        return builder;
    }


    public static class JacksonLocalDateTimeSerializer extends StdSerializer<LocalDateTime>{

        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        public JacksonLocalDateTimeSerializer(){
            this(null);
        }

        protected JacksonLocalDateTimeSerializer(Class<LocalDateTime> type){
            super(type);
        }

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(formatter.format(localDateTime));
        }
    }
}
