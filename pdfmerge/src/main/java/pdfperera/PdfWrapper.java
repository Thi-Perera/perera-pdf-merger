package pdfperera;
import java.io.IOException;


public class PdfWrapper {
    
    private String pdfName;
    private byte[] pdfContent;
    
    public PdfWrapper(String pdfName, byte[] pdfBytes){
        this.pdfName = pdfName;
        this.pdfContent = pdfBytes;
    }
    
    public byte[] getPdfContent() throws IOException {
        // restituisco una copia dell'array cosicchè possa essere modificato senza intaccare l'array originale
        return this.pdfContent.clone();
    }

    
    public String getPdfName() {
        // restituisco il valore tranquillamente,non è necessario clone(), essendo una variabile primitiva passo il valore, non il riferimento
        return this.pdfName;
    }

    public void setPdfContent(byte[] givenContent) throws IOException {
        // restituisco una copia dell'array cosicchè possa essere modificato senza intaccare l'array originale
        this.pdfContent = givenContent;
    }

    
    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }
    
    
}