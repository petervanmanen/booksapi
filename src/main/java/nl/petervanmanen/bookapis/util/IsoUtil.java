package nl.petervanmanen.bookapis.util;

import java.util.Locale;
import java.util.Set;

public class IsoUtil {
    private static final Set<String> ISO_LANGUAGES = Set.of(Locale.getISOLanguages());

    public static boolean validateValidIsoLanguage(String language) {
        return ISO_LANGUAGES.contains(language);
    }
}
