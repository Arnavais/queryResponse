package com.example.QueryResponse.service;

import com.example.QueryResponse.Repository.QueryRepository;
import com.example.QueryResponse.model.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryServiceImplementation implements QueryService{
   @Autowired
    private QueryRepository queryRepository;
    public String addQuery(String query)
    {
        Query newQuery=new Query();
        newQuery.setQuery(query);
        queryRepository.save(newQuery);
        return "string";
    }
}
