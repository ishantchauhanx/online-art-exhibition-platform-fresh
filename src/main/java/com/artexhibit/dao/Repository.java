package com.artexhibit.dao;

import com.artexhibit.exception.DatabaseException;

import java.util.List;

/**
 * Generic repository contract shared by database access classes.
 *
 * @param <T> model type returned by the repository
 */
public interface Repository<T> {
    List<T> findAll() throws DatabaseException;
}
