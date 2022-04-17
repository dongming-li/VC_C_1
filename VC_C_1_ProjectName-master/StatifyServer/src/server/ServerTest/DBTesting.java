

public class DBTesting 
{
    public static void main(String[] args) 
    {
        //commands to be executed:
        String addCommand1 = "add 69";
        String pullCommand1 = "pull BowlingGames";
        String noCommand1 = "rfivjnfv ";
        String addCommand2 = "add 300";
        String pullCommand2 = "pull ";
        
        CommandHandler AdamSandler = new CommandHandler();//CommandHandler object
        
        String addCommand1Result = AdamSandler.commandHandler(addCommand1);//execute command
        System.out.println(addCommand1Result);//print result
        System.out.println("");
        
        String pullCommand1Result = AdamSandler.commandHandler(pullCommand1);
        System.out.println(pullCommand1Result);
        System.out.println("");
        
        String noCommand1Result = AdamSandler.commandHandler(noCommand1);
        System.out.println(noCommand1Result);
        
        String addCommand2Result = AdamSandler.commandHandler(addCommand2);
        System.out.println(addCommand2Result);
        System.out.println("");
        
        String pullCommand2Result = AdamSandler.commandHandler(pullCommand2);
        System.out.println(pullCommand2Result);
        System.out.println("");
    }
}
