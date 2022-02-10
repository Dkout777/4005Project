public class Inspector {
    private int num;
    private int idleTime = 0;
    private int idleStart;
    private Component component;
    private boolean blocked;
    private boolean inspecting;
    public Inspector(int num){
        this.num = num;
        this.blocked = false;
        inspecting = false;
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

    public void setIdleStart(int idleStart) {
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
    public void addIdle(int idleEnd){
        idleTime = idleTime + (idleEnd-idleStart);
        idleStart = -1;
    }
    public int getIdleTime() {
        return idleTime;
    }



}
