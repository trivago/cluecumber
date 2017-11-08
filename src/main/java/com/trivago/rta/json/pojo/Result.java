package com.trivago.rta.json.pojo;

import com.google.gson.annotations.SerializedName;

import java.time.Duration;

public class Result {
    private static final int MICROSECONDS_FACTOR = 1000000;

    private long duration;
    private String status;

    @SerializedName("error_message")
    private String errorMessage;

    private transient String durationString;

    public long getDuration() {
        return duration;
    }

    public void setDuration(final long duration) {
        this.duration = duration;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public long getDurationInMilliseconds() {
        return Duration.ofMillis(duration / MICROSECONDS_FACTOR).toMillis();
    }

    public String getDurationString() {
        Duration durationMilliseconds = Duration.ofMillis(duration / MICROSECONDS_FACTOR);
        long minutes = durationMilliseconds.toMinutes();
        long seconds = durationMilliseconds.minusMinutes(minutes).getSeconds();
        long milliseconds = durationMilliseconds.minusMinutes(minutes).minusSeconds(seconds).toMillis();
        return String.format("%02dm %02ds %03d", minutes, seconds, milliseconds);
    }

    @Override
    public String toString() {
        return "Result{" +
                "duration=" + duration +
                ", status='" + status + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", durationString='" + durationString + '\'' +
                '}';
    }
}
