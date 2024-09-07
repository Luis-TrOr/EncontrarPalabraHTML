import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class BuscarPalabraHTML {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Recuerda poner el nombre del archivo y la palabra que deseas buscar en los argumentos.");
            System.out.println("Como ejemplo: texto.html Palabra");
            return;
        }

        String archivoHtml = args[0];
        String palabra = args[1];
        String nombreHtml = "file-" + palabra + ".log";

        try {
            String h = new String(Files.readAllBytes(Paths.get(archivoHtml)));

            HTMLEditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            kit.read(new StringReader(h), doc, 0);

            Pattern l = Pattern.compile(Pattern.quote(palabra), Pattern.CASE_INSENSITIVE);
            Matcher u = l.matcher(h);

            try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(nombreHtml))) {
                logWriter.write("Archivo HTML: " + archivoHtml + "\n");
                logWriter.write("Palabra clave: " + palabra + "\n");

                int contador = 0;
                while (u.find()) {
                    int posicion = u.start();
                    System.out.println("Palabra en la posicion: " + posicion);
                    logWriter.write("Posicion: " + posicion + "\n");
                    contador++;
                }

                if (contador == 0) {
                    System.out.println("No se encontro la palabra.");
                } else {
                    System.out.println("Total de palabras encontradas: " + contador);
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
