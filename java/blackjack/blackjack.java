package blackjack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

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
            this.deck=new ArrayList<Integer>();
            for(int i=1;i<=13;i++){
                for(int j=0;j<4;j++){
                    this.deck.add(i);
                }
            }
            for(int i=0;i<10;i++){
                Collections.shuffle(this.deck);
            }
        }
    }

    public String print(){
        StringBuilder m=new StringBuilder("=========================\n");
        m.append("    Banker's Hand:");
        if(this.stand==false){
            m.append(" ?");
        }else{
            m.append(" "+util.cardConverter(this.bankerHand.get(0)));
        }
        for(int i=1;i<this.bankerHand.size();i++){
            m.append(" "+util.cardConverter(this.bankerHand.get(i)));
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
        m.append("\n=========================\n");
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

    public boolean getEnded(){
        return this.ended;
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
                }
            }
            this.bankerHandSum.clear();
            Collections.sort(sums);
            this.bankerHandSum.add(0);
            if(sums.size()>1){
                if (sums.get(1)<=21){
                    this.bankerHandSum.add(1);
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
            }
        }
        this.playerHandSum.clear();
        Collections.sort(sums);
        this.playerHandSum.add(0);
        if(sums.size()>1){
            if (sums.get(1)<=21){
                this.playerHandSum.add(1);
            }
        }
    }
    
    private void bankerHit(){
        this.bankerHand.add(this.deck.remove(0));
        this.calcBankerSum();
        if(this.bankerHandSum.get(0)!=-1 && Collections.max(this.bankerHandSum)>=21){
            this.judge();
        }
    }

    public void playerHit(){
        this.playerHand.add(this.deck.remove(0));
        this.calcPlayerSum();
        if(this.playerHand
    }
    
    public void playerDouble(){

    }

    private void judge(){

    }   
}