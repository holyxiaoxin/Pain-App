package com.dexterlimjiarong.painapp;

public class PqasItem {
 
      int questionAnswers[];
 
      public PqasItem() {
      }
      public int[] getQuestions() {
            return questionAnswers;
      }
      public void setQuestion(int questionAnswer, int index) {
    	  questionAnswers[index] = questionAnswer;
      }
}