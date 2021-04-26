/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author jasmi
 */
public class Login {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //declare varibales
        Scanner keyboard= new Scanner(System.in);
        boolean rec=true;
        
        //get input
        System.out.println("Enter username");
        String user=keyboard.nextLine();
         System.out.println("Enter password");
        String pass=keyboard.nextLine();
        
       //call the method
       rec= Login(user,pass);
        if(rec == true){
             System.out.println("You are logined in");
         }
           //else if false
         else{
             System.out.println("Wrong username or password");
         }
        
    }
    
    //login method
    public static boolean Login(String user, String pass){
        File f = new File("login.txt");
        Scanner scnr = null;
        try {
            scnr = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        
        while(scnr.hasNext()){
        String cUser=scnr.nextLine();
        String cPass=scnr.nextLine();
        // if they match return true
       if(user.equals(cUser) && pass.equals(cPass)){
           return true;
       }
       }
       //if they can't find it return false
        if(!scnr.hasNext()){
           return false;
       } 
       //return boolean
  return false;        
    }
}
