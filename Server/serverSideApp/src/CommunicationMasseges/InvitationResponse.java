/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

/**
 *
 * @author imkor
 */
public class InvitationResponse {
    private int invitationID;
    private AcceptedDinedStatus status;

    public InvitationResponse(int invitationID, AcceptedDinedStatus status) {
        this.invitationID = invitationID;
        this.status = status;
    }

    public int getInvitationID() {
        return invitationID;
    }

    public void setInvitationID(int invitationID) {
        this.invitationID = invitationID;
    }

    public AcceptedDinedStatus getStatus() {
        return status;
    }

    public void setStatus(AcceptedDinedStatus status) {
        this.status = status;
    }
}
