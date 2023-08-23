package nl.petervanmanen.bookapis.util;

import lombok.experimental.UtilityClass;

import java.util.Locale;
import java.util.Set;

@UtilityClass
public class IsoUtil {
    private static final Set<String> ISO_LANGUAGES = Set.of(Locale.getISOLanguages());

    public boolean validateValidIsoLanguage(String language) {
        return ISO_LANGUAGES.contains(language);
    }
}
