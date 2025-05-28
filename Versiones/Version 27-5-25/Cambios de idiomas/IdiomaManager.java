package messages;
// Esto es para guardar la elección de idioma q hicimos en inicio sesión
import java.util.Locale;

public class IdiomaManager {
    private static Locale currentLocale = new Locale("es", "ES"); // lo ponemos por defecto español

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        Messages.loadLocale(locale); // para actualizarlos
    }

    public static Locale getLocale() {
        return currentLocale;
    }
}
