package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        List<Object[]> batch = new ArrayList<>();
        for (Role role : user.getRoles()) {
            Object[] values = new Object[]{role.toString(), user.getId()};
            batch.add(values);
        }
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (role ,user_id) VALUES (?,?)", batch);
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        return getBy("id", id);
    }

    @Override
    public User getByEmail(String email) {
        return getBy("email", email);
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, Set<Role>> roles = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles", rs -> {
            roles.merge(rs.getInt("user_id"),
                    EnumSet.of(Role.valueOf(rs.getString("role"))),
                    (r1, r2) -> {
                        r1.addAll(r2);
                        return r1;
                    });
        });
        for (User user : users) {
            user.setRoles(roles.get(user.getId()));
        }
        return users;
    }

    private <T> User getBy(String column, T value) {
        String sql = "SELECT * FROM users WHERE " + column + " = ?";
        List<User> users = jdbcTemplate.query(sql, ROW_MAPPER, value);
        User user = DataAccessUtils.singleResult(users);
        if (user != null) {
            List<Role> roles = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id = ?",
                    (rs, rowNum) -> Role.valueOf(rs.getString("role")),
                    user.getId());
            user.setRoles(roles);
        }
        return user;
    }
}
