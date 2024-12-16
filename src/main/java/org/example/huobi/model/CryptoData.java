package org.example.huobi.model;

import java.util.List;

public record CryptoData(
        String tags,
        String state,
        String wr,
        List<Participant> p1,
        int sm,
        String sc,
        int fp,
        int tpp,
        int tap,
        int ttp,
        Long lr,
        Long flr,
        int mbph,
        int mspl,
        String sp,
        String d,
        String bcdn,
        String qcdn,
        Long elr,
        boolean whe,
        boolean cd,
        boolean te,
        List<Tag> p,
        long toa,
        String qc,
        String scr,
        String bc,
        Long smlr,
        String si,
        long w,
        String dn,
        String suspend_desc
) {}

