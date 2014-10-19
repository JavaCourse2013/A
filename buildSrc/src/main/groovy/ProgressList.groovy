import groovy.text.SimpleTemplateEngine

class ProgressList {
    File baseDir

    def jplList = [:]
    def guiList = [:]
    def interpretList = [:]

    Map<String, Double> summary = [:]
    String html

    ProgressList(File baseDir) {
        this.baseDir = baseDir
        updateList()
        updateSummary()
        updateHtml()
    }

    private void updateSummary() { // 0 ~ 1 int data[name]
        Config.TRAINEES.each { name ->
            def total = 0

            Config.JPL_EX.each { ch, exercises ->
                exercises.each { ex ->
                    if (jplList[name][ch][ex])
                        total++
                }
            }

            Config.GUI_EX.each { ch, exercises ->
                exercises.each { ex ->
                    if (guiList[name][ch][ex])
                        total++
                }
            }

            if (interpretList[name])
                total++

            summary[name] = total / Config.TOTAL
        }
    }

    private void updateList() {
        Config.TRAINEES.each { name ->
            File userDir = new File(baseDir, name)
            Resolver resolver = getResolver(name)

            jplList[name] = [:]
            Config.JPL_EX.each { ch, exercises ->
                jplList[name][ch] = [:]
                exercises.each { ex ->
                    jplList[name][ch][ex] = exists(resolver.getJplDir(userDir, ch, ex))
                }
            }

            guiList[name] = [:]
            Config.GUI_EX.each { ch, exercises ->
                guiList[name][ch] = [:]
                exercises.each { ex ->
                    guiList[name][ch][ex] = exists(resolver.getGuiDir(userDir, ch, ex))
                }
            }

            interpretList[name] = exists(resolver.getInterpretDir(userDir))
        }
    }

    private void updateHtml() {
        // Load HTML template & CSS from resources
        String templateText = getClass().getResource('list.html.template').text
        String cssText = getClass().getResource('style.css').text

        // Load emoji as Data URI Scheme from resources
        String iconBadge = HtmlHelper.getDataUriScheme('image/svg+xml', getClass().getResourceAsStream('java-coffee.svg'))
        String iconOk = HtmlHelper.getDataUriScheme('image/svg+xml', getClass().getResourceAsStream('coffee-bean.svg'))

        SimpleTemplateEngine engine = new SimpleTemplateEngine()
        Writable template = engine.createTemplate(templateText).make([
                CSS           : cssText,
                ICON_BADGE    : iconBadge,
                ICON_OK       : iconOk,
                TRAINEES      : Config.TRAINEES,
                JPL_EX        : Config.JPL_EX,
                GUI_EX        : Config.GUI_EX,
                SUMMARY       : summary,
                JPL_LIST      : jplList,
                GUI_LIST      : guiList,
                INTERPRET_LIST: interpretList
        ])

        html = template.toString()
    }

    private static Resolver getResolver(String name) {
        try {
            // Try to get the custom resolver
            Class<?> resolverClass = Class.forName("${name.capitalize()}Resolver")
            return resolverClass.newInstance()
        } catch (Exception e) {
            return new DefaultResolver()
        }
    }

    private static boolean exists(File file) {
        if (file.isFile())
            return true

        def files = file.list()
        return files != null && 1 <= files.length
    }
}