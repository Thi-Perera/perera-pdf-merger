package pdfperera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*


package pdfperera;
import java.io.File;
import java.io.IOException;


public class App {

    public static void main(String[] args) throws IOException {
        
// ----------------------------------------------------------------------------------------------------------------------
// ---------------------------------------inizio codice temporaneo--------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------------
        File pdf1 = new File("pdfexamples/dummy.pdf");
        File pdf2 = new File("pdfexamples/sample.pdf");
        //due stringhe di bytes momentanemente ad cazzum
        byte[] bytesPdf1 = PdfManipulator.pdfToBytes(pdf1);
        byte[] bytesPdf2 = PdfManipulator.pdfToBytes(pdf2);
    
        // Creazione di due istanze di PdfWrapper temporanee
        PdfWrapper wrapper1 = new PdfWrapper("dummy.pdf", bytesPdf1);
        PdfWrapper wrapper2 = new PdfWrapper("sample.pdf", bytesPdf2);

// ----------------------------------------------------------------------------------------------------------------------
// ----------------------------------------fine codice temporaneo--------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------------

        PdfWrapper outputPdfWrapper = PdfManipulator.mergePdf(wrapper1,wrapper2);
        PdfManipulator.bytesToPdf(outputPdfWrapper.getPdfContent(), "pdfexamples/" + outputPdfWrapper.getPdfName());
        System.out.println("I file sono stati uniti con successo!");
    }
} 



*/

@SpringBootApplication

public class LocalMergingApp {

    public static void main(String[] args) {
        SpringApplication.run(LocalMergingApp.class, args);
    }
    



}
