package ru.geekbrains.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

public class PrettyLogger implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String s) {
        String message = s.trim();
        ObjectMapper mapper = new ObjectMapper();
        if (message.startsWith("{") && (message.endsWith("}") || message.startsWith("[") && message.endsWith("]"))) {
//            message = s;
            try {
                Object value = mapper.readValue(message, Object.class);
                String prettyJsonValue = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
                Platform.get().log(Platform.INFO, prettyJsonValue, null);
            } catch (JsonProcessingException e) {
                Platform.get().log(Platform.WARN, message, e);
            }
        }
        else {
            Platform.get().log(Platform.INFO, message, null);
        }
    }
}
