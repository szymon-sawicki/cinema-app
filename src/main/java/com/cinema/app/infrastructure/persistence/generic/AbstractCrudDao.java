/*
package com.cinema.app.infrastructure.persistence.generic;

import com.cinema.app.infrastructure.persistence.exception.AbstractCrudDaoException;
import com.google.common.base.CaseFormat;
import org.atteo.evo.inflector.English;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public abstract class AbstractCrudDao<T, ID> implements CrudDao<T, ID> {

    protected final Jdbi jdbi;

    @SuppressWarnings("unchecked")
    private final Class<T> entityType = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];


    private String tableName() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, English.plural(entityType.getSimpleName()));
    }


    protected AbstractCrudDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }


    @Override
    public Optional<T> save(T t) {
        var sql = "insert into " + tableName() + " " + columnNamesForSave() + " values " + valuesForSave(t) + ";";
        var insertedCounter = jdbi.withHandle(handle -> handle.execute(sql));
        if (insertedCounter == 0) {
            throw new AbstractCrudDaoException("cannot insert data into db");
        }

        System.out.println("----------------------------------------------");
        System.out.println(Arrays.stream(entityType.getDeclaredFields()).map(Field::getName).toList());
        System.out.println("----------------------------------------------");
        System.out.println(tableName());

        return findNLastElements(insertedCounter)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<T> update(ID id, T t) {
        var updatedRows = jdbi.withHandle(handle -> handle
                .execute("update " + tableName() + " set " + columnsAndValuesForUpdate(t) + whereIdExpression(id)));
        if (updatedRows == 0) {
            throw new AbstractCrudDaoException("cannot find element with id = " + id);
        }

        return findById(id);
    }

    @Override
    public Optional<T> findById(ID id) {

        return jdbi.withHandle(handle -> handle
                .createQuery("select * from " + tableName() + " where id = :id")
                .bind("id", id)
                .mapToBean(entityType)
                .findFirst());
    }

    @Override
    public List<T> saveAll(List<T> items) {

        items.forEach(row -> {
            var sql = "insert into " + tableName() + " " + columnNamesForSave() + " values " + valuesForSave(row) + ";";
            int insertedCounter = jdbi.withHandle(handle -> handle.execute(sql));
            if (insertedCounter == 0) {
                throw new AbstractCrudDaoException("Cannot insert data to db");
            }
        });

        return items;
    }

    @Override
    public List<T> findAllById(List<ID> ids) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from " + tableName() + " where id in (<ids>)"))
                .bindList("ids", ids)
                .mapToBean(entityType)
                .list();
    }

    @Override
    public List<T> findAll() {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from " + tableName())
                .mapToBean(entityType)
                .list());
    }

    @Override
    public Optional<T> deleteById(ID id) {
        var itemToDelete = findById(id);
        var inserted = jdbi
                .withHandle(handle -> handle
                        .createUpdate("delete from " + tableName() + " where id = " + id)
                        .execute());
        if (inserted == 0) {
            throw new AbstractCrudDaoException("Cannot delete item with id " + id);
        }
        return itemToDelete;
    }

    @Override
    public List<T> deleteAllById(List<ID> ids) {
        var elementsToDelete = findAllById(ids);
        var result = jdbi.withHandle(handle ->
                handle
                        .createUpdate("delete from " + tableName() + " where id in (<ids)")
                        .bindList("ids", ids)
                        .execute()
        );
        if (result == 0) {
            throw new AbstractCrudDaoException("cannot find elements to delete");
        }
        return elementsToDelete;
    }

    private List<T> findNLastElements(int n) {
        var sql = "select * from " + tableName() + " order by id limit " + n;
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .mapToBean(entityType)
                .list());
    }

    private String columnNamesForSave() {
        try {
            return " ( " + Arrays.stream(entityType.getDeclaredFields())
                    //   .map(field -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()))
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
                    .map(field -> {
                        field.setAccessible(true);
                        try {
                            if (field.getType().equals(String.class) || field.getType().equals(LocalDate.class) || field.getType().isEnum()) {
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
                            if (field.getType().equals(String.class) || field.getType().equals(LocalDate.class)) {
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
*/
package com.cinema.app.infrastructure.persistence.generic;

