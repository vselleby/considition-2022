package com.selleby.models;

import com.selleby.responses.SubmitResponse;

public record IterationState(Solution solution, SubmitResponse submitResponse, int futureLooking) { }
