package breadQuizzes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;

import java.awt.event.*;
import java.util.ArrayList;

class question {
    private String question;
    private String ans1;
    private String ans2;
    int questionType;
    int weighting;

    boolean clicked = false;
    boolean painted = false;
    boolean spectrumQ;
    boolean answer;


    int[] coord1 = {160,190};
    int[] coord2 = {160,240};
    int size = 20;

    public question(String q, String a1, String a2, int qT, boolean sQ) {
        this.question = q;
        this.ans1 = a1;
        this.ans2 = a2;
        this.questionType = qT;
        this.spectrumQ = sQ;
    }
    public question(String q, String a1, String a2, int qT, boolean sQ, int weight ) {
        this.question = q;
        this.ans1 = a1;
        this.ans2 = a2;
        this.questionType = qT;
        this.spectrumQ = sQ;
        this.weighting = weight;
    }


    public void paint(Graphics g) {
        Font a = new Font("Times New Roman",Font.BOLD, 35) ;
        g.setFont(a);
        //g.drawString(questionType,30,70);
        Font b = new Font("Times New Roman",Font.BOLD, 18) ;
        g.setFont(b);
        g.drawString(question, 55, 130);
        g.drawRect(coord1[0]-size/2, coord1[1]- size/2, 20, 20);
        g.drawString(ans1, coord1[0] + 25,coord1[1]+5);
        g.drawRect(coord2[0]-size/2, coord2[1]- size/2, 20, 20);
        g.drawString(ans2, coord2[0] + 25,coord2[1]+5);
    }

}

class questionSet {
    ArrayList<question> questions;
    boolean answeredYes = false;
    int numAnsweredYes;

    public questionSet(ArrayList<question> questions) {
        this.questions = questions;
    }
}
class allQuestions {
    ArrayList<questionSet> questionSets;

    public allQuestions(ArrayList<questionSet> qSets) {
        this.questionSets = qSets;
    }

    public int getTotalQuestions() {
        int count = 0;
        for(questionSet qs : questionSets) {
            for (question q : qs.questions) {
                count ++;
            }
        }
        return count;
    }

    public question getQuestion(int index) {
        int count = 0;
        for(questionSet qs : questionSets) {
            for (question q : qs.questions) {
                if (count == index) {
                    return q;
                }
                count ++;
            }
        }
        return null;

    }

    public questionSet getQuestionSet(int index) {
        int count = 0;
        for(questionSet qs : questionSets) {
            if (count == index) {
                return qs;
            }
            count ++;
        }
        return null;

    }


}
class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;
    private int scroll = 0;

    public int getX() {
        return this.mouseX;
    }
    public int getY() {
        return this.mouseY;
    }
    public int getButton() {
        return this.mouseB;
    }
    public void resetButton() {
        this.mouseB = -1;
    }
    public void mouseDragged(MouseEvent event) {
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }
    public void mouseMoved(MouseEvent event) {
        this.mouseX = event.getX();
        this.mouseY = event.getY();
    }
    public void mousePressed(MouseEvent event) {
        this.mouseB = event.getButton();
    }
    public void mouseReleased(MouseEvent arg0) {
        this.mouseB = -1;
    }

    public void mouseClicked(MouseEvent arg0) {
    }
    public void mouseEntered(MouseEvent arg0) {
    }
    public void mouseExited(MouseEvent arg0) {
    }

    //scroll commands

    public int getScroll() {
        return this.scroll;
    }
    public void mouseWheelMoved(MouseWheelEvent event) {
        scroll = event.getWheelRotation();
    }
    public boolean isScrollingUp() {
        return this.scroll == -1;
    }
    public boolean isScrollingDown() {
        return this.scroll == 1;
    }
    public void resetScroll() {
        this.scroll = 0;
    }
}



public class quizzes extends Frame {

    private static ArrayList<question> questionsBI = new ArrayList<>();
    private static ArrayList<question> questionsBS = new ArrayList<>();
    private static ArrayList<questionSet> questionSets = new ArrayList<>();
    public static allQuestions allQs =addQs();

    static Mouse m = new Mouse(); // declares mouse
    private final int FrameWidth = 900;
    private final int FrameHeight = 500;

    int currentQ; //current question being answered
    int questionsDone = 0;
    int questionSetsDone = -1;
    int axisSize = 300; // size of final axis
    double finalScore; // used to hold bread ingredient score
    double finalScore2; // used to hold bread structure score

