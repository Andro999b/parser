package org.hatis.parser.analyzers;

import org.hatis.parser.expressions.Expression;

/**
 * Created by andro on 24.12.15.
 */
public interface ExpressionSupplier<T extends Expression> {
    T get();
}
