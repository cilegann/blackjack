g++ -std=c++0x -c -o blackjack.o blackjack.cpp
ar -rcs libblackjack.a blackjack.o
g++ -std=c++0x game.cpp -L. -lblackjack -o game