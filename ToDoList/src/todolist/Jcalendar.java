/*
This file is solely to show the entirety of work done by team member, adn is not used in the execution of
the final appilcation - JR
 */
package todolist;
/**
 *
 * @author krystalecole
 */

//Code Reference:
/***************************************************************************************
*    Title: CalendarProgram
*    Author: Subham Mittal
*    Date: N/A
*    Code version: N/A
*    Availability: https://javahungry.blogspot.com/2013/06/calendar-implementation-gui-based.html
*
***************************************************************************************/
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jrobb
 */
public class Jcalendar {
/**
     * @param args the command line arguments
     */

    static JLabel Month, Year;
    static JButton PrevB, NextB;
    static JTable Calendar;
    static JComboBox YearBox;
    static JFrame Main;
    static JComboBox TimeBox;
    static Container pane;
    static DefaultTableModel MCalendar; //The table model
    static JScrollPane SCalendar; //The scrollpane
    static JPanel CalendarPanel;
    static int NumYear, NumMonth, NumDay, currentYear, currentMonth;
    static JLabel Time;
    
    public static void main (String args[]) throws ParseException{
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException | InstantiationException 
               | IllegalAccessException | UnsupportedLookAndFeelException e) {}

        //System.out.println("Current Date and Time is: " + current);
        
        //Prepars frame format
        Main = new JFrame (""); //Creates the frame
        Main.setSize(400, 400); //Sets the size to 400x400 px
        pane = Main.getContentPane(); //Grabs the content pane
        pane.setLayout(null); //Applies the null layout
        Main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X button is clicked
        
        //Create controls
        Month = new JLabel ("January");
        Year = new JLabel ("Year:");
        Time = new JLabel ("Time:");
        YearBox = new JComboBox();
        TimeBox = new JComboBox();
        PrevB = new JButton ("<<");
        NextB = new JButton (">>");
        MCalendar = new DefaultTableModel(){@Override
        public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
        Calendar = new JTable(MCalendar);
        SCalendar = new JScrollPane(Calendar);
        CalendarPanel = new JPanel(null);

        
        //Set border
        CalendarPanel.setBorder(BorderFactory.createTitledBorder("My Calendar"));
        
        //Register action listeners
        PrevB.addActionListener(new BttnPrev_Action());
        NextB.addActionListener(new BttnNext_Action());
        YearBox.addActionListener(new ComboBYear_Action());
        TimeBox.addActionListener(new ComboBYear_Action());
        
        //Adds the controls to the pane
        pane.add(CalendarPanel);
        CalendarPanel.add(Month);
        CalendarPanel.add(Year);
        CalendarPanel.add(YearBox);
        CalendarPanel.add(TimeBox);
        CalendarPanel.add(PrevB);
        CalendarPanel.add(NextB);
        CalendarPanel.add(SCalendar);
        CalendarPanel.add(Time);
        
        //Set bounds of the calendar
        CalendarPanel.setBounds(0, 0, 400, 365);
        Month.setBounds(185, 5, 100, 100);
        Year.setBounds(240, 305, 80, 20);
        YearBox.setBounds(290, 305, 110, 20);
        Time.setBounds(235, 330, 80, 20);
        TimeBox.setBounds(290, 330, 110, 20);
        PrevB.setBounds(50, 25, 50, 25);
        NextB.setBounds(300, 25, 50, 25);
        SCalendar.setBounds(50, 50, 300, 250);//shift left/right, shift up/down, sizing right. sizing left
        
        //The application frame is size is made visible
        Main.setResizable(false);
        Main.setVisible(true);
        
        //Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); //Create the calendar
        NumDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Gets the day
        NumMonth = cal.get(GregorianCalendar.MONTH); //Gets the month
        NumYear = cal.get(GregorianCalendar.YEAR); //Gets the year
        currentMonth = NumMonth; //Match month and year
        currentYear = NumYear;
        
        //Add calendar day headers
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
            MCalendar.addColumn(headers[i]);
        }
        
        Calendar.getParent().setBackground(Calendar.getBackground()); //Set background
        
        //Allows for no resize or reorder
        Calendar.getTableHeader().setResizingAllowed(false);
        Calendar.getTableHeader().setReorderingAllowed(false);
        
        //Selects a single cell
        Calendar.setColumnSelectionAllowed(true);
        Calendar.setRowSelectionAllowed(true);
        Calendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Count of row column
        Calendar.setRowHeight(38);
        MCalendar.setColumnCount(7);
        MCalendar.setRowCount(6);
        
        //Create table
        for (int i=NumYear-30; i<=NumYear+30; i++){
            YearBox.addItem(String.valueOf(i));
        }
        
        //Current time function
        SimpleDateFormat time = new SimpleDateFormat("hh:mm a");
        Date d = new Date(System.currentTimeMillis());
        TimeBox.addItem(time.format(d)); //Shows time
        
        //Refreshs the calendar
        refreshCalendar (NumMonth, NumYear);
        

    }
    
    public static void refreshCalendar(int month, int year){
        //Variables
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod, som; //Number Of Days, Start Of Month
        
        //Allow/disallow the buttons
        PrevB.setEnabled(true);
        NextB.setEnabled(true);
        if (month == 0 && year <= NumYear-10){PrevB.setEnabled(false);} //Too early
        if (month == 11 && year >= NumYear+100){NextB.setEnabled(false);} //Too late
        Month.setText(months[month]); //Refresh the month label (at the top)
        Month.setBounds(185, 25, 180, 25); //Re-align label with calendar
        YearBox.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
        
        //Clears the table
        for (int i=0; i<6; i++){
            for (int j=0; j<7; j++){
                MCalendar.setValueAt(null, i, j);
            }
        }
        
        //Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
        
        //Draws the calendar
        for (int i=1; i<=nod; i++){
            int row = (i+som-2)/7;
            int column  =  (i+som-2)%7;
            MCalendar.setValueAt(i, row, column);
        }
        
        //Apply the renderers
        Calendar.setDefaultRenderer(Calendar.getColumnClass(0), new TCalendarRenderer());
    }
    
    static class TCalendarRenderer extends DefaultTableCellRenderer{
        @Override
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if (column == 0 || column == 6){ //Weekend
                setBackground(new Color(219, 244, 255));
            }
            else{ //Week
                setBackground(new Color(219, 244, 255));
            }
            if (value != null){
                if (Integer.parseInt(value.toString()) == NumDay && currentMonth == NumMonth && currentYear == NumYear){ //Today
                    setBackground(new Color(237, 237, 237));
                }
            }
            setBorder(null);
            setForeground(new Color(41, 27, 81));
            return this;
        }
    }
    
    static class BttnPrev_Action implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 0){ //Back by one year
                currentMonth = 11;
                currentYear -= 1;
            }
            else{ //Back by one month
                currentMonth -= 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class BttnNext_Action implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            if (currentMonth == 11){ //Foward by one year
                currentMonth = 0;
                currentYear += 1;
            }
            else{ //Foward  by one month
                currentMonth += 1;
            }
            refreshCalendar(currentMonth, currentYear);
        }
    }
    static class ComboBYear_Action implements ActionListener{
        @Override
        public void actionPerformed (ActionEvent e){
            if (YearBox.getSelectedItem() != null){
                String b = YearBox.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }    
}
