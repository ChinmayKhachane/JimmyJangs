public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Player terrance; 
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        createItems();
        parser = new Parser();
        terrance = new Player(370);
    }
     Room outside, theater, cafe, lab, office, bedroom;
     Item knife, sledgehammer, doll, glasses, shoes, coffee, hydroflask, lifevest
     , pencil, book;
  

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
       
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        cafe = new Room("in the campus cafe");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        bedroom = new Room("in the professor's bedroom");
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", cafe);
        outside.setExit("north", bedroom);
        
        theater.setExit("west", outside);
        theater.setExit("up", lab); 
        
        cafe.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        bedroom.setExit("south", outside);

        currentRoom = outside; 
        // start game outside
        
        
    }
    
    private void createItems() 
    {
        knife = new Item("knife",70);
        sledgehammer = new Item("sledgehammer",150);
        doll = new Item("doll", 40);
        shoes = new Item("shoes", 70);
        coffee = new Item("coffee", 60);
        hydroflask = new Item("hydroflask", 80);
        glasses = new Item("glasses", 25);
        lifevest = new Item("lifevest", 50);
        pencil = new Item("pencil", 60);
        book = new Item("book", 40);
        
        lab.addItem("knife", knife);
        outside.addItem("doll", doll);
        office.addItem("sledgehammer", sledgehammer);
        cafe.addItem("shoes", shoes);
        theater.addItem("hydroflask", hydroflask);
        theater.addItem("coffee", coffee);
        outside.addItem("glasses", glasses);
        office.addItem("lifevest", lifevest);
        bedroom.addItem("pencil", pencil);
        bedroom.addItem("book", book);
   
    }
     

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        
    }
}
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("You are in the university at night with a fire raging in the next building");
        System.out.println("Grab all neccessary items and check for win");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        boolean hasWon = false;
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("pickup")){
            take(command);
        }
        else if (commandWord.equals("drop")){
        drop(command);
        }
        else if (commandWord.equals("list"))
        {
        listStuff();
        }
        else if(commandWord.equals("win"))
        {
        hasWon = win();
        }
        // else command not recognised.
        if (hasWon == false) {
        
        return wantToQuit;
        
    } else{
        return hasWon;

    
    }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    private void look(Command command){ 
        currentRoom.printInRoom();
        
    }
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void take(Command command) 
    {
        // checks for second word of command
        if (command.hasSecondWord() == false){
        
        System.out.println("What would you like to pick up?");
        return;
        }
        // checks if room contains item that player wants to pick up
        else if(currentRoom.getHash().containsKey(command.getSecondWord()) == true){
        
        String thing = command.getSecondWord();
        Item z = currentRoom.getItem(thing);
        terrance.pickup(z);
        // checks if player contains item, otherwise doesnt run
        if (terrance.yourStuff().containsKey(command.getSecondWord())){
          
            currentRoom.removeItem(command.getSecondWord());
            
            System.out.println("You successfully picked up the item");
      
        }
    }
        else {
        
        System.out.println("Sorry you cant pick that up because you are in the wrong room");
        
        
        }
    
    
    }
    
    private void drop(Command command)
    {
        // checks for second word
     if (command.hasSecondWord() == false){
        
        System.out.println("What do you want to drop?");
        return;
        }
        // checks if player has that item
     else if(terrance.yourStuff().containsKey(command.getSecondWord())){
          Item x = terrance.stuff(command.getSecondWord());
          terrance.dropit(command.getSecondWord());
          // puts item back in room
         currentRoom.getHash().put(command.getSecondWord(), x);
    }
    else {
    
    System.out.println("Sorry we don't have that item");
    }
}
    private void listStuff() 
    {
        // lists what player has
        System.out.println("Currently you have :");
        terrance.printYourStuff();
    }
    
    private boolean win() 
    {
    // checks if 
    if ( (terrance.yourStuff().containsKey("sledgehammer")) && 
    (terrance.yourStuff().containsKey("knife")) && 
    (terrance.yourStuff().containsKey("lifevest")) && 
    (terrance.yourStuff().containsKey("hydroflask")))
    {
    System.out.println("Congrats, you have won the game!");
    return true;
    } else { 
    
    System.out.println("You do not have all the required items");
    return false;
    
    }
    
    
    
}







}