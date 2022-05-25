package com.example.callblocker.Models;

import java.util.List;

public class CallLogsGrouped {
    long date;
    List<CallLogs> callLogsList;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<CallLogs> getCallLogsList() {
        return callLogsList;
    }

    public void setCallLogsList(List<CallLogs> callLogsList) {
        this.callLogsList = callLogsList;
    }
}