    double time = System.currentTimeMillis();
    static quizzes q = new quizzes();


    public static void main(String[] args) {
        q.setVisible(true);
        q.addMouseListener(m);
        q.addMouseMotionListener(m);
        q.setLocationRelativeTo(null);
        q.runQuestions(allQs);
        q.findScores(allQs);
    }

    public quizzes() {
        setTitle("Bread Quiz");
        setSize(FrameWidth, FrameHeight);
    }

    public static allQuestions addQs() {
        questionsBI.add(new question("Can the sandwich's slices be a non-edible item?", "yes", "no", 1,true));
        questionsBI.add(new question("Can the sandwich's slices consist of any edible food item i.e slices could be slices of cake etc?", "yes", "no", 1,true));
        questionsBI.add(new question("Should the sandwich's slices be bread including food with similar texture to bread i.e banana bread, meatloaf etc?", "yes", "no", 1,true));
        questionsBI.add(new question("Should the sandwich's slices be bread or a bread substitute?", "yes", "no", 1,true));
        questionsBI.add(new question("Should the sandwich's slices be a leavened bread i.e using any rising agent?", "yes", "no", 1,true));
        questionsBI.add(new question("Must the sandwich's slices be any leavened bread with flour and yeast?", "yes", "no", 1,true));

        questionSet qSBI = new questionSet(questionsBI);
        questionSets.add(qSBI);

        questionsBS.add(new question("Must each slice be wider than it is tall", "yes", "no",2,true));
        questionsBS.add(new question("Must the sandwich's slices be completely flat like loaf bread?", "yes", "no",2,true));
        questionsBS.add(new question("Must the sandwich's slices have no holes ie not a bagel", "yes", "no",2,true));
        questionsBS.add(new question("Must each slice have a flat side facing the filling?", "yes", "no",2,true));
        questionsBS.add(new question("Must the sandwich have two separate slices?", "yes", "no",2,true));
        questionsBS.add(new question("Can the sandwich's slices have one or more holes e.g wrap, burrito, hot dog etc?", "yes", "no",2,true));
        questionsBS.add(new question("Can the sandwich's slices fully / spherically encase it?", "yes", "no",2,true));

        questionSet qSBS = new questionSet(questionsBS);
        questionSets.add(qSBS);

        return new allQuestions(questionSets);
    }

    public void runQuestions(allQuestions qs) {
        for (questionSet qSet : qs.questionSets ) {
            questionSetsDone ++;
            currentQ = 0;
            while (currentQ < qSet.questions.size()) {// loops through until all questions answered
                System.out.println(m.getX());
                if (!allQs.getQuestion(questionsDone).painted) { // if current question hasn't been painted to screen
                    repaint(); // paints current question to screen
                }
                checkClick(m.getButton()); // checks if player has clicked the screen
                try {
                    Thread.sleep(100);
                } catch (Exception ignored) {
                }
            }
        }
    }

    public void runEnd(Graphics g) {
        int p = 50;
        while (p < 100) {
            giveDefs(g);
            try {
                Thread.sleep(100);
            } catch (Exception ignored) {
            }
        }
    }

    public void findScores(allQuestions qs) {
        double count = qs.questionSets.get(0).numAnsweredYes;
        double count2 = qs.questionSets.get(1).numAnsweredYes;

        double spectrumCount = qs.questionSets.get(0).questions.size();
        double spectrumCount2 = qs.questionSets.get(1).questions.size();

        if (count > -1) { // if at least one BI question was answered yes to
            finalScore = ((2/(spectrumCount - 1)) * count)-1;  // converts score to number from -1 to 1
        }
        if (count2 > -1) { // if at least one BS question was answered yes t
            finalScore2 = ((2/(spectrumCount2 - 1)) * count2)-1; // converts score to number from -1 to 1
        }
        repaint(); // paints axis

    }


