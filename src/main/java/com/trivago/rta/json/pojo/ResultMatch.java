package com.trivago.rta.json.pojo;

class ResultMatch {
    private Result result;
    private Match match;

    public Result getResult() {
        return result;
    }

    public void setResult(final Result result) {
        this.result = result;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(final Match match) {
        this.match = match;
    }

    @Override
    public String toString() {
        return "ResultMatch{" +
                "result=" + result +
                ", match=" + match +
                '}';
    }
}
