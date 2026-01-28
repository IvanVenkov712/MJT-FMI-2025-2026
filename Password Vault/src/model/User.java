package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public record User(@Serial String userName, @Serial String hashedPassword, @Serial Map<String, WebsitePair> passwords)
    implements Serializable {
}
