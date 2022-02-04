enum Entity{Inspector, Workstation}
public class Event {
    private int time; //Time at which event finishes
    private Entity entity; // whether event is inspector or workstation
    private int num;// number of inspector or workstation

    public Event(int time, Entity entity, int num){
       this.time = time;
       this.entity = entity;
       this.num = num;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public Entity getEntity() { return entity; }
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }




}
