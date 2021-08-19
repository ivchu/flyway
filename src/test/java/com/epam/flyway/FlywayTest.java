package com.epam.flyway;

import com.epam.domain.Company;
import com.epam.domain.Employee;
import com.epam.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

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

    @Test
    void company_table_is_created() {
        final List<Company> companies = jdbcTemplate
                .query("SELECT name, location FROM company", (rs, rowNum) -> new Company(
                        rs.getString("name"),
                        rs.getString("location")
                ));
        for (Company company : companies) {
            System.out.println(company);
        }

        Assertions.assertThat(companies).isNotEmpty();
    }

    @Test
    void employee_table_is_created() {
        final List<Employee> employees = jdbcTemplate
                .query("SELECT job, first_name, last_name FROM employee", (rs, rowNum) -> new Employee(
                        rs.getString("first_name"),
                        rs.getString("first_name"),
                        rs.getString("job")
                ));
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        Assertions.assertThat(employees).isNotEmpty();
    }
}