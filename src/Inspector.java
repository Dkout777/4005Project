public class Inspector {
    private int num;
    private double idleTime = 0;
    private double idleStart;
    int type;
    private Component component;
    private boolean blocked;
    private boolean inspecting;
    public Inspector(int num){
        this.num = num;
        this.blocked = false;
        inspecting = false;
    }
    public int getType(){
        if( component.getNum() == 1){
            return 0;
        }
        else if(component.getNum() ==2){
            return 1;
        }
        else return 2;
    }

    /**
     * Accepts component and sets inspecting to true
     * @param component component accepted
     */
    public void acceptComponent(Component component){
        this.component = component;
        inspecting = true;
    }

    /**
     * returns components and clears it, and sets inspecting to false.
     * @return
     */
    public Component giveComponent(){
        Component give = this.component;
        this.component = null;
        inspecting = false;
        return give;
    }

    public void setIdleStart(double idleStart) {
        this.idleStart = idleStart;
    }


    public void setBlocked(boolean bool){
        blocked = bool;
    }


    public boolean getBlocked(){
        return blocked;
    }


    public boolean isInspecting(){return inspecting;}


    public int getNum(){
        return num;
    }


    public Component getComponent() {
        return component;
    }


    public void setInspecting(boolean inspecting) {
        this.inspecting = inspecting;
    }

    /**
     * Gets  called when no longer blocked, idle start time gets subtracted  from end time and adds it to idleTime
     * @param idleEnd end of idle time
     */
    public void addIdle(double idleEnd){
        idleTime = idleTime + (idleEnd-idleStart);
        idleStart = -1;
    }
    public double getIdleTime() {
        return idleTime;
    }



}
