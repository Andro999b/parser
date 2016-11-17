package org.hatis.parser.data;

/**
 * Created by Andrey on 31.05.2015.
 */
public interface DataAccessor {
    Object getValue(Object...keys) throws Exception;
}
