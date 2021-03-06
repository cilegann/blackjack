#include "blackjack.h"
#include<iostream>
#include<vector>
#include<algorithm>
#include <ctime>
#include <cstdlib>
using namespace std;
int myrandom (int i) { return std::rand()%i;}
string cardConverter(int in){
    if(in==1){
        return "A";
    }else if(in==11){
        return "J";
    }else if(in==12){
        return "Q";
    }else if(in==13){
        return "K";
    }else{
        return to_string(in);
    }
}

blackjack::blackjack(bool debug){
    cout<<"\n\n***** New Player with new shuffled deck *****\n";
    this->bankerHand.clear();
    this->playerHand.clear();
    this->bankerHandSum.clear();
    this->bankerHandSum.push_back(0);
    this->playerHandSum.clear();
    this->playerHandSum.push_back(0);
    this->debug=debug;
    this->playerChip=100;
    this->bet=20;
    this->stand=false;
    this->ended=false;
    this->gameResult=0;
    this->deck.clear();

    for(int i=1;i<=13;i++){
        for(int j=0;j<4;j++){
            this->deck.push_back(i);
        }
    }
    srand ( unsigned ( std::time(0) ) );
    for(int i=0;i<10;i++){
        random_shuffle(this->deck.begin(),this->deck.end(),myrandom);
    }
    for(int i=0;i<2;i++){
        this->playerHand.push_back(this->deck.front());
        this->deck.erase(this->deck.begin());
        this->bankerHand.push_back(this->deck.front());
        this->deck.erase(this->deck.begin());
    }
    this->calcPlayerSum();
    cout<<this->getMsg(1);
    if(*max_element(this->playerHandSum.begin(),this->playerHandSum.end())>=21){
        this->judge();
    }
}

void blackjack::continueGame(bool newDeck){
    this->bankerHand.clear();
    this->playerHand.clear();
    this->bankerHandSum.clear();
    this->playerHandSum.clear();
    this->bet=20;
    this->stand=false;
    this->ended=false;
    this->gameResult=0;
    if(newDeck){
        cout<<"\n\n*** Continue with new deck ***\n";
        this->deck.clear();
        for(int i=1;i<=13;i++){
            for(int j=0;j<4;j++){
                this->deck.push_back(i);
            }
        }
        srand ( unsigned ( std::time(0) ) );
        for(int i=0;i<10;i++){
            random_shuffle(this->deck.begin(),this->deck.end(),myrandom);
        }
    }else{
        cout<<"\n\n*** Continue with original deck ***\n";
    }
    
    for(int i=0;i<2;i++){
        this->playerHand.push_back(this->deck.front());
        this->deck.erase(this->deck.begin());
        this->bankerHand.push_back(this->deck.front());
        this->deck.erase(this->deck.begin());
    }
    this->calcPlayerSum();
    cout<<this->getMsg(1);
    if(*max_element(this->playerHandSum.begin(),this->playerHandSum.end())>=21){
        this->judge();
    }
}

string blackjack::getMsg(bool fullMsg){
    string m="  =========================\n";
    m+=("     Banker's Hand:");
    if(!this->stand)    m+=" ?";
    else    m+=(" "+cardConverter(this->bankerHand[0]));
    for(int i=1;i<this->bankerHand.size();i++)  m+=(" "+cardConverter(this->bankerHand[i]));

    m+=("\n   > Banker's Sum:");
    for(int i=0;i<this->bankerHandSum.size();i++)    m+=(" "+to_string(this->bankerHandSum[i]));

    m+=("\n     Player's Hand:");
    for(int i=0;i<this->playerHand.size();i++)  m+=(" "+cardConverter(this->playerHand[i]));

    m+=("\n     Player's Sum:");
    for(int i=0;i<this->playerHandSum.size();i++)  m+=(" "+to_string(this->playerHandSum[i]));

    if(fullMsg){
        m+=("\n     Player's Remaining chip: "+to_string(this->playerChip));
        m+=("\n     Bet: "+to_string(this->bet));
    }

    if(this->debug){
        m+=("\n     Deck length: "+to_string(this->deck.size()));
        m+=("\n     Deck:");
        for(int i=0;i<this->deck.size();i++)    m+=(" "+cardConverter(this->deck[i]));

    }
    m+=("\n  =========================\n");
    return m;
}

vector<int> blackjack::getPlayerHand(){
    return this->playerHand;
}

vector<int> blackjack::getBankerHand(){
    if(this->stand){
        return this->bankerHand;
    }else{
        vector<int> r;
        r.push_back(-1);
        r.push_back(this->bankerHand[1]);
        return r;
    }
}

int blackjack::getPlayerChip(){
    return this->playerChip;
}

int blackjack::getBet(){
    return this->bet;
}

bool blackjack::getEnded(){
    return this->ended;
}

bool blackjack::getStand(){
    return this->stand;
}

int blackjack::getGameResult(){
    return this->gameResult;
}

