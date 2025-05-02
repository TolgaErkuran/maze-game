import java.lang.annotation.Target;
import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

class Main {
    public static void main(String[] args) {
        GameController game = new GameController(10, 10, 10, 100);
        game.runSimulation();
    }
}
class MazeTile{
    int x,y;
    char type;//Wall--> W, Empty-->E, Trap-->T,PowerUp-->P
    boolean hasAgent;
    public MazeTile(int x,int y, char type){
        this.x=x;
        this.y=y;
        this.type=type;
        this.hasAgent=false;
    }

    boolean isTraversable(){
        return type != '#';
    }
    @Override
    public String toString(){
        if(hasAgent) {
            return "A";
        }else{
            return String.valueOf(type);
        }
    }
    
}
class Agent{
    int id;
    int currentX,currentY;
    Stack<String> moveHistory;
    boolean hasReachedGoal;
    int totalMoves;
    int backTracks;
    boolean hasPowerUp;
    
    public Agent(int id,int currentX,int currentY){
        this.id=id;
        this.currentX=currentX;
        this.currentY=currentY;
        this.moveHistory = new Stack<>();
        this.hasReachedGoal=false;
        this.totalMoves = 0;
        this.backTracks = 0;
        this.hasPowerUp = false;

    }
    void move(String direction){
        int oldX=currentX;
        int oldY=currentY;

        switch(direction.toUpperCase()){
            case "UP":
                currentX--;
                break;
            case "DOWN":
                currentX++;
                break;
            case "LEFT":
                currentY--;
                break;
            case "RIGHT":
                currentY++;
                break;
            default:
                System.out.println("Geçersiz yön:" + direction);
                return;
        }


        moveHistory.push(direction);


        totalMoves++;
        
    }
    void backtrack(){
        if(moveHistory.isEmpty()){
            System.out.println("Backtrack yapılamaz,geçmiş hamle bulunmuyor");
            return;
        }

        String lastMove = moveHistory.pop();
        String reverseMove = "";

        switch(lastMove){
            case "UP":
                reverseMove = "DOWN";
                break;
            case "DOWN":
                reverseMove = "UP";
                break;
            case "RIGHT":
                reverseMove = "LEFT";
                break;
            case "LEFT":
                reverseMove = "RIGHT";
                break;
            default:
                System.out.println("Geçersiz geçmiş hareketi:" + lastMove);
                return;



        }
        if(maze.isValidMove(currentX,currentY,reverseMove)){
            move(reverseMove);
            backTracks++;
        }else{
            System.out.println("Geçersiz Geri Dönüş:"+reverseMove);
        }
        
    }
    void usePower(){}
    
    public void recordMove(int x,int y){
        String position = x + "," + y;
        moveHistory.push(position);
        totalMoves++;
    }
    public String getMoveHistoryAsString(){
        if(moveHistory.isEmpty()){
            return "Hiç hamle yapılmadı";
        }

        List<String> historyList = new ArrayList<>(moveHistory);

        return String.join("-->",historyList);
    }
    
    
    
}
class MazeManager{
    MazeTile[][] maze;
    int width,height;
    List <Agent> agents;
    List <Integer> rotatingRows;

    public MazeManager(int width,int height){
        if(width<=0||height<=0){
            throw new IllegalArgumentException("Width and height must be positive");
        }
    this.maze=  new MazeTile[width][height];
    this.width = width;
    this.height = height;
    this.agents = new ArrayList<>();
    this.rotatingRows = new ArrayList<>();
    }

    public void generateMaze(){
        Random rastgele = new Random();

        for(int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                char type;
                int yüzde = rastgele.nextInt(100);

                if(yüzde < 55){
                    type = '#';
                }
                else if(yüzde < 90){
                    type = ' ';
                }else if(yüzde < 95){
                    type = 'P';
                }else{
                    type = 'T';
                }

                maze[x][y] = new MazeTile(x, y, type);  
            }
        }

