package com.epam.flyway;

import java.util.List;

import com.epam.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

@DataJpaTest
class FlywayTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void databaseHasBeenInitialized() {

        jdbcTemplate.execute(
                "insert into test_user (username, first_name, last_name) values('ivanspresov', 'Ivan', 'Spresov')"
        );

        final List<User> users = jdbcTemplate
                .query("SELECT username, first_name, last_name FROM test_user", (rs, rowNum) -> new User(
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                ));
        for (User user : users) {
            System.out.println(user);
        }

        Assertions.assertThat(users).isNotEmpty();
    }
}