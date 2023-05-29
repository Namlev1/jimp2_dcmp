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
    private File encodedFile, codeFile;
    @FXML private Label encodedFileChooserLabel;
    @FXML private Label codeFileChooserLabel;
    @FXML private Label errorLabel;
    @FXML private VBox mainVBox;
    @FXML private VBox codeTableContent;
    @FXML private ScrollPane codeTableScrollPane;
    @FXML private VBox codeTableVBox;
    @FXML
    private void handleFileChooserButtonClicked(ActionEvent clickedButton) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz skompresowany plik");
        fileChooser.setInitialDirectory(new java.io.File(System.getProperty("user.dir")+"/src/main/java/app")); // ustawiamy ścieżkę obecnego pliku jako domyślną ścieżkę
        Object source = clickedButton.getSource();
        if (source instanceof Button)
            setExtensionFilter(fileChooser, ((Button)source)/*zwraca String z nazwą klikniętego przycisku*/);
        else
            throw new IOException("niepoprawnie wywołana metoda");
        File chosenFile = fileChooser.showOpenDialog(null);
        if (chosenFile == null)
            return;
        setCurrentFile(chosenFile, (Button)source);
        setLabelText(chosenFile.getName(), (Button)source);
        codeFileChooserLabel.getParent().requestLayout();
    }
    @FXML
    private void handleDecompressButtonPressed(ActionEvent elementCalling) throws IOException {
        if (!(elementCalling.getSource() instanceof Button))
            return;
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
    private void setExtensionFilter(FileChooser fileChooser, Button associatedButton)
    {
        fileChooser.setSelectedExtensionFilter(null); // resetujemy poprzednio ustawione filtry
        ExtensionFilter chosenExtensionFilter;
        switch (associatedButton.getId())
        {
            case "codeFileChooserButton":
                chosenExtensionFilter = new ExtensionFilter("Słowniki kodów", "*.txt");
                break;
            case "encodedFileChooserButton":
                chosenExtensionFilter = new ExtensionFilter("Zakodowane pliki", "*.bin");
                break;
            default:
                chosenExtensionFilter = new ExtensionFilter("Wszystkie pliki", "*.*");
        }
        fileChooser.getExtensionFilters().add(chosenExtensionFilter);
    }
    private void setLabelText(String text, Button associatedButton){
        switch (associatedButton.getId()){
            case "codeFileChooserButton":
                codeFileChooserLabel.setText(text);
                break;
            case "encodedFileChooserButton":
                encodedFileChooserLabel.setText(text);
                break;
        }
    }
    private void setCurrentFile(File chosenFile, Button associatedButton){
        switch (associatedButton.getId()){
            case "codeFileChooserButton":
                codeFile = chosenFile;
                break;
            case "encodedFileChooserButton":
                encodedFile = chosenFile;
                break;
        }
    }
}