        maze[width-1][height-1].type='G';
        
    }

    CircularLinkedListRowNode rotatingHead= null;
    CircularLinkedListRowNode currentRotating = null;

    void rotateCorridor(List<Integer> rowIDs){
        if(rowIDs.isEmpty()){
            return;
        }

        CircularLinkedListRowNode prev = null;

        for(int rowID : rowIDs){
            CircularLinkedListRowNode node = new CircularLinkedListRowNode(rowID);
            if(rotatingHead == null){
                rotatingHead = node;
            }else{
                prev.next = node;
            }
            prev = node;


        }
        prev.next = rotatingHead;
        currentRotating = rotatingHead;    
    }

    public void rotateCorridorByTurn(int turnCount){
        if(turnCount % 10 ==0 && currentRotating != null){
            rotateCorridor(Collections.singletonList(currentRotating.rowID));
            currentRotating = currentRotating.next;
        }
    }
    boolean isValidMove(int fromX,int fromY, String direction){
        int hedefX = fromX;
        int hedefY = fromY;

        switch(direction.toUpperCase()){
            case "UP":
                hedefY -=1;
                break;
            case "DOWN":
                hedefY +=1;
                break;
            case "RIGHT":
                hedefX +=1;
                break;
            case "LEFT":
                hedefX -=1;
                break;
            default:
                System.out.println("Hatalı Yön");
                break;
        }

        if(hedefX<0 || hedefX >= width || hedefY <0 || hedefY >= height){
            System.out.println("Labirent Sınırları Aşıldı!!!");
        }

        MazeTile hedefTile = maze[hedefX][hedefY];

        return hedefTile.isTraversable() && !hedefTile.hasAgent;
    }
    MazeTile getTile(int x,int y){
        if(x<0||x>=width||y<0||y>=height){
            return null;
        }
        return maze[x][y];
        
    }
    void updateAgentLocation(Agent a,int oldX,int oldY){
        MazeTile oldTile = getTile(oldX, oldY);
            if(oldTile != null){
                oldTile.hasAgent = false;
            }

        MazeTile newTile = getTile(a.currentX, a.currentY);
            if(newTile != null){
                newTile.hasAgent = true;
            }
        
        }

    void printMazeSnapshot(){
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                MazeTile tile = maze[x][y];
                if(tile.hasAgent){
                    System.out.print("$");
                }else{
                    System.out.print(tile.type);
                }

            }
            System.out.println();

        }
        System.out.println();
    }
}
class TurnManager{
    Queue<Agent> agentQueue;
    int currentRound;
    
    int agentsPlayedInCurrentRound = 0;

    public void initalizeAgents(List<Agent> agents){
        agentQueue = new LinkedList<>(agents);
        currentRound = 1;
    }

    public void advanceTurn(){
        if(agentQueue.isEmpty()){
            return;
        }

        Agent current = agentQueue.poll();
        if(!current.hasReachedGoal){
            agentQueue.offer(current);
        }

        agentsPlayedInCurrentRound++;

        if(agentsPlayedInCurrentRound >= agentQueue.size()){
            currentRound++;
            agentsPlayedInCurrentRound = 0;
        }
    }
    Agent getCurrentAgent(){
        Agent currentAgent = agentQueue.peek();
        System.out.println("Sıradaki ajan ID:"+currentAgent.id);
        return currentAgent;
    }
    public boolean allAgentsFinished(){
        for(Agent a : agentQueue){
            if(!a.hasReachedGoal){
                return false;
            }
        }
        return true;
    }
    void logTurnSummary(Agent a){
        System.out.println("Ajan ID:"+ a.id + "| Konum:("+a.currentX+","+a.currentY+")");
        System.out.println("Toplam Hamle:"+a.totalMoves+"| Geri Dönüş:"+a.backTracks);
        System.out.println("Hedefe Ulaştı mı"+(a.hasReachedGoal? "Evet":"Hayır"));
        System.out.println("Hareket Geçmişi:"+a.getMoveHistoryAsString());
        System.out.println("-----------------------------------------------------");
    }
}
class GameController{
    MazeManager maze;
    TurnManager turns;
    int MaxTurns;
    int turnCount;

    public GameController(int width,int height,int numAgents,int MaxTurns){
        this.maze = new MazeManager(width, height);
        this.turns = new TurnManager();
        this.MaxTurns = MaxTurns;
        this.turnCount = 0;

        initializeGame(numAgents);
    }
    
