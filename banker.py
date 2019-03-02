import random

class banker():
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
        m="B["+("X" if self.stand==False else str(self.bankerHand[0]))
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
        m+="Banker hand:"
        if not self.stand:
            m+=" X"
            for card in self.bankerHand[1:]:
                m+=(" "+(str(card) if (card!=1 and card<11) else("A" if card==1 else("J" if card==11 else("Q" if card==12 else "K")))))
        else:
            for card in self.bankerHand:
                m+=(" "+(str(card) if (card!=1 and card<11) else("A" if card==1 else("J" if card==11 else("Q" if card==12 else "K")))))
        
        m+=("\nBanker sum: "+str(self.bankerHandSum))
        m+="\nPlayer hand:"
        for card in self.playerHand:
            m+=(" "+(str(card) if (card!=1 and card<11) else("A" if card==1 else("J" if card==11 else("Q" if card==12 else "K")))))
        m+=("\nPlayer sum: "+str(self.playerHandSum))
        m+=("\nPlayer Remainig chip: "+str(self.playerChip)+"\nBet: "+str(self.bet))
        m+=("\n"+self.getStatusMsg())
        m+="\n========================="
        return m

    def calcBankerSum(self):
        #TODO: implement
        pass
    
    def calcPlayerSum(self):
        #TODO: implement
        pass

    def hit(self):
        c=self.deck[0]
        self.deck=self.deck[1:]
        return c

    def bankerHit(self):
        self.bankerHand.append(self.hit())

    def playerHit(self):
        self.playerHand.append(self.hit())

    def playerStand(self):
        self.stand=True

    def playerWin(self):
        self.playerChip+=self.bet
        
    def playerLose(self):
        self.playerChip-=self.bet
