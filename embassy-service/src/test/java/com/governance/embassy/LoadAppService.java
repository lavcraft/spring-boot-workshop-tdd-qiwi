package com.governance.embassy;

import com.governance.embassy.service.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadAppService {
    private final NotificationService notificationService;

    @PostConstruct
//    @EventListener(ContextRefreshedEvent.class)
    public void postConstruct() {
        if (!isProduction()) {
            throw new RuntimeException("sth went wrong");
        }
        notificationService.sendMessage();
    }

    private boolean isProduction() {
        return false;
    }
}
