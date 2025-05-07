package com.github.andercamargo.financial.entity.common.gateway;


public interface ICommonGateway<T> {
    T create(T entity);
    T update(T entity);
}