package view;

/** ANSI color codes for console output. Works on Windows 10+, Linux, and macOS terminals. */
public class ConsoleColors {
  // Reset
  public static final String RESET = "\033[0m";

  // Regular Colors
  public static final String BLACK = "\033[0;30m";
  public static final String RED = "\033[0;31m";
  public static final String GREEN = "\033[0;32m";
  public static final String YELLOW = "\033[0;33m";
  public static final String BLUE = "\033[0;34m";
  public static final String MAGENTA = "\033[0;35m";
  public static final String CYAN = "\033[0;36m";
  public static final String WHITE = "\033[0;37m";

  // Bold
  public static final String BOLD = "\033[1m";
  public static final String BOLD_RED = "\033[1;31m";
  public static final String BOLD_GREEN = "\033[1;32m";
  public static final String BOLD_YELLOW = "\033[1;33m";
  public static final String BOLD_BLUE = "\033[1;34m";
  public static final String BOLD_MAGENTA = "\033[1;35m";
  public static final String BOLD_CYAN = "\033[1;36m";
  public static final String BOLD_WHITE = "\033[1;37m";

  // Background
  public static final String BG_BLACK = "\033[40m";
  public static final String BG_RED = "\033[41m";
  public static final String BG_GREEN = "\033[42m";
  public static final String BG_YELLOW = "\033[43m";
  public static final String BG_BLUE = "\033[44m";
  public static final String BG_MAGENTA = "\033[45m";
  public static final String BG_CYAN = "\033[46m";
  public static final String BG_WHITE = "\033[47m";

  // Utility methods
  public static String success(String text) {
    return BOLD_GREEN + text + RESET;
  }

  public static String error(String text) {
    return BOLD_RED + text + RESET;
  }

  public static String warning(String text) {
    return BOLD_YELLOW + text + RESET;
  }

  public static String info(String text) {
    return BOLD_CYAN + text + RESET;
  }

  public static String highlight(String text) {
    return BOLD_BLUE + text + RESET;
  }

  public static String title(String text) {
    return BOLD_MAGENTA + text + RESET;
  }

  private ConsoleColors() {
    throw new UnsupportedOperationException("Utility class");
  }
}
