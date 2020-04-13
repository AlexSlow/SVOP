package com.svop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

/**
 * Защита websocket
 */

public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    /**
     * Отключить защиту от веб сокета
     * @return
     */
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

}
