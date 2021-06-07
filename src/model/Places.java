package model;

public enum Places {
    BOGOTA(234,258),BRASILIA(297,291), MIAMI(217,175), NEW_YORK(307,114), MADRID(434,142), PARIS(455,121), TOKIO(817,154), OTAWA(262,112), MILAN(487,140), MOSCOW(630,74), SYDNEY(846,377),PEKIN(709,151),BERLIN(512,95),LONDON(439,88);
    private int x;
    private int y;
    private Places(int x,int y){
        this.x = x;
        this.y = y;
    }
    public int  getXPosition(){
        return x;
    }
    public int getYPosition(){
        return y;
    }
}
