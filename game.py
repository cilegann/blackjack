from blackjack import blackjack as bj

command=""
startup=True
while(1):
    if startup:
        b=bj()
        startup=False

    while(not b.getEnded()):
        command=input("[H] for hit (get another card) / [S] for stand / [D] for double : ")
        if command in {"H","h"}:
            b.playerHit()
        elif command in {"S","s"}:
            b.playerStand()
        elif command in {"D","d"}:
            b.playerDouble()
        else:
            print("Command illegal")

    while(1):
        command=input("[R] for new game / [C] for continue / [N]: for new deck")
        if command in {"R","r"}:
            b.newPlayer()
            break
        elif command in {"C","c"}:
            b.continueGame()
            break
        elif command in {"N","n"}:
            b.continueGame(newDeck=True)
            break
        else:
            print("Command illegal")
    
