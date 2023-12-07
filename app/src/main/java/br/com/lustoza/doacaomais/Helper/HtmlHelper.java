package br.com.lustoza.doacaomais.Helper;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by ubuntu on 2/11/17.
 */
public class HtmlHelper {

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
