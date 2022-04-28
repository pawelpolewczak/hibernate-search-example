package com.example.hibernatesearchexample.product;

import com.example.hibernatesearchexample.search.IntRangeSearchDataFilter;
import com.example.hibernatesearchexample.search.KeywordSearchDataFilter;
import com.example.hibernatesearchexample.search.SearchDataFilter;
import com.example.hibernatesearchexample.utils.BooleanQueryCollector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
class ProductSearchRepositoryImpl implements ProductSearchRepository {

    private final EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> searchProducts(List<SearchDataFilter> filters) {
        var fullTextEntityManager = getFullTextEntityManager();
        List<Query> queries = filters.stream()
                .filter(SearchDataFilter::isValidFilter)
                .map(filter -> getQueryBasedOnFilter(fullTextEntityManager, filter))
                .toList();

        Query combinedBoolQuery = getCombinedBoolQuery(queries);
        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(combinedBoolQuery, Product.class);
        return fullTextQuery.getResultList();
    }

    public Query getQueryBasedOnFilter(FullTextEntityManager fullTextEntityManager, SearchDataFilter filter) {
        switch (filter) {
            case KeywordSearchDataFilter keywordFilter:
                return getKeywordQueryOnField(fullTextEntityManager, keywordFilter.field(), keywordFilter.value());
            case IntRangeSearchDataFilter rangeFilter:
                return getIntRangeQueryOnField(fullTextEntityManager, rangeFilter.field(), rangeFilter.min(), rangeFilter.max());
            default:
                log.error("Error: Unknown search filter data!");
                return null;
        }

    }

    public Query getCombinedBoolQuery(List<Query> queries) {
        return queries.stream()
                .filter(Objects::nonNull)
                .map(query -> new BooleanClause(query, BooleanClause.Occur.MUST))
                .collect(BooleanQueryCollector.toBooleanQuery());
    }

    private Query getIntRangeQueryOnField(FullTextEntityManager fullTextEntityManager, String field, int min, int max) {
        QueryBuilder queryBuilder = getQueryBuilder(fullTextEntityManager);
        return queryBuilder
                .range()
                .onField(field)
                .from(min).to(max)
                .createQuery();
    }

    private Query getKeywordQueryOnField(FullTextEntityManager fullTextEntityManager, String field, String value) {
        QueryBuilder queryBuilder = getQueryBuilder(fullTextEntityManager);
        return queryBuilder
                .keyword()
                .onField(field)
                .matching(value)
                .createQuery();
    }

    private QueryBuilder getQueryBuilder(FullTextEntityManager fullTextEntityManager) {
        return fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Product.class)
                .get();
    }

    FullTextEntityManager getFullTextEntityManager() {
        return Search.getFullTextEntityManager(entityManager);
    }
}
