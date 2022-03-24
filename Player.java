/* This is the class Player. It creates a Hashmap which stores all the items 
 that the person is carrying. It contains a weight limit 
   */

import java.util.Set;
import java.util.HashMap;

public class Player
{
private HashMap<String, Item> stuff;
private double lim;
private double currWeight = 0;
public Player(double w) 
{
lim = w;
stuff = new HashMap<>();
}

public void pickup(Item pick) {
   // checks that weight is greater than limit
    currWeight += pick.getWeight();
    if (currWeight >= lim) {
    
    System.out.println("Sorry you can't pick up " + pick.getName() + " because you exceed weight capacity.");
    currWeight -= pick.getWeight();
    }
    
    else {
    /* adds item to hashmap. The key is just the name of the item, 
    which is consistent with the item.*/
    stuff.put(pick.getName(), pick);
    
    }


}
// drops items
public void dropit(String drop) {

    if (stuff.get(drop) == null) {
// checks if hashmap contains item
    System.out.println("You dont have that item");
    return;
    }
    else {
    
    stuff.remove(drop);
    currWeight -= stuff.get(drop).getWeight();
    System.out.println("Item has been succcessfully removed");
}

}

public Item stuff(String a) {

    return stuff.get(a);
}
public HashMap<String, Item> yourStuff(){

    return stuff;

}
public void printYourStuff() {

for(String i: stuff.keySet()){

System.out.println(i);

} 

}



  
}
