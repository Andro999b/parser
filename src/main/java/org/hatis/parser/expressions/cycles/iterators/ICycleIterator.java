package org.hatis.parser.expressions.cycles.iterators;

import java.util.Iterator;

/**
 * Общий интерфейс итератора цилка парсера
 *
 * @author andrey
 *
 * @param <E>
 */
public interface ICycleIterator<E> extends Iterator<E> {
    String key();
    Object item();
    int index();
    IteratorState getState();
    int size();
}
