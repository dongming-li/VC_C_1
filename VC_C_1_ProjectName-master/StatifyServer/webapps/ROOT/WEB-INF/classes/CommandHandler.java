//package server;
import java.util.Scanner;


public class CommandHandler 
{
    public CommandHandler()
    {
        
    }
    
   /*
    *temporary version 3
    *
    *String input format: "<cmd> <dataString>"
    *               ie.   "add 201"
    *
    *returns String output format depends on command (cmd)
    */
    public String commandHandler(String commandAndData)
    {
        Scanner scan = new Scanner(commandAndData);
        String cmd;
        String dataString;
        
        
        cmd = scan.next();
        dataString = scan.nextLine();
        
        switch(cmd)
        {
            case "add" :
                return addHandler(dataString);
            case "pull" :
                return pullHandler(dataString);
            default :
        }
        
        return " Something went wrong with Command \"" + cmd + "\", Data: \"" + dataString + "\"";
    }
    
    public String addHandler(String dataString)
    {
        //TODO:  
        return "You made it to the addHandler method! You gave this data: \"" + dataString + "\"";
    }
    
    public String pullHandler(String dataString)
    {
        //TODO:
        return "You made it to the pullHandler method! You gave this data: \"" + dataString + "\"";
    }
}
