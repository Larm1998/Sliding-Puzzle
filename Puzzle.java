//AUTHOR Luis Rangel

import javax.swing.*;
import java.awt.Font;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;


public class Puzzle implements ActionListener{

    private JFrame ventana;
    private JButton[] btn_puzzle=new JButton[16];
    private JButton[] btn_puzzle_win=new JButton[16];
    private JButton btn_start, btn_puzzle2, btn_start2, btn_records, btn_exit;
    private JLabel lbl_title, lbl_time, lbl_try;
    private int dir=0,dir1=0,x,y,x1,y1,i,count=0,seg=0,min=0,hours=0,win=0,go=0;
    private String resp;
    Random rnd=new Random();
    Font font1, font2,font3;

    Timer timer2=new Timer(1000, new ActionListener(){
        
        public void actionPerformed(ActionEvent e){
            seg++;

            lbl_time.setText(String.valueOf(hours)+":"+String.valueOf(min)+":"+String.valueOf(seg));

            if(seg>=59){
                min++;
                seg=0;

                if(min>=59){
                    hours++;
                    min=0;
                }
            }
        }
    });

    Timer timer=new Timer(30, new ActionListener(){
    
        public void actionPerformed(ActionEvent e) {
                x=btn_puzzle2.getLocation().x;
                y=btn_puzzle2.getLocation().y;
                System.out.println(x);
                System.out.println(x1);
                lbl_try.setText("Try: "+String.valueOf(count));

                if(y!=y1){
                    btn_puzzle[15].setLocation(x1,y1+dir1);
                    btn_puzzle2.setLocation(x,y+dir);
                    CheckWin();
                }
                else if(x!=x1){
                    btn_puzzle[15].setLocation(x1+dir1, y1);
                    btn_puzzle2.setLocation(x+dir,y);
                    CheckWin();
                }
                else if(y==y1){
                    timer.stop();
                    y1=y1+dir1;
                    dir=0;
                    dir1=0;
                    CheckWin();
                }
                else if(x==x1){
                    timer.stop();
                    x1=x1+dir1;
                    dir=0;
                    dir1=0;
                    CheckWin();
                }
        }
    });


    public static void main(String args[]){
        new Puzzle();
    }

    Puzzle(){
        ventana=new JFrame("Puzzle Master");
        ventana.setBounds(750,100,615,750);
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for(int j=0;j<btn_puzzle_win.length;j++){
            btn_puzzle_win[j]=new JButton();
            btn_puzzle_win[j].setBounds(100+100*(j%4),110+100*(j/4),100,100);
            ventana.add(btn_puzzle_win[j]);
            btn_puzzle_win[j].setVisible(false);
        }
        btn_puzzle_win[15].setEnabled(false);

        for(i=0;i<btn_puzzle.length;i++){
            btn_puzzle[i]=new JButton(String.valueOf(i+1));
            btn_puzzle[i].setBounds(100+100*(i%4),110+100*(i/4),100,100);
            btn_puzzle[i].addActionListener(this);
            ventana.add(btn_puzzle[i]);
        }
        btn_puzzle[15].setVisible(false);

        btn_start=new JButton("Shuffle");
        btn_start.setBounds(203,600,96,40);
        btn_start.addActionListener(this);
        ventana.add(btn_start);

        btn_start2=new JButton("Shuffle 2");
        btn_start2.setBounds(303,600,96,40);
        btn_start2.addActionListener(this);
        ventana.add(btn_start2);

        btn_records=new JButton("Records");
        btn_records.setBounds(400,80,100,23);
        btn_records.addActionListener(this);
        ventana.add(btn_records);

        btn_exit=new JButton("Exit");
        btn_exit.setBounds(480,650,80,30);
        btn_exit.addActionListener(this);
        ventana.add(btn_exit);

        font1=new Font("Comic Sans MS",Font.ITALIC,30);
        font2=new Font("Comic Sans MS",Font.BOLD,15);
        font3=new Font("Comic Sans MS",Font.BOLD,30);

        lbl_title=new JLabel("Puzzle Master");
        lbl_title.setBounds(205,20,200,50);
        lbl_title.setFont(font1);
        ventana.add(lbl_title);

        lbl_try=new JLabel("Try:");
        lbl_try.setBounds(100,85,100,20);
        lbl_try.setFont(font2);
        ventana.add(lbl_try);

        lbl_time=new JLabel("00:00:00");
        lbl_time.setBounds(234,530,200,50);
        lbl_time.setFont(font3);
        ventana.add(lbl_time);

        ventana.setVisible(true);
    }

