import com.selleby.Api;
import com.selleby.ForwardLookingSolver;
import com.selleby.models.DailyStat;
import com.selleby.models.Solution;
import com.selleby.responses.GameResponse;
import com.selleby.responses.SubmitResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ForwardLookingSolverTest {
    Api apiMock;
    SubmitResponse responseMock;
    ForwardLookingSolver solver;
    List<Integer> capturedOrders;

    @BeforeEach
    void setUp() {
        capturedOrders = new ArrayList<>();
        apiMock = mock(Api.class);
        responseMock = mock(SubmitResponse.class);
        when(apiMock.mapInfo()).thenReturn(new GameResponse(1, 1000, "test"));
        solver = new ForwardLookingSolver(apiMock, 3);
        solver.setMapDays(1);
    }

    @Test
    public void incrementOddStep() {
        when(apiMock.submitGame(any(Solution.class))).then(input -> {
            Solution solution = input.getArgument(0);
            int order = solution.orders.get(0);
            capturedOrders.add(order);
            DailyStat dailyStat = new DailyStat(1, 0, 0, 1000);
            switch (order) {
                case 0, 1, 3, 7 -> dailyStat.customerScore = order;
                default -> dailyStat.customerScore = -order;
            }
            responseMock.dailys = List.of(dailyStat);
            return responseMock;
        });
        Solution solution = new Solution();
        SubmitResponse response = solver.solve(solution);
        ArgumentCaptor<Solution> argument = ArgumentCaptor.forClass(Solution.class);
        verify(apiMock, times(6)).submitGame(argument.capture());
        assertEquals(0, capturedOrders.get(0));
        assertEquals(1, capturedOrders.get(1));
        assertEquals(3, capturedOrders.get(2));
        assertEquals(7, capturedOrders.get(3));
        assertEquals(15, capturedOrders.get(4));
        assertEquals(8, capturedOrders.get(5));
        assertEquals(7, response.dailys.get(0).customerScore);
    }

    @Test
    public void incrementEvenFinish() {
        when(apiMock.submitGame(any(Solution.class))).then(input -> {
            Solution solution = input.getArgument(0);
            int order = solution.orders.get(0);
            capturedOrders.add(order);
            DailyStat dailyStat = new DailyStat(1, 0, 0, 1000);
            switch (order) {
                case 0, 1, 3, 7, 15, 31, 32 -> dailyStat.customerScore = order;
                default -> dailyStat.customerScore = -order;
            }
            responseMock.dailys = List.of(dailyStat);
            return responseMock;
        });
        Solution solution = new Solution();
        SubmitResponse response = solver.solve(solution);
        ArgumentCaptor<Solution> argument = ArgumentCaptor.forClass(Solution.class);
        verify(apiMock, times(10)).submitGame(argument.capture());
        assertEquals(0, capturedOrders.get(0));
        assertEquals(1, capturedOrders.get(1));
        assertEquals(3, capturedOrders.get(2));
        assertEquals(7, capturedOrders.get(3));
        assertEquals(15, capturedOrders.get(4));
        assertEquals(31, capturedOrders.get(5));
        assertEquals(63, capturedOrders.get(6));
        assertEquals(32, capturedOrders.get(7));
        assertEquals(34, capturedOrders.get(8));
        assertEquals(33, capturedOrders.get(9));
        assertEquals(32, response.dailys.get(0).customerScore);
    }


    @Test
    public void incrementReduceStepThenIncreaseStep() {
        when(apiMock.submitGame(any(Solution.class))).then(input -> {
            Solution solution = input.getArgument(0);
            int order = solution.orders.get(0);
            capturedOrders.add(order);
            DailyStat dailyStat = new DailyStat(1, 0, 0, 1000);
            switch (order) {
                case 0, 1, 3, 7, 15, 31, 63, 64, 66, 70, 78, 94 -> dailyStat.customerScore = order;
                default -> dailyStat.customerScore = -order;
            }
            responseMock.dailys = List.of(dailyStat);
            return responseMock;
        });
        Solution solution = new Solution();
        SubmitResponse response = solver.solve(solution);
        ArgumentCaptor<Solution> argument = ArgumentCaptor.forClass(Solution.class);
        verify(apiMock, times(15)).submitGame(argument.capture());
        assertEquals(0, capturedOrders.get(0));
        assertEquals(1, capturedOrders.get(1));
        assertEquals(3, capturedOrders.get(2));
        assertEquals(7, capturedOrders.get(3));
        assertEquals(15, capturedOrders.get(4));
        assertEquals(31, capturedOrders.get(5));
        assertEquals(63, capturedOrders.get(6));
        assertEquals(127, capturedOrders.get(7));
        assertEquals(64, capturedOrders.get(8));
        assertEquals(66, capturedOrders.get(9));
        assertEquals(70, capturedOrders.get(10));
        assertEquals(78, capturedOrders.get(11));
        assertEquals(94, capturedOrders.get(12));
        assertEquals(126, capturedOrders.get(13));
        assertEquals(95, capturedOrders.get(14));

        assertEquals(94, response.dailys.get(0).customerScore);
    }

    @Test
    public void companyBudgetExceeded() {
        when(apiMock.submitGame(any(Solution.class))).then(input -> {
            Solution solution = input.getArgument(0);
            int order = solution.orders.get(0);
            capturedOrders.add(order);
            DailyStat dailyStat = new DailyStat(1, 0, 0, 1000);
            dailyStat.customerScore = order;
            switch (order) {
                case 0, 1, 3, 7 -> dailyStat.companyBudget = 1000;
                default -> dailyStat.companyBudget = -1000;
            }
            responseMock.dailys = List.of(dailyStat);
            return responseMock;
        });

        Solution solution = new Solution();
        solver.solve(solution);
        ArgumentCaptor<Solution> argument = ArgumentCaptor.forClass(Solution.class);
        verify(apiMock, times(6)).submitGame(argument.capture());
        assertEquals(0, capturedOrders.get(0));
        assertEquals(1, capturedOrders.get(1));
        assertEquals(3, capturedOrders.get(2));
        assertEquals(7, capturedOrders.get(3));
        assertEquals(15, capturedOrders.get(4));
        assertEquals(8, capturedOrders.get(5));
    }
}
