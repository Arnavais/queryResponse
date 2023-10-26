package com.example.QueryResponse.service;
import com.example.QueryResponse.model.Query;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryValidation {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private QueryServiceImplementation queryServiceImplementation;

    public List<Map<String, Object>> validateSqlQuery(Query query ) {
        List<Map<String, Object>> error = new ArrayList<>();
        Map<String, Object> errorMap = new HashMap<>();
        try {
            Statement statement = CCJSqlParserUtil.parse(query.getQuery());
            if (statement instanceof Select) {
                return jdbcTemplate.queryForList(query.getQuery());
            } else {
                String operation = statement.getClass().getSimpleName().toLowerCase();
                String warning ="Operation '" + operation + "' is not allowed.";
                errorMap.put("Warning",warning);
                error.add(errorMap);
                return error;
            }
        } catch (DataAccessException e){ //error related to database access
            String errorDescription=  e.getCause().getMessage();
            errorMap.put("Error",errorDescription);
            error.add(errorMap);
            return error;
        } catch (JSQLParserException e) { // Related to SQl parsing
            String errorDescription=  e.getCause().getMessage();
            errorMap.put("Error",errorDescription);
            error.add(errorMap);
            return error;
        }
    }

}


