package app;

public class CompressorInitializer {
    public static void initializeCompressor(String inputFilePath) {
        try {
            //mozliwe ze trzeba u was zmienic sciezke w cd
            String executeCompressor = "cd src/main/java/app & compress.exe -i " + inputFilePath + " -o output.bin";

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd", "/c", executeCompressor);

            Process compress = processBuilder.start(); //uruchomienie pliku exe z kompresja
            compress.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
