from banker import banker
b=banker()
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