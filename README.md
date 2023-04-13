# Pdf_Merge_API
multiple pdf merger java API 

# Titolo del Progetto

Un API per il merge di più pdf che in uno + creazione di un custom connector per l'implementazione in un workflow su  Windows Power Automate 


## Prerequisiti

- JDK 1.8 o superiore
- Apache Maven 3.6.0 o superiore
- Connessione Internet


## Configurazione

1. Clonare il repository
git clone https://github.com/NOME_DEL_REPO.git
2. Eseguire il comando Maven per compilare ed eseguire l'applicazione
mvn spring-boot:run
3. L'applicazione sarà disponibile all'indirizzo `http://localhost:8080`


## API

### Endpoint

POST api/pdf/merge

### Parametri della richiesta

| Nome  | Tipo | Descrizione |
| ------------- | ------------- | ------------- |
| `pdfList`  | `String[]`  | Array di stringhe Base64 che rappresentano i PDF da unire |
| `fileName`  | `String`  | Nome del file di output |

### Esempio di richiesta

```json
{
    "pdfList": [
        "JVBERi0xLjQKJcfs...",
        "JVBERi0xLjQKJcfs..."
    ],
    "fileName": "merged.pdf"
}
```

### Risposta

Se la richiesta ha successo, l'API restituirà il file PDF unito.

### Esempio di risposta

Il file PDF unito verrà restituito come flusso di byte.


## Dipendenze

- Spring Boot
- Apache PDFBox

## Licenza

Questo progetto è distribuito con la licenza MIT. Consultare il file LICENSE per ulteriori informazioni.
