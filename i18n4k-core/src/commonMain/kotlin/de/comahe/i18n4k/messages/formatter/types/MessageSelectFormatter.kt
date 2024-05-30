package de.comahe.i18n4k.messages.formatter.types

import de.comahe.i18n4k.Locale
import de.comahe.i18n4k.messages.formatter.MessageFormatContext
import de.comahe.i18n4k.messages.formatter.MessageParameters
import de.comahe.i18n4k.messages.formatter.MessageValueFormatter
import de.comahe.i18n4k.messages.formatter.parsing.MessagePart
import de.comahe.i18n4k.messages.formatter.parsing.StylePart
import de.comahe.i18n4k.messages.formatter.parsing.StylePartArgument
import de.comahe.i18n4k.messages.formatter.parsing.StylePartList
import de.comahe.i18n4k.messages.formatter.parsing.StylePartMessage
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import kotlinx.collections.immutable.persistentMapOf

/**
 * Select a text value based on the value of the parameter.
 *
 * Format:
 *
 *     { PARAMETER_NUMBER, select, VALUE1  {TEXT1} VALUE2 VALUE3 {TEXT2} /VALUE_REGEX/ {TEXT3} other {OTHERWISE_TEXT} }
 * - `PARAMETER_NUMBER`:
 *    - Number of the parameter which value is matched against the values of the select list
 * - `VALUE*`:
 *    - If a values matches the value of the parameter, the corresponding text (TEXT*) is selected
 *    - List of several values for the same text can be listed before the next text element
 * - `VALUE_REGEX`
 *    - When the value is prefixed and suffixed by "/", it is seen as regular repression, e.g.
 *      `/([A-Z])\w+/`
 *    - If the regex matches the value of the parameter, the corresponding text (TEXT3) is
 *      selected
 * - `TEXT*`:
 *    - Text that is returned by the pattern if the value matches the value of the parameter
 *    - Texts themselves can also contain patterns. So, the patterns can be nested.
 * - `OTHERWISE_TEXT`
 *    - If none of the values before matched, this text will be selected
 *    - If there is no OTHERWISE_TEXT specified and no value matches, an empty string is returned
 *
 * Values and texts are trimmed (leading and tailing whitespaces are removed)
 *
 * Example:
 *
 *     {0} has forgotten {1, select, female: her | his } {3, select, one: bag | {2} bags}.
 *
 * Usage:
 *
 *     FORGOTTEN_BAG("Peter", "male", 1, "one")
 *     -> Peter has forgotten his bag.
 *     FORGOTTEN_BAG("Mary", "female", 2, "few")
 *     -> Mary has forgotten her 2 bags.
 */
object MessageSelectFormatter : MessageValueFormatter {

    private const val OTHER_VALUE = "other"

    private const val REGEX_PREFIX = "/"
    private const val REGEX_SUFFIX = "/"

    private val regexCache = atomic(persistentMapOf<CharSequence, Regex>())

    override val typeId: String
        get() = "select"

    override fun format(
        result: StringBuilder,
        value: Any?,
        typeId: CharSequence,
        style: StylePart?,
        parameters: MessageParameters,
        locale: Locale,
        context: MessageFormatContext
    ) {
        if (style == null)
            return

        val messagePart: MessagePart? = getMessagePartForMatchingStyle(value, style)

        messagePart?.format(result, parameters, locale, context)
    }

    private fun getMessagePartForMatchingStyle(value: Any?, style: StylePart): MessagePart? {
        if (style is StylePartMessage)
            return style.messagePart

        var foundMatch = false
        if (style is StylePartList) {
            for (subStyle in style.list) {
                if (subStyle is StylePartArgument && valueMatches(value, subStyle.value))
                    foundMatch = true
                if (subStyle is StylePartMessage && foundMatch)
                    return subStyle.messagePart
            }
        }
        return null
    }

    private fun valueMatches(value: Any?, name: CharSequence): Boolean {
        if (OTHER_VALUE == name)
            return true

        //TODO check number for >,<,=

        val valueString: CharSequence = value as? CharSequence
            ?: value?.toString()
            ?: return false

        if (name.startsWith(REGEX_PREFIX) && name.endsWith(REGEX_SUFFIX)) {
            val regex = getRegex(
                name.subSequence(REGEX_PREFIX.length, name.length - REGEX_SUFFIX.length)
            )
            return regex.matches(valueString)
        }
        return valueString == name
    }

    private fun getRegex(text: CharSequence): Regex {
        var regex = regexCache.value[text]
        if (regex != null)
            return regex
        regexCache.update {
            var regexLocal = it[text]
            if (regexLocal != null) {
                regex = regexLocal
                return@update it
            }
            regexLocal = Regex(text.toString())
            regex = regexLocal
            return@update it.put(text, regexLocal)
        }
        return regex!!
    }
}