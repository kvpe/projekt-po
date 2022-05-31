package Application;

public final class ApplicationArguments {

    private final int size; //map
   // private final int maxSquadSize;
    //STILL HAS THINGS TO IMPLEMENT
    public int getSize() {
        return size;
    }

    public ApplicationArguments(int size) {
        this.size = size;
        //this.maxSquadSize = maxSquadSize;
    }
    public String toString(){
        return "size "+ this.size;
              //  + "max squad size " + this.maxSquadSize;
    }
}