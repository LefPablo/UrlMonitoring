package org.monitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;

@Service
public class UrlMonitoringService {

    public static final int DEFAULT_PORT = 80;

    private final Logger logger = LoggerFactory.getLogger(UrlMonitoringService.class);

    public boolean isUrlAvailable(URL url) {
        return pingHost(url.getHost(), DEFAULT_PORT);
    }

    public boolean pingHost(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port));
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

}