void blackjack::calcBankerSum(){
    if(this->stand){
        vector<int> sums;
        sums.push_back(0);
        for(int i=0;i<this->bankerHand.size();i++){
            int card=this->bankerHand[i];
            if(card==11 || card==12 || card==13){
                for(int i=0;i<sums.size();i++){
                    sums[i]+=10;
                }
            }else if(card!=1){
                for(int i=0;i<sums.size();i++){
                    sums[i]+=card;
                }
            }else{
                int old_size=sums.size();
                for(int j=0;j<old_size;j++){
                    sums.push_back(sums[j]+1);
                    sums.push_back(sums[j]+11);
                }
                for(int j=0;j<old_size;j++){
                    sums.erase(sums.begin());
                }
            }
        }
        this->bankerHandSum.clear();
        sort(sums.begin(),sums.end());
        this->bankerHandSum.push_back(sums[0]);
        if(sums.size()>1 && sums[1]<=21){
            this->bankerHandSum.push_back(sums[1]);
        }
    }
}

void blackjack::calcPlayerSum(){
    vector<int> sums;
    sums.push_back(0);
    for(int i=0;i<this->playerHand.size();i++){
        int card=this->playerHand[i];
        if(card==11 || card==12 || card==13){
            for(int i=0;i<sums.size();i++){
                sums[i]+=10;
            }
        }else if(card!=1){
            for(int i=0;i<sums.size();i++){
                sums[i]+=card;
            }
        }else{
            int old_size=sums.size();
            for(int j=0;j<old_size;j++){
                sums.push_back(sums[j]+1);
                sums.push_back(sums[j]+11);
            }
            for(int j=0;j<old_size;j++){
                sums.erase(sums.begin());
            }
        }
    }
    this->playerHandSum.clear();
    sort(sums.begin(),sums.end());
    this->playerHandSum.push_back(sums[0]);
    if(sums.size()>1 && sums[1]<=21){
        this->playerHandSum.push_back(sums[1]);
    }
}

void blackjack::bankerHit(){
    cout<<("\n  Banker Hit\n");
    this->bankerHand.push_back(this->deck.front());
    this->deck.erase(this->deck.begin());
    this->calcBankerSum();
    cout<<this->getMsg(0);
    if(this->bankerHandSum[0]!=0 && *max_element(this->bankerHandSum.begin(),this->bankerHandSum.end())>=21){
        this->judge();
    }
}

void blackjack::playerHit(){
    cout<<("\n  Player Hit\n");
    this->playerHand.push_back(this->deck.front());
    this->deck.erase(this->deck.begin());
    this->calcPlayerSum();
    cout<<this->getMsg(0);
    if(this->playerHandSum[0]!=0 && *max_element(this->playerHandSum.begin(),this->playerHandSum.end())>=21){
        this->judge();
    }
}

void blackjack::bankerHitTo17(){
    while(*max_element(this->bankerHandSum.begin(),this->bankerHandSum.end())<17){
        this->bankerHit();
    }
}

void blackjack::playerStand(){
    cout<<("\n  Player Stand\n");
    this->stand=1;
    this->calcBankerSum();
    cout<<this->getMsg(0);
    this->bankerHitTo17();
    if(!this->ended){
        this->judge();
    }
}

void blackjack::playerDouble(){
    cout<<("\n  Player Double\n");
    this->bet*=2;
    this->playerHit();
    if(!this->ended){
        this->playerStand();
    }
    if(!this->ended){
        this->judge();
    }
}

void blackjack::judge(){
    cout<<("\n  >>> Game Result <<<\n");
    this->stand=1;
    this->calcBankerSum();
    this->calcPlayerSum();
    cout<<this->getMsg(0);
    int b=*max_element(this->bankerHandSum.begin(),this->bankerHandSum.end());
    int p=*max_element(this->playerHandSum.begin(),this->playerHandSum.end());
    if(b>21){
        cout<<("  Banker bust\n");
        this->playerWin();
    }else if(p>21){
        cout<<("  Player bust\n");
        this->playerLose();
    }else{
        if(p>b){
            this->playerWin();
        }else if(b>p){
            this->playerLose();
        }else{
            this->gamePush();
        }
    }
    this->ended=true;
}

void blackjack::playerWin(){
    cout<<("  Player Win!\n");
    this->playerChip+=this->bet;
    cout<<("  Remaining chip: "+to_string(this->playerChip)+"\n\n");
    this->gameResult=1;
}

void blackjack::playerLose(){
    cout<<("  Player Lose!\n");
    this->playerChip-=this->bet;
    cout<<("  Remaining chip: "+to_string(this->playerChip)+"\n\n");
    this->gameResult=-1;
}

void blackjack::gamePush(){
    cout<<("  Game Push!\n");
    cout<<("  Remaining chip: "+to_string(this->playerChip)+"\n\n");
    this->gameResult=0;
}