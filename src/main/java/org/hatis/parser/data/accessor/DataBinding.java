package org.hatis.parser.data.accessor;

import org.hatis.parser.data.DataAccessor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey on 04.06.2015.
 */
public class DataBinding implements DataAccessor {
    protected Map<String, Object> keyValues = new HashMap<>();

    public DataBinding() {}

    public DataBinding(Map<String, ?> keyValues) {
        this.keyValues.putAll(keyValues);
    }

    public void setValue(String key, Object value){
        keyValues.put(key, value);
    }

    public void setValue(String[] keys, Object value){
        keyValues.put(StringUtils.join(keys, '.'), value);
    }

    @Override
    public Object getValue(Object...keys) {
        return keyValues.get(StringUtils.join(keys, '.'));
    }
}
