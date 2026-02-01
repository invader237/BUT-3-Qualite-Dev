package com.iut.banque.shared.auth.domain.entity;

@FunctionalInterface
public interface GetEntity<T> {
    T apply();
}
