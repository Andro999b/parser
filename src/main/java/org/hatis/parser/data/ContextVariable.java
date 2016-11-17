package org.hatis.parser.data;

import org.hatis.parser.Context;

/**
 * Интерфейс для доступа к перменным внутри java кода из кода шаблона
 */
public interface ContextVariable {
    Object getValue(Context context);
}
