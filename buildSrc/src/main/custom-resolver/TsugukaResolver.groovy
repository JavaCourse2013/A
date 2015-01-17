class TsugukaResolver extends DefaultResolver {

    @Override
    File resolveJplDir(File baseDir, int ch, int ex) {
        if (ch == 16) {
            return new File(baseDir, String.format("ex%02d_%02d", ch, ex))
        } else {
            return new File(baseDir, String.format("JPL/ch%02d/ex%<02d_%02d", ch, ex))
        }
    }

    @Override
    File resolveGuiDir(File baseDir, int ch, int ex) {
        return new File(baseDir, String.format("GUI/ex%02d_%02d", ch, ex))
    }

    @Override
    File resolveInterpretDir(File baseDir) {
        return new File(baseDir, 'Interpret')
    }
}