    public void initializeGame(int numAgents){
        List<Agent> agentList = new ArrayList<>();
        for(int i=0;i<numAgents;i++){
            agentList.add(new Agent(i, 0, 0));
        }


        maze = new MazeManager(10, 10);
        maze.generateMaze();


        turns= new TurnManager();
        turns.initalizeAgents(agentList);


        turnCount=0;
        MaxTurns = 100;


    }
    void runSimulation(){
        while(!turns.allAgentsFinished()&&turnCount<MaxTurns){
            Agent currentAgent = turns.getCurrentAgent();
            processAgentAction(currentAgent);

            turns.logTurnSummary(currentAgent);

            turns.advanceTurn();

            if(turns.agentsPlayedInCurrentRound >= 10){
                turnCount++;
                turns.agentsPlayedInCurrentRound = 0;
            }

            printFinalStatistics();
        }
    }
    public void processAgentAction(Agent a){
        String direction = a.getMoveHistoryAsString();

        boolean validMove = maze.isValidMove(a.currentX, a.currentY, direction);
        if(validMove){
            switch(direction){
                case "UP":
                    a.currentY--;
                    break;
                case "DOWN":
                    a.currentY++;
                    break;
                case "LEFT":
                    a.currentX--;
                    break;
                case "RIGHT":
                    a.currentY++;
                    break;
                default:
                    break;
            }

            a.recordMove(a.currentX,a.currentY);
            a.totalMoves++;

            if(maze.getTile(a.currentX, a.currentY).type == 'G'){
                a.hasReachedGoal = true;
            }
        }else{
            a.backtrack();
            a.backTracks++;
        }
    }
    void checkTileEffect(Agent a,MazeTile tile){
        if(tile.type=='P'){
            a.usePower();
            a.hasPowerUp = true;
            System.out.println("Ajan"+a.id+"güç artışı kazandı!");
        }

        if(tile.type=='G'){
            a.hasReachedGoal = true;
            System.out.println("Ajan"+a.id+"hedefe ulaştı!");
        }

        if(tile.type=='T'){
            //TRAP TANIMI YAP
            System.out.println("Ajna"+a.id+"tuzağa yakalandı!");
        }
    }

    void printFinalStatistics(){
        System.out.println("Oyun Sonu Ajan İstatistikleri:");
        System.out.println("------------------------------");

        for(Agent agent : turns.agentQueue){
            System.out.println("Ajan ID:"+agent.id);
            System.out.println("Toplam Hamle Sayısı:"+agent.totalMoves);
            System.out.println("Geri Dönme Sayısı:"+agent.backTracks);

            if(agent.hasReachedGoal){
                System.out.println("Ajan Hedefe Ulaştı!!!");
            }else{
                System.out.println("Ajan Hedefe Ulaşamadı!!!");
            }

            System.out.println();
        }

        System.out.println("Toplam Tur Sayısı:"+turnCount);
        System.out.println("Oyun Bitti!");
    }
    public void LogGameSummaryToFile(String fileName){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
            writer.write("Oyunun Sonu - Ajan İstatistikleri\n");
            writer.write("---------------------------------");

            for(Agent agent: turns.agentQueue){
                writer.write("Ajan ID:"+agent.id+"\n");
                writer.write("Toplam Hamle Sayısı:"+agent.totalMoves+"\n");
                writer.write("Geri Dönme Sayısı:"+agent.backTracks+"\n");

                if(agent.hasReachedGoal){
                    writer.write("Ajan Hedefe Ulaştı!!!\n");
                }else{
                    writer.write("Ajan Hedefe Ulaşamadı!!!\n");
                }

                writer.write("\n");
            }

            writer.write("Toplam Tur Sayısı:"+turnCount+"\n");
            writer.write("Oyun Bitti!!!\n");
        }catch(IOException e){
            System.out.println("Dosya Kaydedilirken Bir Hata Oluştu:"+e.getMessage());
        }
    }
}
class CircularLinkedListRowNode {
    int rowID;
    CircularLinkedListRowNode next;

    public CircularLinkedListRowNode(int rowID){
        this.rowID = rowID;
        this.next = null;

    }
}
