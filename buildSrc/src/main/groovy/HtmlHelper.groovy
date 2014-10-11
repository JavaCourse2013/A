class HtmlHelper {
    static String getDataUriScheme(String mimeType, InputStream is) {
        String encoded = is.bytes.encodeBase64().toString()
        return "data:$mimeType;base64,$encoded"
    }
}
