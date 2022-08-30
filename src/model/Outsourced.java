package model;

/** This is the Outsourced class. It is for the creation of Outsourced parts.*/
public class Outsourced extends Part{

    protected String companyName;

    /** This is the Outsourced part constructor. */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method returns the companyName for an Outsourced part.
     @return Returns companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /** This method sets the companyName.
     @param companyName sets companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
