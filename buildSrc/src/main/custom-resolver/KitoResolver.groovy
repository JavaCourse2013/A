class KitoResolver implements Resolver {

    @Override
    File resolveJplDir(File baseDir, int ch, int ex) {
        if(ch == 1) {
            return new File(baseDir, String.format("src/JPL/ch%02d/Ex%<02d_%02d.java", ch, ex))
        } else if(ch < 7) {
            return new File(baseDir, String.format("src/JPL/ch%02d_%02d", ch, ex))
        } else {
            return new File(baseDir, String.format("src/JPL/ex%02d_%02d", ch, ex))
        }
    }

    @Override
    File resolveGuiDir(File baseDir, int ch, int ex) {
        return new File(baseDir, "src/GUI/ex${ch}_${ex}")
    }

    @Override
    File resolveInterpretDir(File baseDir) {
        return new File(baseDir, 'src/Interpret')
    }
}
