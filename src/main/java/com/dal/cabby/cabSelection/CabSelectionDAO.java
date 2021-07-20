package com.dal.cabby.cabSelection;

public class CabSelectionDAO {
    public String cabName;
    public double cabDistanceFromOrigin;
    public int driver_Id;
    public String routeTrafficDensity;
    public int cabSpeedOnRoute;

    public String toString(){
        String result=String.format("%s -> Driver_ID: %s ",this.cabName,this.driver_Id);
        return result;
    }

    public CabSelectionDAO(String cabName, double cabDistanceFromOrigin, int driver_Id, String routeTrafficDensity,
                           int cabSpeedOnRoute){
        this.cabName=cabName;
        this.cabDistanceFromOrigin=cabDistanceFromOrigin;
        this.driver_Id=driver_Id;
        this.routeTrafficDensity=routeTrafficDensity;
        this.cabSpeedOnRoute=cabSpeedOnRoute;
    }

    public String getCabName() {
        return cabName;
    }

    public void setCabName(String cabName) {
        this.cabName = cabName;
    }

    public double getCabDistanceFromOrigin() {
        return cabDistanceFromOrigin;
    }

    public void setCabDistanceFromOrigin(double cabDistanceFromOrigin) {
        this.cabDistanceFromOrigin = cabDistanceFromOrigin;
    }

    public int getDriver_Id() {
        return driver_Id;
    }

    public void setDriver_Id(int driver_Id) {
        this.driver_Id = driver_Id;
    }

    public String getRouteTrafficDensity() {
        return routeTrafficDensity;
    }

    public void setRouteTrafficDensity(String routeTrafficDensity) {
        this.routeTrafficDensity = routeTrafficDensity;
    }

    public int getCabSpeedOnRoute() {
        return cabSpeedOnRoute;
    }

    public void setCabSpeedOnRoute(int cabSpeedOnRoute) {
        this.cabSpeedOnRoute = cabSpeedOnRoute;
    }
}
