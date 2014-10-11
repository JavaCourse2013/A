import groovy.text.SimpleTemplateEngine

class ProgressList {
    File baseDir

    ProgressList(File baseDir) {
        this.baseDir = baseDir
    }

    def getSummary() { // 0 ~ 1 int data[name]
        def jplList = getJplList()
        def guiList = getGuiList()
        def interpretList = getInterpretList()

        def summary = [:]

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

        return summary
    }

    def getJplList() { // boolean data[name][ch][ex]
        def data = [:]
        Config.TRAINEES.each { name ->
            File userDir = new File(baseDir, name)
            Resolver resolver = getResolver(name)
            data[name] = [:]
            Config.JPL_EX.each { ch, exercises ->
                data[name][ch] = [:]
                exercises.each { ex ->
                    data[name][ch][ex] = exist(resolver.getJplDir(userDir, ch, ex))
                }
            }
        }

        return data
    }

    def getGuiList() { // boolean data[name][ch][ex]
        def data = [:]
        Config.TRAINEES.each { name ->
            File userDir = new File(baseDir, name)
            Resolver resolver = getResolver(name)
            data[name] = [:]
            Config.JPL_EX.each { ch, exercises ->
                data[name][ch] = [:]
                exercises.each { ex ->
                    data[name][ch][ex] = exist(resolver.getGuiDir(userDir, ch, ex))
                }
            }
        }

        return data
    }

    def getInterpretList() {    // data interpretList[name]
        def data = [:]
        Config.TRAINEES.each { name ->
            File userDir = new File(baseDir, name)
            Resolver resolver = getResolver(name)
            data[name] = exist(resolver.getInterpretDir(userDir))
        }

        return data
    }

    String getHTML() {
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
                SUMMARY       : getSummary(),
                JPL_LIST      : getJplList(),
                GUI_LIST      : getGuiList(),
                INTERPRET_LIST: getInterpretList()
        ])

        return template.toString()
    }

    static Resolver getResolver(String name) {
        try {
            // Try to get the custom resolver
            Class<?> resolverClass = Class.forName("${name.capitalize()}Resolver")
            return resolverClass.newInstance()
        } catch (Exception e) {
            return new DefaultResolver()
        }
    }

    static exist(File file) {
        if (file.isFile())
            return true

        def files = file.list()
        return files != null && 1 <= files.length
    }
}