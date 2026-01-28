package model;

import java.io.Serial;
import java.io.Serializable;

public record WebsitePair(@Serial String userName, @Serial byte[] encryptedPassword) implements Serializable {
}
