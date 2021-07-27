package com.cinema.app.infrastructure.dao.persistence;

import com.cinema.app.infrastructure.dao.persistence.exception.AbstractCrudDaoException;
import com.google.common.base.CaseFormat;
import org.atteo.evo.inflector.English;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCrudDao<T,ID> implements CrudDao<T,ID> {

    protected final Jdbi jdbi;

    @SuppressWarnings("unchecked")
    private final Class<T> entityType = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];



    private String tableName() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, English.plural(entityType.getSimpleName()));
    }



    protected AbstractCrudDao(Jdbi jdbi) { this.jdbi = jdbi; };

    @Override
    public Optional<T> save(T t) {
        return Optional.empty();
    }

    @Override
    public Optional<T> update(ID id, T t) {
        return Optional.empty();
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public List<T> saveAll(List<T> items) {
        return null;
    }

    @Override
    public List<T> findAllById(List<ID> ids) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public Optional<T> deleteById(ID id) {
        return Optional.empty();
    }

    @Override
    public List<T> deleteAllById(List<ID> ids) {
        return null;
    }

    private String columnNamesForSave() {
        try {
            return " ( " + Arrays
                    .stream(entityType.getDeclaredFields())
                    .map(Field::getName)
                    .filter(name -> !name.equalsIgnoreCase("id"))
                    .collect(Collectors.joining(", ")) + " ) ";
        } catch (Exception e) {
            throw new AbstractCrudDaoException(e.getMessage());
        }
    }

    private String valuesForSave(T t) {
        try {
            return " ( " + Arrays
                    .stream(entityType.getDeclaredFields())
                    .filter(field -> !field.getName().equalsIgnoreCase("id"))
                    .map(field ->{
                        field.setAccessible(true);
                        try {
                            if(field.getType().equals(String.class) || field.getType().equals(LocalDate.class)) {
                                return "'" + field.get(t) + "'";
                            }
                            return field.get(t).toString();
                        } catch (Exception ee) {
                            throw new AbstractCrudDaoException(ee.getMessage());
                        }
                    })
                    .collect(Collectors.joining(", ")) + " ) ";
        } catch (Exception e) {
            throw new AbstractCrudDaoException(e.getMessage());
        }
    }

    private String columnsAndValuesForUpdate(T t) {
        try {
            return Arrays.stream(entityType.getDeclaredFields())
                    .filter(field -> !field.getName().equalsIgnoreCase("id"))
                    .map(field -> {
                        field.setAccessible(true);
                        try {
                            if(field.getType().equals(String.class) || field.getType().equals(LocalDate.class)) {
                                return field.getName() + " = '" + field.get(t) + "'";
                            }
                            return field.getName() + " = " + field.get(t).toString();
                        } catch (Exception e) {
                            throw new AbstractCrudDaoException(e.getMessage());
                        }
                    })
                    .collect(Collectors.joining(", "));
        } catch (Exception e) {
            throw new AbstractCrudDaoException(e.getMessage());
        }
    }

    private String whereIdExpression(ID id) {
        try {
            return " where id = " + id;
        } catch (Exception e) {
            throw new AbstractCrudDaoException(e.getMessage());
        }
    }

}
