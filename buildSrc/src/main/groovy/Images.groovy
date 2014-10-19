import org.apache.commons.io.FilenameUtils
import org.ho.yaml.Yaml

class Images extends AbstractMap<String, String> {
    private static final Map<String, String> mimeTypes = ['svg': 'image/svg+xml']

    private Map<String, String> imageMap

    Images(String baseDir) {
        Map<String, String> map = new HashMap<>()
        List<String> paths = Yaml.load(getClass().getClassLoader().getResourceAsStream("$baseDir/list.yaml"))

        paths.each { path ->
            String name = FilenameUtils.getBaseName(path)
            String ext = FilenameUtils.getExtension(path)
            InputStream is = getClass().getClassLoader().getResourceAsStream("$baseDir/$path")

            map.put(name, getDataUriScheme(mimeTypes[ext], is))
        }

        imageMap = Collections.unmodifiableMap(map)
    }

    @Override
    Set<Map.Entry<String, String>> entrySet() {
        return imageMap.entrySet()
    }

    private static String getDataUriScheme(String mimeType, InputStream is) {
        String encoded = is.bytes.encodeBase64().toString()
        return "data:$mimeType;base64,$encoded"
    }
}
