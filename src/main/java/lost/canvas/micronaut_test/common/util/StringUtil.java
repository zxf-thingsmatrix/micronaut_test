package lost.canvas.micronaut_test.common.util;

public final class StringUtil {
    StringUtil() {
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //  non-blank
    ////////////////////////////////////////////////////////////////////////////////////////
    public boolean nonBlank(CharSequence charSequence) {
        if (Utils.empty.isEmpty(charSequence)) {
            return false;
        }

        int len = charSequence.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //  is-blank
    ////////////////////////////////////////////////////////////////////////////////////////
    public boolean isBlank(CharSequence charSequence) {
        return !nonBlank(charSequence);
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // if-blank-default
    ////////////////////////////////////////////////////////////////////////////////////////
    public CharSequence blankDefault(CharSequence charSequence, CharSequence defaultValue) {
        return isBlank(charSequence) ? defaultValue : charSequence;
    }

}
