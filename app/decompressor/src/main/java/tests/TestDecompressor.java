package tests;

import app.*;

import java.io.*;
import java.util.Scanner;

public class TestDecompressor {
    public TestDecompressor(int fileLength) throws IOException {
        String inputFile = generateRandomFile(fileLength); //stworzenie losowego pliku
        compressor(inputFile); //kompresja stworzonego pliku

        //Dekompresor
        Dictionary dictionary = new Dictionary();
        File dictFile = new File("tests/code.txt");
        File binFile = new File("tests/output.bin");
        try{
            dictionary.readCodes(dictFile);
            FileDecompressor decompressor = new FileDecompressor(dictionary);
            decompressor.decompress(binFile);
        } catch(Exception e) {
            e.printStackTrace();
        }

        //pokazanie zawartosci plikow przed i po kompresji i dekompresji
        checkFiles("tests/" + inputFile, "output.txt");

    }

    public static void compressor(String inputFilePath) {
        try {
            //mozliwe ze trzeba u was zmienic sciezke w cd
            String executeCompressor = "cd tests & compress.exe -i " + inputFilePath + " -o output.bin";

            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd", "/c", executeCompressor);

            Process compress = processBuilder.start(); //uruchomienie pliku exe z kompresja
            compress.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateRandomFile(int fileLenght) throws IOException {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz"; //62 znaki

        StringBuilder generatedFileContent = new StringBuilder();

        for(int i=0; i<fileLenght; i++) {
            int index = (int)(Math.random() * 61); //wylosuj znak do dodania do pliku
            generatedFileContent.append(characters.charAt(index));
        }

        String fileName = "testinputfile" + fileLenght + ".txt"; //utworz plik

        FileWriter writer = new FileWriter("tests/" + fileName);

        writer.write(generatedFileContent.toString()); //zapisz string do pliku

        writer.close();

        return fileName;
    }

    private void checkFiles(String basicFilePath, String decompressedFilePath) throws FileNotFoundException {
        Scanner basicFileScanner = new Scanner(new File(basicFilePath));
        Scanner decompressedFileScanner = new Scanner(new File(decompressedFilePath));

        StringBuilder basicFileContent = new StringBuilder();
        StringBuilder decompressedFileContent = new StringBuilder();

        //wypisanie tresci obu plikow
        while (basicFileScanner.hasNextLine())
        {
            basicFileContent.append(basicFileScanner.nextLine());
        }
        while (decompressedFileScanner.hasNextLine())
        {
            decompressedFileContent.append(decompressedFileScanner.nextLine());
        }
        System.out.println();
        System.out.println("Plik przed kompresją:");
        System.out.println(basicFileContent);
        System.out.println("Plik po dekompresji:");
        System.out.println(decompressedFileContent);
        System.out.println();

        //porownanie obu plikow
        int differentCharacters = 0;

        for(int i=0; i<basicFileContent.length(); i++) {
            if(basicFileContent.charAt(i) != decompressedFileContent.charAt(i)) {
                differentCharacters++;
            }
        }

        System.out.println("Pliki roznia sie " + differentCharacters + " znakami");
    }
}