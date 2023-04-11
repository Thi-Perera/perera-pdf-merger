package pdfperera;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdf") // annotazione per mappare le richieste HTTP GET all'endpoint "/api/object"
public class RequestController {


    @GetMapping("/api/hello")
    ResponseEntity<String> hello() {

        return new ResponseEntity<>("sei stupida", HttpStatus.OK);
    }
    
    
    @PostMapping("/merge")
    public ResponseEntity<String> mergePDFs(@RequestBody Payload request) throws IOException {
// Verifica della chiave OAuth2 qui
//        if (!verifyOAuth2(request.getOauth2Key())) {
//            throw new InvalidOAuth2Exception("Chiave OAuth2 non valida!");
//        }
        
        // se l'operazione della richiesta è UnionePdf , effettua il merge di tutti i pdf e restituisce l'oggetto pdf finale

        System.out.println(request.getOperazione());
        System.out.println(request.getFileQuantity());
        //System.out.println(request.getFileb64(0));
        //System.out.println(request.getFileb64(1));


            // controllo sulle quantità di file
            if (request.getFileQuantity() < 2) {
                return new ResponseEntity<>("needed at least 2 pdf files for merging", HttpStatus.BAD_REQUEST);
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
                            System.out.println( "b64files: "+ PdfManipulator.b64ToBytes(request.getFileb64(index)));
                            // curioso il fatto che se sostituisci la riga con "String pdfname = "pdf" + (char)index;" il secondo pdf si chiamerà "pdfname: pdf☺"
                            String pdfname = "pdf" + ((char)index+1);
                            System.out.println(pdfname);
                            //è qui l'errore credo, file_to_merge non inizializza un cazzo
                            Files_to_Merge[index] = new PdfWrapper(pdfname, PdfManipulator.b64ToBytes(request.getFileb64(index)));
                        }
                        else{
                            String invalidFile = "file" + ((char)index+1);
                            return new ResponseEntity<>(checkString + " in: " + invalidFile, HttpStatus.BAD_REQUEST);
                        }
                    index++;
                }
                PdfWrapper FinalPdf = PdfManipulator.mergePdf(Files_to_Merge);
                FinalPdf.setPdfName(request.getfilename() + ".pdf"); 
                String response = getObject(FinalPdf);
                System.out.println("merge effettuato.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Gestisci l'eccezione qui
                return new ResponseEntity<>("Errore durante l'unione dei file PDF: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        

        
    }

    //crea Getobject che dato Un wrapper Pdf , costruisce un json per il response
    @ResponseBody 
    public String getObject(PdfWrapper MergedPdf) throws IOException { 
        Map<String, Object> object = new HashMap<>(); // creazione di un nuovo oggetto HashMap che associa stringhe ad oggetti di tipo generico
        // inserimento di coppie chiave-valore nell'HashMap
        object.put("operazione", "UnionePDF");
        object.put("PdfContent", MergedPdf.getPdfContent());
        object.put("PdfName", MergedPdf.getPdfName());

        ObjectMapper mapper = new ObjectMapper(); // creazione di un nuovo oggetto ObjectMapper di Jackson per la serializzazione JSON
        return mapper.writeValueAsString(object); // conversione dell'HashMap in una stringa JSON e restituzione della stringa come valore di ritorno del metodo
    }
    
    private boolean verifyOAuth2(String oauth2Key) {
        // Verifica della chiave OAuth2 qui
        // Restituisce true se la chiave è valida, false altrimenti
        return true;
    }
    
}


/* 
@SpringBootApplication
@RestController
public class RequestHandler {
  
    public static void main(String[] args) {

        SpringApplication.run(RequestHandler.class, args);
    }

    @GetMapping("/api/hello")
    public String sayHello() {
        return "Hello, World!";
    }

}



//codice per creare un il json per il response

@GetMapping("/api/object") // annotazione per mappare le richieste HTTP GET all'endpoint "/api/object"
@ResponseBody // annotazione per indicare che il valore restituito dal metodo deve essere utilizzato come corpo della risposta HTTP
public String getObject() throws JsonProcessingException { // definizione del metodo getObject, che può lanciare un'eccezione JsonProcessingException
  Map<String, Object> object = new HashMap<>(); // creazione di un nuovo oggetto HashMap che associa stringhe ad oggetti di tipo generico
  object.put("key1", "value1"); // inserimento di una coppia chiave-valore nell'HashMap
  object.put("key2", "value2"); // inserimento di una seconda coppia chiave-valore nell'HashMap

  ObjectMapper mapper = new ObjectMapper(); // creazione di un nuovo oggetto ObjectMapper di Jackson per la serializzazione JSON
  return mapper.writeValueAsString(object); // conversione dell'HashMap in una stringa JSON e restituzione della stringa come valore di ritorno del metodo
}

*/
