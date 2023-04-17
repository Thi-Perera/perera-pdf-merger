package pdfperera;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/api/pdf") // annotazione per mappare le richieste HTTP GET all'endpoint "/api/object"
public class pdfmergeAPI {

    //il main fa partire l'api e si mette in ascolto su una porta specifica.
    public static void main(String[] args) {
        SpringApplication.run(pdfmergeAPI.class, args);
    }

    // la key da inserire nell'header della richiesta con label       API_KEY: <api_key>
    String api_key = "ABC123";

    
    // la routine di controllo della richiesta per effettuare un merge dei pdf richiesti
    @PostMapping("/merge")
    public ResponseEntity<String> mergePDFs(@RequestHeader("API_KEY") String api_key_received, @RequestBody Payload request) throws IOException {

        System.out.println("request ricevuto.");

        // controllo della key nell'header della richiesta
        if(!api_key.trim().equals(api_key_received)){
            System.out.println("API key non valida.\nresponse inviato");
            return new ResponseEntity<>("{\"message\": \"API key scorretta\"}", HttpStatus.BAD_REQUEST);
        }

        // controllo sulle quantità di file
        if (request.getFileQuantity() < 2) {
            System.out.println("quantià non valida.\nresponse inviato");
            return new ResponseEntity<>("{\"message\": \"needed at least 2 pdf files for merging\"}", HttpStatus.BAD_REQUEST);
        }

        // controlla se è un pdf valido, o un file invalido/nullo
        try {
            PdfWrapper[] Files_to_Merge = new PdfWrapper[request.getFileQuantity()];
            int index = 0;
            // Controllo su tutti i File della richiesta;
            // se sono validi(formato Pdf) si passano alla routine di merging,
            // al contrario viene inviata un Response BAD_REQUEST con i dettagli 
            while (index < request.getFileQuantity()) {
                    String checkString = PdfManipulator.CheckIsPDF(PdfManipulator.b64ToBytes(request.getFileb64(index)));
                    if ( checkString == "ValidPdf"){
                        String pdfname = "pdf" + ((char)index+1);
                        Files_to_Merge[index] = new PdfWrapper(pdfname, PdfManipulator.b64ToBytes(request.getFileb64(index)));
                    }
                    else{
                        String invalidFile = "file" + ((char)index+1);
                        System.out.println("file: "+invalidFile+" non valido.\nresponse inviato");
                        return new ResponseEntity<>(checkString + " in: " + invalidFile, HttpStatus.BAD_REQUEST);
                    }
                index++;
            }
            PdfWrapper FinalPdf = PdfManipulator.mergePdf(Files_to_Merge);
            FinalPdf.setPdfName(request.getfilename() + ".pdf"); 
            String response = getObject(FinalPdf, request.getfilename());
            System.out.println("merge effettuato.\nresponse inviato");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("internal server error.\nresponse inviato");
            return new ResponseEntity<>("Errore durante l'unione dei file PDF: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }

    //crea Getobject che dato Un wrapper Pdf , costruisce un json per il response
    @ResponseBody 
    public String getObject(PdfWrapper MergedPdf, String name) throws IOException { 
        Map<String, Object> object = new HashMap<>(); // creazione di un nuovo oggetto HashMap che associa stringhe ad oggetti di tipo generico
        // inserimento di coppie chiave-valore nell'HashMap
        object.put("PdfContent", MergedPdf.getPdfContent());
        object.put("PdfName", name + ".pdf");

        ObjectMapper mapper = new ObjectMapper(); // creazione di un nuovo oggetto ObjectMapper di Jackson per la serializzazione JSON
        return mapper.writeValueAsString(object); // conversione dell'HashMap in una stringa JSON e restituzione della stringa come valore di ritorno del metodo
    }
    
    
    

}



