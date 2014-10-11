class KatoResolver implements Resolver {

    @Override
    File getJplDir(File baseDir, int ch, int ex) {
        return new File(baseDir, String.format("src/local/JPL/ch%02d/ex%<02d_%02d", ch, ex))
    }

    @Override
    File getGuiDir(File baseDir, int ch, int ex) {
        return new File(baseDir, "src/local/GUI/ex${ch}_${ex}")
    }

    @Override
    File getInterpretDir(File baseDir) {
        return new File(baseDir, 'src/local/Interpret')
    }
}
