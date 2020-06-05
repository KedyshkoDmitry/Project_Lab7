package bsu.rfe.java.group8.lab7.Kedyshko.var7;

public class Peer {
    private String name;
    private String address;

    public void setPeer(String name, String address){
        this.name = name;
        this.address = address;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }
}