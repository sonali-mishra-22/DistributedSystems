package edu.dt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by tphadke on 8/29/17.
 */
public class Processor implements Observer {
    //Each processsor has a message Buffer to store messages
    Buffer messageBuffer ;
    Integer id ;
    List<Processor> children ;
    Processor parent;
    //Initially it will be all the neighbors of a Processor. When a graph is created this list is populated
    List<Processor> unexplored ;

    public Processor() {
        messageBuffer = new Buffer();
        id = Integer.MIN_VALUE; //This is an invalid value. Since only +ve values are acceptable as processor Ids.
        children = new ArrayList<>();
        //Initially it will be all the neighbors of a Processor. When a graph is created this list is populated
        unexplored = new ArrayList<>();
        //Each processor is observing itself;
        messageBuffer.addObserver(this);
    }

    //This method will only be used by the Processor
    private void removeFromUnexplored(Processor processor){
        Iterator<Processor> unExploredIterator = unexplored.iterator();
        while(unExploredIterator.hasNext()){
        	if(processor!=null && unExploredIterator.next().id.equals(processor.id)){
        		unExploredIterator.remove();
        	}
        }
    }

    //This method will add a message to this processors buffer.
    //Other processors will invoke this method to send a message to this Processor
    public void sendMessgeToMyBuffer(Message message,Processor caller){
        messageBuffer.setMessage(message, caller);
    }


    //This is analogous to recieve method.Whenever a message is dropped in its buffer this Pocesssor will respond
    //TODO: implement the logic of receive method here
    //      Hint: Add switch case for each of the conditions given in receive
    public void update(Observable observable, Object arg) {
    	Buffer buffer = (Buffer)observable;
    	if(buffer.getMessage() != null){
	    	if(buffer.getMessage().equals(Message.M)){
	    		if(parent == null){
	    			parent = (Processor)arg;
	    			removeFromUnexplored((Processor)arg);
	    			explore();
	    		}else{
	    			Processor caller = (Processor)arg;
	    			removeFromUnexplored(caller);
	    			caller.sendMessgeToMyBuffer(Message.ALREADY, this);
	    		}
	    	}else if(buffer.getMessage().equals(Message.ALREADY)){
	    		explore();
	    	}else if(buffer.getMessage().equals(Message.PARENT)){
	    		this.children.add((Processor)arg);
	    		explore();
	    	}
    	}
	    else{
    		parent = this;
    		explore();
    	}
    }

    private void explore(){
       if(!unexplored.isEmpty()){
    	   Processor pk = unexplored.get(0);
    	   unexplored.remove(0);
    	   pk.sendMessgeToMyBuffer(Message.M,this);
       }else{
    	   if(parent != null && !this.equals(parent)){
    		   parent.sendMessgeToMyBuffer(Message.PARENT,this);
    	   }
       }
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processor other = (Processor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    

}
