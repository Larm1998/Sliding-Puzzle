import java.util.*;
import java.io.*;
import javax.swing.*;

public class Winner{

    private JFrame window;
    JScrollPane listScroller;
    DefaultListModel<String> listModel;
    JList<String> list;

    Winner(){
        
        window=new JFrame("Best Players");
        window.setBounds(800,100,300,300);
        window.setLayout(null);

        listModel=new DefaultListModel<String>();
        list=new JList<String>(listModel);

        listScroller=new JScrollPane(list);
        listScroller.setBounds(20,25,240,200);
        window.add(listScroller);

        See();

        window.setVisible(true);
    }

    public void save(String resp, String time, String tries){
        FileWriter fw;

        try{

            fw=new FileWriter("Players.txt",true);
            fw.write(resp+"\n");
            fw.write(time+"\n");
            fw.write(tries+"\n");
            fw.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error Saving"+e.toString(), "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void See(){
        File f=new File("Players.txt");
        Scanner sc;
        String player, move, time;
        listModel.clear();

        try{
            sc=new Scanner(f);
            while(sc.hasNextLine()){

                player=sc.nextLine();
                time=sc.nextLine();
                move=sc.nextLine();

                listModel.addElement(player+" "+time+" "+move);

                
            }
        }
        catch(Exception e){
            System.out.println("Error reading in screen "+e.toString());
        }
    }

}