    public void Create(){
        
    }

    public void Shuffle(){
        int num;
        for(i=0;i<15;i++){

            num=rnd.nextInt(15);
            btn_puzzle2=btn_puzzle[i];
            x= btn_puzzle2.getLocation().x;
            y= btn_puzzle2.getLocation().y;
            x1= btn_puzzle[num].getLocation().x;
            y1= btn_puzzle[num].getLocation().y;
            btn_puzzle[num].setLocation(x,y);
            btn_puzzle2.setLocation(x1, y1);
            btn_puzzle[i].setEnabled(true);
            go=1;
        }
    }

    public void CheckWin(){

        int cordX, cordY;
        for(int j=0;j<15;j++){
            cordX=btn_puzzle_win[j].getLocation().x;
            cordY=btn_puzzle_win[j].getLocation().y;

            if(btn_puzzle[j].getLocation().x==cordX && btn_puzzle[j].getLocation().y==cordY){
                win++;

                // HERE I PRINT THE POINTS TO KNOW IF THE PUZZLE IS COMPLETE
                //System.out.println("casi "+win);
            }
            else{
                win=0;
            }
            
        }
        if(win==15){
            String time,tries;
            time=String.valueOf(hours)+":"+String.valueOf(min)+":"+String.valueOf(seg);
            tries=String.valueOf(count);
            timer.stop();
            timer2.stop();
            resp=JOptionPane.showInputDialog(null, "Congratulations you have completed the puzzle"+"\n"+"Enter your Name please");
            System.out.println(" YOU WON ");
            Winner winner=new Winner();
            winner.save(resp,time,tries);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){

        if(e.getSource()==btn_records){
            new Winner();
        }
        
        if(e.getSource()==btn_exit){
            System.exit(0);
        }

        // FIRST GAME MODE
        if(e.getSource()==btn_start){
            Shuffle();
            timer2.restart();
            hours=0;
            min=0;
            seg=0;
            count=0;
            timer2.start();
            btn_start2.setEnabled(false);
        }
        
        // SECOND GAME MODE
        if(e.getSource()== btn_start2){
            timer2.restart();
            hours=0;
            min=0;
            seg=0;
            count=0;
            timer2.start();
            x=btn_puzzle[14].getLocation().x;
            y=btn_puzzle[14].getLocation().y;
            x1=btn_puzzle[15].getLocation().x;
            y1=btn_puzzle[15].getLocation().y;
            btn_puzzle[14].setLocation(x1,y1);
            btn_puzzle[15].setLocation(x,y);
            Shuffle();
            btn_start.setEnabled(false);
            go=1;

        }
        for(i=0;i<btn_puzzle.length;i++){
            
            if(e.getSource()== btn_puzzle[i]){
                System.out.println("Tile pressed "+i);

                if(e.getSource()!=btn_start){

                    if(!timer.isRunning()&& go==1){
                        btn_puzzle2=btn_puzzle[i];
                        x=btn_puzzle2.getLocation().x;
                        y=btn_puzzle2.getLocation().y;
                        x1=btn_puzzle[15].getLocation().x;
                        y1=btn_puzzle[15].getLocation().y;
                        
                        // This print helped me to discover how much pixels i have to compare the movement
                        //System.out.println("Coordenada x "+x+"\n"+"Coordenada y "+y+"\n"+"Coordenada x1 "+x1+"\n"+"Coordenada y1 "+y1);

                        //Move Down
                        if(y+100==y1 && x==x1){
                            dir=10;
                            dir1=-100;
                            timer.start();
                            count++;
                        }
                        //Move up
                        else if(y-100==y1 && x==x1){
                            dir=-10;
                            dir1=100;
                            timer.start();
                            count++;
                        }
                        //Mover Right
                        else if(x+100==x1 && y==y1){
                            dir=10;
                            dir1=-100;
                            timer.start();
                            count++;
                        }
                        //Mover left
                        else if(x-100==x1 && y==y1){
                            dir=-10;
                            dir1=100;
                            timer.start();
                            count++;
                        }
                    }
                }
            }
        }
    }
}
