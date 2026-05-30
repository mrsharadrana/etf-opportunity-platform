package com.sharad.platformapi.domain;

public enum CrashLayer {

    NO_DEPLOYMENT(0, "Very low fear detected. No deployment required."),
    LAYER_1(10, "Low fear detected. Initial 10% deployment recommended."),
    LAYER_2(20, "Moderate fear detected. Deploy 20% to start protection."),
    LAYER_3(20, "Elevated fear detected. Maintain 20% crash-layer protection."),
    LAYER_4(20, "Severe fear detected. Increase protective deployment to 20%."),
    LAYER_5(30, "Extreme fear detected. Deploy maximum 30% protective layer.");

    private final int deployPercent;
    private final String explanation;

    CrashLayer(int deployPercent, String explanation) {
        this.deployPercent = deployPercent;
        this.explanation = explanation;
    }

    public int getDeployPercent() {
        return deployPercent;
    }

    public String getExplanation() {
        return explanation;
    }
}
