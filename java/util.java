public class util{
    /** 
    * convert card value to A 2 3 ... J Q K
    * 
    * @param in  card value
    * @return String, converted String
    */ 
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