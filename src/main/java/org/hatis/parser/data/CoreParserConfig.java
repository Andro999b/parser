package org.hatis.parser.data;

import org.hatis.parser.analyzers.TokensAnalyzer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 *
 * Created by Andrey on 01.06.2015.
 */
public class CoreParserConfig {
    private TokensAnalyzer analyzer;

    //locales and formats
    private Locale locale = Locale.US;
    private NumberFormat outNumberFormat = NumberFormat.getNumberInstance(locale);
    private NumberFormat inNumberFormat = NumberFormat.getNumberInstance(locale);
    private Collection<ParserModule> modules = new ArrayList<>();

    public CoreParserConfig(TokensAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public TokensAnalyzer getAnalyzer() {
        return analyzer;
    }

    public Locale getLocale() {
        return locale;
    }

    /**
     * Этот метод также изменяет формат чисел
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
        this.outNumberFormat = NumberFormat.getNumberInstance(locale);
        this.inNumberFormat = NumberFormat.getNumberInstance(locale);
    }

    public NumberFormat getOutputNumberFormat() {
        return outNumberFormat;
    }

    public NumberFormat getInputNumberFormat() {
        return inNumberFormat;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.outNumberFormat = numberFormat;
        this.inNumberFormat = numberFormat;
    }

    public void setOutputNumberFormat(NumberFormat numberFormat) {
        this.outNumberFormat = numberFormat;
    }

    public void setInputNumberFormat(NumberFormat numberFormat) {
        this.inNumberFormat = numberFormat;
    }

    public void addModule(ParserModule module) {
        modules.add(module);
        module.modifyAnalyzer(analyzer);
    }

    public Collection<ParserModule> getModules() {
        return modules;
    }
}
