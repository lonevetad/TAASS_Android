package com.example.myapplication2;

//Singleton UserLogged
public class UserLogged{

    private static UserLogged myIstance = null;

    public Long userId;
    public String userName, userEmail;
    public boolean isLogged;

    protected UserLogged(){
        userName = "";
        userEmail = "";
        isLogged = false;
    }

    public static synchronized UserLogged getInstance(){
        if(myIstance == null)
            myIstance = new UserLogged();
        return myIstance;
    }

    public static void setIstanceData(Long id, String userName){
        UserLogged istanza = UserLogged.getInstance();
        myIstance.userId = id;
        myIstance.userName = userName;
        myIstance.isLogged = true;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
