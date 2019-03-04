#include<vector>
using namespace std;
class blackjack{
private:
    vector<int> bankerHand;
    vector<int> playerHand;
    vector<int> bankerHandSum;
    vector<int> playerHandSum;
    vector<int> deck;
    bool debug;
    bool stand;
    bool ended;
    int playerChip;
    int bet;
    int gameResult;
    void calcBankerSum();
    void calcPlayerSum();
    void bankerHit();
    void bankerHitTo17();
    void judge();
    void playerWin();
    void playerLose();
    void gamePush();
public:
    blackjack(bool debug);
    void continueGame(bool newDeck);
    string getMsg(bool fullMsg);
    vector<int> getPlayerHand();
    vector<int> getBankerHand();
    int getPlayerChip();
    int getBet();
    bool getEnded();
    bool getStand();
    int getGameResult();
    void playerHit();
    void playerDouble();
    void playerStand();
    
    
};