package model;

/**
 * outsourced part model.
 *
 * <p><b>RUNTIME ERROR</b> On my previous version, there was error that the companyName could not be saved and
 *  hence it could always display null when I could call getCompanyName(). I have fixed this by adding
 *  setCompanyName(..) inside the constructor which was missing before.</p>
 *
 * @author Rifatul Karim
 * @version 1.0
 */
public class Outsourced extends Part{

    /**
     * The company name for the part
     */
    private String companyName;

    /**
     * Constructor for a new instance of an Outsourced part.
     *
     * @param id the ID for the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory level of the part
     * @param min the minimum level for the part
     * @param max the maximum level for the part
     * @param companyName the name of a company
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * Gets the name of the company
     *
     * @return companyName name of a company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set the name of the company
     *
     * @param companyName name of a company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}