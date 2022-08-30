package model;

/**This is the InHouse class. It is for the creation of InHouse parts.*/
public class InHouse extends Part {

    private int machineID;

    /** This is the InHouse part constructor. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /** This method returns the machineID for an InHouse part.
     @return returns machineID
     */
    public int getMachineID() {
        return machineID;
    }

    /** This method sets the machineID.
     @param machineID sets machineID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
