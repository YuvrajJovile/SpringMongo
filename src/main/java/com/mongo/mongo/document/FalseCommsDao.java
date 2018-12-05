package com.mongo.mongo.document;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Document
public class FalseCommsDao implements Serializable {

    @Id
    private Integer id;

    private Integer count;
    private String utteranceType;
    private String utteranceId;
    private String asr;
    private String translation;
    private Integer falseWake = -1;
    private Integer falseComms = -1;
    private String comment;
    private String bucket;
    private String userId;


    public FalseCommsDao() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUtteranceId() {
        return utteranceId;
    }

    public String getUtteranceType() {
        return utteranceType;
    }

    public void setUtteranceType(String utteranceType) {
        this.utteranceType = utteranceType;
    }

    public void setUtteranceId(String utteranceId) {
        this.utteranceId = utteranceId;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public int isFalseWake() {
        return falseWake;
    }

    public Integer getFalseWake() {
        return falseWake;
    }

    public void setFalseWake(Integer falseWake) {
        this.falseWake = falseWake;
    }

    public Integer getFalseComms() {
        return falseComms;
    }

    public void setFalseComms(Integer falseComms) {
        this.falseComms = falseComms;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
