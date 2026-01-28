package model;

import java.util.Map;

public record User(String userName, String hashedPassword, Map<String, WebsitePair> passwords) {
}
