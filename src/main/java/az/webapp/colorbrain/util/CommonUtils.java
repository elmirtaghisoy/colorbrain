package az.webapp.colorbrain.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    public static String generateRandomFolderName() {
        return UUID.randomUUID().toString();
    }

}
