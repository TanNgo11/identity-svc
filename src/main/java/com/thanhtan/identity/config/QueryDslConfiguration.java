package com.thanhtan.identity.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfiguration {


    @PersistenceContext
    private EntityManager em;

    @Bean
    public JPAQueryFactory getJPAQueryFactory() {
        return new JPAQueryFactory(em);
    }
}