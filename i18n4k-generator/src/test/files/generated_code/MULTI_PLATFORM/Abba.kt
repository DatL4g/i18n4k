import de.comahe.i18n4k.Locale
import de.comahe.i18n4k.messages.MessageBundle
import de.comahe.i18n4k.messages.providers.MessagesProvider
import de.comahe.i18n4k.strings.LocalizedString
import de.comahe.i18n4k.strings.LocalizedStringFactory1
import de.comahe.i18n4k.strings.LocalizedStringFactory2
import de.comahe.i18n4k.strings.LocalizedStringFactory3
import de.comahe.i18n4k.strings.LocalizedStringFactory4
import de.comahe.i18n4k.strings.LocalizedStringFactory5
import kotlin.Array
import kotlin.Int
import kotlin.String
import kotlin.jvm.JvmStatic

/**
 * Massage constants for bundle 'Abba'. Generated by i18n4k.
 */
public object Abba : MessageBundle() {
  /**
   * Number
   */
  @JvmStatic
  public val _1_number_key: LocalizedString = getLocalizedString0(0)

  /**
   * Hello, World
   */
  @JvmStatic
  public val Hello_World_: LocalizedString = getLocalizedString0(1)

  /**
   * I ^
   * ^have two
   * circumflexes
   * in the text
   */
  @JvmStatic
  public val Multi_Line_Value: LocalizedString = getLocalizedString0(2)

  /**
   * Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Phasellus faucibus molestie nisl.
   * Mauris suscipit, ligula sit amet pharetra semper, nibh ante cursus purus, vel sagittis velit
   * mauris vel metus. Pellentesque ipsum. Integer malesuada. Fusce wisi. Nullam dapibus fermentum
   * ipsum. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
   * commodo consequat. Integer rutrum, orci vestibulum ullamcorper ultricies, lacus quam ultricies
   * odio, vitae placerat pede sem sit amet enim. Quisque tincidunt scelerisque libero. Etiam posuere
   * lacus quis dolor. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet
   * sapien wisi sed libero. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet,
   * consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore
   * magnam aliquam quaerat voluptatem. Pellentesque sapien.
   */
  @JvmStatic
  public val longTextExample: LocalizedStringFactory1 = getLocalizedStringFactory1(3)

  /**
   * 3rd value: {2}
   */
  @JvmStatic
  public val max_param_3: LocalizedStringFactory3 = getLocalizedStringFactory3(4)

  /**
   * Not all have translations
   */
  @JvmStatic
  public val no_all_have_translations: LocalizedStringFactory1 = getLocalizedStringFactory1(5)

  /**
   * Parameter 1: {0}
   */
  @JvmStatic
  public val param_1: LocalizedStringFactory1 = getLocalizedStringFactory1(6)

  /**
   * Parameter 2: {0},{1}
   */
  @JvmStatic
  public val param_2: LocalizedStringFactory2 = getLocalizedStringFactory2(7)

  /**
   * Parameter 3: {0},{1},{2}
   */
  @JvmStatic
  public val param_3: LocalizedStringFactory3 = getLocalizedStringFactory3(8)

  /**
   * Parameter 4: {0},{1},{2},{3}
   */
  @JvmStatic
  public val param_4: LocalizedStringFactory4 = getLocalizedStringFactory4(9)

  /**
   * Parameter 5: {0},{1},{2},{3},{4}
   */
  @JvmStatic
  public val param_5: LocalizedStringFactory5 = getLocalizedStringFactory5(10)

  /**
   * Extraterrestrial 👽
   */
  @JvmStatic
  public val utf_8_value: LocalizedString = getLocalizedString0(11)

  /**
   * Special key
   */
  @JvmStatic
  public val _Specail_Key_: LocalizedString = getLocalizedString0(12)

  init {
    registerTranslation(Abba_en)
    registerTranslation(Abba_de)
  }
}

/**
 * Translation of message bundle 'Abba' for locale 'en'. Generated by i18n4k.
 */
private object Abba_en : MessagesProvider {
  @JvmStatic
  private val _data: Array<String?> = arrayOf(
      "Number",
      "Hello, World",
      """
      |I ^
      |^have two
      |circumflexes
      |in the text
      """.trimMargin(),
      "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Phasellus faucibus molestie nisl. Mauris suscipit, ligula sit amet pharetra semper, nibh ante cursus purus, vel sagittis velit mauris vel metus. Pellentesque ipsum. Integer malesuada. Fusce wisi. Nullam dapibus fermentum ipsum. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Integer rutrum, orci vestibulum ullamcorper ultricies, lacus quam ultricies odio, vitae placerat pede sem sit amet enim. Quisque tincidunt scelerisque libero. Etiam posuere lacus quis dolor. In laoreet, magna id viverra tincidunt, sem odio bibendum justo, vel imperdiet sapien wisi sed libero. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Pellentesque sapien.",
      "3rd value: {2}",
      "Not all have translations",
      "Parameter 1: {0}",
      "Parameter 2: {0},{1}",
      "Parameter 3: {0},{1},{2}",
      "Parameter 4: {0},{1},{2},{3}",
      "Parameter 5: {0},{1},{2},{3},{4}",
      "Extraterrestrial 👽",
      "Special key")

  public override val locale: Locale = Locale("en")

  public override val size: Int
    get() = _data.size

  public override fun `get`(index: Int): String? = _data[index]
}

/**
 * Translation of message bundle 'Abba' for locale 'de'. Generated by i18n4k.
 */
private object Abba_de : MessagesProvider {
  @JvmStatic
  private val _data: Array<String?> = arrayOf(
      "Nummer",
      "Hallo, Welt!",
      """
      |Ich ^
      |^ habe zwei
      | Zirkumflex
      |im Text
      """.trimMargin(),
      null,
      "3. Wert: {2}",
      "",
      "Parameter 1: {0}",
      "Parameter 2: {0},{1}",
      "Parameter 3: {0},{1},{2}",
      "Parameter 4: {0},{1},{2},{3},",
      "Parameter 5: {0},{1},{2},{3},{4}",
      "Außerirdischer 👽",
      "Besonderer Schlüssel")

  public override val locale: Locale = Locale("de")

  public override val size: Int
    get() = _data.size

  public override fun `get`(index: Int): String? = _data[index]
}
