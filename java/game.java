import java.util.*;
import java.lang.*;
public class game{
    public static void main(String[] argv){
        String cmd;
        Scanner input=new Scanner(System.in);
        blackjack b=new blackjack(Arrays.stream(argv).anyMatch("--debug"::equals));
        while(true){
            while(!b.getEnded()){
                System.out.print("[H] for hit (get another card) / [S] for stand / [D] for double : ");
                cmd=input.next();
                if(cmd.equals("H")){
                    b.playerHit();
                }else if(cmd.equals("S")){
                    b.playerStand();
                }else if(cmd.equals("D")){
                    b.playerDouble();
                }
            }
            int gameResult=b.gameResult();
            while(true){
                System.out.print("[R] for new game / [C] for continue / [N] for new deck : ");
                cmd=input.next();
                if(cmd.equals("R")){
                    b=new blackjack(Arrays.stream(argv).anyMatch("--debug"::equals));
                    break;
                }else if(cmd.equals("C")){
                    b.continueGame(false);
                    break;
                }else if(cmd.equals("N")){
                    b.continueGame(true);
                    break;
                }
            }
        }
        
    }
    
}