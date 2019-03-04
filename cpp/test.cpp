#include<iostream>
#include<vector>
using namespace std;
vector<int> add(vector<int> a){
    for(int i=0;i<a.size();i++){
        a[i]++;
    }
    return a;
}
int main(){
    vector<int> a;
    a.push_back(1);
    a.push_back(2);
    for(int i=0;i<a.size();i++){
        cout<<a[i]<<" ";
    }
    cout<<endl;
    a=add(a);
    for(int i=0;i<a.size();i++){
        cout<<a[i]<<" ";
    }
    cout<<endl;
}