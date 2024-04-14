package Cas2_Threading.BankExample;

public interface IBank {
    void transfer(int from,int to,int amount);

    int count();

    int getTotalBalance();

    //napravicemo razlicite banke koje sinhronizuju niti na razlicite nacine
}
