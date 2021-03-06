package com.sortedqueue.programmercreek.database;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Alok Omkar on 2017-03-08.
 */

public class InterviewQuestionModel implements Parcelable {

    private int typeOfQuestion;
    private String programLanguage;

    private String question;
    private int correctOption;

    private ArrayList<Integer> correctOptions;
    private ArrayList<Integer> correctSequence;

    private ArrayList<OptionModel> optionModels;
    private String modelId;

    private String output;
    private String explanation;
    private String code;


    public InterviewQuestionModel(int typeOfQuestion, String programLanguage, String question, int correctOption, ArrayList<Integer> correctOptions, ArrayList<Integer> correctSequence, ArrayList<OptionModel> optionModels, String modelId, String output, String explanation) {
        this.typeOfQuestion = typeOfQuestion;
        this.programLanguage = programLanguage;
        this.question = question;
        this.correctOption = correctOption;
        this.correctOptions = correctOptions;
        this.correctSequence = correctSequence;
        this.optionModels = optionModels;
        this.modelId = modelId;
        this.output = output;
        this.explanation = explanation;
    }

    public InterviewQuestionModel(int typeOfQuestion,
                                  String programLanguage,
                                  String question,
                                  int correctOption,
                                  ArrayList<Integer> correctOptions,
                                  ArrayList<Integer> correctSequence,
                                  ArrayList<OptionModel> optionModels) {
        this.typeOfQuestion = typeOfQuestion;
        this.programLanguage = programLanguage;
        this.question = question;
        this.correctOption = correctOption;
        this.correctOptions = correctOptions;
        this.correctSequence = correctSequence;
        this.optionModels = optionModels;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public InterviewQuestionModel() {
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(int typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(int correctOption) {
        this.correctOption = correctOption;
    }

    public ArrayList<Integer> getCorrectOptions() {
        return correctOptions;
    }

    public void setCorrectOptions(ArrayList<Integer> correctOptions) {
        this.correctOptions = correctOptions;
    }

    public ArrayList<Integer> getCorrectSequence() {
        return correctSequence;
    }

    public void setCorrectSequence(ArrayList<Integer> correctSequence) {
        this.correctSequence = correctSequence;
    }

    public ArrayList<OptionModel> getOptionModels() {
        return optionModels;
    }

    public void setOptionModels(ArrayList<OptionModel> optionModels) {
        this.optionModels = optionModels;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.typeOfQuestion);
        dest.writeString(this.programLanguage);
        dest.writeString(this.question);
        dest.writeInt(this.correctOption);
        dest.writeList(this.correctOptions);
        dest.writeList(this.correctSequence);
        dest.writeTypedList(this.optionModels);
        dest.writeString(this.modelId);
        dest.writeString(this.output);
        dest.writeString(this.explanation);
        dest.writeString(this.code);
    }

    protected InterviewQuestionModel(Parcel in) {
        this.typeOfQuestion = in.readInt();
        this.programLanguage = in.readString();
        this.question = in.readString();
        this.correctOption = in.readInt();
        this.correctOptions = new ArrayList<Integer>();
        in.readList(this.correctOptions, Integer.class.getClassLoader());
        this.correctSequence = new ArrayList<Integer>();
        in.readList(this.correctSequence, Integer.class.getClassLoader());
        this.optionModels = in.createTypedArrayList(OptionModel.CREATOR);
        this.modelId = in.readString();
        this.output = in.readString();
        this.explanation = in.readString();
        this.code = in.readString();
    }

    public static final Creator<InterviewQuestionModel> CREATOR = new Creator<InterviewQuestionModel>() {
        @Override
        public InterviewQuestionModel createFromParcel(Parcel source) {
            return new InterviewQuestionModel(source);
        }

        @Override
        public InterviewQuestionModel[] newArray(int size) {
            return new InterviewQuestionModel[size];
        }
    };

    @Override
    public String toString() {
        return "InterviewQuestionModel{" +
                "typeOfQuestion=" + typeOfQuestion +
                ", programLanguage='" + programLanguage + '\'' +
                ", question='" + question + '\'' +
                ", correctOption=" + correctOption +
                ", correctOptions=" + correctOptions +
                ", correctSequence=" + correctSequence +
                ", optionModels=" + optionModels +
                ", modelId='" + modelId + '\'' +
                ", output='" + output + '\'' +
                ", explanation='" + explanation + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
