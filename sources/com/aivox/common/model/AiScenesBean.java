package com.aivox.common.model;

/* JADX INFO: loaded from: classes.dex */
public class AiScenesBean {
    private int scenesBgRes;
    private int scenesIconRes;
    private int scenesNameRes;
    private int scenesType;
    private boolean selected;

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean z) {
        this.selected = z;
    }

    public int getScenesType() {
        return this.scenesType;
    }

    public void setScenesType(int i) {
        this.scenesType = i;
    }

    public int getScenesNameRes() {
        return this.scenesNameRes;
    }

    public void setScenesNameRes(int i) {
        this.scenesNameRes = i;
    }

    public int getScenesIconRes() {
        return this.scenesIconRes;
    }

    public void setScenesIconRes(int i) {
        this.scenesIconRes = i;
    }

    public int getScenesBgRes() {
        return this.scenesBgRes;
    }

    public void setScenesBgRes(int i) {
        this.scenesBgRes = i;
    }
}
