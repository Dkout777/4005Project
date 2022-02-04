public class Inspector {
    private int num;
    private Component component;
    private boolean blocked;
    private boolean inspecting;
    public Inspector(int num){
        this.num = num;
        this.blocked = false;
        inspecting = false;
    }
    public void acceptComponent(Component component){
        this.component = component;
        inspecting = true;
    }
    public Component giveComponent(){
        Component give = this.component;
        this.component = null;
        inspecting = false;
        return give;
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

}
