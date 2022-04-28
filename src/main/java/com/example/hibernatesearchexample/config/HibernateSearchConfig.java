package com.example.hibernatesearchexample.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class HibernateSearchConfig {
    private final EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void setupSearchIndexes() throws InterruptedException {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManagerFactory.createEntityManager());
        fullTextEntityManager.createIndexer().startAndWait();
        fullTextEntityManager.close();
    }
}
