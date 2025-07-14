package de.comahe.i18n4k.messages.formatter

import de.comahe.i18n4k.Locale
import de.comahe.i18n4k.messages.formatter.parsing.MessageParser
import de.comahe.i18n4k.messages.formatter.parsing.MessagePart
import de.comahe.i18n4k.messages.formatter.types.MessageAttribSelectFormatter
import de.comahe.i18n4k.messages.formatter.types.MessageAttribValueFormatter
import de.comahe.i18n4k.messages.formatter.types.MessageDeclensionValueFormatter
import de.comahe.i18n4k.messages.formatter.types.MessageGenderSelectFormatter
import de.comahe.i18n4k.messages.formatter.types.MessageNumberFormatters
import de.comahe.i18n4k.messages.formatter.types.MessageSelectFormatter
import de.comahe.i18n4k.messages.formatter.types.MessageTransformFormatters
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import kotlinx.collections.immutable.persistentMapOf

/**
 * Default formatter for messages of `i18n4k`
 *
 * Format must be similar to the Java MessageFormat:
 *
 * MessageFormat uses patterns of the following form:
 * ```
 * MessageFormatPattern:
 *      String
 *      MessageFormatPattern FormatElement String
 *
 * FormatElement:
 *      { ArgumentIndex }
 *      { ArgumentIndex , FormatType }
 *      { ArgumentIndex , FormatType , FormatStyle }
 *
 * ArgumentIndex: argument with the given index in the parameter list.
 *                Zero-base (0 is the index of the first argument).
 *                Use "~" if the index is irrelevant
 *
 * FormatType: type to format, like number,date, etc. Defined be registered formatter
 *
 * FormatStyle: Specific style to the FormatType
 *      StylePartArgument
 *      StylePartMessage
 *      StylePartList
 *
 * StylePartArgument: A named argument for the style
 *     String
 *
 * StylePartMessage: A message text. Can also contain format pattern. So the patterns can be nested.
 *      {MessageFormatPattern}
 *
 * StylePartList: A list of style parts
 *      StylePartArgument
 *      StylePartMessage
 *      StylePartList [ StylePartArgument ]
 *      StylePartList [ StylePartMessage ]
 *
 * ```
 *
 * Within a String, a pair of single quotes can be used to quote any arbitrary characters except
 * single quotes. For example, pattern string "'{0}'" represents string "{0}", not a FormatElement.
 * A single quote itself must be represented by doubled single quotes '' throughout a String.
 * For example, pattern string "'{''}'" is interpreted as a sequence of '{ (start of quoting and
 * a left curly brace), '' (a single quote), and }' (a right curly brace and end of quoting),
 * not '{' and '}' (quoted left and right curly braces): representing string "{'}", not "{}".
 *
 * Any unmatched quote is treated as closed at the end of the given pattern. For example, pattern
 * string "'{0}" is treated as pattern "'{0}'".
 *
 * Any curly braces within an unquoted pattern must be balanced. For example, "ab {0} de" and "ab
 * '}' de" are valid patterns, but "ab {0'}' de", "ab } de" and "''{''" are not.
 *
 * The `FormatType` is evaluated by [MessageValueFormatter]. The [MessageValueFormatter] defines the
 * possible values of the `FormatStyle`.
 *
 * The following [MessageValueFormatter] are added by default
 * * [MessageNumberFormatters]
 */
object MessageFormatterDefault : MessageFormatter {

    private val messageFormatContext = atomic(
        MessageFormatContext(
            MessageNumberFormatters.all
                + MessageTransformFormatters.all
                + MessageSelectFormatter
                + MessageAttribValueFormatter
                + MessageAttribSelectFormatter
                + MessageGenderSelectFormatter
                + MessageDeclensionValueFormatter
        )
    )

    private val parsedMessageCache = atomic(persistentMapOf<String, MessagePart>())

    fun registerMessageValueFormatters(vararg f: MessageValueFormatter) {
        messageFormatContext.update { it.withMessageValueFormatters(*f) }
    }

    override fun format(message: String, parameters: MessageParameters, locale: Locale): String {
        return getMessagePartFor(message)
            .format(parameters, locale, messageFormatContext.value)
            .toString()
    }

    override fun getMessageParametersNames(message: String, locale: Locale): List<Pair<CharSequence, CharSequence?>> {
        val parameterNames = mutableListOf<Pair<CharSequence, CharSequence?>>()
        getMessagePartFor(message).fillInParameterNames(parameterNames)
        return parameterNames
    }

    private fun getMessagePartFor(message: String): MessagePart {
        var result: MessagePart? = parsedMessageCache.value[message]
        if (result != null)
            return result

        parsedMessageCache.update { cache ->
            var messagePart = cache[message]
            if (messagePart != null) {
                result = messagePart
                return@update cache
            }
            messagePart = MessageParser(message).parseMessage()
            result = messagePart
            return@update cache.put(message, messagePart)
        }
        return result!!
    }


}