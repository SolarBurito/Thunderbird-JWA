/******************************************************************************
 * Copyright (C) 2019 Eric Pogue.
 * 
 * This file and the ThunderbirdLite applciation is liscensed under the 
 * BSD-3-Clause.
 * 
 * You may use any part of the file as long as you give credit in your 
 * source code.
 * 
 * This application utilizes the HttpRequest.java library developed by 
 * Eric Pogue
 * 
 *****************************************************************************/
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Container; 
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

class ContactTile extends JPanel {
    private ThunderbirdContact contactInSeat = null;

    private Boolean isAnAisle = false;
    public void setAisle() { isAnAisle = true; }

    ContactTile() {
        super();

        // Todo: Remove everything to do with random colors.
        // Todo: Implement visually appealing colors for aisles and seats.
        // JWA - Removed Random colors
    }

    ContactTile(ThunderbirdContact contactInSeatIn) {
        super();
        contactInSeat = contactInSeatIn;
    }


    private static int GetNumberBetween(int min, int max) {
        Random myRandom = new Random();
        return min + myRandom.nextInt(max-min+1);
    }   

    public void paintComponent(Graphics g) {
        super.paintComponent(g); 

        int panelWidth = getWidth();
        int panelHeight = getHeight();


        if (isAnAisle) {
        	g.setColor(new Color(100,100,100));
        } else {
        	g.setColor(new Color(200,200,200));

        }

        g.fillRect(10,10,panelWidth-10,panelHeight-10);

        

        final int fontSize=18;
        g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
        g.setColor(new Color(0,0,0));
        int stringX = (panelWidth/2)-60;
        int stringY = (panelHeight/2)-30;
        if (contactInSeat != null) {

            // ToDo: Dispay preferred name instead of first and last name. 
            // JWA: Display Seat Number, First, Last, and Preferred Name
            String firstAndLastName = contactInSeat.getFirstName()+" "+contactInSeat.getLastName();
            String preferredName = contactInSeat.getPreferredName();
            int seatNum = contactInSeat.getSeat();
            String writeStr = String.format("%d\n%s\n%s",seatNum,firstAndLastName,preferredName);
            drawString(g,(writeStr),stringX,stringY);
        }
    }

    public void drawString(Graphics g, String text, int x, int y){
    	for (String line : text.split("\n")){
    		g.drawString(line, x, y += g.getFontMetrics().getHeight());
    	}
    }
}

class ThunderbirdLiteFrame extends JFrame implements ActionListener {
    private ArrayList<ContactTile> tileList;

    ThunderbirdModel tbM = new ThunderbirdModel();



    public ThunderbirdLiteFrame() {
        setBounds(200,200,1200,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        JButton reverseView = new JButton("Reverse View");
        buttonPanel.add(reverseView);
        reverseView.addActionListener(this);

        JPanel contactGridPanel = new JPanel();
        contentPane.add(contactGridPanel, BorderLayout.CENTER);

        //contactGridPanel.setLayout(new GridLayout(4,8));
        contactGridPanel.setLayout(new GridLayout(11,9));

        tbM.LoadIndex();
    	tbM.LoadContactsThreaded();



        // Todo: Review ThunderbirdModel in detail and implement a multithreaded version of loading contacts. 
        // Hint: Review LoadContact() and LoadContactsThreaded() in detail.

        System.out.println("Printing Model:");
        System.out.println(tbM);
        tbM.ValidateContacts();   


        tileList = new ArrayList<ContactTile>();
        
        for(int i=0; i<99; i++) {
            ThunderbirdContact contactInSeat = tbM.findContactInSeat(i);
            if (contactInSeat != null) {
                System.out.println(contactInSeat);
            }

            ContactTile tile = new ContactTile(contactInSeat);

            if ((i % 9 == 8 || i % 9 == 0 || i % 9 == 5 || i % 9 == 2)){
                tile.setAisle();
            }

            if (i < 10){
            	tile.setAisle();
            }

            if (i % 9 == 1 && (i < 10 || i > 65)){
            	tile.setAisle();
            }

            if (i % 9 == 3 && i % 2 == 1 && i < 90){
            	tile.setAisle();
            }

            if (i % 9 == 4 && i % 2 == 0 && i < 90){
            	tile.setAisle();
            }

            if (i > 74 && i < 82){
            	tile.setAisle();
            }

            if (i > 38 && i < 46 && i != 39){
            	tile.setAisle();
            }

            if ((i % 9 == 6 || i % 9 == 7) && i > 80){
            	tile.setAisle();
            }

            tileList.add(tile);
            contactGridPanel.add(tile);
        }
    }

    public void actionPerformed(ActionEvent e) {
        for(ContactTile tile : tileList) {
            // Todo: Remove randomization functionality and implement a visually appealing view of seats and aisles.

            // Todo: Implement reverse view where it looks like you are looking at the room from the back instead of the front 
            //     of the room. 
            ThunderbirdContact contactInSeatIn = tbM.findContactInSeat(tile);
            contactInSeatIn.flipSeat();

        }

        repaint();
    }

}

// Todo: Rename the following class to Thunderbird.
// Hint: This will also require you to rename the Java file.
public class ThunderbirdLite {
    public static void main(String[] args) {

        // Todo: Update the following line so that it reflects the name change to Thunderbird.
        System.out.println("ThunderbirdPrime Starting...");
        try{
            ThunderbirdLiteFrame myThunderbirdLiteFrame = new ThunderbirdLiteFrame();
            myThunderbirdLiteFrame.setVisible(true);
        }catch(Exception e){
            System.out.print("Error displaying Window");
        }
    }
}