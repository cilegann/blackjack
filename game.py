from blackjack import blackjack as bj
b=bj()
print(b.getStatus())
b.playerStand()
print(b.getStatus())
b.playerLose()
print(b.getStatus())

b.playerStand()
print(b.getStatus())
b.playerLose()
print(b.getStatus())
b.newPlayer()
print(b.getStatus())