package pdfperera;

public class Payload {
        private String filename;
        private String[] files;

    
//per adesso ho un getter e un setter per tutti i campi del json, non è detto che siano necessari.


        public String getfilename() {
            return filename;
        }
    
        public void setfilename(String filename) {
            this.filename = filename;
        }
    
        public String getFileb64(int i) {
            if(i<files.length){
                return files[i];
            }
            return "no such file in payload";
        }
        public int getFileQuantity(){
            return files.length;
        }

    
        public void setFiles(String[] files) {
            this.files = files;
        }

    /* 

        public String getOauth2Key() {
            return oauth2Key;
        }

        public void setOauth2Key(String oauth2Key) {
            this.oauth2Key = oauth2Key;
        }

    */

    }

