package com.selleby.models;

import com.selleby.responses.SubmitResponse;

import java.util.Objects;

public record IterationState(Solution solution, SubmitResponse submitResponse, int futureLooking) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IterationState that = (IterationState) o;
        return futureLooking == that.futureLooking && solution.equals(that.solution) && Objects.equals(submitResponse, that.submitResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solution, submitResponse, futureLooking);
    }
}
