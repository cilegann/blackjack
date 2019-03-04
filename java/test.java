import java.util.*;
import java.lang.*;
public class test{
    public static void main(String[] argv){
        for(int i=0;i<argv.length;i++){
            System.out.println(argv[i]);
        }
        System.out.println(Arrays.stream(argv).anyMatch("--debug"::equals));
    }
    
}