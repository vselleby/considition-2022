package responses;

public class GameResponse {
    
    public String mapName;
    public int companyBudget;
    public String behavior;

    public GameResponse(String mapName, int companyBudget, String behavior){
        this.mapName = mapName;
        this.companyBudget = companyBudget;
        this.behavior = behavior;
    }
}
