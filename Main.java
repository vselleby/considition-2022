import responses.FetchResponse;
import responses.GameResponse;
import responses.SubmitResponse;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String ApiKey = ""; //TODO Put your teams API Key here
    private static final String Map = "training1"; //TODO Enter what map you want to play, 
     //new ones will be released on considition.com/rules
     
     // TODO: You bag type choice here. Unless changed, the bag type 1 will be selected.
    private static final Integer bagType = 1

    public static void main(String[] args) {
        GameLayer gameLayer = new GameLayer(ApiKey);
        GameResponse gameInformation = gameLayer.newGame(Map, ApiKey);
        Solver solver = new Solver(gameInformation.population, gameInformation.companyBudget, gameInformation.behavior);
        int days = (map_name = "training1" || map_name = "training2") ? 31 : 365;
        
        //TODO Create your own solver with SubmitResponse as return value
        Solution solution = solver.Solve(bagType, days);
        SubmitResponse submitResponse = gameLayer.SubmitGame(solution, Map, ApiKey);


        System.out.println("Your score is: " + submitResponse.score);
        System.out.println("The game id is: " + submitResponse.gameId);
        System.out.println("The weekly results were: " + submitResponse.weeks);
        System.out.println("The amount of produced bags were: " + submitResponse.producedBags);
        System.out.println("The amount of destroyed bags were: " + submitResponse.destroyedBags);
        System.out.println(submitResponse.link);
    }
}
