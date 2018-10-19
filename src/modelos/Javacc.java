package modelos;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ErickRG
 */
public class Javacc {

    //Función para ejecutar nuestros archivos javacc
    public String lexer(String code) throws UnsupportedEncodingException, IOException {
        String resultado = "";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("entrada.txt"), "utf-8"))) {
            writer.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Runtime rt = Runtime.getRuntime();
            String[] location = {"cmd", "/c", "cd /src/compilador"};
            Process proc = rt.exec(location);
            String[] commands = {"cmd", "/c", "javacc Parser.jj"};
            proc = rt.exec(commands);
            String[] commands1 = {"cmd", "/c", "javac *.java"};
            proc = rt.exec(commands1);
            String[] commands2 = {"cmd", "/c", "java Parser < entrada.txt "};
            proc = rt.exec(commands2);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            String s = null;
            //Estas son etiquetas HTML para jEditorPane, modificar el tipo de formato que permite.
            while ((s = stdInput.readLine()) != null) {
                resultado += ("<span style='color: white; font-family:verdana;font-size:110%;'>" + s + "<br></span>");
            }

            while ((s = stdError.readLine()) != null) {
                resultado += ("<span style='color: red; font-family:verdana;font-size:100%;'>" + s + "<br></span>");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}