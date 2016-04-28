package beans;



public class Dolphinarium extends TestBean{

    public Dolphinarium(){
        name = "Moscow Dolphins";
    }

    private String name;

    private Trainer firstTrainer;
    private Trainer secondTrainer;

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public Trainer getFirstTrainer() {
        return firstTrainer;
    }

    public void setFirstTrainer(Trainer firstTrainer) {
        this.firstTrainer = firstTrainer;
    }

    public Trainer getSecondTrainer() {
        return secondTrainer;
    }

    public void setSecondTrainer(Trainer secondTrainer) {
        this.secondTrainer = secondTrainer;
    }
}
