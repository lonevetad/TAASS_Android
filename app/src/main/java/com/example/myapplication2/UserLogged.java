package com.example.myapplication2;

//Singleton UserLogged
public class UserLogged{

    private static UserLogged myIstance = null;

    public Long userId;
    public String userName = "";
    public String email = "";
    public boolean isLogged = false;

    protected UserLogged(){}

    public static synchronized UserLogged getInstance(){
        if(myIstance == null)
            myIstance = new UserLogged();
        return myIstance;
    }

    public static void setIstanceData(Long id, String userName,String email){
        UserLogged istanza = UserLogged.getInstance();
        myIstance.userId = id;
        myIstance.userName = userName;
        myIstance.isLogged = true;
        myIstance.email = email;
    }

    public void logout(){
        if(this.isLogged){
            this.isLogged = false;
            this.userId = new Long(-1);
            this.userName = "";
            this.email = "";
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

}
