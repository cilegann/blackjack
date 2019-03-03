package blackjack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import blackjack.util.util;
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
        for(int i=0;i<this.deck.size();i++){
            System.out.print(util.cardConverter(this.deck.get(i))+" ");
        }
    }
    
    public String print(){
        StringBuilder m=new StringBuilder("=========================\n");
        m.append("    Banker's Hand:");
        if(this.stand==false){
            m.append(" ?");
        }else{
            m.append(" "+util.cardConverter(this.bankerHand.get(0)));
            for(int i=1;i<this.bankerHand.size();i++){
                m.append(" "+util.cardConverter(this.bankerHand.get(i)));
            }
        }
        m.append("\n  > Banker's Sum:");
        for(int i=0;i<this.bankerHandSum.size();i++){
            m.append(" "+this.bankerHandSum.get(i));
        }
        m.append("\n    Player's Hand:");
        for(int i=0;i<this.playerHand.size();i++){
            m.append(" "+this.playerHand.get(i));
        }
        m.append("\n  > Player's Sum:");
        for(int i=0;i<this.playerHandSum.size();i++){
            m.append(" "+this.playerHandSum.get(i));
        }
        m.append("\n    Player's Remaining chip: "+this.playerChip);
        m.append("\n    Bet: "+this.bet);
        if(this.debug){
            m.append("\n    Deck length: "+this.deck.size());
            m.append("\n    Deck:");
            for(int i=0;i<this.deck.size();i++){
                m.append(" "+util.cardConverter(this.deck.get(i)));
            }
        }
        m.append("\n=========================\n")
        System.out.println(m);
        return m.toString();
    }
}