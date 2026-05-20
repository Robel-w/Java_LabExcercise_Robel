package service;
import javax.swing.*;
import java.io.*;

public class FileService {
    public static void saveFile(JFrame frame, String content){
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(frame);
        if(option == JFileChooser.APPROVE_OPTION){
            try(FileWriter writer = new FileWriter(fileChooser.getSelectedFile())){
                writer.write(content);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static String openFile(JFrame frame){
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(frame);
        if(option == JFileChooser.APPROVE_OPTION ){
            try(BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))){
                StringBuilder content = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    content.append(line).append('\n');
                }
                return content.toString();
            }catch(IOException e){
                e.printStackTrace();
            }

        }
        return "";
    }
}
