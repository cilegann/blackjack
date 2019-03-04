import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class blackjack{
    private ArrayList<Integer> bankerHand;
    private ArrayList<Integer> playerHand;
    private boolean debug;
    private ArrayList<Integer> bankerHandSum;
    private ArrayList<Integer> playerHandSum;
    private int playerChip;
    private int bet;
    private boolean stand;
    private boolean ended;
    private ArrayList<Integer> deck;
    
    public blackjack(boolean debug){
        System.out.println("\n\n***** New Player with new shuffled deck *****");
        this.bankerHand=new ArrayList<Integer>();
        this.playerHand=new ArrayList<Integer>();
        this.debug=debug;
        this.bankerHandSum=new ArrayList<Integer>();
        this.playerHandSum=new ArrayList<Integer>();
        this.playerChip=100;
        this.bet=20;
        this.stand=false;
        this.ended=false;
        this.deck=new ArrayList<Integer>();
        for(int i=1;i<=13;i++){
            for(int j=0;j<4;j++){
                this.deck.add(i);
            }
        }
        for(int i=0;i<10;i++){
            Collections.shuffle(this.deck);
        }
        for(int i=0;i<2;i++){
            this.playerHand.add(this.deck.remove(0));
            this.bankerHand.add(this.deck.remove(0));
        }
        this.calcPlayerSum();
        this.print(true);
        if( Collections.max(this.playerHandSum) >= 21){
            this.judge();
        }
    }
    
    public void continueGame(boolean newDeck){

        this.bankerHand=new ArrayList<Integer>();
        this.playerHand=new ArrayList<Integer>();
        this.bankerHandSum=new ArrayList<Integer>();
        this.playerHandSum=new ArrayList<Integer>();
        this.bet=20;
        this.stand=false;
        this.ended=false;
        if(newDeck){
            System.out.println("\n\n*** Continue with new deck ***");
            this.deck=new ArrayList<Integer>();
            for(int i=1;i<=13;i++){
                for(int j=0;j<4;j++){
                    this.deck.add(i);
                }
            }
            for(int i=0;i<10;i++){
                Collections.shuffle(this.deck);
            }   
        }else{
            System.out.println("\n\n*** Continue with original deck ***");
        }
        for(int i=0;i<2;i++){
            this.playerHand.add(this.deck.remove(0));
            this.bankerHand.add(this.deck.remove(0));
        }
        this.calcPlayerSum();
        this.print(true);
        if( Collections.max(this.playerHandSum) >= 21){
            this.judge();
        }
    }

    public String print(boolean fullMsg){
        StringBuilder m=new StringBuilder("  =========================\n");
        m.append("     Banker's Hand:");
        if(this.stand==false){
            m.append(" ?");
        }else{
            m.append(" "+util.cardConverter(this.bankerHand.get(0)));
        }
        for(int i=1;i<this.bankerHand.size();i++){
            m.append(" "+util.cardConverter(this.bankerHand.get(i)));
        }
        m.append("\n   > Banker's Sum:");
        for(int i=0;i<this.bankerHandSum.size();i++){
            m.append(" "+this.bankerHandSum.get(i));
        }
        m.append("\n     Player's Hand:");
        for(int i=0;i<this.playerHand.size();i++){
            m.append(" "+ util.cardConverter( this.playerHand.get(i)));
        }
        m.append("\n   > Player's Sum:");
        for(int i=0;i<this.playerHandSum.size();i++){
            m.append(" "+this.playerHandSum.get(i));
        }
        if(fullMsg){
            m.append("\n     Player's Remaining chip: "+this.playerChip);
            m.append("\n     Bet: "+this.bet);
        }
        if(this.debug){
            m.append("\n     Deck length: "+this.deck.size());
            m.append("\n     Deck:");
            for(int i=0;i<this.deck.size();i++){
                m.append(" "+util.cardConverter(this.deck.get(i)));
            }
        }
        m.append("\n  =========================\n");
        System.out.println(m);
        return m.toString();
    }

    public ArrayList<Integer> getPlayerHand(){
        return this.playerHand;
    }
    
    public ArrayList<Integer> getBankerHand(){
        if(this.stand){
            return this.bankerHand;
        }else{
            ArrayList<Integer> r = new ArrayList<Integer>();
            r.add(-1);
            r.add(this.bankerHand.get(1));
            return r;
        }
    }

    public int getPlayerChip(){
        return this.playerChip;
    }

    public int getBet(){
        return this.bet;
    }

    public boolean getEnded(){
        return this.ended;
    }

    public boolean getStand(){
        return this.stand;
    }

    private void calcBankerSum(){
        if(this.stand){
            ArrayList<Integer> sums=new ArrayList<Integer>();
            sums.add(0);
            for(int i=0;i<this.bankerHand.size();i++){
                int card=this.bankerHand.get(i);
                if(card==11 || card==12 || card==13){
                    for(int j=0;j<sums.size();j++){
                        sums.set(j,sums.get(j)+10);
                    }
                }else if(card!=1){
                    for(int j=0;j<sums.size();j++){
                        sums.set(j,sums.get(j)+card);
                    }
                }else{
                    int old_size=sums.size();
                    for(int j=0;j<old_size;j++){
                        sums.add(sums.get(j)+1);
                        sums.add(sums.get(j)+11);
                    }
                    for(int j=0;j<old_size;j++){
                        sums.remove(j);
                    }
                }
            }
            this.bankerHandSum.clear();
            Collections.sort(sums);
            this.bankerHandSum.add(sums.get(0));
            if(sums.size()>1){
                if (sums.get(1)<=21){
                    this.bankerHandSum.add(sums.get(1));
                }
            }
        }
    }

    private void calcPlayerSum(){
        ArrayList<Integer> sums=new ArrayList<Integer>();
        sums.add(0);
        for(int i=0;i<this.playerHand.size();i++){
            int card=this.playerHand.get(i);
            if(card==11 || card==12 || card==13){
                for(int j=0;j<sums.size();j++){
                    sums.set(j,sums.get(j)+10);
                }
            }else if(card!=1){
                for(int j=0;j<sums.size();j++){
                    sums.set(j,sums.get(j)+card);
                }
            }else{
                int old_size=sums.size();
                for(int j=0;j<old_size;j++){
                    sums.add(sums.get(j)+1);
                    sums.add(sums.get(j)+11);
                }
                for(int j=0;j<old_size;j++){
                    sums.remove(j);
                }
            }
        }
        this.playerHandSum.clear();
        Collections.sort(sums);
        this.playerHandSum.add(sums.get(0));
        if(sums.size()>1){
            if (sums.get(1)<=21){
                this.playerHandSum.add(sums.get(1));
            }
        }
    }
    
    private void bankerHit(){
        System.out.println("\n  Banker Hit");
        this.bankerHand.add(this.deck.remove(0));
        this.calcBankerSum();
        this.print(false);
        if(this.bankerHandSum.size()!=0 && Collections.max(this.bankerHandSum)>=21){
            this.judge();
        }
    }

    private void bankerHitTo17(){
        while(Collections.max(this.bankerHandSum)<17){
            this.bankerHit();
        }
    }

    public void playerHit(){
        System.out.println("\n  Player Hit");
        this.playerHand.add(this.deck.remove(0));
        this.calcPlayerSum();
        this.print(false);
        if( Collections.max(this.playerHandSum) >= 21){
            this.judge();
        }
    }
    
    public void playerDouble(){
        System.out.println("\n  Player Double");
        this.bet*=2;
        this.playerHit();
        this.playerStand();
        if(!this.ended){
            this.judge();
        }
    }

    public void playerStand(){
        System.out.println("\n  Player Stand");
        this.stand=true;
        this.calcBankerSum();
        this.print(false);
        this.bankerHitTo17();
        if(!this.ended){
            this.judge();
        }
    }

    private void judge(){
        System.out.println("\n  >>> Game Result <<<");
        this.stand=true;
        this.ended=true;
        this.calcBankerSum();
        this.calcPlayerSum();
        this.print(false);
        int b=Collections.max(this.bankerHandSum);
        int p=Collections.max(this.playerHandSum);
        if(b>21){
            System.out.println("  Banker bust");
            this.playerWin();
        }else if(p>21){
            System.out.println("  Player bust");
            this.playerLose();
        }else{
            if(p>b){
                this.playerWin();
            }else if(b>p){
                this.playerLose();
            }else{
                this.gamePush();
            }
        }
    }   

    private void playerWin(){
        System.out.println("  Player Win!");
        this.playerChip+=this.bet;
        System.out.println("  Remaining chip: "+this.playerChip+"\n");
    }

    private void playerLose(){
        System.out.println("  Player Lose!");
        this.playerChip-=this.bet;
        System.out.println("  Remaining chip: "+this.playerChip+"\n");
    }
    
    private void gamePush(){
        System.out.println("  Game Push!");
        System.out.println("  Remaining chip: "+this.playerChip+"\n");
    }
}