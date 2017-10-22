package VectorClock;
import java.util.Observable;
import java.util.Observer;

public class Processor extends Thread implements Observer {
    private Buffer messageBuffer ;
    private int procID ;
    private VectorClock vc ;
    private String threadname;
    public int eventCount;
    private int numProcessors;
    public Processor(int id, int totalProcessors, String threadName) {
        this.messageBuffer = new Buffer();
        this.procID = id;
        this.numProcessors = totalProcessors;
        this.vc = new VectorClock(totalProcessors);
        this.threadname = threadName;
        this.messageBuffer.addObserver(this);
    }
    public int getProcID() {
        return procID;
    }
    public void setProcID(int procID) {
        this.procID = procID;
    }
    public VectorClock getVc() {
        return vc;
    }
    public void setVc(VectorClock vc) {
        this.vc = vc;
    }
    public Buffer getMessageBuffer() {
        return messageBuffer;
    }
    public void setMessageBuffer(Buffer messageBuffer) {
        this.messageBuffer = messageBuffer;
    }
    public void sendMessageToMyBuffer(Message msg){
        this.messageBuffer.setMessage(msg);
    }
    public void update(Observable observable, Object arg) {
        Buffer buffer = (Buffer) observable;
        Message msg = buffer.getMessage();
        Event event = msg.getEvent();
        EventType type = event.getEventType();
        VectorClock vc = msg.getVectorClock();
        switch(type) {
            case SEND:
                System.out.println("SEND event found so it is a RECEIVE event at this P" + procID);
                System.out.println("VectorClock before this RECEIVE event at P" + procID );
                printArray(vc.getTimestampArray());
                calculateVectorClocks(vc);
                System.out.println("VectorClock after this RECEIVE event at P" + procID );
                printArray(vc.getTimestampArray());
                break;
            default:
                break;
        }
    }
    
    public int compareTo(VectorClock receivedVC) {
        int[] ts = this.vc.getTimestampArray();
        int[] receivedTs = receivedVC.getTimestampArray();
        int retval =  Integer.valueOf(ts[this.procID]).compareTo(Integer.valueOf(receivedTs[this.procID]));
        if(retval > 0) {
            System.out.println("obj1 i greater than obj2");
        } else if(retval < 0) {
            System.out.println("obj1 is less than obj2");
        } else {
            System.out.println("obj1 is equal to obj2");
        }
        return retval;
    }
    
    public void calculateVectorClocks(VectorClock vc2) {
        System.out.println("In P" + procID + "'s calculateVectorClock, before calculation " +this.getVc().toString());
        for(int i = 0;i < numProcessors; i++) {
            if(i != procID) {
                int max = Math.max(vc.getTimestampArray()[i], vc2.getTimestampArray()[i]);
                System.out.println("Inside max function, max = "+ max);
                vc.update(i, max);
            }
        }
        if(vc.getTimestampArray()[procID] < vc2.getTimestampArray()[procID]) {
            vc.update(procID,vc2.getTimestampArray()[procID]+1);
        }
        System.out.println("In P" + procID + "'s calculateVectorClock after calculation " +this.getVc().toString());
    }
    
    @Override
    public void run() {
        System.out.println("inside run method of P" + procID);
    }
    
    public void executeEvent(Event event) {
        switch(event.getEventType()) {
            case RECEIVE:
                System.out.println("VectorClock before RECEIVE event at P" + procID );
                printArray(vc.getTimestampArray());
                vc.update(procID, vc.getTimestampArray()[procID]+1);
                System.out.println("VectorClock after RECEIVE event at P" + procID );
                printArray(vc.getTimestampArray());
                eventCount++;
                break;
            case SEND:
                System.out.println("P" + procID + " SEND to P" +event.getToProcessor().getProcID());
                Message message = new Message(event,vc,this,event.getToProcessor());
                System.out.println("VectorClock before SEND event at P" + procID );
                printArray(vc.getTimestampArray());
                vc.update(procID, vc.getTimestampArray()[procID]+1);
                System.out.println("VectorClock after SEND event at P" + procID );
                printArray(vc.getTimestampArray());
                event.getToProcessor().sendMessageToMyBuffer(message);
                eventCount++;
                break;
            case COMPUTE:
                System.out.println("VectorClock before compute event at P" + procID );
                printArray(vc.getTimestampArray());
                System.out.println("COMPUTING at P"+procID);
                vc.update(procID , vc.getTimestampArray()[this.procID]+1);
                System.out.println("VectorClock after compute event at P" + procID );
                printArray(vc.getTimestampArray());
                eventCount++;
                break;
            default:
                break;
        }
        
    }
    public static void printArray(int[] array) {
        System.out.print("[");
        for (int i : array) {
            
            System.out.print(i + " ");
        }
        System.out.print("]");
        System.out.println();
    }
}
