package agh.cs.oop.kubicki;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Display extends JFrame implements ActionListener {

    private Timer timer;
    private JTextArea area;
    private Life life;
    private int i = 0;

    public Display(){
        initSimulation();
        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN,20));
        add(area);
        setSize(1000, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        timer = new Timer(10,this);
        timer.start();
    }

    private void initSimulation(){
        Jungle jungle = new Jungle(20,20);
        this.life = new Life(jungle);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        i++;
        this.life.simulate();
        if (i>1000) return;
        area.setText("Iterations: " + (i) + "\n" + life.getMap().toString());
    }
}