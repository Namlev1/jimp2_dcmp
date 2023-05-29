package app;

import javafx.beans.binding.DoubleBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;

public class SceneController {
    private File encodedFile, codeFile, uncompressedFile;
    @FXML private Label encodedFileChooserLabel;
    @FXML private Button codeFileChooserButton;
    @FXML private Button encodedFileChooserButton;
    @FXML private Label codeFileChooserLabel;
    @FXML private Label errorLabel;
    @FXML private VBox mainVBox;
    @FXML private VBox codeTableContent;
    @FXML private ScrollPane codeTableScrollPane;
    @FXML private VBox codeTableVBox;
    @FXML private ComboBox modeSelector;
    @FXML
    private void handleFileChooserButtonClicked(ActionEvent clickedButton) {
        if (!(clickedButton.getSource() instanceof Button))
            return;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz skompresowany plik");
        fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.dir")+"/src/main/java/app")); // ustawiamy ścieżkę obecnego pliku jako domyślną ścieżkę
        String sourceName = ((Button)clickedButton.getSource()).getId();
        if (modeSelector.getValue().equals("Kompresor"))
            sourceName = "compressor";
        setExtensionFilter(fileChooser, sourceName/*zwraca String z nazwą klikniętego przycisku*/);
        File chosenFile = fileChooser.showOpenDialog(null);
        if (chosenFile == null)
            return;
        setCurrentFile(chosenFile, sourceName);
        setLabelText(chosenFile.getName(), sourceName);
        codeFileChooserLabel.getParent().requestLayout();
    }
    @FXML void handleModeChanged(ActionEvent elementCalling) {
        if (!(elementCalling.getSource() instanceof ComboBox))
            return;
        encodedFileChooserLabel.setText("");
        codeFileChooserLabel.setText("");
        switch ((String) modeSelector.getValue()) {
            case "Dekompresor":
                encodedFileChooserButton.setVisible(true);
                encodedFileChooserButton.setVisible(true);
                codeFileChooserButton.setText("Wybierz plik wejsciowy");

                return;
            case "Kompresor":
                encodedFileChooserButton.setVisible(false);
                encodedFileChooserButton.setVisible(false);
                codeFileChooserButton.setText("Wybierz nieskompresowany plik wejściowy");
        }
    }
    @FXML
    private void handleMainActionButtonClicked (ActionEvent elementCalling) throws IOException {
        if (!(elementCalling.getSource() instanceof Button))
            return;
        switch ((String) modeSelector.getValue()) {
            case "Dekompresor":
                handleDecompressRequest();
                return;
            case "Kompresor":
                handleCompressRequest();
        }
    }
    private void handleDecompressRequest() throws IOException {
        if (encodedFile == null || codeFile == null)
        {
            mainVBox.setStyle("-fx-background-color: transparent");
            errorLabel.setText("Wybierz pliki");
            errorLabel.setVisible(true);
            return;
        }
        errorLabel.setVisible(false);
        mainVBox.setStyle("-fx-background-color: #cccccc");
        DecompressorInitializer.initializeDecompressor(codeFile, encodedFile);
        displayCodeTable(DecompressorInitializer.dictionary);
        mainVBox.setStyle("-fx-background-color: white");
    }
    private void handleCompressRequest() {
        if (uncompressedFile == null)
        {
            mainVBox.setStyle("-fx-background-color: transparent");
            errorLabel.setText("Wybierz plik");
            errorLabel.setVisible(true);
            return;
        }
        errorLabel.setVisible(false);
        mainVBox.setStyle("-fx-background-color: #cccccc");
        // tutaj wywolywana jest funkcja uruchamiajaca dekompresor, np initializeDecompressor(File uncompressedFile)
        // displayCodeTable(DecompressorInitializer.dictionary); // można, nie trzeba, dodać opcję wyświetlania słownika po kompresji
        mainVBox.setStyle("-fx-background-color: white");
    }

    private void displayCodeTable(Dictionary dictionary) {
        codeTableVBox.setFillWidth(true);
        for (Symbol s : dictionary.getSymbols()) {
            if (!(s == null))
                codeTableVBox.getChildren().add(createTableHBox(String.valueOf(s.getCharacter()), s.getCodeInBinaryString()));
        }

        codeTableVBox.applyCss();
        codeTableVBox.layout();

        codeTableScrollPane.setContent(codeTableVBox);
        codeTableScrollPane.setFitToHeight(true);
        codeTableScrollPane.setFitToWidth(true);

        codeTableScrollPane.applyCss();
        codeTableScrollPane.layout();

        codeTableContent.setVisible(true);
    }

    private HBox createTableHBox(String character, String code) {
        HBox hbox = new HBox();
        DoubleBinding width = hbox.widthProperty().divide(2);
        hbox.getChildren().addAll(createTableLabel(character, width), createTableLabel(code, width));
        hbox.setStyle("-fx-border-color: black;");
        hbox.setAlignment(Pos.BASELINE_CENTER);
        return hbox;
    }
    private Label createTableLabel(String text, DoubleBinding width) {
        Label returnLabel = new Label(text);
        returnLabel.setStyle("-fx-font-size: 14px; -fx-text-alignment: CENTER; -fx-border-color: gray;");
        returnLabel.setAlignment(Pos.CENTER);
        returnLabel.prefWidthProperty().bind(width);
        return returnLabel;
    }
    private void setExtensionFilter(FileChooser fileChooser, String associatedButton)
    {
        fileChooser.setSelectedExtensionFilter(null); // resetujemy poprzednio ustawione filtry
        ExtensionFilter chosenExtensionFilter;
        switch (associatedButton)
        {
            case "codeFileChooserButton":
                chosenExtensionFilter = new ExtensionFilter("Słowniki kodów", "*.txt");
                break;
            case "encodedFileChooserButton":
                chosenExtensionFilter = new ExtensionFilter("Zakodowane pliki", "*.bin");
                break;
            case "compressor":
                chosenExtensionFilter = new ExtensionFilter("Wszystkie pliki", "*.*");
            default:
                chosenExtensionFilter = new ExtensionFilter("Wszystkie pliki", "*.*");
        }
        fileChooser.getExtensionFilters().add(chosenExtensionFilter);
    }
    private void setLabelText(String text, String associatedButton){
        switch (associatedButton){
            case "codeFileChooserButton":
            case "compressor":
                codeFileChooserLabel.setText(text);
                break;
            case "encodedFileChooserButton":
                encodedFileChooserLabel.setText(text);
                break;
        }
    }
    private void setCurrentFile(File chosenFile, String associatedButton){
        switch (associatedButton){
            case "codeFileChooserButton":
                codeFile = chosenFile;
                break;
            case "encodedFileChooserButton":
                encodedFile = chosenFile;
                break;
            case "compressor":
                uncompressedFile = chosenFile;
        }
    }
}
