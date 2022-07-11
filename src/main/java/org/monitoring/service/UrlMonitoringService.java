package org.monitoring.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

@Service
public class UrlMonitoringService {

    public static final int DEFAULT_PORT = 80;
    private boolean urlAvailable;

    public boolean isUrlAvailable(URL url) {
        urlAvailable = pingHost(url.getHost(), DEFAULT_PORT);
        return urlAvailable;
    }

    public boolean getLastResult() {
        return urlAvailable;
    }

    private boolean pingHost(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port));
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

}
