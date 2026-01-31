package server.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public record User(String userName, String hashedPassword, Map<String, WebsitePair> passwords)
    implements Serializable {

    @Serial
    private static final long serialVersionUID = -3294026624543407027L;
}