    public void paint(Graphics g) {
        if (questionsDone < allQs.getTotalQuestions()) {
            allQs.getQuestion(questionsDone).paint(g);
            allQs.getQuestion(questionsDone).painted = true;
        }else {
            int size = 10;
            g.setColor(Color.red);
            g.fillRect(FrameWidth/2 - axisSize/2, FrameHeight / 2 - axisSize/2, axisSize/2, axisSize/2);
            g.setColor(Color.green);
            g.fillRect(FrameWidth/2 - axisSize/2, FrameHeight / 2 , axisSize/2, axisSize/2);
            g.setColor(new Color(79, 79, 189));
            g.fillRect(FrameWidth/2, FrameHeight / 2 - axisSize/2 , axisSize/2, axisSize/2);
            Color purple = new Color(168, 69, 208);
            g.setColor(purple);
            g.fillRect(FrameWidth/2 , FrameHeight / 2 , axisSize/2, axisSize/2);

            g.setColor(Color.gray);
            for (int i = 0; i < 20; i ++) {
                g.drawLine((FrameWidth/2 - axisSize/2) + i*axisSize/20,FrameHeight/2 +axisSize/2,(FrameWidth/2- axisSize/2)+ i*axisSize/20, FrameHeight/2 -axisSize/2);
            }
            for (int x = 0; x < 20; x ++) {
                g.drawLine((FrameWidth/2 - axisSize/2),(FrameHeight/2 -axisSize/2 )+ x*axisSize/20,(FrameWidth/2+axisSize/2), (FrameHeight/2 -axisSize/2)+ x*axisSize/20);
            }

            g.setColor(Color.black);
            g.drawRect(FrameWidth/2 - axisSize/2, FrameHeight / 2 - axisSize/2, axisSize, axisSize);

            g.fillRect(FrameWidth/2-2,FrameHeight/2 -axisSize/2,4, axisSize);
            g.fillRect(FrameWidth/2 - axisSize/2,FrameHeight/2 -2,axisSize, 4);

            Font f = new Font("Times New Roman", Font.BOLD, 16);
            g.setFont(f);
            g.drawString("Structuralist", FrameWidth/2 - 42,(FrameHeight/2 - axisSize/2) -4);
            g.drawString("Abstractionist", FrameWidth/2 - 47,(FrameHeight/2 + axisSize/2) +12);
            g.drawString("Fundamentalist", (FrameWidth/2 +axisSize/2) +5,FrameHeight/2 +5);
            g.drawString("Inclusionary", (FrameWidth/2 -axisSize/2) -83,FrameHeight/2 +3);

            g.drawOval((int) (FrameWidth/2 + (axisSize*finalScore)/2) -size/2,(int) (FrameHeight/2 + (axisSize*finalScore2)/2) -size/2, size, size);
            g.fillOval((int) (FrameWidth/2 + (axisSize*finalScore)/2) -size/2, (int) (FrameHeight/2 + (axisSize*finalScore2)/2) -size/2, size, size);
            q.runEnd(g);
        }

    }

    public void giveDefs(Graphics g) {
        int x = m.getX();
        int y = m.getY();
        System.out.println(x);
        if(x > FrameWidth/2 - 42 && x < FrameWidth/2 ) {
            g.drawString("poop",20,20);
        }
    }

    public void checkClick(int b){
        double delta = System.currentTimeMillis() - time;
        if (b == 1 && delta > 150) {
            time = System.currentTimeMillis();
            int x = m.getX();
            int y = m.getY();
            if(x < allQs.getQuestion(questionsDone).coord1[0] + allQs.getQuestion(questionsDone).size/2 && x > allQs.getQuestion(questionsDone).coord1[0] - allQs.getQuestion(questionsDone).size/2
                    && y < allQs.getQuestion(questionsDone).coord1[1] + allQs.getQuestion(questionsDone).size/2 && y > allQs.getQuestion(questionsDone).coord1[1] - allQs.getQuestion(questionsDone).size/2) {
                if(!allQs.getQuestionSet(questionSetsDone).answeredYes) {
                    allQs.getQuestionSet(questionSetsDone).answeredYes = true;
                    allQs.getQuestionSet(questionSetsDone).numAnsweredYes = currentQ;
                }
                allQs.getQuestion(questionsDone).answer = true;
                currentQ ++;
                questionsDone++;
            }else if(x < allQs.getQuestion(questionsDone).coord2[0] + allQs.getQuestion(questionsDone).size/2 && x > allQs.getQuestion(questionsDone).coord2[0] - allQs.getQuestion(questionsDone).size/2
                    && y < allQs.getQuestion(questionsDone).coord2[1] + allQs.getQuestion(questionsDone).size/2 && y > allQs.getQuestion(questionsDone).coord2[1] - allQs.getQuestion(questionsDone).size/2) {
                allQs.getQuestion(questionsDone).answer = false;
                currentQ ++;
                questionsDone++;
            }
        }
    }

}
