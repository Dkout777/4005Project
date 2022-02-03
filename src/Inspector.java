public class Inspector {
    int num;
    Component component;
    boolean blocked;
    int timeToCompletion;
    boolean inspecting;
    public Inspector(int num){
        this.num = num;
        this.blocked = false;
        inspecting = false;
    }
    public void acceptComponent(Component component){
        this.component = component;
        timeToCompletion = component.getServiceTime();
        inspecting = true;
    }
    public void lowerTimeToCompletion(int elapsedTime){
        timeToCompletion= timeToCompletion - elapsedTime;
    }
    public void setBlocked(boolean bool){
        blocked = bool;
    }

}
