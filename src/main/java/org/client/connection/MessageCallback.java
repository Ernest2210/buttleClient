package org.client.connection;

import java.util.Map;

@FunctionalInterface
public interface MessageCallback {
    void callback(Map<String, String> data);
}
