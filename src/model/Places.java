package model;

public enum Places {

    BOGOTA(227,256),BRASILIA(319,294), MIAMI(212,173), NEW_YORK(275,121), MADRID(429,134), PARIS(445,112), TOKIO(810,148), OTAWA(255,107), MILAN(483,135), MOSCOW(630,74), SYDNEY(841,372),PEKIN(709,151),BERLIN(512,86),LONDON(436,84);

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
