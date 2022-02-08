/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

/**
 *
 * @author Mohamed Rashed
 */
public class SignUpResponse {
    SignUpStatusEnum signup;

    public SignUpResponse(SignUpStatusEnum signup) {
        this.signup = signup;
    }

    public SignUpStatusEnum getSignup() {
        return signup;
    }

    public void setSignup(SignUpStatusEnum signup) {
        this.signup = signup;
    }
    
}
