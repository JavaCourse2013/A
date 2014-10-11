class KitoResolver implements Resolver {

    @Override
    File getJplDir(File baseDir, int ch, int ex) {
        if(ch < 7) {
            return new File(baseDir, String.format("JPL/ch%02d_%02d", ch, ex))
        } else {
            return new File(baseDir, String.format("JPL/ex%02d_%02d", ch, ex))
        }
    }

    @Override
    File getGuiDir(File baseDir, int ch, int ex) {
        return new File(baseDir, "src/GUI/ex${ch}_${ex}")
    }

    @Override
    File getInterpretDir(File baseDir) {
        return new File(baseDir, 'src/Interpret')
    }
}
