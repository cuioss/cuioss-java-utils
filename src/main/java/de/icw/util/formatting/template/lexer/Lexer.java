package de.icw.util.formatting.template.lexer;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import de.icw.util.formatting.template.FormatterSupport;
import de.icw.util.formatting.template.token.Token;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Functionality of scanning plain text and split this to tokens
 *
 * @param <T> bounds lexer to one type to avoid cross using accidentally
 * @author Eugen Fischer
 */
@ToString
@EqualsAndHashCode
public abstract class Lexer<T extends FormatterSupport> implements Serializable {

    private static final long serialVersionUID = 8645233576605974741L;

    private final List<String> tokenList;

    /**
     * Constructor of Lexer.</br>
     * Source provide information of "tokens" which he supports.
     * Therefore {@link FormatterSupport#getSupportedPropertyNames()} will be used.
     *
     * @param source must not be null
     * @throws NullPointerException if source is missing
     * @throws IllegalArgumentException if attribute is null or empty
     */
    public Lexer(final T source) {
        checkNotNull(source, "Source must not be null");
        this.tokenList = Lists.newArrayList(source.getSupportedPropertyNames());
        for (final String attribute : this.tokenList) {
            checkArgument(!isNullOrEmpty(attribute), "Attributes must not be null or empty. '"
                    + this.tokenList.toString() + "'");
        }
    }

    /**
     * @return the tokenList
     */
    protected final List<String> getTokenList() {
        return this.tokenList;
    }

    /**
     * Throw IllegalArgumentException with information about wrong token and supported tokens
     *
     * @param wrongToken
     * @param allowedTokens
     */
    protected static final void throwUnsupportedTokenException(final String wrongToken,
            final List<String> allowedTokens) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Unsupported token '").append(wrongToken).append("' was detected.\n")
                .append("Allowed are :\n");
        for (final String allowedToken : allowedTokens) {
            builder.append(" - ").append(allowedToken).append("\n");
        }
        throw new IllegalArgumentException(builder.toString());
    }

    /**
     * Parse template into Token List according attribute list
     *
     * @param input template string
     * @return created list of token, list could be empty if input template is null or empty
     * @throws IllegalArgumentException if template include unknown token, or doesn't fit the rules
     */
    public abstract List<Token> scan(final String input);

    /**
     * Validate template by scan this
     *
     * @param input to be validated
     */
    public final void validateTemplate(final String input) {
        scan(input);
    }

    /**
     * Supported expression language
     */
    public enum ExpressionLanguage {
        /** [attribute1][attribute2]..[attribute n] */
        SIMPLE_SQUARED_BRACKTES,
        /** {attribute1}{attribute2}..{attribute n} */
        SIMPLE_CURLY_BRACKETS,
        /** <attribute1><attribute2>..<attribute n> */
        SIMPLE_ANGLE_BRACKET,
        /**
         * usage of String Template Expression Language
         *
         * @see <a
         *      href="http://www.antlr.org/wiki/display/ST/StringTemplate+3+Documentation">
         *      Documentation</a>
         */
        STEL
    }

}
