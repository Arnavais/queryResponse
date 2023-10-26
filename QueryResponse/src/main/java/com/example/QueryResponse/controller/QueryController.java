package com.example.QueryResponse.controller;

import com.example.QueryResponse.model.Query;
import com.example.QueryResponse.service.QueryServiceImplementation;
import com.example.QueryResponse.service.QueryValidation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.sf.jsqlparser.JSQLParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/Query")
@CrossOrigin(origins = "http://localhost:3000")
public class QueryController {

    @Autowired
    private QueryValidation queryValidation;

    @Autowired
    private QueryServiceImplementation queryServiceImplementation;

    @PersistenceContext
    private EntityManager entityManager;
   @Autowired
   private JdbcTemplate jdbcTemplate;
    @PostMapping("/add")
    public  List<Map<String, Object>> acceptSqlQuery(@RequestBody Query query) throws JSQLParserException, UnsupportedEncodingException {
        queryServiceImplementation.addQuery(query.getQuery());
        return queryValidation.validateSqlQuery(query);

    }
}
