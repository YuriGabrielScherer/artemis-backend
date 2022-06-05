package br.com.karate.service.abstracts;

public interface CrudAbstractService<T, Filter> extends AbstractService<T, Filter> {
    public boolean delete(long code);

    public T findByCode(long code);

    public T findByCodeThrowsException(long code);
}
