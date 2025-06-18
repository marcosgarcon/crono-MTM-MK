// src/module-info.java
module com.mkcalculadora {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; // Necessário para a classe Image
    requires javafx.media; // Necessário para o MediaPlayer e MediaView

    // Se usar Apache POI e OpenPDF, você precisará adicionar requires estáticos
    // requires org.apache.poi.poi;
    // requires org.apache.poi.ooxml;
    // requires com.lowagie.text; // ou similar para OpenPDF

    opens com.mkcalculadora to javafx.fxml, javafx.graphics, javafx.controls, javafx.media; // Abre o pacote para JavaFX
    exports com.mkcalculadora; // Exporta o pacote para ser usado por outras classes
}
