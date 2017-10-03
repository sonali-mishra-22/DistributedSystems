package VectorClock;

public class Message {
    private Event event;
    private Processor fromProcessor;
    private Processor toProcessor;
    private VectorClock vectorClock;
    public Message(Event event, VectorClock vc, Processor fromProcessor, Processor toProcessor) {
        this.vectorClock = vc;
        this.event = event;
        this.fromProcessor = fromProcessor;
        this.toProcessor = toProcessor;
    }
    public VectorClock getVectorClock() {
        return vectorClock;
    }
    public void setVectorClock(VectorClock vectorClock) {
        this.vectorClock = vectorClock;
    }
    public Event getEvent() {
        return this.event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public Processor getFromProcessor() {
        return this.fromProcessor;
    }
    public void setFromProcessor(Processor fromProcessor) {
        this.fromProcessor = fromProcessor;
    }
    public Processor getToProcessor() {
        return this.toProcessor;
    }
    public void setToProcessor(Processor toProcessor) {
        this.toProcessor = toProcessor;
    }
    
}
