package pdfperera;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.springframework.util.Assert;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
import org.apache.pdfbox.preflight.parser.PreflightParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.apache.pdfbox.preflight.PreflightDocument;
import org.apache.pdfbox.preflight.ValidationResult;
import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
import org.apache.pdfbox.preflight.parser.PreflightParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import ch.qos.logback.core.util.Loader;

public class PdfManipulator {


    public static File bytesToPdf(byte[] byteData, String destinationPath) throws IOException {
        
        // scrivo i Bytes in un File pdf sul disco
        try (FileOutputStream fos = new FileOutputStream(destinationPath)) {
            fos.write(byteData);
            //fos.close // no need, try-with-resources auto close
        }
        File PdfFile = new File(destinationPath);

        return PdfFile;
    }


    public static byte[] pdfToBytes(File file) throws IOException {
        Path path = file.toPath();
        byte[] fileBytes = Files.readAllBytes(path);
        return fileBytes;
    }

    public static PdfWrapper mergePdf(PdfWrapper[] pdfwrapperArray) throws IOException {
        // Creazione di un'istanza di PDFMergerUtility
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
    
        // Configurazione dell'output come ByteArrayOutputStream
        ByteArrayOutputStream mergedOutputStream = new ByteArrayOutputStream();
        pdfMerger.setDestinationStream(mergedOutputStream);
    
        // Aggiunta dei file PDF da unire
        for (PdfWrapper pdf : pdfwrapperArray) {
            pdfMerger.addSource(new ByteArrayInputStream(pdf.getPdfContent()));
        }
    
        // Unione dei file
        pdfMerger.mergeDocuments(null);
    
        
        //nome del file output post merge
        String mergedPdfName = "";
        for (PdfWrapper pdf : pdfwrapperArray) {
            String name = pdf.getPdfName();
            if (name.toLowerCase().endsWith(".pdf")) {
                name = name.substring(0, name.length() - 4); //rimuovo ".pdf" dalla fine
            }
            mergedPdfName += "+" + name;
        }
        mergedPdfName = mergedPdfName.substring(1); //rimuovo il primo char "+".
        mergedPdfName += ".pdf";


        // Creazione del nuovo PdfWrapper con i byte stream del merge
        byte[] mergedPdfBytes = mergedOutputStream.toByteArray();
        PdfWrapper mergedPdfWrapper = new PdfWrapper(mergedPdfName, mergedPdfBytes);
        
        return mergedPdfWrapper;
    }




    public static byte[] b64ToBytes(String base64String) throws IOException {
            // convert base64 to Bytes
            byte[] decodedData = Base64.getDecoder().decode(base64String);

            return decodedData;
    }

    


    public static String bytesToB64(byte[] pdfContent) throws IOException {

            // Codificare l'array di byte come una stringa Base64
            String base64String = Base64.getEncoder().encodeToString(pdfContent);

            return base64String;
    }




    public static String CheckIsPDF(byte[] data)
    {
        // Verifica se il parametro 'data' è null e, in tal caso, genera un'eccezione 'ArgumentNullException'
        if ((data == null))
        {
           return "argumentnull exception";
        }

        // Verifica se il parametro 'data' è troppo corto (ha meno di 5 byte, ovvero un pdf corrotto) o se il suo header
        // non è quello previsto e, in tal caso, genera un'eccezione 'InvalidFileFormatException'
        if (data.length < 5 || data[0] != 0x25 || data[1] != 0x50 || data[2] != 0x44 || data[3] != 0x46 || data[4] != 0x2D)
        {
            return "Invalid file header";
        }

        // Calcola la versione del file sulla base dei byte 5 e 6 del parametro 'data' (devono contenere rispettivamente
        // la cifra delle decine e quella delle unità della versione)
        int majorVersion = data[5] - '0';
        int minorVersion = data[7] - '0';
        int version = majorVersion * 10 + minorVersion;
        
        System.out.println( version);

        // controllo delle versioni.
        if (version < 10 || version > 17)
        {
            return "Unsupported PDF version: " + Integer.toString(version);
        }

        return "ValidPdf";

    }



}
