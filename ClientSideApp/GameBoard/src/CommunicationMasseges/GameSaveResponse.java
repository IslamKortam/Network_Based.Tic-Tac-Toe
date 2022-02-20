package CommunicationMasseges;

public class GameSaveResponse {
    private AcceptedDinedStatus status;

    public GameSaveResponse(AcceptedDinedStatus status){
        this.status = status;
    }
    
    public void setStatus(AcceptedDinedStatus status){
        this.status = status;
    }

    public AcceptedDinedStatus getStatus(){
        return this.status;
    }
}
