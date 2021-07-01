package model;

/**
 * Inhouse part model.
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class Inhouse extends Part {

    /**
     * The data of machine ID for the part
     */
    private long machineId;

    /**
     * Constructor for a new instance of an InHouse object.
     *
     * @param id the Id of the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory level value of the part
     * @param min the min value of the part
     * @param max the max value of the part
     * @param machineId the machine Id for the part
     */
    public Inhouse (int id, String name, double price, int stock, int min, int max, long machineId) {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    /**
     * Gets the machine Id of a part.
     *
     * @return machineId of the part
     */
    public long getMachineId() {
        return machineId;
    }

    /**
     * Sets the value of the machineId
     *
     * @param machineId the machineId of the part
     */
    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }
}