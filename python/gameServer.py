from blackjack import blackjack as bj
import socket




HOST='0.0.0.0'
PORT=6566

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1) #reuse tcp
sock.bind((HOST, PORT))
sock.listen(5)
print("BlackJack server is running at "+HOST+":"+str(PORT))

while(1):
    client, address = s.accept()
    print(str(address)+" connected")
    b=bj(debug=True)
    while(True):
        s.send(str.encode(b.getStatusMsg))
        c=s.recv(1024).decode('utf-8')
        