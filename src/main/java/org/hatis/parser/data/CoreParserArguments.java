package org.hatis.parser.data;

import org.hatis.parser.data.accessor.DataBinding;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrey on 01.06.2015.
 */
public class CoreParserArguments {
    protected boolean trimWhiteSpaces = true;
    protected DataAccessor dataObject;
    protected Map<String, Object> variables = new HashMap<>();

    public CoreParserArguments(CoreParserArguments arguments) {
        this.trimWhiteSpaces = arguments.trimWhiteSpaces;
        this.dataObject = arguments.dataObject;
        this.variables.putAll(arguments.getVariables());
    }

    public CoreParserArguments(DataAccessor dataObject) {
        this.dataObject = dataObject;
    }

    public CoreParserArguments() {
        this.dataObject = new DataBinding();
    }

    public CoreParserArguments(DataAccessor dataObject, boolean trimWhiteSpaces) {
        this.trimWhiteSpaces = trimWhiteSpaces;
        this.dataObject = dataObject;
    }

    public boolean isTrimWhiteSpaces() {
        return trimWhiteSpaces;
    }

    public DataAccessor getDataObject() {
        return dataObject;
    }

    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    public Map<String, Object> getVariables() {
        return variables;
    }
}
