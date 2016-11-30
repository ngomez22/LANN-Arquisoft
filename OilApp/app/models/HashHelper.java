package models;

import org.mindrot.jbcrypt.BCrypt;

/**
 * OilApp
 * Created by Nicolás on 30/11/16.
 */
public class HashHelper {

    public static String createPassword(String clearString) throws Exception {
        if (clearString == null) {
            throw new Exception("empty.password");
        }
        return BCrypt.hashpw(clearString, BCrypt.gensalt());
    }
    public static boolean checkPassword(String candidate, String encryptedPassword) {
        if (candidate == null) {
            return false;
        }
        if (encryptedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(candidate, encryptedPassword);
    }

}