import com.cinema.app.infrastructure.persistence.exception.AbstractCrudDaoException;
import com.google.common.base.CaseFormat;
import org.atteo.evo.inflector.English;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCrudDao<T, ID> implements CrudDao<T, ID> {

    protected final Jdbi jdbi;

    @SuppressWarnings("unchecked")
    private final Class<T> entityType = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private String tableName() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, English.plural(entityType.getSimpleName()));
    }

    protected AbstractCrudDao(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public Optional<T> save(T t) {
        var sql = "insert into " + tableName() + " " + columnNamesForSave() + " values " + valuesForSave(t) + ";";
        var insertedCounter = jdbi.withHandle(handle -> handle.execute(sql));
        if (insertedCounter == 0) {
            throw new AbstractCrudDaoException("Cannot insert data to db");
        }

       return findNLastElements(insertedCounter)
                .stream()
                .findFirst();
    }

    @Override
    public List<T> saveAll(List<T> items) {

        items.forEach(row ->{
            var sql = "insert into " + tableName() + " " + columnNamesForSave() + " values " + valuesForSave(row) + ";";
            int insertedCounter = jdbi.withHandle(handle -> handle.execute(sql));
            if (insertedCounter == 0) {
                throw new AbstractCrudDaoException("Cannot insert data to db");
            }} );

        return findNLastElements(items.size());

    }

    @Override
    public Optional<T> update(ID id, T t) {
        var updatedRows = jdbi.withHandle(handle -> handle
                .execute("update " + tableName() + " set " + columnAndValuesForUpdate(t) + whereIdExpression(id)));
        if (updatedRows == 0) {
            throw new AbstractCrudDaoException("Cannot update row with id " + id);
        }
        return findById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from " + tableName() + " where id = :id")
                .bind("id", id)
                .mapToBean(entityType)
                .findFirst());
    }

    @Override
    public List<T> findAllById(List<ID> ids) {
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from " + tableName() + " where id in (<ids>)")
                .bindList("ids", ids)
                .mapToBean(entityType)
                .list());
    }

    @Override
    public List<T> findAll() {

        System.out.println(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery("select * from " + tableName())
                .mapToBean(entityType)
                .list());
    }

    @Override
    public Optional<T> deleteById(ID id) {
        var itemToDelete = findById(id);
        var inserted = jdbi
                .withHandle(handle -> handle.createUpdate("delete from " + tableName() + " where id = " + id).execute());
        if (inserted == 0) {
            throw new AbstractCrudDaoException("Cannot delete item with id " + id);
        }
        return itemToDelete;
    }

    @Override
    public List<T> deleteAllById(List<ID> ids) {
        var elementsToDelete = findAllById(ids);
        var result = jdbi.withHandle(handle -> handle
                .createUpdate("delete from " + tableName() + " where id in (<ids>)")
                .bindList("ids", ids)
                .execute()
        );
        if (result == 0) {
            throw new AbstractCrudDaoException("cannot find elements to delete");
        }
        return elementsToDelete;
    }

    private List<T> findNLastElements(int n) {
        var sql = "select * from " + tableName() + " order by id DESC limit " + n;
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .mapToBean(entityType)
                .list());
    }

    private String columnNamesForSave() {
        try {
            return " ( " + Arrays
                    .stream(entityType.getDeclaredFields())
                    .filter(field -> !field.getName().equalsIgnoreCase("id"))
                    .map(field -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()))
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
                    .map(field -> {
                        field.setAccessible(true);
                        try {
                            if (field.getType().equals(String.class) ||
                                    field.getType().equals(LocalDateTime.class) || field.getType().equals(LocalDate.class)
                                    || field.getType().isEnum()) {
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

    private String columnAndValuesForUpdate(T t) {
        try {
            // insert into teams (name, points) values ('A', 10)
            // Interesuje mnie to: ('A', 10)
            return Arrays
                    .stream(entityType.getDeclaredFields())
                    .filter(field -> !field.getName().equalsIgnoreCase("id"))
                    .map(field -> {
                        field.setAccessible(true);
                        try {
                            if (field.getType().equals(String.class) || field.getType().equals(LocalDateTime.class) || field.getType().isEnum()) {
                                return field.getName() + " = '" + field.get(t) + "'";
                            }
                            return field.getName() + " = " + field.get(t).toString();
                        } catch (Exception ee) {
                            throw new AbstractCrudDaoException(ee.getMessage());
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
