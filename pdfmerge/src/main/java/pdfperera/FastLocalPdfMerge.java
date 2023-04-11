package pdfperera;

import java.io.*;
import java.util.*;

public class FastLocalPdfMerge {
    public static void main(String[] args) throws IOException {
        File folder = new File("pdfmerge/src/main/resources/pdf_files");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

        List<PdfWrapper> pdfList = new ArrayList<>();
        for (int i = 0; i < listOfFiles.length; i++) {
            byte[] bytes = PdfManipulator.pdfToBytes(listOfFiles[i]);
            PdfWrapper wrapper = new PdfWrapper(listOfFiles[i].getName(), bytes);
            pdfList.add(wrapper);
        }

        Scanner scanner = new Scanner(System.in);
        List<Integer> order = new ArrayList<>();
        while (true) {
            System.out.println("Seleziona il numero del PDF da unire (o digita 'done' per completare l'ordine):");
            for (int i = 0; i < pdfList.size(); i++) {
                if (!order.contains(i)) {
                    System.out.println((i + 1) + ". " + pdfList.get(i).getPdfName());
                }
            }

            String input = scanner.nextLine().toLowerCase().trim();
            if (input.equals("done")) {
                break;
            }

            int index;
            try {
                index = Integer.parseInt(input) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Input non valido, riprova.");
                continue;
            }

            if (index < 0 || index >= pdfList.size()) {
                System.out.println("Input non valido, riprova.");
                continue;
            }

            if (order.contains(index)) {
                System.out.println("PDF già selezionato, riprova.");
                continue;
            }

            order.add(index);
            System.out.println("PDF " + pdfList.get(index).getPdfName() + " aggiunto all'ordine.");
        }

        if (order.isEmpty()) {
            System.out.println("Nessun PDF selezionato, programma terminato.");
            return;
        }

        PdfWrapper[] pdfArray = new PdfWrapper[order.size()];
        for (int i = 0; i < order.size(); i++) {
            pdfArray[i] = pdfList.get(order.get(i));
        }

        PdfWrapper mergedPdf = PdfManipulator.mergePdf(pdfArray);


        byte[] bytes = mergedPdf.getPdfContent();
        String name = mergedPdf.getPdfName();
        PdfManipulator.bytesToPdf(bytes, "pdfmerge/src/main/resources/pdf_files/" + name);
        System.out.println("I PDF sono stati uniti con successo. Il PDF risultante è stato salvato come " + name);
    }
}
