package com.selleby.responses;


public class ForwardLookingResponse extends SubmitResponse {
    public final int forwardLookingDays;

    public ForwardLookingResponse(SubmitResponse reponse, int forwardLookingDays) {
        super(reponse);
        this.forwardLookingDays = forwardLookingDays;
    }
}
