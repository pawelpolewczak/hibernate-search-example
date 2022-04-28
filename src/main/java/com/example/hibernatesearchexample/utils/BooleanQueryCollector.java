package com.example.hibernatesearchexample.utils;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class BooleanQueryCollector implements Collector<BooleanClause, BooleanQuery.Builder, BooleanQuery> {


    @Override
    public Supplier<BooleanQuery.Builder> supplier() {
        return BooleanQuery.Builder::new;
    }

    @Override
    public BiConsumer<BooleanQuery.Builder, BooleanClause> accumulator() {
        return BooleanQuery.Builder::add;
    }

    @Override
    public BinaryOperator<BooleanQuery.Builder> combiner() {
        return (left, right) -> left.add(right.build(), BooleanClause.Occur.MUST);
    }

    @Override
    public Function<BooleanQuery.Builder, BooleanQuery> finisher() {
        return BooleanQuery.Builder::build;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }

    public static BooleanQueryCollector toBooleanQuery() {
        return new BooleanQueryCollector();
    }
}
