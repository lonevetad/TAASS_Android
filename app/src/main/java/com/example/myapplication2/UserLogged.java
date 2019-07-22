package com.example.myapplication2;

//Singleton UserLogged
public class UserLogged{

    private static UserLogged myIstance = null;

    public Long userId;
    public String userName;

    protected UserLogged(){}

    public static synchronized UserLogged getInstance(){
        if(myIstance == null)
            myIstance = new UserLogged();
        return myIstance;
    }

    public static void setIstanceData(Long id, String userName){
        UserLogged istanza = UserLogged.getInstance();
        myIstance.userId = id;
        myIstance.userName = userName;
    }

}
