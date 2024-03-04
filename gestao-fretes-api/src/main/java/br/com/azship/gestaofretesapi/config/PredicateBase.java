package br.com.azship.gestaofretesapi.config;

import com.querydsl.core.BooleanBuilder;
public class PredicateBase {

    public BooleanBuilder builder;

    public PredicateBase() {
        this.builder = new BooleanBuilder();
    }

    public BooleanBuilder build() {
        return this.builder;
    }
}
