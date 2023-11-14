package tr.org.povatr.mongodb.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tr.org.povatr.mongodb.entity.User;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private User user = User.builder()
            .name("erdem")
            .name("aydemir")
            .build();

    @Test
    public void saveUser() throws InterruptedException {
        userService.save(user);
        Thread.sleep(1000);
    }

    @Test
    public void testService() {
        userService.deleteById(user.getId());
    }
}
