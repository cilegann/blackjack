import random

class blackjack():
    def __init__(self,debug=False):
        print("\n\n***** New Player with new shuffled deck *****")
        self.bankerHand=[]
        self.playerHand=[]
        self.debug=debug
        self.bankerHandSum='?'
        self.playerHandSum='?'
        self.playerChip=100
        self.bet=20
        self.stand=False
        self.ended=False
        self.gameResult=0
        self.deck=[i+1 for i in range(13) for j in range(4)]
        for i in range(5):
            self.shuffleDeck()
        for i in range(2):
            self.bankerHand.append(self.hit())
            self.playerHand.append(self.hit())
        self.calcPlayerSum()
        print(self.getMsg())
        if max(self.playerHandSum)==21:
            self.judge()
            
    def newPlayer(self):
        self.__init__()

    def continueGame(self,newDeck=False):
        self.bankerHand=[]
        self.playerHand=[]
        self.bankerHandSum='?'
        self.playerHandSum='?'
        self.bet=20
        self.stand=False
        self.ended=False
        self.gameResult=0
        if(self.playerChip<self.bet):
            print("Chip not enough for bet, end game.")
            self.ended=True
            return
        if newDeck:
            print("\n\n*** Continue with new deck ***")
            self.deck=[i+1 for i in range(13) for j in range(4)]
            for i in range(5):
                self.shuffleDeck()
        else:
            print("\n\n*** Continue with original deck ***")
        for i in range(2):
            self.bankerHand.append(self.hit())
            self.playerHand.append(self.hit())
        self.calcPlayerSum()
        print(self.getMsg())
        if max(self.playerHandSum)==21:
            self.judge()
       

    def shuffleDeck(self):
        if self.debug:
            print("Shuffling deck")
        random.shuffle(self.deck)

    def getDeck(self):
        return self.deck

    def getBankerHand(self):
        return self.bankerHand

    def getPlayerHand(self):
        return self.playerHand

    def getPlayerChip(self):
        return self.playerChip

    def getBankerHandSum(self):
        self.calcBankerSum()
        return self.bankerHandSum
    
    def getPlayerHandSum(self):
        self.calcPlayerSum()
        return self.playerHandSum

    def getStatus(self):
        m="B["+("?" if self.stand==False else str(self.bankerHand[0]))
        for card in self.bankerHand[1:]:
            m+=(","+str(card) if (card!=1 and card<11) else(",A" if card==1 else(",J" if card==11 else(",Q" if card==12 else ",K"))))
        m+="]"
        m+=("P["+(str(self.playerHand[0]) if (self.playerHand[0]!=1 and self.playerHand[0]<11) else("A" if self.playerHand[0]==1 else("J" if self.playerHand[0]==11 else("Q" if self.playerHand[0]==12 else "K")))))
        for card in self.playerHand[1:]:
            m+=(","+str(card) if (card!=1 and card<11) else(",A" if card==1 else(",J" if card==11 else(",Q" if card==12 else ",K"))))
        m+="]"
        m+=("C["+str(self.playerChip)+"]")
        m+=("BET["+str(self.bet)+"]")
        return m

    def getMsg(self,fullMsg=False):
        m="=========================\n"
        m+="    Banker's Hand:"
        if not self.stand:
            m+=" ?"
            for card in self.bankerHand[1:]:
                m+=(" "+(str(card) if (card!=1 and card<11) else("A" if card==1 else("J" if card==11 else("Q" if card==12 else "K")))))
        else:
            for card in self.bankerHand:
                m+=(" "+(str(card) if (card!=1 and card<11) else("A" if card==1 else("J" if card==11 else("Q" if card==12 else "K")))))
        m+=("\n  > Banker's Sum: "+str(self.bankerHandSum))
        
        m+="\n    Player's Hand:"
        for card in self.playerHand:
            m+=(" "+(str(card) if (card!=1 and card<11) else("A" if card==1 else("J" if card==11 else("Q" if card==12 else "K")))))
        m+=("\n  > Player's Sum: "+str(self.playerHandSum))
        
        if fullMsg:
            m+=("\n    Player's Remainig chip: "+str(self.playerChip)+"\n    Bet: "+str(self.bet))
        
        if self.debug:
            m+=("\n    MSG: "+self.getStatus())
            m+=("\n    Deck Length: "+str(len(self.deck)))
            m+=("\n    Deck: "+str(self.deck))

        m+="\n=========================\n"
        return m

    def getStand(self):
        return self.stand

    def getEnded(self):
        return self.ended

    def getGameResult(self):
        return self.gameResult
        
    def calcBankerSum(self):
        if self.stand:
            cards=self.bankerHand
            sums=[0]
            for card in cards:
                toadd=card
                if card in {11,12,13}:
                    toadd=10
                if card!=1:
                    for i in range(len(sums)):
                        sums[i]+=toadd
                else:
                    sums=[i+j for i in sums for j in [1,11]]
            if len(sums)>1:
                sums.sort()
                if sums[1]>21:
                    sums=sums[:1]
                else:
                    sums=sums[:2]
            self.bankerHandSum=sums

    def calcPlayerSum(self):
        cards=self.playerHand
        sums=[0]
        for card in cards:
            toadd=card
            if card in {11,12,13}:
                toadd=10
            if card!=1:
                for i in range(len(sums)):
                    sums[i]+=toadd
            else:
                sums=[i+j for i in sums for j in [1,11]]
        if len(sums)>1:
            sums.sort()
            if sums[1]>21:
                sums=sums[:1]
            else:
                sums=sums[:2]
        self.playerHandSum=sums

    def hit(self):
        return self.deck.pop(0)

    def bankerHit(self):
        print("\nBanker Hit")
        self.bankerHand.append(self.hit())
        self.calcBankerSum()
        print(self.getMsg())
        if self.bankerHandSum!="?" and max(self.bankerHandSum)>=21:
            self.judge()

    def playerHit(self):
        print("\nPlayer Hit")
        self.playerHand.append(self.hit())
        self.calcPlayerSum()
        print(self.getMsg())
        if max(self.playerHandSum)>=21:
            self.judge()

    def bankerHitsTo17(self):
        while(max(self.bankerHandSum)<17):
            self.bankerHit()

    def playerStand(self):
        print("\nPlayer Stand")
        self.stand=True
        self.calcBankerSum()
        print(self.getMsg())
        self.bankerHitsTo17()
        if not self.ended:
            self.judge()

    def playerDouble(self):
        print("\nPlayer Double")
        self.bet*=2
        self.playerHit()
        if(not self.ended):
            self.playerStand()

    def judge(self):
        print("\n>>> Game Result <<<")
        self.stand=True
        self.calcBankerSum()
        self.calcPlayerSum()
        b=max(self.bankerHandSum)
        p=max(self.playerHandSum)
        print(self.getMsg(fullMsg=True))
        if b>21:
            print("Banker bust")
            self.playerWin()
        else:
            if p>21:
                print("Player bust")
                self.playerLose()
            else:
                self.playerWin() if p>b else(self.playerLose() if p<b else self.gamePush())
        self.ended=True

    def playerWin(self):
        print("Player Win!")
        self.playerChip+=self.bet
        self.gameResult=1
        print("Remaining chip: ",self.playerChip,"\n")
    
    def playerLose(self):
        print("Player Lose!")
        self.playerChip-=self.bet
        self.gameResult=-1
        print("Remaining chip: ",self.playerChip,"\n")

    def gamePush(self):
        print("PUSH!")
        self.gameResult=0
        print("Remaining chip: ",self.playerChip,"\n")