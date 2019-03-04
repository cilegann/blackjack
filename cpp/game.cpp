//#include"blackjack.cpp"
#include<iostream>
#include<vector>
using namespace std;
int main(){
    vector<int> a;
    for(int i=0;i<10;i++){
        a.push_back(i);
    }
    for(int i=0;i<2;i++){
        a.erase(a.begin());
    }
    for(int i=0;i<a.size();i++){
        cout<<a[i]<<" ";
    }
}