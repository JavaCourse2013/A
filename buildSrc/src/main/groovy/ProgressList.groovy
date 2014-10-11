import groovy.text.SimpleTemplateEngine

class ProgressList {
    File baseDir

    ProgressList(File baseDir) {
        this.baseDir = baseDir
    }

    def getJplList() { // boolean jplList[name][ch][ex]
        def data = [:]
        Config.TRAINEES.each { name ->
            File userDir = new File(baseDir, name)
            Resolver resolver = getResolver(name)
            data[name] = [:]
            Config.JPL_EX.each { ch, exercises ->
                data[name][ch] = [:]
                exercises.each { ex ->
                    data[name][ch][ex] = hasFile(resolver.getJplDir(userDir, ch, ex));
                }
            }
        }

        return data
    }

    def getGuiList() { // boolean guiList[name][ch][ex]
        def data = [:]
        Config.TRAINEES.each { name ->
            File userDir = new File(baseDir, name)
            Resolver resolver = getResolver(name)
            data[name] = [:]
            Config.JPL_EX.each { ch, exercises ->
                data[name][ch] = [:]
                exercises.each { ex ->
                    data[name][ch][ex] = hasFile(resolver.getGuiDir(userDir, ch, ex));
                }
            }
        }

        return data
    }

    def getInterpretList() {    // boolean interpretList[name]
        def data = [:]
        Config.TRAINEES.each { name ->
            File userDir = new File(baseDir, name)
            Resolver resolver = getResolver(name)
            data[name] = hasFile(resolver.getInterpretDir(userDir));
        }

        return data
    }

    String getHTML() {
        SimpleTemplateEngine engine = new SimpleTemplateEngine()
        String templateText = getClass().getResource('list.template').text
        Writable template = engine.createTemplate(templateText).make([
                TRAINEES      : Config.TRAINEES,
                JPL_EX        : Config.JPL_EX,
                GUI_EX        : Config.GUI_EX,
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
            // Return the default resolver
            return new DefaultResolver()
        }
    }

    static hasFile(File dir) {
        def files = dir.list()
        return files != null && 1 <= files.length
    }
}