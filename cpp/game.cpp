#include"blackjack.cpp"
#include<iostream>
#include<vector>
using namespace std;
int main(){
    string cmd;
    blackjack b=blackjack(1);
    while(1){
        while(!b.getEnded()){
            cout<<("[H] for hit (get another card) / [S] for stand / [D] for double : ");
            cin>>cmd;
            if(cmd=="H")    b.playerHit();
            if(cmd=="S")    b.playerStand();
            if(cmd=="D")    b.playerDouble();
        }
        cout<<"GR: "<<b.getGameResult()<<endl;
        while(1){
            cout<<("[R] for new game / [C] for continue / [N] for new deck : ");
            cin>>cmd;
            if(cmd=="R"){
                b=blackjack(1);
                break;
            }
            if(cmd=="C"){
                b.continueGame(0);
                break;
            }
            if(cmd=="N"){
                b.continueGame(1);
                break;
            }
        }
    }
    
}