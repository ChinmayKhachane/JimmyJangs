// The item class. Only contains a double weight and a String name
public class Item
{
    private String obj;
    private double weight;
public Item (String name, double w) {
    
    weight = w;
    obj = name;


}

public double getWeight() {

return weight;    
}
   
public String getName() {

return obj;
}
}
