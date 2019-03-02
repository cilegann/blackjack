import random

class blackjack():
    def __init__(self):
        self.bankerHand=[]
        self.playerHand=[]
        self.bankerHandSum='?'
        self.playerHandSum='?'
        self.playerChip=100
        self.bet=20
        self.stand=False
        self.deck=[i+1 for i in range(13) for j in range(4)]
        self.shuffleDeck()
        self.shuffleDeck()
        self.bankerHit()
        self.bankerHit()
        self.playerHit()
        self.playerHit()

    def newPlayer(self):
        self.__init__()

    def newGame(self):
        self.bankerHand=[]
        self.playerHand=[]
        self.stand=False
        self.deck=[i+1 for i in range(13) for j in range(4)]
        self.shuffleDeck()
        self.shuffleDeck()
        self.bankerHit()
        self.bankerHit()
        self.playerHit()
        self.playerHit()

    def shuffleDeck(self):
        random.shuffle(self.deck)

    def getDeck(self):
        return self.deck

    def getBankerHand(self):
        return self.bankerHand

    def getPlayerHand(self):
        return self.playerHand

    def getPlayerChip(self):
        return self.playerChip

    def getStatusMsg(self):
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

    def getStatus(self):
        m="\n=========================\n"
        m+="Banker's Hand:"
        if not self.stand:
            m+=" ?"
            for card in self.bankerHand[1:]:
                m+=(" "+(str(card) if (card!=1 and card<11) else("A" if card==1 else("J" if card==11 else("Q" if card==12 else "K")))))
        else:
            for card in self.bankerHand:
                m+=(" "+(str(card) if (card!=1 and card<11) else("A" if card==1 else("J" if card==11 else("Q" if card==12 else "K")))))
        
        m+=("\nBanker's Sum: "+str(self.bankerHandSum))
        m+="\nPlayer's Hand:"
        for card in self.playerHand:
            m+=(" "+(str(card) if (card!=1 and card<11) else("A" if card==1 else("J" if card==11 else("Q" if card==12 else "K")))))
        m+=("\nPlayer's Sum: "+str(self.playerHandSum))
        m+=("\nPlayer's Remainig chip: "+str(self.playerChip)+"\nBet: "+str(self.bet))
        m+=("\n"+self.getStatusMsg())
        m+="\n========================="
        return m

    def calcBankerSum(self):
        if self.stand:
            cards=self.bankerHand
            cards.sort()
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
            sums=list(filter(lambda a: a<=21,sums))
            sums.sort()
            self.bankerHandSum=sums

            
    def calcPlayerSum(self):
        cards=self.playerHand
        cards.sort()
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
        sums=list(filter(lambda a: a<=21,sums))
        sums.sort()
        self.playerHandSum=sums

    def hit(self):
        c=self.deck[0]
        self.deck=self.deck[1:]
        return c

    def bankerHit(self):
        print("Banker Hit")
        self.bankerHand.append(self.hit())
        self.calcBankerSum()

    def playerHit(self):
        print("Player Hit")
        self.playerHand.append(self.hit())
        self.calcPlayerSum()

    def bankerHitsTo17(self):
        while(max(self.bankerHandSum)<17):
            self.bankerHit()

    def playerStand(self):
        self.stand=True
        self.calcBankerSum()
        self.bankerHitsTo17()

    def playerWin(self):
        self.playerChip+=self.bet
        
    def playerLose(self):
        self.playerChip-=self.bet
