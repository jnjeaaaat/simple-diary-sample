package com.example.demo.src.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TestDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> getJwtList() {
        String getJwtListQuery = "select email from user";

        return this.jdbcTemplate.queryForList(getJwtListQuery, String.class);
    }
    public int getUserCount() {
        String getUserCountQuery = "select count(*) from user";

        return this.jdbcTemplate.queryForObject(getUserCountQuery, int.class);
    }
}
