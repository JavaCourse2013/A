class SugimotoResolver implements Resolver {

    @Override
    File getJplDir(File baseDir, int ch, int ex) {
        return new File(baseDir, String.format("src/JPL/ch%02d/ex%<02d_%02d", ch, ex))
    }

    @Override
    File getGuiDir(File baseDir, int ch, int ex) {
        return new File(baseDir, "src/GUI/exercise${ch}_${ex}")
    }

    @Override
    File getInterpretDir(File baseDir) {
        return new File(baseDir, 'src/Interpret')
    }
}
