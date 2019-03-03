package blackjack.util;
public class util{
    public static String cardConverter(int in){
        if(in==1){
            return "A";
        }else if(in==11){
            return "J";
        }else if(in==12){
            return "Q";
        }else if(in==13){
            return "K";
        }else{
            return ""+in;
        }
    }
}