package com.selleby.models;

import com.selleby.responses.SubmitResponse;

import java.util.Objects;

public record IterationState(Solution solution, SubmitResponse submitResponse, int futureLooking) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IterationState that = (IterationState) o;
        return futureLooking == that.futureLooking && submitResponse.equals(that.submitResponse) && solution.equals(that.solution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solution, futureLooking);
    }
}
