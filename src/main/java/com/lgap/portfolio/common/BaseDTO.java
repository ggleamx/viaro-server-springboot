package com.lgap.portfolio.common;

public abstract class BaseDTO<T, D extends BaseDTO<T,D>> {
    public abstract D fromEntity(T entity);
}
