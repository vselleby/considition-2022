package com.selleby.responses;


public class ForwardLookingResponse extends SubmitResponse {
    public final int forwardLookingDays;

    public ForwardLookingResponse(SubmitResponse response, int forwardLookingDays) {
        super(response.score, response.gameId, response.dailys, response.totalProducedBags, response.totalDestroyedBags, response.visualizer);
        this.forwardLookingDays = forwardLookingDays;
    }
}
