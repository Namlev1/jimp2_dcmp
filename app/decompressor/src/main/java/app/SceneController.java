package app;

import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;
import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class SceneController {
    private File encodedFile, codeFile;
    @FXML private Label encodedFileChooserLabel;
    @FXML private Label codeFileChooserLabel;
    @FXML private Label errorLabel;
    @FXML private VBox mainVBox;
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
    private void handleDecompressButtonPressed(ActionEvent elementCalling)
    {
        if (!(elementCalling.getSource() instanceof Button))
            return;
        if (encodedFile == null || codeFile == null)
        {
            mainVBox.setStyle("-fx-background-color: #transparent");
            errorLabel.setText("Wybierz pliki");
            errorLabel.setVisible(true);
            return;
        }
        errorLabel.setVisible(false);
        mainVBox.setStyle("-fx-background-color: #cccccc");
        DecompressorInitializer.initializeDecompressor(codeFile, encodedFile);
        mainVBox.setStyle("-fx-background-color: white");
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