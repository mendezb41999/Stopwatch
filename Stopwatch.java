import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextArea;



public class Stopwatch extends JFrame //since Stopwatch is a subclass of JFrame, all of the JFrame properties and
                                      //methods are available to a Stopwatch. In particular, the Stopwatch constructor
                                      //can use the JFrame "add" method, for adding components to a JFrame.
{
     public static final Color VERY_LIGHT_BLUE = new Color(51,204,255);

      //frame specifications
      final int FIELD_WIDTH =20;

      //member variables for a Stopwatch object
      private JTextField seconds;
      private JTextField minutes;
      private JTextField tenthsSeconds;
      private int min;
      private int sec;
      private int tenth;
      //constructor for a Stopwatch object
      public Stopwatch()
      {


        //frame settings
        setTitle("Stopwatch");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(30, 30);
        setLayout(new BorderLayout());

        //textPanel with 6 components for the three time textFields and their descriptions
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());//using a grid layout with 3 rows, 2 columns

         //textPanel component 1,1 for minutes
         minutes = new JTextField(" ", 2);
         minutes.setEditable(false);
         minutes.setBackground(Color.YELLOW);
         Font font = new Font("Courier", Font.BOLD, 40);
         minutes.setFont(font);
         textPanel.add(minutes); //add the textField to the textPanel grid
 
         //textPanel component 1,2 for "minutes"
         JTextField minutesLabel = new JTextField(":", 0);
         minutesLabel.setBackground(Color.YELLOW);
         minutesLabel.setFont(font);
         textPanel.add(minutesLabel); 


         //textPanel component 2,1 for seconds
         seconds = new JTextField(" ", 2);
         seconds.setBackground(Color.YELLOW);
         seconds.setFont(font);
         textPanel.add(seconds); 
 
         //textPanel component 2,2 for "seconds"
         JTextField secondsLabel = new JTextField(":", 0);
         secondsLabel.setBackground(Color.YELLOW);
         secondsLabel.setFont(font);
         textPanel.add(secondsLabel); 

         //textPanel component 3,1 for tenths of a second
         tenthsSeconds = new JTextField(" ", 2);
         tenthsSeconds.setBackground(Color.YELLOW);
         tenthsSeconds.setFont(font);
         textPanel.add(tenthsSeconds); 
 
         

         add(textPanel, BorderLayout.NORTH); //add the entire panel to the frame from the top of the frame
 
         //initialize the time variables
         min = 0;
         sec = 0;
         tenth = 0;

        final int DELAY = 100; //delay constant is 100/1000 milliseconds = 1/10 second
        Timer t = new Timer(DELAY, listener); //Construct a timer object with delay 1/10 sec. 
                                              //The event handler "listener" will process
                                              //each timer update event every tenth of a second.
                                              //Because the code for this is a bit lengthy,
                                              //the detailed code for "listener" is given later,
                                              //so as not to clutter up the constructor.

        //create  a JPanel for command buttons for start, stop, reset
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GREEN);
        buttonPanel.setLayout(new FlowLayout()); //buttons will "flow" accross the panel, left to right

        //create the start button component  
        JButton startButton = new JButton("Start");
        
        //The response to clicking the start button is to simply start the timer; since this action
        //can be coded so simply, the event handler is defined "anonymously", 
        //without declaring a named ActionListener interface object.
        //Instead, we simply provide the code for the method actionPerformed(ActionEvent e)
        //which the ActionListener interface requires, and we add the event handler to the button,   
        //using addActionListener, all in one step:
  
        startButton.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
           {            
            t.start();//start the timer
           } 
        }           
        );

        //now add the button to the panel
        buttonPanel.add(startButton);



        //the stop button is designed similarly:

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {             
              t.stop();//stop the timer
            }
          }        
        );

        buttonPanel.add(stopButton);

         //to handler the "reset" button click, stop the timer, blank out the time fields,
         //and reset the time variables to 0:

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener()
          {
            public void actionPerformed(ActionEvent e)
            {
              t.stop();
              seconds.setText(" ");
              minutes.setText(" ");
              tenthsSeconds.setText(" ");
              min=0;
              sec=0;
              tenth=0;
            }
          }
         
        );

        buttonPanel.add(resetButton);

        //add the button panel component to the frame
        add(buttonPanel, BorderLayout.CENTER);
  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();  
 
     }//this concludes the constructor for a Stopwatch object

     //We still have to describe the event handler for the timer, 
     //to give the response whenever there is a timer event
     //corresponding to the passing of another 1/10 second time interval:

     ActionListener listener = new ActionListener()  //as usual, the action listener object
                                                     //must implement the method actionPerformed
                                                     //for the ActionListener interface. Because the
                                                     //code for this is a bit lengthy, we put this code
                                                     //in a named object called "listener", which
                                                     //appears in this separate section of the program,
                                                     //rather than putting all of this code into an
                                                     //anonymously defined listener as we did with the button
                                                     //click event handlers.
     {
       public void actionPerformed(ActionEvent event)
       {
         tenth = tenth + 1; //each tenth of a second, increment the tenths counter mod 10
         if(tenth ==  10) //each new full second, increment the seconds counter and reset the tenths counter
         {
           tenth = 0;
           sec = sec + 1;
           if(sec == 60) //each new full minute, increment the minutes counter and reset the seconds counter
           { 
             sec = 0;
             min = min +1;
             minutes.setEditable(true); //enable the minutes textField 
             


                    if(min<10){
              minutes.setText("0"+ Integer.toString(min)); //display the minutes
		}
		    else{
	      minutes.setText(Integer.toString(min));
		}
             minutes.setEditable(false);//disable the minutes textField, which turns off the blinking cursor.
                                        //The cursor is only an issue for the first textField.
           }
		  if(sec<10){

          seconds.setText("0"+Integer.toString(sec));   //display seconds    
			}
		else{
		
          seconds.setText(Integer.toString(sec));
		}
         }

		if(tenth<10){

         tenthsSeconds.setText("0"+Integer.toString(tenth)); //display tenths
		}
		else{
	tenthsSeconds.setText(Integer.toString(tenth));
	}
      }
     };
 
   public static void main(String[] args)
   {
     Stopwatch sw = new Stopwatch(); //create a new Stopwatch object
     sw.setVisible(true); //make the stopwatch visible to the user,
                          //who can then trigger button click events.
   }

}    
