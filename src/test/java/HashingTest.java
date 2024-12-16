import com.github.adrjo.util.HashHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashingTest {

    @Test
    void testPasswordHash() {
        String encoded = HashHelper.hashAndSaltPassword("secret_passwordgjkdfshgjkdfhdfjkghfdjkghdfgjkdfhgd^^^^¨¨4¨234¨23jkfghfdjkgd¨khjkhjäöåöäfhgdhfgjkdfhggdf");

        assertTrue(HashHelper.verifyHash("secret_passwordgjkdfshgjkdfhdfjkghfdjkghdfgjkdfhgd^^^^¨¨4¨234¨23jkfghfdjkgd¨khjkhjäöåöäfhgdhfgjkdfhggdf", encoded));
    }
}
